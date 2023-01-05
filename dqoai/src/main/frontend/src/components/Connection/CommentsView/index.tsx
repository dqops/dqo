import React, { ChangeEvent, useState } from 'react';
import CommentItem from './CommentItem';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import { CommentSpec } from '../../../api';
import clsx from 'clsx';

interface ICommentsViewProps {
  comments: CommentSpec[];
  onChange: (comments: CommentSpec[]) => void;
  className?: string;
  isUpdated?: boolean;
  setIsUpdated?: (value: boolean) => void;
  text: string;
  setText: (value: string) => void;
}

const CommentsView = ({
  comments,
  onChange,
  className,
  isUpdated,
  setIsUpdated,
  text,
  setText
}: ICommentsViewProps) => {

  const onAdd = () => {
    onChange([
      ...comments,
      {
        comment: text,
        comment_by: 'user',
        date: new Date().toISOString()
      }
    ]);
    setText('');
  };

  const onChangeComment = (key: number, value?: string) => {
    onChange(
      comments.map((comment, index) =>
        key === index
          ? {
              ...comment,
              comment: value
            }
          : comment
      )
    );
  };

  const onRemoveComment = (key: number) => {
    onChange(comments.filter((item, index) => index !== key));
  };

  const onChangeText = (e: ChangeEvent<HTMLInputElement>) => {
    setText(e.target.value);
    if (!isUpdated && setIsUpdated) {
      setIsUpdated(true);
    }
  };

  return (
    <div className="p-4">
      <table className={clsx('my-6 w-full', className)}>
        <thead>
          <th className="text-left w-full pr-4 py-2">Comment</th>
          <th className="text-left px-8 py-2">Author</th>
          <th className="text-left px-8 py-2">Date</th>
          <th className="px-8 py-2">Action</th>
        </thead>
        <tbody>
          {comments &&
            comments.map((comment, index) => (
              <CommentItem
                comment={comment}
                key={index}
                idx={index}
                onChange={onChangeComment}
                onRemove={onRemoveComment}
              />
            ))}
        </tbody>
      </table>
      <div className="flex items-center space-x-4">
        <div className="flex-1">
          <Input className="h-10" value={text} onChange={onChangeText} />
        </div>
        <IconButton className="w-10 h-10" onClick={onAdd}>
          <SvgIcon name="add" className="w-6" />
        </IconButton>
      </div>
    </div>
  );
};

export default CommentsView;
