import { IconButton, Tooltip } from '@material-tailwind/react';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { LocalDataDomainModel } from '../../api';
import Button from '../../components/Button';
import Loader from '../../components/Loader';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { DataDomainApiClient } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import DataDomainCreateDialog from './DataDomainCreateDialog';
import DataDomainDeleteDialog from './DataDomainDeleteDialog';

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
  const getLocalDataDomains = async () => {
    setLoading(false);
    DataDomainApiClient.getLocalDataDomains()
      .then((res) => {
        setDataDomains(res.data);
      })
      .finally(() => setLoading(false));
  };

  const dispatch = useActionDispatch();

  useEffect(() => {
    getLocalDataDomains();
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
  const editDataDomain = (domain: string, display_name: string) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.DATA_DOMAIN_DETAIL(domain),
        value: ROUTES.DATA_DOMAIN_DETAIL(domain),
        label: domain,
        state: { data_domain: domain, data_domain_name: display_name }
      })
    );
  };

  const addDataDomain = () => {
    setOpenCreateDialog(true);
  };

  if (loading) {
    return <Loader className="w-6 h-6" isFull={false} />;
  }

  return (
    <div className="text-sm">
      {!userProfile.can_use_data_domains && (
        <div className="p-2 text-red-500">
          Data domains require an active DQOps Cloud ENTERPRISE license. Please
          contact DQOps sales to activate data domains.
        </div>
      )}
      <table className="w-full">
        <thead className="border-b w-full relative border-b-gray-400 text-sm">
          <th className="px-6 py-4 text-left w-70">Domain ID</th>
          <th className="px-6 py-4 text-left w-70">Data domain name</th>
          <th className="px-2 py-4 text-left ">
            <div className="ml-1.5">Action</div>
          </th>
          <Button
            label="Add data domain"
            color="primary"
            variant="contained"
            className="absolute right-2 top-2 w-40"
            onClick={addDataDomain}
            disabled={!userProfile.can_use_data_domains}
          />
        </thead>
        <tbody>
          {dataDomains.map((domain) => (
            <tr key={domain.domain_name} className=" text-sm">
              <td className="px-6 py-4 text-left">{domain.domain_name}</td>
              <td className="px-6 py-4 text-left">{domain.display_name}</td>
              <td className="py-4 text-left">
                {domain.domain_name !== '(default)' && (
                  <div className="flex gap-x-2">
                    <IconButton
                      size="sm"
                      onClick={() =>
                        editDataDomain(
                          domain.domain_name ?? '',
                          domain.display_name ?? ''
                        )
                      }
                      ripple={false}
                      color="teal"
                      className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                      disabled={!userProfile.can_use_data_domains}
                    >
                      <Tooltip content="Modify">
                        <div>
                          <SvgIcon name="edit" className="w-4" />
                        </div>
                      </Tooltip>
                    </IconButton>
                    <IconButton
                      size="sm"
                      onClick={() => deleteDataDomain(domain.domain_name ?? '')}
                      disabled={!userProfile.can_use_data_domains}
                      color="teal"
                      className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                    >
                      <Tooltip content="Delete">
                        <div>
                          <SvgIcon name="delete" className="w-4" />
                        </div>
                      </Tooltip>
                    </IconButton>
                  </div>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <DataDomainCreateDialog
        open={openCreateDialog}
        onClose={() => {
          getLocalDataDomains();
          setOpenCreateDialog(false);
        }}
      />
      <DataDomainDeleteDialog
        open={selectedDataDomainToDelete?.length !== 0}
        onClose={() => setSelectedDataDomainToDelete('')}
        onConfirm={() => deleteDataDomain(selectedDataDomainToDelete)}
        yesNo
      />
    </div>
  );
}
