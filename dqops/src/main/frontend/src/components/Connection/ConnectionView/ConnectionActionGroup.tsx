import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setActiveFirstLevelUrl } from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { ConnectionApiClient } from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Button from '../../Button';
import AddSchemaDialog from '../../CustomTree/AddSchemaDialog';
import ConfirmDialog from './ConfirmDialog';

interface IConnectionActionGroupProps {
  isDisabled?: boolean;
  onUpdate?: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
}

const ConnectionActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate
}: IConnectionActionGroupProps) => {
  const {
    connection: connectionName,
    checkTypes,
    tab
  }: { connection: any; checkTypes: any; tab: any } = useDecodedParams();
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const [isOpen, setIsOpen] = useState(false);
  const history = useHistory();
  const [addSchemaDialogOpen, setAddSchemaDialogOpen] = useState(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const removeConnection = async () => {
    await ConnectionApiClient.deleteConnection(connectionName ?? '');
  };
  const importMetaData = () => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.CONNECTION_DETAIL(
          checkTypes,
          connectionName || '',
          'schemas?import_schema=true'
        )
      )
    );
    history.push(
      `${ROUTES.CONNECTION_DETAIL(
        checkTypes,
        connectionName || '',
        'schemas?import_schema=true'
      )}`
    );
  };
  console.log(tab);

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {isSourceScreen && (
        <>
          <Button
            className="!h-10"
            variant={
              !(userProfile.can_manage_data_sources !== true)
                ? 'outlined'
                : 'contained'
            }
            color={
              !(userProfile.can_manage_data_sources !== true)
                ? 'primary'
                : 'secondary'
            }
            label="Add Schema"
            onClick={() => setAddSchemaDialogOpen(true)}
            disabled={userProfile.can_manage_data_sources !== true}
          />
          <Button
            className="!h-10"
            variant={
              !(userProfile.can_manage_data_sources !== true)
                ? 'outlined'
                : 'contained'
            }
            color={
              !(userProfile.can_manage_data_sources !== true)
                ? 'primary'
                : 'secondary'
            }
            label="Delete Connection"
            onClick={() => setIsOpen(true)}
            disabled={userProfile.can_manage_data_sources !== true}
          />

          {tab === 'schemas' ? (
            <Button
              className="!h-10"
              label="Manage metadata"
              color={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'primary'
                  : 'secondary'
              }
              variant={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'outlined'
                  : 'contained'
              }
              onClick={importMetaData}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          ) : (
            <Button
              className="!h-10"
              label="Import metadata"
              color={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'primary'
                  : 'secondary'
              }
              variant={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'outlined'
                  : 'contained'
              }
              onClick={() => importMetaData()}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          )}
        </>
      )}

      {onUpdate && (
        <Button
          color={
            isUpdated &&
            !isDisabled &&
            !(userProfile.can_manage_data_sources !== true)
              ? 'primary'
              : 'secondary'
          }
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
