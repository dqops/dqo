import React, { useState } from 'react';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel
} from '../../api';
import Button from '../../components/Button';
import { sortPatterns } from '../../utils';
import SvgIcon from '../../components/SvgIcon';
import { useDefinition } from '../../contexts/definitionContext';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';

type TPattern =
  | DefaultTableChecksPatternListModel
  | DefaultColumnChecksPatternListModel;

type TDefaultCheckPatternsTableProps = {
  patterns: TPattern[];
  deletePattern: (patternName: string) => void;
  onChange: (data: any) => void;
};

type THeaderElement = {
  label: string;
  key:
    | 'pattern_name'
    | 'priority'
    | 'can_edit'
    | 'yaml_parsing_error'
    | 'connection'
    | 'schema'
    | 'table';
};

const headerElement: THeaderElement[] = [
  { label: 'Pattern name', key: 'pattern_name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' }
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
  const [dir, setDir] = useState<'asc' | 'desc'>('asc');
  const [patternDelete, setPatternDelete] = useState('');
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';

  const getPreparedPatterns = () => {
    const arr: any[] = [];

    patterns.forEach((x) => {
      const targetSpec: any = x[targetSpecKey as keyof TPattern];
      if (
        targetSpec &&
        typeof targetSpec === 'object' &&
        Object.keys(targetSpec).length !== 0
      ) {
        arr.push({ ...x, ...targetSpec });
      } else {
        arr.push(x);
      }
    });

    return arr;
  };

  return (
    <table>
      <thead>
        <tr>
          {headerElement.map((elem) => (
            <th
              className="px-4"
              key={elem.label}
              onClick={() =>
                elem.key &&
                (onChange(
                  sortPatterns(
                    getPreparedPatterns(),
                    elem.key as keyof TPattern,
                    dir
                  )
                ),
                setDir(dir === 'asc' ? 'desc' : 'asc'))
              }
            >
              <div className="flex gap-x-1 items-center cursor-default">
                <div>{elem.label}</div>
                {elem.key && (
                  <div>
                    <SvgIcon name="chevron-up" className="w-2 h-2" />
                    <SvgIcon name="chevron-down" className="w-2 h-2" />
                  </div>
                )}
              </div>
            </th>
          ))}
        </tr>
      </thead>
      <tbody className=" border-t border-gray-100">
        {getPreparedPatterns().map((pattern, index) => (
          <tr key={index}>
            <td className="px-4">{pattern.pattern_name}</td>
            <td className="px-4">{pattern.priority}</td>
            <td className="px-4">{pattern?.connection}</td>
            <td className="px-4">{pattern?.schema}</td>
            <td className="px-4">{pattern?.table}</td>
            <td className="px-4">
              <Button
                variant="text"
                label="edit"
                color="primary"
                onClick={() => editPattern(type, pattern.pattern_name ?? '')}
              />
            </td>
            <td className="px-4">
              <Button
                variant="text"
                label="delete"
                color="primary"
                onClick={() => setPatternDelete(pattern.pattern_name ?? '')}
              />
            </td>
          </tr>
        ))}
        <ConfirmDialog
          open={patternDelete.length > 0}
          onConfirm={async () => {
            deletePattern(patternDelete ?? ''), setPatternDelete('');
          }}
          onClose={() => setPatternDelete('')}
          message={`Are you sure you want to delete the ${patternDelete} pattern ?`}
        />
      </tbody>
    </table>
  );
}
