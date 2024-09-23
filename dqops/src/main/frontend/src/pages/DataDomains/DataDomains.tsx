import { IconButton } from '@material-tailwind/react';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { LocalDataDomainModel } from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import Loader from '../../components/Loader';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { DataDomainApiClient } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import DataDomainCreateDialog from './DataDomainCreateDialog';

export default function DataDomains() {
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const [dataDomains, setDataDomains] = React.useState<
    Array<LocalDataDomainModel>
  >([]);
  const [loading, setLoading] = React.useState<boolean>(true);
  const [selectedDataDomainToDelete, setSelectedDataDomainToDelete] =
    React.useState<string>('');
  const [openCreateDialog, setOpenCreateDialog] =
    React.useState<boolean>(false);

  const dispatch = useActionDispatch();
  useEffect(() => {
    setLoading(false);
    DataDomainApiClient.getLocalDataDomains()
      .then((res) => {
        setDataDomains(res.data);
      })
      .finally(() => setLoading(false));
  }, []);

  const deleteDataDomain = async (domain: string) => {
    await DataDomainApiClient.deleteDataDomain(domain)
      .then(() => {
        setLoading(true);
        DataDomainApiClient.getLocalDataDomains()
          .then((res) => {
            setDataDomains(res.data);
          })
          .finally(() => setLoading(false));
      })
      .catch((err) => console.error(err));
  };
  const editDataDomain = (domain: string) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.DATA_DOMAIN_DETAIL(domain),
        value: ROUTES.DATA_DOMAIN_DETAIL(domain),
        label: domain,
        state: { data_domain: domain }
      })
    );
  };

  const addDataDomain = () => {};

  if (loading) {
    return <Loader className="w-6 h-6" isFull={false} />;
  }

  return (
    <div>
      {!userProfile.can_use_data_domains && (
        <div>
          Data domains require an active DQOps Cloud ENTERPRISE license. Please
          contact DQOps sales to activate data domains.
        </div>
      )}
      <table className="w-full ">
        <thead className="border-b w-full border-b-gray-400 relative flex items-center text-sm">
          <th className="px-6 py-4 text-left block w-70">Domain ID</th>
          <th className="px-6 py-4 text-left block w-70">Data domain name</th>
          <th className="px-2 py-4 text-left block ml-11">Action</th>
          <Button
            label="Add data domain"
            color="primary"
            variant="contained"
            className="absolute right-2 top-2 w-40"
            onClick={addDataDomain}
          />
        </thead>
        <tbody>
          {dataDomains.map((domain) => (
            <tr key={domain.domain_name} className="border-b border-gray-200">
              <td className="px-6 py-4 text-left block w-100">
                {domain.domain_name}
              </td>
              <td className="px-6 py-4 text-left block w-100">
                {domain.display_name}
              </td>
              <td className="px-2 py-4 text-left block ml-11">
                <IconButton
                  size="sm"
                  onClick={() => editDataDomain(domain.domain_name ?? '')}
                  ripple={false}
                  color="teal"
                  className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  disabled={userProfile.can_manage_definitions !== true}
                >
                  <SvgIcon name="edit" className="w-4" />
                </IconButton>
                <div className="flex gap-x-2">
                  <IconButton
                    size="sm"
                    onClick={() => deleteDataDomain(domain.domain_name ?? '')}
                    disabled={userProfile.can_manage_definitions !== true}
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  >
                    <SvgIcon name="delete" className="w-4" />
                  </IconButton>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <DataDomainCreateDialog
        open={openCreateDialog}
        onClose={() => setOpenCreateDialog(false)}
      />
      <ConfirmDialog
        open={selectedDataDomainToDelete?.length !== 0}
        onClose={() => setSelectedDataDomainToDelete('')}
        onConfirm={() => deleteDataDomain(selectedDataDomainToDelete)}
        message={`Are you sure you want to delete ${selectedDataDomainToDelete} credential?`}
      />
    </div>
  );
}
