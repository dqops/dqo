import React, { useMemo } from 'react';
import { TableListModel } from '../../api';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';

type SchemaTablesProps = {
  tables: TableListModel[];
};

type TButtonTabs = {
  label: string;
  value: string;
};

export const SchemaTables = ({ tables }: SchemaTablesProps) => {
  const {
    checkTypes
  }: {
    checkTypes: CheckTypes;
  } = useParams();
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
          { label: 'Daily checks', value: 'daily' },
          { label: 'Daily table status', value: 'table-quality-status-daily' },
          { label: 'Monthly checks', value: 'monthly' },
          {
            label: 'Monthly table status',
            value: 'table-quality-status-monthly'
          }
        ];

      default:
        return [];
    }
  }, [checkTypes]);

  return (
    <table className="w-full">
      <thead>
        <tr>
          <th className="px-4 text-left">Table</th>
          <th className="px-4 text-left">Disabled</th>
          <th className="px-4 text-left">Stage</th>
          <th className="px-4 text-left">Filter</th>
          <th></th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {tables.map((item, index) => (
          <tr key={index}>
            <Button 
              className="px-4 underline cursor-pointer"
              label={item.target?.table_name} 
              onClick={() => goToTable(item, buttonTabs[0].value)}
            />
            <td className="px-4">
              {item?.disabled ? <SvgIcon
               name='close'
               className = 'text-red-700'
               width={30}
               height={22}
             /> : null}</td>
            <td className="px-4">{item?.stage}</td>
            <td className="px-4">{item?.filter}</td>
            {buttonTabs.map((button) => {
              return (
                <td className="px-4" key={button.value}>
                  <Button
                    variant="text"
                    label={button.label}
                    color="primary"
                    onClick={() => goToTable(item, button.value)}
                  />
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );
};
