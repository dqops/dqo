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
  const history = useHistory();
  const goToTable = (item: TableListModel) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkTypes ?? CheckTypes.SOURCES,
      item.connection_name ?? '',
      item.target?.schema_name ?? '',
      item.target?.table_name ?? '',
      'detail'
    );
    dispatch(
      addFirstLevelTab(checkTypes ?? CheckTypes.SOURCES, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          checkTypes ?? CheckTypes.SOURCES,
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

  const prepareLabel = (label: string | undefined) => {
    if (!label) return;
    if (label.length > 50) {
      return label.slice(0, 50) + '...';
    }
    return label;
  };

  const getLabelsOverview = (labels: string[]) => {
    return labels.map((x) => prepareLabel(x)).join(',');
  };

  return (
    <tr className="text-sm">
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
      <td className="px-4 text-sm">{item?.filter}</td>
      <td className="px-4 text-sm">{getLabelsOverview(item?.labels ?? [])}</td>
      {item?.data_quality_status?.dimensions ? (
        <SchemaTableItemDimensions item={item} dimensionKeys={dimensionKeys} />
      ) : (
        <SvgIcon name="hourglass" className="w-4 h-4" />
      )}
    </tr>
  );
}
