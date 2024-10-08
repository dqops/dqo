import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { ChangeEvent } from 'react';
import { useSelector } from 'react-redux';
import { CommentSpec } from '../../../api';
import { IRootState } from '../../../redux/reducers';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import CommentItem from './CommentItem';

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
      <div className={clsx('w-full', className)}>
        <div className="flex items-center font-bold">
          <div className="w-9/12">Comment</div>
          <div className="w-1/12">Author</div>
          <div className="w-1/12">Date</div>
          <div className="px-0 pr-8 text-center max-w-34 min-w-34 w-34">
            Action
          </div>
        </div>
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
      </div>
      <div className="flex items-center w-full">
        <div className="min-w-40 py-2 w-11/12">
          <Input
            className="h-10 focus:!ring-0 focus:!border"
            value={text}
            onChange={onChangeText}
          />
        </div>
        <div className="px-0 pr-8 max-w-34 min-w-34 py-2">
          <div className="flex justify-center">
            <IconButton
              ripple={false}
              size="sm"
              className="bg-teal-500 shadow-none hover:shadow-none hover:bg-[#028770]"
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

export default CommentsView;
