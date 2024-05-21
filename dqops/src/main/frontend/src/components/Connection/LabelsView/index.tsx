import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { KeyboardEvent } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import LabelItem from './LabelItem';

interface ILabelsViewProps {
  labels: string[];
  onChange: (labels: string[]) => void;
  hasAdd?: boolean;
  title?: string;
  titleClassName?: string;
}

const LabelsView = ({
  labels = [],
  onChange,
  hasAdd,
  title,
  titleClassName
}: ILabelsViewProps) => {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

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
  };

  const onAdd = () => {
    onChange([...labels, '']);
  };

  const onChangeText = (value: string) => {
    if (!labels.length) {
      onChange([value]);
    } else {
      onChange(
        labels.map((label, index) =>
          index < labels.length - 1 ? label : value
        )
      );
    }
  };

  return (
    <div className="p-4 text-sm w-full max-w-200">
      <div className={clsx('flex items-center font-bold', titleClassName)}>
        <div className="text-left min-w-40 w-11/12 pr-4">
          {title ?? 'Label'}
        </div>
        <div className="px-0 pr-8 text-center max-w-34 min-w-34 w-34">
          Action
        </div>
      </div>
      {labels.slice(0, labels.length - 1).map((label, index) => (
        <LabelItem
          label={label}
          key={index}
          idx={index}
          onChange={onChangeLabel}
          onRemove={onRemoveLabel}
          canUserEditLabel={userProfile.can_edit_labels}
        />
      ))}
      <div className="flex items-center w-full">
        <div className="min-w-40 py-2 w-11/12">
          <Input
            className="focus:!ring-0 focus:!border"
            value={labels.length ? labels[labels.length - 1] : ''}
            onChange={(e) => onChangeText(e.target.value)}
            onKeyDown={onKeyDown}
          />
        </div>
        <div className="px-0 pr-8 max-w-34 min-w-34 py-2">
          <div className="flex justify-center">
            <IconButton
              size="sm"
              className="bg-teal-500"
              onClick={onAdd}
              disabled={userProfile.can_edit_labels === false}
            >
              <SvgIcon name="add" className="w-4" />
            </IconButton>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LabelsView;
