import React, { useMemo } from 'react';
import { useDispatch } from 'react-redux';
import { TableListModel } from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SchemaTableItemDimensions from './SchemaTableItemDimensions';

type TButtonTabs = {
  label: string;
  value: string;
};

export default function SchemaTableItem({
  item,
  dimensionKeys
}: {
  item: TableListModel;
  dimensionKeys: string[];
}) {
  const {
    checkTypes
  }: {
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const dispatch = useDispatch();

  const goToTable = (item: TableListModel, tab: string) => {
    dispatch(
      addFirstLevelTab(checkTypes, {
        url: ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          item.connection_name ?? '',
          item.target?.schema_name ?? '',
          item.target?.table_name ?? '',
          tab
        ),
        value: ROUTES.TABLE_LEVEL_VALUE(
          checkTypes,
          item.connection_name ?? '',
          item.target?.schema_name ?? '',
          item.target?.table_name ?? ''
        ),
        state: {},
        label: item.target?.table_name ?? ''
      })
    );
    return;
  };

  const buttonTabs: TButtonTabs[] = useMemo(() => {
    switch (checkTypes) {
      case CheckTypes.PROFILING:
        return [
          {
            label: 'Basic statistics',
            value: 'statistics'
          },
          { label: 'Profiling checks', value: 'advanced' },
          { label: 'Profiling table status', value: 'table-quality-status' }
        ];

      case CheckTypes.SOURCES:
        return [
          {
            label: 'Table configuration',
            value: 'detail'
          },
          { label: 'Date and time column', value: 'timestamps' },
          { label: 'Incident configuration', value: 'incident_configuration' }
        ];

      case CheckTypes.MONITORING:
      case CheckTypes.PARTITIONED:
        return [
          { label: 'Daily table status', value: 'table-quality-status-daily' },
          { label: 'Daily checks', value: 'daily' }
        ];

      default:
        return [];
    }
  }, [checkTypes]);

  return (
    <tr className="text-sm">
      <Button
        className="px-4 underline cursor-pointer text-sm"
        label={item.target?.table_name}
        onClick={() => goToTable(item, buttonTabs[0].value)}
      />
      <td className="px-4">
        {item?.disabled ? (
          <SvgIcon
            name="close"
            className="text-red-700"
            width={30}
            height={22}
          />
        ) : null}
      </td>
      <td className="px-4 text-sm">{item?.stage}</td>
      <td className="px-4 text-sm">{item?.filter}</td>
      {item?.data_quality_status?.dimensions ? (
        <SchemaTableItemDimensions item={item} dimensionKeys={dimensionKeys} />
      ) : (
        <SvgIcon name="hourglass" className="w-4 h-4" />
      )}
    </tr>
  );
}
