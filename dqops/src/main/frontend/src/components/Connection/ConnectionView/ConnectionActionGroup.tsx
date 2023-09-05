import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { ConnectionApiClient } from '../../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../../shared/routes";
import AddSchemaDialog from "../../CustomTree/AddSchemaDialog";
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

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
  const history = useHistory();
  const [addSchemaDialogOpen, setAddSchemaDialogOpen] = useState(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});


  const removeConnection = async () => {
    await ConnectionApiClient.deleteConnection(
      connectionName ?? ''
    );
  };
  const goToSchemas = (isImport = true) => {
    history.push(`${ROUTES.CONNECTION_DETAIL(checkTypes, connectionName, 'schemas')}${isImport ? '?import_schema=true' : ''}`)

    if (onImport) {
      onImport();
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {isSourceScreen ? (
        <>
          <Button
            className="!h-10"
            variant={!(userProfile.can_manage_data_sources !== true) ? "outlined" : "contained"}
            color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
            label="Add Schema"
            onClick={() => setAddSchemaDialogOpen(true)}
            disabled={userProfile.can_manage_data_sources !== true}
          />
          <Button
            className="!h-10"
            variant={!(userProfile.can_manage_data_sources !== true) ? "outlined" : "contained"}
            color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
            label="Delete Connection"
            onClick={() => setIsOpen(true)}
            disabled={userProfile.can_manage_data_sources !== true}
          />
          <Button
            className="!h-10"
            label="Import metadata"
            color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
            variant={!(userProfile.can_manage_data_sources !== true) ? "outlined" : "contained"}
            onClick={() => goToSchemas()}
            disabled={userProfile.can_manage_data_sources !== true}
          />
        </>
      ) : (
        tab === 'schemas' ? (
          <Button
            className="!h-10"
            label="Manage metadata"
            color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
            variant={!(userProfile.can_manage_data_sources !== true) ? "outlined" : "contained"}
            onClick={() => goToSchemas()}
            disabled={userProfile.can_manage_data_sources !== true}
          />
        ) : null
      )}

      {onUpdate && (
        <Button
          color={isUpdated && !isDisabled && !(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40 !h-10"
          onClick={onUpdate}
          loading={isUpdating}
          disabled={isDisabled || userProfile.can_manage_data_sources !== true}
          
        />
      )}
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        connection={connectionName}
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
