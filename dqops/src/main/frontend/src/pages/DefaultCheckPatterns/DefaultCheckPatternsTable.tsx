import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel
} from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import SvgIcon from '../../components/SvgIcon';
import { useDefinition } from '../../contexts/definitionContext';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { sortPatterns } from '../../utils';

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
    | 'table'
    | 'column';
};

const headerElementTablePatterns: THeaderElement[] = [
  { label: 'Pattern name', key: 'pattern_name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' }
];

const headerElementColumnPatterns: THeaderElement[] = [
  { label: 'Pattern name', key: 'pattern_name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' },
  { label: 'Column', key: 'column' }
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
  const [indexSortingElement, setIndexSortingElement] = useState(1)
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';
  const headerElement = type === 'column' ? headerElementColumnPatterns : headerElementTablePatterns;


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

  const sortPreparedPattern = (elem: THeaderElement, index: number, dir: "asc" | "desc") => {
    (onChange(
      sortPatterns(
        getPreparedPatterns(),
        elem.key as keyof TPattern,
        dir
      )
    ),
    setDir(dir),
    setIndexSortingElement(index)
    )
  }

  return (
    <table>
      <thead>
        <tr>
          {headerElement.map((elem, index) => (
            <th
              className="px-4"
              key={elem.label}
            >
              <div className="flex gap-x-1 items-center cursor-default">
                <div>{elem.label}</div>
                <div>
                  {!(indexSortingElement === index && dir === 'asc') ?
                  <SvgIcon name="chevron-up" className="w-2 h-2 text-black" onClick={() => sortPreparedPattern(elem, index, 'asc')}/>
                   : <div className='w-2 h-2'/>}
                  {!(indexSortingElement === index && dir === 'desc') ?
                  <SvgIcon name="chevron-down" className="w-2 h-2 text-black" onClick={() => sortPreparedPattern(elem, index, 'desc')}/>
                  : <div className='w-2 h-2'/>}
                </div>
              </div>
            </th>
          ))}
        </tr>
      </thead>
      <tbody className=" border-t border-gray-100">
        {getPreparedPatterns().map((pattern, index) => (
          <tr key={index} className='text-sm'>
            <td className="px-4 text-teal-500" onClick={() => editPattern(type, pattern.pattern_name ?? '')}>{pattern.pattern_name}</td>
            <td className="px-4">{pattern.priority}</td>
            <td className="px-4">{pattern?.connection}</td>
            <td className="px-4">{pattern?.schema}</td>
            <td className="px-4">{pattern?.table}</td>
            {type === 'column' && 
            <td className="px-4">{pattern?.column}</td>
            }
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
