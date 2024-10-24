import { Tooltip } from '@material-tailwind/react';
import React from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { TableListModel } from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { formatNumber } from '../../shared/constants';
import { CheckTypes, ROUTES } from '../../shared/routes';
import {
  getFirstLevelTableTab,
  limitTextLength,
  useDecodedParams
} from '../../utils';
import SchemaTableItemDimensions from './SchemaTableItemDimensions';

type TTableWithSchema = TableListModel & { schema?: string };

export default function SchemaTableItem({
  item,
  dimensionKeys,
  showDimensions,
  maxRowCount,
  maxDelay
}: {
  item: TTableWithSchema;
  dimensionKeys: string[];
  showDimensions?: boolean;
  maxRowCount?: number;
  maxDelay?: number;
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

  const goToTable = (item: TableListModel, checkType?: CheckTypes) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkType ?? CheckTypes.MONITORING,
      item.connection_name ?? '',
      item.target?.schema_name ?? '',
      item.target?.table_name ?? '',
      getFirstLevelTableTab(checkType ?? CheckTypes.MONITORING)
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
      'data-quality-summary'
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

  const getLabelsOverview = (labels: string[]) => {
    return labels.map((x) => limitTextLength(x, 20)).join(', ');
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
      <td className="content-start pt-2 max-w-100 min-w-50 whitespace-normal break-all">
        <Button
          className="ml-4 !px-0 underline cursor-pointer text-xs py-0 text-start"
          label={item.target?.table_name}
          onClick={() => goToTable(item, checkTypes)}
        />
      </td>
      <td className="px-4 text-xs content-start pt-2 max-w-70 min-w-30 break-all">
        {item?.stage}
      </td>
      <td className="px-4 text-xs content-start pt-2 max-w-100 min-w-50 break-all">
        {getLabelsOverview(item?.labels ?? [])}
      </td>
      {item?.data_quality_status?.dimensions || !showDimensions ? (
        <>
          {showDimensions ? (
            <SchemaTableItemDimensions
              item={item}
              dimensionKeys={dimensionKeys}
            />
          ) : (
            <>
              <td className="content-start pt-2">
                <div className="flex items-center pl-4">
                  <div className="w-11 ">
                    {formatNumber(item.data_quality_status?.total_row_count)}
                  </div>
                  {maxRowCount && item.data_quality_status?.total_row_count ? (
                    <div
                      className=" h-3 border border-gray-100 flex ml-2"
                      style={{ width: '66.66px' }}
                    >
                      <div
                        className="h-3 bg-teal-500"
                        style={{
                          width: `${
                            ((item.data_quality_status?.total_row_count /
                              maxRowCount) *
                              200) /
                            3
                          }px`
                        }}
                      ></div>
                    </div>
                  ) : (
                    <div
                      className=" h-3  flex ml-2"
                      style={{ width: '66.66px' }}
                    ></div>
                  )}
                </div>
              </td>
              <td className="content-start pt-2">
                <div className="flex items-center px-4">
                  <div className="w-11">
                    {item.data_quality_status?.data_freshness_delay_days
                      ? item.data_quality_status?.data_freshness_delay_days.toFixed(
                          1
                        ) + ' d'
                      : ''}
                  </div>
                  {maxDelay &&
                  item.data_quality_status?.data_freshness_delay_days ? (
                    <div
                      className=" h-3 border border-gray-100 flex ml-2"
                      style={{ width: '66.66px' }}
                    >
                      <div
                        className="h-3 bg-teal-500"
                        style={{
                          width: `${
                            ((item.data_quality_status
                              ?.data_freshness_delay_days /
                              maxDelay) *
                              200) /
                            3
                          }px`
                        }}
                      ></div>
                    </div>
                  ) : (
                    <></>
                  )}
                </div>
              </td>
            </>
          )}
          <td>
            <div className="flex gap-x-2 items-center justify-center mx-3">
              <Tooltip
                content={'Data sources'}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
                placement="right-start"
              >
                <div>
                  <SvgIcon
                    name="data_sources"
                    className="w-5 h-5 cursor-pointer"
                    onClick={() => goToTable(item, CheckTypes.SOURCES)}
                  />
                </div>
              </Tooltip>
              <Tooltip
                content={'Profiling'}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <SvgIcon
                    name="profiling"
                    className="w-5 h-5 cursor-pointer"
                    onClick={() => goToTable(item, CheckTypes.PROFILING)}
                  />
                </div>
              </Tooltip>
              <Tooltip
                content={'Monitoring checks'}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <SvgIcon
                    name="monitoring_checks"
                    className="w-5 h-5 cursor-pointer"
                    onClick={() => goToTable(item, CheckTypes.MONITORING)}
                  />
                </div>
              </Tooltip>
              <Tooltip
                content={'Partition checks'}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <SvgIcon
                    name="partitioned_checks"
                    className="w-5 h-5 cursor-pointer"
                    onClick={() => goToTable(item, CheckTypes.PARTITIONED)}
                  />
                </div>
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
