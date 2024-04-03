import React from 'react';
import { TParameters } from '../../../../../shared/constants';
import SvgIcon from '../../../../SvgIcon';
import { CheckTypes, ROUTES } from '../../../../../shared/routes';
import { addFirstLevelTab } from '../../../../../redux/actions/source.actions';
import { useActionDispatch } from '../../../../../hooks/useActionDispatch';
import { useHistory } from 'react-router-dom';
type TSelecColumnGroupingOverlook = {
  editConfigurationParameters: TParameters;
  onChangeEditConnectionSchemaTable: (open: boolean) => void;
};

export default function SelecColumnGroupingOverlook({
  editConfigurationParameters,
  onChangeEditConnectionSchemaTable
}: TSelecColumnGroupingOverlook) {
  const dispatch = useActionDispatch();
  const history = useHistory();

  const goToRefTable = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      editConfigurationParameters.refConnection ?? '',
      editConfigurationParameters.refSchema ?? '',
      editConfigurationParameters.refTable ?? '',
      'detail'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      editConfigurationParameters.refConnection ?? '',
      editConfigurationParameters.refSchema ?? '',
      editConfigurationParameters.refTable ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url: url,
        value,
        label: editConfigurationParameters.refTable ?? '',
        state: {}
      })
    );

    history.push(url);
  };

  return (
    <div className="flex items-center gap-4 mb-4">
      <SvgIcon
        name="chevron-right"
        className="w-5 h-5"
        onClick={() => onChangeEditConnectionSchemaTable(true)}
      />
      <span>Comparing this table to the reference table:</span>
      <a className="text-teal-500 cursor-pointer" onClick={goToRefTable}>
        {editConfigurationParameters.refConnection}.
        {editConfigurationParameters.refSchema}.
        {editConfigurationParameters.refTable}
      </a>
    </div>
  );
}
