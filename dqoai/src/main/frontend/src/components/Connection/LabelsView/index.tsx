import React, { KeyboardEvent } from 'react';
import LabelItem from './LabelItem';
import Input from "../../Input";
import { IconButton } from "@material-tailwind/react";
import SvgIcon from "../../SvgIcon";

interface ILabelsViewProps {
  labels: string[];
  onChange: (labels: string[]) => void;
  hasAdd?: boolean;
}

const LabelsView = ({ labels = [], onChange, hasAdd }: ILabelsViewProps) => {
  const onChangeLabel = (key: number, value: string) => {
    onChange(labels.map((label, index) => (key === index ? value : label)));
  };

  const onRemoveLabel = (key: number) => {
    onChange(labels.filter((item, index) => index !== key));
  };

  const onKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      onChange([...labels, '']);
    }
  }

  const onAdd = () => {
    onChange([...labels, '']);
  };

  const onChangeText = (value: string) => {
    if (!labels.length) {
      onChange([value]);
    } else {
      onChange(labels.map((label, index) => index < labels.length - 1 ? label : value));
    }
  }

  return (
    <div className="p-4">
      <table className="w-full">
        <thead>
          <th className="text-left min-w-40 w-full pr-4 py-2">Label</th>
          <th className="px-8 min-w-34 max-w-34 py-2">Action</th>
        </thead>
        <tbody>
          {labels.slice(0, labels.length - 1).map((label, index) => (
            <LabelItem
              label={label}
              key={index}
              idx={index}
              onChange={onChangeLabel}
              onRemove={onRemoveLabel}
            />
          ))}
          <tr>
            <td className="pr-4 min-w-40 py-2">
              <Input
                className="focus:!ring-0 focus:!border"
                value={labels.length ? labels[labels.length - 1] : ""}
                onChange={(e) => onChangeText(e.target.value)}
                onKeyDown={onKeyDown}
              />
            </td>
            {hasAdd && (
              <td className="px-8 max-w-34 min-w-34 py-2">
                <div className="flex justify-center">
                  <IconButton
                    size="sm"
                    className="bg-teal-500"
                    onClick={onAdd}
                  >
                    <SvgIcon name="add" className="w-4" />
                  </IconButton>
                </div>
              </td>
            )}
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default LabelsView;
