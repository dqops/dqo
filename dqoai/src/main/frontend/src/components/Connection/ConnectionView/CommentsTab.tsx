import React, { useEffect, useState } from 'react';
import { CommentSpec } from '../../../api';
import { AxiosResponse } from 'axios';
import { ConnectionApiClient } from '../../../services/apiClient';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
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
    <tr>
      <td className="pr-4 min-w-40 py-2">
        {isEditing ? (
          <Input value={text} onChange={(e) => setText(e.target.value)} />
        ) : (
          comment.comment
        )}
      </td>
      <td className="px-8 min-w-60 py-2">{comment.comment_by}</td>
      <td className="px-8 min-w-60 py-2">{comment.date}</td>
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

interface ICommentsTabProps {
  connectionName: string;
}

const CommentsTab = ({ connectionName }: ICommentsTabProps) => {
  const [comments, setComments] = useState<CommentSpec[]>([]);
  const [text, setText] = useState('');

  const fetchCommentsTab = async () => {
    try {
      const res: AxiosResponse<CommentSpec[]> =
        await ConnectionApiClient.getConnectionComments(connectionName);

      setComments(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchCommentsTab().then();
  }, [connectionName]);

  const onAdd = () => {
    setComments([
      ...comments,
      {
        comment: text,
        comment_by: 'user',
        date: new Date().toDateString()
      }
    ]);
    setText('');
  };

  const onChangeComment = (key: number, value?: string) => {
    setComments(
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
    setComments(comments.filter((item, index) => index !== key));
  };

  return (
    <div className="p-4">
      <table className="my-6 w-full">
        <thead>
          <th className="text-left min-w-40 w-full pr-4 py-2">Comment</th>
          <th className="text-left min-w-60 px-8 py-2">Author</th>
          <th className="text-left min-w-60 px-8 py-2">Date</th>
          <th className="px-8 min-w-40 py-2">Action</th>
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
          <Input
            className="h-10"
            value={text}
            onChange={(e) => setText(e.target.value)}
          />
        </div>
        <IconButton className="w-10 h-10" onClick={onAdd}>
          <SvgIcon name="add" className="w-6" />
        </IconButton>
      </div>
    </div>
  );
};

export default CommentsTab;
