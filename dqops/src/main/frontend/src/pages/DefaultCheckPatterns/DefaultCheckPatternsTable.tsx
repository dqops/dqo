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
    | keyof Pick<
        DefaultTableChecksPatternListModel,
        'pattern_name' | 'priority' | 'can_edit' | 'yaml_parsing_error'
      >
    | undefined;
};

const headerElement: THeaderElement[] = [
  { label: 'Pattern name', key: 'pattern_name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: undefined },
  { label: 'Schema', key: undefined },
  { label: 'Table', key: undefined }
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
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';
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
                (onChange(sortPatterns(patterns, elem.key, dir)),
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
        {patterns.map((pattern, index) => (
          <tr key={index}>
            <td className="px-4">{pattern.pattern_name}</td>
            <td className="px-4">{pattern.priority}</td>
            <td className="px-4">
              {(pattern?.[targetSpecKey as keyof TPattern] as any)?.connection}
            </td>
            <td className="px-4">
              {(pattern?.[targetSpecKey as keyof TPattern] as any)?.schema}
            </td>
            <td className="px-4">
              {(pattern?.[targetSpecKey as keyof TPattern] as any)?.table}
            </td>
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
                onClick={() => deletePattern(pattern.pattern_name ?? '')}
              />
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
