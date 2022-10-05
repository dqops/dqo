import React, { useEffect, useState } from 'react';
import { CommentSpec } from '../../../api';
import { AxiosResponse } from 'axios';
import { ColumnApiClient } from '../../../services/apiClient';
import CommentsView from '../CommentsView';

interface ICommentsTabProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const CommentsTab = ({
  connectionName,
  schemaName,
  tableName,
  columnName,
}: ICommentsTabProps) => {
  const [comments, setComments] = useState<CommentSpec[]>([]);

  const fetchComments = async () => {
    try {
      const res: AxiosResponse<CommentSpec[]> =
        await ColumnApiClient.getColumnComments(
          connectionName,
          schemaName,
          tableName,
          columnName
        );

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
