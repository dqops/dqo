import React, { useEffect, useState } from 'react';
import { CommentSpec } from '../../../api';
import { AxiosResponse } from 'axios';
import { ConnectionApiClient } from '../../../services/apiClient';
import CommentsView from '../CommentsView';

interface ICommentsTabProps {
  connectionName: string;
}

const CommentsTab = ({ connectionName }: ICommentsTabProps) => {
  const [comments, setComments] = useState<CommentSpec[]>([]);

  const fetchComments = async () => {
    try {
      const res: AxiosResponse<CommentSpec[]> =
        await ConnectionApiClient.getConnectionComments(connectionName);

      setComments(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchComments().then();
  }, [connectionName]);

  return <CommentsView comments={comments} onChange={setComments} />;
};

export default CommentsTab;
