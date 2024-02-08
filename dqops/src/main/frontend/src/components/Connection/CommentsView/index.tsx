import React, { ChangeEvent } from 'react';
import CommentItem from './CommentItem';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import { CommentSpec } from '../../../api';
import clsx from 'clsx';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

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
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

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
    <div className="p-4 text-sm">
      <table className={clsx('w-full', className)}>
        <thead>
          <tr>
            <th className="text-left min-w-40 w-full pr-4 py-2">Comment</th>
            <th className="text-left px-8 py-2">Author</th>
            <th className="text-left px-8 py-2">Date</th>
            <th className="px-8 py-2 text-center max-w-34 min-w-34 w-34">
              Action
            </th>
          </tr>
        </thead>
        <tbody>
          {comments
            ? comments.map((comment, index) => (
                <CommentItem
                  comment={comment}
                  key={index}
                  idx={index}
                  onChange={onChangeComment}
                  onRemove={onRemoveComment}
                />
              ))
            : ''}
          <tr>
            <td className="pr-4 min-w-40 py-2">
              <Input
                className="h-10 focus:!ring-0 focus:!border"
                value={text}
                onChange={onChangeText}
              />
            </td>
            <td></td>
            <td></td>
            <td className="px-8 max-w-34 min-w-34 py-2">
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
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default CommentsView;
