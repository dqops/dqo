import React, { useEffect } from 'react';
import { SourceSchemasApi } from '../../services/apiClient';
import Button from '../Button';

interface IImportSchemasProps {
  connectionName: string;
  onPrev: () => void;
  onNext: () => void;
}

const ImportSchemas = ({
  connectionName,
  onPrev,
  onNext
}: IImportSchemasProps) => {
  useEffect(() => {
    console.log('connectionName');
    SourceSchemasApi.getRemoteSchemas(connectionName).then((res) => {
      console.log('------', res);
    });
  }, [connectionName]);
  return (
    <div>
      <div>{connectionName}</div>

      <div className="flex space-x-4 justify-end mt-6">
        <Button
          color="primary"
          variant="outlined"
          label="Prev"
          className="w-40"
          onClick={onPrev}
        />
        <Button
          color="primary"
          variant="contained"
          label="Next"
          className="w-40"
          onClick={onNext}
        />
      </div>
    </div>
  );
};

export default ImportSchemas;
