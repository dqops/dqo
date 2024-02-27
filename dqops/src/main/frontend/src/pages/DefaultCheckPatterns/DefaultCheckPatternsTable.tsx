import React from 'react';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel
} from '../../api';
import Button from '../../components/Button';

type TDefaultCheckPatternsTableProps = {
  patterns:
    | DefaultTableChecksPatternListModel[]
    | DefaultColumnChecksPatternListModel[];
};
export default function DefaultCheckPatternsTable({
  patterns
}: TDefaultCheckPatternsTableProps) {
  return (
    <table>
      <thead>
        <tr>
          <th className="px-4">Pattern name</th>
          <th className="px-4">Priority</th>
          <th className="px-4">Connection</th>
          <th className="px-4">Schema</th>
          <th className="px-4">Table</th>
          <th className="px-4"></th>
          <th className="px-4"></th>
          <th className="px-4"></th>
        </tr>
      </thead>
      <tbody className=" border-t border-gray-200">
        {patterns.map((pattern, index) => (
          <tr key={index}>
            <td className="px-4">{pattern.pattern_name}</td>
            <td className="px-4">{pattern.priority}</td>
            <td className="px-4"></td>
            <td className="px-4"></td>
            <td className="px-4"></td>
            <td className="px-4">
              <Button variant="text" label="edit" />
            </td>
            <td className="px-4">
              <Button variant="text" label="edit" />
            </td>
            <td className="px-4">
              <Button variant="text" label="edit" />
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
