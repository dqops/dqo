import React from 'react';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel
} from '../../api';
import Button from '../../components/Button';
import { sortPatterns } from '../../utils';
import SvgIcon from '../../components/SvgIcon';

type TDefaultCheckPatternsTableProps = {
  patterns: (
    | DefaultTableChecksPatternListModel
    | DefaultColumnChecksPatternListModel
  )[];
  deletePattern: (patternName: string) => void;
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
  deletePattern
}: TDefaultCheckPatternsTableProps) {
  const editPattern = () => {};

  return (
    <table>
      <thead>
        <tr>
          {headerElement.map((elem) => (
            <th
              className="px-4"
              key={elem.label}
              onClick={() => elem.key && sortPatterns(patterns, elem.key)}
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
            <td className="px-4"></td>
            <td className="px-4"></td>
            <td className="px-4"></td>
            <td className="px-4">
              <Button
                variant="text"
                label="edit"
                color="primary"
                onClick={editPattern}
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
            {/* <td className="px-4">
              <Button
                variant="text"
                label="download"
                color="primary"
                onClick={downloadPattern}
              />
              <a
                href={`/api/dictionaries/${pattern.pattern_name}/download`}
                rel="noreferrer"
                target="_blank"
                className="text-teal-500"
              >
                download
              </a>
            </td> */}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
