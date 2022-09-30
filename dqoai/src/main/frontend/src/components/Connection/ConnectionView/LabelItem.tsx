import React, { useState } from 'react';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';

interface ILabelItemProps {
  idx: number;
  label: string;
  onChange: (key: number, value: string) => void;
  onRemove: (key: number) => void;
}

const LabelItem = ({ idx, label, onChange, onRemove }: ILabelItemProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [text, setText] = useState(label);

  const onEdit = () => setIsEditing(true);

  const onSave = () => {
    setIsEditing(false);
    onChange(idx, text);
  };

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2">
        {isEditing ? (
          <Input value={text} onChange={(e) => setText(e.target.value)} />
        ) : (
          label
        )}
      </td>
      <td className="px-8 min-w-40 py-2">
        <div className="flex space-x-2 items-center justify-center">
          <IconButton size="sm" onClick={isEditing ? onSave : onEdit}>
            <SvgIcon name={isEditing ? 'save' : 'edit'} className="w-4" />
          </IconButton>
          <IconButton size="sm" onClick={() => onRemove(idx)}>
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        </div>
      </td>
    </tr>
  );
};

export default LabelItem;
