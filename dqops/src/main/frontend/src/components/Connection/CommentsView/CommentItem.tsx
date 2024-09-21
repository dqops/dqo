import { IconButton } from '@material-tailwind/react';
import moment from 'moment';
import React, { useState } from 'react';
import { CommentSpec } from '../../../api';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';

interface ICommentItemProps {
  idx: number;
  comment: CommentSpec;
  onChange: (key: number, value?: string) => void;
  onRemove: (key: number) => void;
}

const CommentItem = ({
  idx,
  comment,
  onChange,
  onRemove
}: ICommentItemProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [text, setText] = useState(comment.comment);

  const onEdit = () => setIsEditing(true);

  const onSave = () => {
    setIsEditing(false);
    onChange(idx, text);
  };

  return (
    <div className="w-full flex items-center">
      <div className="pr-4 min-w-40 py-2 w-9/12 whitespace-normal">
        {isEditing ? (
          <Input
            className="focus:!ring-0 focus:!border"
            value={text}
            onChange={(e) => setText(e.target.value)}
          />
        ) : (
          comment.comment
        )}
      </div>
      <div className=" w-1/12 py-2">{comment.comment_by}</div>
      <div className=" w-1/12 py-2">
        {moment(comment.date).format('MMM, DD YYYY')}
      </div>
      <div className="px-8 min-w-34 max-w-34 py-2">
        <div className="flex space-x-2 items-center justify-end">
          <IconButton
            ripple={false}
            size="sm"
            onClick={isEditing ? onSave : onEdit}
            color="teal"
            className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
          >
            <SvgIcon name={isEditing ? 'save' : 'edit'} className="w-4" />
          </IconButton>
          <IconButton
            ripple={false}
            size="sm"
            onClick={() => onRemove(idx)}
            color="teal"
            className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        </div>
      </div>
    </div>
  );
};

export default CommentItem;
