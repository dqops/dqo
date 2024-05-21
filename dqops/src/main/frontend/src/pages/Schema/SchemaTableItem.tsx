import React from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { TableListModel } from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SchemaTableItemDimensions from './SchemaTableItemDimensions';

type TTableWithSchema = TableListModel & { schema?: string };

export default function SchemaTableItem({
  item,
  dimensionKeys
}: {
  item: TTableWithSchema;
  dimensionKeys: string[];
}) {
  const {
    checkTypes,
    connection,
    schema
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
  } = useDecodedParams();
  const dispatch = useDispatch();
  const history = useHistory();

  const goToTable = (
    item: TableListModel,
    checkType?: CheckTypes,
    tab?: string
  ) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkType ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.target?.schema_name ?? '',
      item.target?.table_name ?? '',
      tab ?? 'detail'
    );
    dispatch(
      addFirstLevelTab(checkType ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          checkType ?? CheckTypes.MONITORING,
          item.connection_name ?? '',
          item.target?.schema_name ?? '',
          item.target?.table_name ?? ''
        ),
        state: {},
        label: item.target?.table_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const goToConnection = (item: TableListModel) => {
    const url = ROUTES.CONNECTION_DETAIL(
      checkTypes ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      'detail'
    );
    dispatch(
      addFirstLevelTab(checkTypes ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.CONNECTION_LEVEL_VALUE(
          checkTypes ?? CheckTypes.MONITORING,
          item.connection_name ?? ''
        ),
        state: {},
        label: item.target?.table_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const goToSchema = (item: TableListModel) => {
    const url = ROUTES.SCHEMA_LEVEL_PAGE(
      checkTypes ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.target?.schema_name ?? '',
      'tables'
    );
    dispatch(
      addFirstLevelTab(checkTypes ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.SCHEMA_LEVEL_VALUE(
          checkTypes ?? CheckTypes.MONITORING,
          item.connection_name ?? '',
          item.target?.schema_name ?? ''
        ),
        state: {},
        label: item.target?.table_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const prepareLabel = (label: string | undefined) => {
    if (!label) return;
    if (label.length > 20) {
      return label.slice(0, 20) + '...';
    }
    return label;
  };

  const getLabelsOverview = (labels: string[]) => {
    return labels.map((x) => prepareLabel(x)).join(',');
  };

  return (
    <tr className="text-sm my-3">
      {(!checkTypes || !connection || !schema) && (
        <>
          {!connection && (
            <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
              <Button
                className="ml-4 !px-0 underline cursor-pointer text-sm py-0 text-start "
                label={item.connection_name}
                onClick={() => goToConnection(item)}
              />
            </td>
          )}
          <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
            <Button
              className="ml-4 !px-0 underline cursor-pointer text-sm py-0 text-start"
              label={item.schema}
              onClick={() => goToSchema(item)}
            />
          </td>
        </>
      )}
      <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
        <Button
          className="ml-4 !px-0 underline cursor-pointer text-sm py-0 text-start"
          label={item.target?.table_name}
          onClick={() => goToTable(item, checkTypes)}
        />
      </td>
      <td className="px-4 text-sm content-start pt-2">{item?.stage}</td>
      <td className="px-4 text-sm content-start pt-2">
        {getLabelsOverview(item?.labels ?? [])}
      </td>
      {item?.data_quality_status?.dimensions ? (
        <SchemaTableItemDimensions item={item} dimensionKeys={dimensionKeys} />
      ) : (
        <td className="content-start pt-2">
          <SvgIcon name="hourglass" className="w-4 h-4" />
        </td>
      )}

      <td>
        <div className="flex gap-x-2 items-center justify-center mr-3">
          <SvgIcon
            name="data_sources"
            className="w-5 h-5"
            onClick={() => goToTable(item, CheckTypes.SOURCES)}
          />
          <SvgIcon
            name="profiling"
            className="w-5 h-5"
            onClick={() => goToTable(item, CheckTypes.PROFILING, 'statistics')}
          />
          <SvgIcon
            name="monitoring_checks"
            className="w-5 h-5"
            onClick={() =>
              goToTable(
                item,
                CheckTypes.MONITORING,
                'table-quality-status-daily'
              )
            }
          />
          <SvgIcon
            name="partitioned_checks"
            className="w-5 h-5"
            onClick={() =>
              goToTable(
                item,
                CheckTypes.PARTITIONED,
                'table-quality-status-daily'
              )
            }
          />
        </div>
      </td>
    </tr>
  );
}
