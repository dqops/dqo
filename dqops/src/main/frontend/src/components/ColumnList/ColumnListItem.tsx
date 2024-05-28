import { Tooltip } from '@material-tailwind/react';
import React from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { ColumnListModel } from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import SchemaTableItemDimensions from '../../pages/Schema/SchemaTableItemDimensions';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../shared/routes';
import {
  getFirstLevelColumnTab,
  getFirstLevelTableTab,
  prepareString,
  useDecodedParams
} from '../../utils';

type TColumnWithSchema = ColumnListModel & { schema?: string };

export default function SchemaTableItem({
  item,
  dimensionKeys
}: {
  item: TColumnWithSchema;
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

  const goToTable = (item: ColumnListModel, checkType?: CheckTypes) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkType ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.table?.schema_name ?? '',
      item.table?.table_name ?? '',
      getFirstLevelTableTab(checkType ?? CheckTypes.MONITORING)
    );
    dispatch(
      addFirstLevelTab(checkType ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          checkType ?? CheckTypes.MONITORING,
          item.connection_name ?? '',
          item.table?.schema_name ?? '',
          item.table?.table_name ?? ''
        ),
        state: {},
        label: item.table?.table_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const goToColumn = (item: ColumnListModel, checkType?: CheckTypes) => {
    const url = ROUTES.COLUMN_LEVEL_PAGE(
      checkType ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.table?.schema_name ?? '',
      item.table?.table_name ?? '',
      item.column_name ?? '',
      getFirstLevelColumnTab(checkType ?? CheckTypes.MONITORING)
    );
    dispatch(
      addFirstLevelTab(checkType ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.COLUMN_LEVEL_VALUE(
          checkType ?? CheckTypes.MONITORING,
          item.connection_name ?? '',
          item.table?.schema_name ?? '',
          item.table?.table_name ?? '',
          item.column_name ?? ''
        ),
        state: {},
        label: item.column_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const goToConnection = (item: ColumnListModel) => {
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
        label: item.connection_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const goToSchema = (item: ColumnListModel) => {
    const url = ROUTES.SCHEMA_LEVEL_PAGE(
      checkTypes ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.table?.schema_name ?? '',
      'tables'
    );
    dispatch(
      addFirstLevelTab(checkTypes ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.SCHEMA_LEVEL_VALUE(
          checkTypes ?? CheckTypes.MONITORING,
          item.connection_name ?? '',
          item.table?.schema_name ?? ''
        ),
        state: {},
        label: item.table?.schema_name ?? ''
      })
    );
    history.push(url);
    return;
  };

  const getLabelsOverview = (labels: string[]) => {
    return labels.map((x) => prepareString(x, 20)).join(',');
  };

  return (
    <tr className="text-xs my-3">
      {(!checkTypes || !connection || !schema) && (
        <>
          {!connection && (
            <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
              <Button
                className="ml-4 !px-0 underline cursor-pointer text-xs py-0 text-start "
                label={item.connection_name}
                onClick={() => goToConnection(item)}
              />
            </td>
          )}
          <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
            <Button
              className="ml-4 !px-0 underline cursor-pointer text-xs py-0 text-start"
              label={item.schema}
              onClick={() => goToSchema(item)}
            />
          </td>
        </>
      )}
      <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
        <Button
          className="ml-4 !px-0 underline cursor-pointer text-xs py-0 text-start"
          label={item.table?.table_name}
          onClick={() => goToTable(item, checkTypes)}
        />
      </td>
      <td className="content-start pt-2 max-w-72 min-w-50 whitespace-normal break-all">
        <Button
          className="ml-4 !px-0 underline cursor-pointer text-xs py-0 text-start"
          label={item.column_name}
          onClick={() => goToColumn(item, checkTypes)}
        />
      </td>{' '}
      <td className="px-4 text-xs content-start pt-2">
        {prepareString(item.type_snapshot?.column_type, 15)}
      </td>
      <td className="px-4 text-xs content-start pt-2">
        {getLabelsOverview(item?.labels ?? [])}
      </td>
      {item?.data_quality_status?.dimensions ? (
        <>
          <SchemaTableItemDimensions
            item={item}
            dimensionKeys={dimensionKeys}
          />
          <td>
            <div className="flex gap-x-2 items-center justify-center mx-3">
              <Tooltip
                content={'Add a new connection and manage its settings'}
                className="max-w-80 py-4 px-4 bg-gray-800 delay-700"
              >
                <SvgIcon
                  name="data_sources"
                  className="w-5 h-5 cursor-pointer"
                  onClick={() => goToColumn(item, CheckTypes.SOURCES)}
                />
              </Tooltip>
              <Tooltip
                content={'Profiling'}
                className="max-w-80 py-4 px-4 bg-gray-800 delay-700"
              >
                <SvgIcon
                  name="profiling"
                  className="w-5 h-5 cursor-pointer"
                  onClick={() => goToColumn(item, CheckTypes.PROFILING)}
                />
              </Tooltip>
              <Tooltip
                content={'Monitoring Checks'}
                className="max-w-80 py-4 px-4 bg-gray-800 delay-700"
              >
                <SvgIcon
                  name="monitoring_checks"
                  className="w-5 h-5 cursor-pointer"
                  onClick={() => goToColumn(item, CheckTypes.MONITORING)}
                />
              </Tooltip>
              <Tooltip
                content={'Partitioned Checks'}
                className="max-w-80 py-4 px-4 bg-gray-800 delay-700"
              >
                <SvgIcon
                  name="partitioned_checks"
                  className="w-5 h-5 cursor-pointer"
                  onClick={() => goToColumn(item, CheckTypes.PARTITIONED)}
                />
              </Tooltip>
            </div>
          </td>
        </>
      ) : (
        <td className="content-start pt-2">
          <SvgIcon name="hourglass" className="w-4 h-4" />
        </td>
      )}
    </tr>
  );
}
