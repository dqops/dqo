import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { ConnectionApiClient } from '../../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../../shared/routes";

interface IConnectionActionGroupProps {
  isDisabled?: boolean;
  onUpdate?: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  onImport?: () => void;
}

const ConnectionActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  onImport
}: IConnectionActionGroupProps) => {
  const { connection: connectionName, checkTypes, tab }: { connection: any; checkTypes: any; tab: any } = useParams();
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const [isOpen, setIsOpen] = useState(false);
  const { connectionBasic } = useSelector(
    (state: IRootState) => state.connection
  );
  const history = useHistory();

  const removeConnection = async () => {
    if (connectionBasic) {
      await ConnectionApiClient.deleteConnection(
        connectionBasic.connection_name ?? ''
      );
    }
  };
  const goToSchemas = (isImport = true) => {
    history.push(`${ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connectionName, 'schemas')}${isImport ? '?import_schema=true' : ''}`)

    if (onImport) {
      onImport();
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {isSourceScreen ? (
        <>
          <Button
            variant="text"
            color="info"
            label="Delete Connection"
            onClick={() => setIsOpen(true)}
          />
          <Button
            label="Import metadata"
            color="info"
            variant="text"
            onClick={() => goToSchemas()}
          />
        </>
      ) : (
        tab === 'schemas' ? (
          <Button
            label="Manage metadata"
            color="info"
            variant="text"
            onClick={() => goToSchemas(false)}
          />
        ) : null
      )}

      {onUpdate && (
        <Button
          color={isUpdated && !isDisabled ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
          disabled={isDisabled}
        />
      )}
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        connection={connectionBasic}
        onConfirm={removeConnection}
      />
    </div>
  );
};

export default ConnectionActionGroup;
