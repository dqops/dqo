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

  const goToTable = (item: TableListModel) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkTypes ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.target?.schema_name ?? '',
      item.target?.table_name ?? '',
      'detail'
    );
    dispatch(
      addFirstLevelTab(checkTypes ?? CheckTypes.MONITORING, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          checkTypes ?? CheckTypes.MONITORING,
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
    <tr className="text-sm">
      {(!checkTypes || !connection || !schema) && (
        <>
          <td>
            <Button
              className="px-4 underline cursor-pointer text-sm"
              label={item.connection_name}
              onClick={() => goToConnection(item)}
            />
          </td>
          <td>
            <Button
              className="px-4 underline cursor-pointer text-sm"
              label={item.schema}
              onClick={() => goToSchema(item)}
            />
          </td>
        </>
      )}
      <Button
        className="px-4 underline cursor-pointer text-sm"
        label={item.target?.table_name}
        onClick={() => goToTable(item)}
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
      <td className="px-4 text-sm">{getLabelsOverview(item?.labels ?? [])}</td>
      {item?.data_quality_status?.dimensions ? (
        <SchemaTableItemDimensions item={item} dimensionKeys={dimensionKeys} />
      ) : (
        <SvgIcon name="hourglass" className="w-4 h-4" />
      )}
    </tr>
  );
}
