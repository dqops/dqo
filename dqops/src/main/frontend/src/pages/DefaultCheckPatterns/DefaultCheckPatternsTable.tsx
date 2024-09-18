import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ColumnQualityPolicyListModel,
  TableQualityPolicyListModel
} from '../../api';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import ClientSidePagination from '../../components/Pagination/ClientSidePagination'; // Import pagination component
import SvgIcon from '../../components/SvgIcon';
import Switch from '../../components/Switch';
import { useDefinition } from '../../contexts/definitionContext';
import { getFirstLevelSensorState } from '../../redux/selectors';
import {
  ColumnQualityPoliciesApiClient,
  TableQualityPoliciesApiClient
} from '../../services/apiClient';
import { sortPatterns } from '../../utils';

type TPattern = TableQualityPolicyListModel | ColumnQualityPolicyListModel;

type TDefaultCheckPatternsTableProps = {
  patterns: TPattern[];
  deletePattern: (patternName: string) => void;
  onChange: (data: any) => void;
};

type THeaderElement = {
  label: string;
  key:
    | 'policy_name'
    | 'priority'
    | 'can_edit'
    | 'yaml_parsing_error'
    | 'connection'
    | 'schema'
    | 'table'
    | 'column'
    | 'action';
};

const headerElementTablePatterns: THeaderElement[] = [
  { label: 'Quality policy name', key: 'policy_name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' },
  { label: 'Action', key: 'action' }
];

const headerElementColumnPatterns: THeaderElement[] = [
  { label: 'Quality policy name', key: 'policy_name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' },
  { label: 'Column', key: 'column' },
  { label: 'Action', key: 'action' }
];

export default function DefaultCheckPatternsTable({
  patterns,
  deletePattern,
  onChange
}: TDefaultCheckPatternsTableProps) {
  const { type }: { type: 'table' | 'column' } = useSelector(
    getFirstLevelSensorState
  );
  const { openDefaultCheckPatternFirstLevelTab } = useDefinition();
  const editPattern = (type: string, pattern: string) => {
    openDefaultCheckPatternFirstLevelTab(type, pattern);
  };
  const [dir, setDir] = useState<'asc' | 'desc'>('desc');
  const [patternDelete, setPatternDelete] = useState('');
  const [indexSortingElement, setIndexSortingElement] = useState(1);
  const [displayedPatterns, setDisplayedPatterns] = useState<any[]>([]); // State for displayed patterns

  const headerElement =
    type === 'column'
      ? headerElementColumnPatterns
      : headerElementTablePatterns;

  const sortPreparedPattern = (
    elem: THeaderElement,
    index: number,
    dir: 'asc' | 'desc'
  ) => {
    onChange(sortPatterns(patterns, elem.key as keyof TPattern, dir)),
      setDir(dir === 'asc' ? 'desc' : 'asc'),
      setIndexSortingElement(index);
  };

  const handleDisablePattern = (pattern: TPattern) => {
    const newPatterns = patterns.map((x) => {
      if (x.policy_name === pattern.policy_name) {
        x.disabled = !x.disabled;
      }
      return x;
    });
    if (type === 'table') {
      TableQualityPoliciesApiClient.getTableQualityPolicyTarget(
        pattern.policy_name ?? ''
      ).then((res) => {
        TableQualityPoliciesApiClient.updateTableQualityPolicyTarget(
          res.data.policy_name ?? '',
          {
            ...res.data,
            disabled: !res.data.disabled
          }
        );
      });
    } else {
      ColumnQualityPoliciesApiClient.getColumnQualityPolicyTarget(
        pattern.policy_name ?? ''
      ).then((res) => {
        ColumnQualityPoliciesApiClient.updateColumnQualityPolicyTarget(
          res.data.policy_name ?? '',
          {
            ...res.data,
            disabled: !res.data.disabled
          }
        );
      });
    }
    onChange(newPatterns);
  };

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th></th>
            {headerElement.map((elem, index) => (
              <th className="px-4" key={elem.label}>
                <div
                  className={clsx(
                    'flex gap-x-1 items-center cursor-default',
                    elem.key === 'action' && 'ml-3.5'
                  )}
                >
                  <div>{elem.label}</div>
                  {elem.key !== 'action' && (
                    <div>
                      {!(indexSortingElement === index && dir === 'asc') ? (
                        <SvgIcon
                          name="chevron-up"
                          className="w-2 h-2 text-black"
                          onClick={() =>
                            sortPreparedPattern(elem, index, 'desc')
                          }
                        />
                      ) : (
                        <div className="w-2 h-2" />
                      )}
                      {!(indexSortingElement === index && dir === 'desc') ? (
                        <SvgIcon
                          name="chevron-down"
                          className="w-2 h-2 text-black"
                          onClick={() =>
                            sortPreparedPattern(elem, index, 'asc')
                          }
                        />
                      ) : (
                        <div className="w-2 h-2" />
                      )}
                    </div>
                  )}
                </div>
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="border-t border-gray-100">
          {displayedPatterns.map((pattern, index) => (
            <tr key={index} className="text-sm">
              <td>
                <Switch
                  checked={!pattern.disabled}
                  onChange={() => handleDisablePattern(pattern)}
                />
              </td>
              <td
                className={clsx(
                  'px-4 underline cursor-pointer',
                  pattern.disabled && 'text-gray-200'
                )}
                onClick={() => editPattern(type, pattern.policy_name ?? '')}
              >
                {pattern.policy_name}
              </td>
              <td className="px-4">{pattern.priority}</td>
              <td className="px-4">{pattern?.connection}</td>
              <td className="px-4">{pattern?.schema}</td>
              <td className="px-4">{pattern?.table}</td>
              {type === 'column' && <td className="px-4">{pattern?.column}</td>}
              <td className="px-4">
                <div className="flex items-center gap-x-4">
                  <IconButton
                    size="sm"
                    onClick={() => editPattern(type, pattern.policy_name ?? '')}
                    ripple={false}
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  >
                    <SvgIcon name="edit" className="w-4" />
                  </IconButton>
                  <IconButton
                    size="sm"
                    onClick={() => setPatternDelete(pattern.policy_name ?? '')}
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  >
                    <SvgIcon name="delete" className="w-4" />
                  </IconButton>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <ClientSidePagination
        items={patterns}
        onChangeItems={setDisplayedPatterns}
      />
      <ConfirmDialog
        open={patternDelete.length > 0}
        onConfirm={async () => {
          deletePattern(patternDelete ?? ''), setPatternDelete('');
        }}
        onClose={() => setPatternDelete('')}
        message={`Are you sure you want to delete the ${patternDelete} pattern?`}
      />
    </div>
  );
}
