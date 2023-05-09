import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { ConnectionApiClient } from '../../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../../shared/routes";
import { useTree } from "../../../contexts/treeContext";
import AddSchemaDialog from "../../CustomTree/AddSchemaDialog";

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
  const { sourceRoute } = useTree();
  const [addSchemaDialogOpen, setAddSchemaDialogOpen] = useState(false);

  const removeConnection = async () => {
    if (connectionBasic) {
      await ConnectionApiClient.deleteConnection(
        connectionBasic.connection_name ?? ''
      );
    }
  };
  const goToSchemas = (isImport = true) => {
    history.push(`${ROUTES.CONNECTION_DETAIL(sourceRoute, connectionName, 'schemas')}${isImport ? '?import_schema=true' : ''}`)

    if (onImport) {
      onImport();
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      <Button
        className="!h-10"
        variant="outlined"
        color="primary"
        label="Add Schema"
        onClick={() => setAddSchemaDialogOpen(true)}
      />
      {isSourceScreen ? (
        <>
          <Button
            className="!h-10"
            variant="outlined"
            color="primary"
            label="Delete Connection"
            onClick={() => setIsOpen(true)}
          />
          <Button
            className="!h-10"
            label="Import metadata"
            color="primary"
            variant="outlined"
            onClick={() => goToSchemas()}
          />
        </>
      ) : (
        tab === 'schemas' ? (
          <Button
            className="!h-10"
            label="Manage metadata"
            color="primary"
            variant="outlined"
            onClick={() => goToSchemas()}
          />
        ) : null
      )}

      {onUpdate && (
        <Button
          color={isUpdated && !isDisabled ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40 !h-10"
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
      <AddSchemaDialog
        open={addSchemaDialogOpen}
        onClose={() => setAddSchemaDialogOpen(false)}
      />
    </div>
  );
};

export default ConnectionActionGroup;
