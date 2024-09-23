import React from 'react';
import { useSelector } from 'react-redux';
import { DqoUserRolesModelAccountRoleEnum } from '../../api';
import { IRootState } from '../../redux/reducers';
import Select from '../Select';

interface IDataDomainItemProps {
  dataDomains: { [key: string]: string }[];
  onChange: (dataDomains: { [key: string]: string }[]) => void;
  index: number;
}

const DataDomainItem = ({
  dataDomains,
  onChange,
  index
}: IDataDomainItemProps) => {
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const value = Object.values(dataDomains[index])[0];
  const key = Object.keys(dataDomains[index])[0];

  //   const onRemove = () => {
  //     const copiedDataDomains = [...dataDomains].filter(
  //       (_, idx) => idx !== index
  //     );
  //     onChange(copiedDataDomains);
  //   };

  //   const onChangeKey = (newKey: string, value: string) => {
  //     const updatedDataDomains = [...dataDomains];
  //     updatedDataDomains[index] = { [newKey]: value };
  //     onChange(updatedDataDomains);
  //   };

  const onChangeValue = (key: string, newValue: string) => {
    const updatedDataDomains = [...dataDomains];
    updatedDataDomains[index] = { [key]: newValue };
    onChange(updatedDataDomains);
  };

  //   const onAdd = () => {
  //     onChange([...(dataDomains ?? []), { ['']: '' }]);
  //   };

  //   const isKeyRed = key.length === 0 && value.length > 0;
  //   const isValueRed = key.length > 0 && value.length === 0;
  //   const areBothEmpty = key.length === 0 && value.length === 0;

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/2">{key}</td>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <Select
          value={value}
          onChange={(val) => onChangeValue(key, val)}
          options={Object.values(DqoUserRolesModelAccountRoleEnum).map((x) => ({
            label: x,
            value: x
          }))}
          disabled={userProfile.account_role?.toLowerCase() !== 'admin'}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
        {/* {!(index === 0 && dataDomains.length === 1) && (
          <IconButton
            ripple={false}
            className="bg-teal-500 mx-1 !shadow-none hover:!shadow-none hover:bg-[#028770]"
            size="sm"
            onClick={onRemove}
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        )}
        {index === dataDomains.length - 1 && (
          <IconButton
            ripple={false}
            className="bg-teal-500 mx-1 !shadow-none hover:!shadow-none hover:bg-[#028770]"
            size="sm"
            onClick={onAdd}
            disabled={isKeyRed || isValueRed || areBothEmpty}
          >
            <SvgIcon name="add" className="w-4" />
          </IconButton>
        )} */}
      </td>
    </tr>
  );
};

export default DataDomainItem;
