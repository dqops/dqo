import React, { ChangeEvent, useEffect, useState } from 'react';
import {
  ColumnApiClient,
  ConnectionApiClient,
  EnviromentApiClient,
  JobApiClient,
  SchemaApiClient,
  TableApiClient,
  TableComparisonsApi
} from '../../../../services/apiClient';
import {
  DeleteStoredDataQueueJobParameters,
  DqoJobHistoryEntryModelStatusEnum,
  DqoSettingsModel,
  TableColumnsStatisticsModel,
  TableComparisonConfigurationModelCheckTypeEnum,
  TableComparisonGroupingColumnPairModel,
  TableComparisonModel
} from '../../../../api';
import Button from '../../../Button';
import Input from '../../../Input';
import SvgIcon from '../../../SvgIcon';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Select, { Option } from '../../../Select';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../../shared/routes';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { setCurrentJobId } from '../../../../redux/actions/source.actions';
import TableActionGroup from '../TableActionGroup';
import { SelectGroupColumnsTable } from '../SelectGroupColumnsTable';
import clsx from 'clsx';
import DeleteOnlyDataDialog from '../../../CustomTree/DeleteOnlyDataDialog';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../../redux/reducers';
import useConnectionSchemaTableExists from '../../../../hooks/useConnectionSchemaTableExists';
import {
  getTableDailyMonitoringChecks,
  getTableDailyPartitionedChecks,
  getTableMonthlyMonitoringChecks,
  getTableMonthlyPartitionedChecks,
  getTableProfilingChecksModel
} from '../../../../redux/actions/table.actions';

type TParameters = {
  name?: string;
  refConnection?: string;
  refSchema?: string;
  refTable?: string;
};

type EditReferenceTableProps = {
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  selectedReference?: string;
  isUpdatedParent?: boolean;
  timePartitioned?: 'daily' | 'monthly';
  onRunChecksRowCount?: () => void;
  disabled?: boolean;
  isCreating?: boolean;
  goToRefTable?: () => void;
  onChangeUpdatedParent: (variable: boolean) => void;
  combinedFunc: (name: string) => void;
  cleanDataTemplate: DeleteStoredDataQueueJobParameters | undefined;
  onChangeIsDataDeleted: (arg: boolean) => void;
  onChange: (obj: Partial<TableComparisonModel>) => void;
  isDataDeleted: boolean;
  listOfExistingReferences: Array<string | undefined>;
  canUserCompareTables?: boolean;
  onUpdateParent: () => void;
  columnOptions: {
    comparedColumnsOptions: Option[];
    referencedColumnsOptions: Option[];
  };
  onChangeParameters: (obj: Partial<TParameters>) => void;
};

const EditReferenceTable = ({
  onBack,
  selectedReference,
  timePartitioned,
  isUpdatedParent,
  onRunChecksRowCount,
  disabled,
  isCreating,
  goToRefTable,
  onChangeUpdatedParent,
  combinedFunc,
  cleanDataTemplate,
  onChangeIsDataDeleted,
  onChange,
  isDataDeleted,
  listOfExistingReferences,
  canUserCompareTables,
  onUpdateParent,
  columnOptions,
  onChangeParameters
}: EditReferenceTableProps) => {
  const [name, setName] = useState('');
  const [connectionOptions, setConnectionOptions] = useState<Option[]>([]);
  const [schemaOptions, setSchemaOptions] = useState<Option[]>([]);
  const [tableOptions, setTableOptions] = useState<Option[]>([]);
  const [refConnection, setRefConnection] = useState('');
  const [refSchema, setRefSchema] = useState('');
  const [refTable, setRefTable] = useState('');
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useParams();
  const [isUpdated, setIsUpdated] = useState(false);
  const [dataGroupingArray, setDataGroupingArray] = useState<
    TableComparisonGroupingColumnPairModel[]
  >([]);
  const [extendRefnames, setExtendRefnames] = useState(false);
  const [extendDg, setExtendDg] = useState(false);
  const [deletingData, setDeletingData] = useState(false);
  const [isButtonEnabled, setIsButtonEnabled] = useState<boolean | undefined>(
    false
  );
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [popUp, setPopUp] = useState(false);
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [jobId, setJobId] = useState<number>();
  const [profileSettings, setProfileSettings] = useState<DqoSettingsModel>();
  const [listOfWarnings, setListOfWarnings] = useState<Array<boolean>>(
    Array(8).fill(false)
  );
  const [listOfReferenceWarnings, setListOfReferenceWarnings] = useState<
    Array<boolean>
  >(Array(8).fill(false));
  const [referenceTableStatistics, setReferenceTableStatistics] =
    useState<TableColumnsStatisticsModel>();
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const dispatch = useActionDispatch();

  const { tableExist, schemaExist, connectionExist } =
    useConnectionSchemaTableExists(refConnection, refSchema, refTable);

  const onChangeDataGroupingArray = (
    reference: boolean,
    index: number,
    columnName: string
  ) => {
    const data = [...dataGroupingArray];
    if (reference === true) {
      if (data[index]) {
        data[index].reference_table_column_name = columnName;
      } else {
        data[index] = { reference_table_column_name: columnName };
      }
    } else {
      if (data[index]) {
        data[index].compared_table_column_name = columnName;
      } else {
        data[index] = { compared_table_column_name: columnName };
      }
    }
    onChange({ grouping_columns: data });
    setDataGroupingArray(data);
    setIsUpdated(true);
  };

  useEffect(() => {
    ConnectionApiClient.getAllConnections().then((res) => {
      setConnectionOptions(
        res.data.map((item) => ({
          label: item.connection_name ?? '',
          value: item.connection_name ?? ''
        }))
      );
    });
  }, []);

  useEffect(() => {
    if (selectedReference) {
      TableComparisonsApi.getTableComparisonConfiguration(
        connection,
        schema,
        table,
        selectedReference
      ).then((res) => {
        if (res && res?.data) {
          setName(res.data?.table_comparison_configuration_name ?? '');
          setRefConnection(res.data?.reference_connection ?? '');
          setRefSchema(res.data?.reference_table?.schema_name ?? '');
          setRefTable(res.data?.reference_table?.table_name ?? '');
          setDataGroupingArray(res.data.grouping_columns ?? []);
        }
      });
    }
  }, [selectedReference]);

  useEffect(() => {
    if (
      refConnection &&
      refConnection.length !== 0 &&
      connectionExist === true
    ) {
      SchemaApiClient.getSchemas(refConnection).then((res) => {
        if (res !== undefined) {
          setSchemaOptions(
            res.data.map((item) => ({
              label: item.schema_name ?? '',
              value: item.schema_name ?? ''
            }))
          );
        }
      });
    }
  }, [refConnection, connectionExist]);

  useEffect(() => {
    if (
      refConnection &&
      refConnection.length !== 0 &&
      refSchema &&
      refSchema.length !== 0 &&
      connectionExist === true &&
      schemaExist === true
    ) {
      TableApiClient.getTables(refConnection, refSchema).then((res) => {
        if (res !== undefined) {
          setTableOptions(
            res.data.map((item) => ({
              label: item.target?.table_name ?? '',
              value: item.target?.table_name ?? ''
            }))
          );
        }
      });
    }
  }, [refConnection, refSchema, connectionExist, schemaExist]);

  const onUpdate = async () => {
    if (selectedReference) {
      await TableComparisonsApi.updateTableComparisonConfiguration(
        connection,
        schema,
        table,
        selectedReference ?? '',
        {
          table_comparison_configuration_name: name,
          compared_connection: connection,
          compared_table: {
            schema_name: schema,
            table_name: table
          },
          reference_connection: refConnection,
          reference_table: {
            schema_name: refSchema,
            table_name: refTable
          },
          grouping_columns: dataGroupingArray,
          check_type:
            checkTypes as TableComparisonConfigurationModelCheckTypeEnum,
          time_scale: timePartitioned
        }
      )
        .then(() => {
          onBack(false);
        })
        .catch((err) => {
          console.log('err', err);
        });
      onUpdateParent();
    } else {
      if (listOfExistingReferences.includes(name.toString())) {
        setPopUp(true);
      } else {
        if (checkTypes === CheckTypes.PROFILING) {
          await TableComparisonsApi.createTableComparisonProfiling(
            connection,
            schema,
            table,
            {
              table_comparison_configuration_name: name,
              compared_connection: connection,
              compared_table: {
                schema_name: schema,
                table_name: table
              },
              reference_connection: refConnection,
              reference_table: {
                schema_name: refSchema,
                table_name: refTable
              },
              grouping_columns: dataGroupingArray ?? []
            }
          )
            .then(() => {
              dispatch(
                getTableProfilingChecksModel(
                  checkTypes,
                  firstLevelActiveTab,
                  connection,
                  schema,
                  table
                )
              );
              onBack(false);
              setPopUp(false);
            })
            .catch((error) => {
              if (error.response.status === 409) {
                setPopUp(true);
              }
            });
        } else if (
          checkTypes === CheckTypes.PARTITIONED &&
          timePartitioned === 'daily'
        ) {
          await TableComparisonsApi.createTableComparisonPartitionedDaily(
            connection,
            schema,
            table,
            {
              table_comparison_configuration_name: name,
              compared_connection: connection,
              compared_table: {
                schema_name: schema,
                table_name: table
              },
              reference_connection: refConnection,
              reference_table: {
                schema_name: refSchema,
                table_name: refTable
              },
              grouping_columns: dataGroupingArray ?? []
            }
          )
            .then(() => {
              dispatch(
                getTableDailyPartitionedChecks(
                  checkTypes,
                  firstLevelActiveTab,
                  connection,
                  schema,
                  table
                )
              );
              onBack(false);
              setPopUp(false);
            })
            .catch((error) => {
              if (error.response.status === 409) {
                setPopUp(true);
              }
            });
        } else if (
          checkTypes === CheckTypes.PARTITIONED &&
          timePartitioned === 'monthly'
        ) {
          await TableComparisonsApi.createTableComparisonPartitionedMonthly(
            connection,
            schema,
            table,
            {
              table_comparison_configuration_name: name,
              compared_connection: connection,
              compared_table: {
                schema_name: schema,
                table_name: table
              },
              reference_connection: refConnection,
              reference_table: {
                schema_name: refSchema,
                table_name: refTable
              },
              grouping_columns: dataGroupingArray ?? []
            }
          )
            .then(() => {
              dispatch(
                getTableMonthlyPartitionedChecks(
                  checkTypes,
                  firstLevelActiveTab,
                  connection,
                  schema,
                  table
                )
              );
              onBack(false);
              setPopUp(false);
            })
            .catch((error) => {
              if (error.response.status === 409) {
                setPopUp(true);
              }
            });
        } else if (
          checkTypes === CheckTypes.MONITORING &&
          timePartitioned === 'daily'
        ) {
          await TableComparisonsApi.createTableComparisonMonitoringDaily(
            connection,
            schema,
            table,
            {
              table_comparison_configuration_name: name,
              compared_connection: connection,
              compared_table: {
                schema_name: schema,
                table_name: table
              },
              reference_connection: refConnection,
              reference_table: {
                schema_name: refSchema,
                table_name: refTable
              },
              grouping_columns: dataGroupingArray ?? []
            }
          )
            .then(() => {
              dispatch(
                getTableDailyMonitoringChecks(
                  checkTypes,
                  firstLevelActiveTab,
                  connection,
                  schema,
                  table
                )
              );
              onBack(false);
              setPopUp(false);
            })
            .catch((error) => {
              if (error.response.status === 409) {
                setPopUp(true);
              }
            });
        } else if (
          checkTypes === CheckTypes.MONITORING &&
          timePartitioned === 'monthly'
        ) {
          await TableComparisonsApi.createTableComparisonMonitoringMonthly(
            connection,
            schema,
            table,
            {
              table_comparison_configuration_name: name,
              compared_connection: connection,
              compared_table: {
                schema_name: schema,
                table_name: table
              },
              reference_connection: refConnection,
              reference_table: {
                schema_name: refSchema,
                table_name: refTable
              },
              grouping_columns: dataGroupingArray ?? []
            }
          )
            .then(() => {
              dispatch(
                getTableMonthlyMonitoringChecks(
                  checkTypes,
                  firstLevelActiveTab,
                  connection,
                  schema,
                  table
                )
              );
              onBack(false);
              setPopUp(false);
            })
            .catch((error) => {
              if (error.response.status === 409) {
                console.log(error);
                setPopUp(true);
              }
            });
        }
        combinedFunc(name);
        // setPopUp(false);
      }
    }
    setIsButtonEnabled(false);
    setIsUpdated(false);
    onChangeUpdatedParent(false);
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
    setIsUpdated(true);
  };

  const changePropsTable = (value: string) => {
    setRefTable(value);
    setIsUpdated(true);
    if (isCreating === false) {
      onChange({
        reference_connection: refConnection,
        reference_table: { schema_name: refSchema, table_name: value }
      });
    }
  };
  const changePropsSchema = (value: string) => {
    setRefSchema(value);
    setIsUpdated(true);
  };
  const changePropsConnection = (value: string) => {
    setRefConnection(value);
    setIsUpdated(true);
  };

  const getRequiredColumnsIndexes = (
    dataGrouping: TableComparisonGroupingColumnPairModel[]
  ) => {
    const referenceGrouping = dataGrouping.map(
      (x) => x?.reference_table_column_name
    );
    const comparedGrouping = dataGrouping.map(
      (x) => x?.compared_table_column_name
    );

    const maxLeghtToCheck = Math.max(
      referenceGrouping.length,
      comparedGrouping.length
    );

    const referenceMissingIndexes = [];
    const comparedMissingIndexes = [];

    let check = false;
    for (let i = maxLeghtToCheck - 1; i >= 0; i--) {
      if (check === false) {
        if (referenceGrouping?.[i] && comparedGrouping?.[i]) {
          check = true;
        } else if (
          referenceGrouping?.[i] &&
          (comparedGrouping?.[i] === undefined ||
            comparedGrouping?.[i]?.length === 0)
        ) {
          check = true;
          comparedMissingIndexes.push(i);
        } else if (
          comparedGrouping?.[i] &&
          (referenceGrouping?.[i] === undefined ||
            referenceGrouping?.[i]?.length === 0)
        ) {
          check = true;
          referenceMissingIndexes.push(i);
        }
      } else {
        if (
          comparedGrouping?.[i] === undefined ||
          comparedGrouping?.[i]?.length === 0
        ) {
          comparedMissingIndexes.push(i);
        }
        if (
          referenceGrouping?.[i] === undefined ||
          referenceGrouping?.[i]?.length === 0
        ) {
          referenceMissingIndexes.push(i);
        }
      }
    }
    return { referenceMissingIndexes, comparedMissingIndexes };
  };

  const saveRun = () => {
    onUpdate();
    if (onRunChecksRowCount) {
      onRunChecksRowCount();
    }
  };

  useEffect(() => {
    setIsButtonEnabled(
      (name.length !== 0 &&
        connection.length !== 0 &&
        schema.length !== 0 &&
        table.length !== 0 &&
        refConnection.length !== 0 &&
        refSchema.length !== 0 &&
        refTable.length !== 0 &&
        getRequiredColumnsIndexes(dataGroupingArray).comparedMissingIndexes
          .length === 0 &&
        getRequiredColumnsIndexes(dataGroupingArray).referenceMissingIndexes
          .length === 0 &&
        (isUpdated || isUpdatedParent)) ||
        (isCreating === true &&
          refConnection.length !== 0 &&
          refSchema.length !== 0 &&
          refTable.length !== 0 &&
          name.length !== 0)
    );
  }, [isUpdated, isUpdatedParent, dataGroupingArray, refTable]);

  const deleteDataFunct = async (params: {
    [key: string]: string | boolean;
  }) => {
    setDeleteDataDialogOpened(false);
    try {
      setDeletingData(!isDataDeleted);
      const res = await JobApiClient.deleteStoredData(
        undefined,
        false,
        undefined,
        {
          ...(cleanDataTemplate || {}),
          ...params
        }
      );
      dispatch(
        setCurrentJobId(
          checkTypes,
          firstLevelActiveTab,
          res.data?.jobId?.jobId ?? 0
        )
      );
      setJobId(res.data?.jobId?.jobId);
    } catch (err) {
      console.error(err);
    }
  };
  const disabledDeleting =
    job &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.succeeded &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.failed;

  useEffect(() => {
    if (
      job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
      job?.status === DqoJobHistoryEntryModelStatusEnum.failed
    ) {
      onChangeIsDataDeleted(!isDataDeleted);
      setDeletingData(false);
    }
  }, [job?.status]);

  const fetchProfileSettings = async () => {
    try {
      const res = await EnviromentApiClient.getDqoSettings();
      setProfileSettings(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchProfileSettings();
    getColumnsStatistics();
  }, []);

  const warningMessage = `Warning: DQOps compares up to --dqo.sensors.limit.sensor-readout-limit data groups which is set to ${profileSettings?.properties?.['dqo.sensor.limits.sensor-readout-limit']} rows.
  You have selected a column which has more distinct values or the distinct row count statistics is not captured. Also when multiple columns are selected,
  the number of groupings may exceed the ${profileSettings?.properties?.['dqo.sensor.limits.sensor-readout-limit']} limit`;

  const getColumnsStatistics = async () => {
    try {
      await ColumnApiClient.getColumnsStatistics(
        connection,
        schema,
        table
      ).then((res) => setStatistics(res.data));
    } catch (err) {
      console.error(err);
    }
  };
  const getReferenceTableStatistics = async () => {
    try {
      if (refTable && refSchema && refConnection) {
        await ColumnApiClient.getColumnsStatistics(
          refConnection,
          refSchema,
          refTable
        ).then((res) => setReferenceTableStatistics(res.data));
      }
    } catch (err) {
      console.error(err);
    }
  };

  const checkIfDistinctCountIsBiggerThanLimit = (
    columnName: string,
    index: number,
    reference: boolean
  ) => {
    const stats = reference ? referenceTableStatistics : statistics;

    const column = stats?.column_statistics?.find(
      (col) => col.column_name === columnName
    );

    const columnStatistics = column?.statistics?.find(
      (stat) => stat.collector === 'distinct_count'
    );

    if (
      columnStatistics?.result === undefined ||
      profileSettings?.properties?.[
        'dqo.sensor.limits.sensor-readout-limit'
      ] === undefined ||
      columnStatistics?.result >
        profileSettings?.properties?.['dqo.sensor.limits.sensor-readout-limit']
    ) {
      const tnp = reference ? listOfReferenceWarnings : listOfWarnings;
      tnp[index] = columnName.length > 0 ? true : false;
      reference ? setListOfReferenceWarnings(tnp) : setListOfWarnings(tnp);
    } else {
      const tnp = reference ? listOfReferenceWarnings : listOfWarnings;
      tnp[index] = false;
      reference ? setListOfReferenceWarnings(tnp) : setListOfWarnings(tnp);
    }
  };

  const calculateTableDistinctCount = (
    comparedArray: string[],
    referenceArray: string[]
  ) => {
    const filteredStatisticsCompared =
      comparedArray.flatMap((column) =>
        statistics?.column_statistics?.filter((x) => x.column_name === column)
      ) || [];

    const filteredStatisticsReference =
      referenceArray.flatMap((column) =>
        referenceTableStatistics?.column_statistics?.filter(
          (x) => x.column_name === column
        )
      ) || [];

    let comparedTableDistinctCount = 1;
    let referenceTableDistinctCount = 1;

    for (
      let i = 0;
      i < (filteredStatisticsCompared ? filteredStatisticsCompared?.length : 0);
      i++
    ) {
      comparedTableDistinctCount *= Number(
        filteredStatisticsCompared?.[i]?.statistics?.find(
          (stat) => stat.collector === 'distinct_count'
        )?.result
      );
      referenceTableDistinctCount *= Number(
        filteredStatisticsReference?.[i]?.statistics?.find(
          (stat) => stat.collector === 'distinct_count'
        )?.result
      );
      if (
        (comparedTableDistinctCount || referenceTableDistinctCount) >
        Number(
          profileSettings?.properties?.[
            'dqo.sensor.limits.sensor-readout-limit'
          ]
        )
      ) {
        return true;
      }
    }
    if (
      isNaN(comparedTableDistinctCount) ||
      isNaN(referenceTableDistinctCount)
    ) {
      return true;
    }

    return false;
  };

  useEffect(() => {
    dataGroupingArray?.forEach((item, index) =>
      checkIfDistinctCountIsBiggerThanLimit(
        item.compared_table_column_name ?? '',
        index,
        false
      )
    );
    dataGroupingArray?.forEach((item, index) =>
      checkIfDistinctCountIsBiggerThanLimit(
        item.reference_table_column_name ?? '',
        index,
        true
      )
    );
  }, [statistics, referenceTableStatistics, profileSettings]);

  useEffect(() => {
    getReferenceTableStatistics();
  }, [refTable]);

  useEffect(() => {
    calculateTableDistinctCount(
      dataGroupingArray?.map(
        (item) => item?.compared_table_column_name ?? ''
      ) ?? [],
      dataGroupingArray?.map(
        (item) => item?.reference_table_column_name ?? ''
      ) ?? []
    );
  }, [dataGroupingArray]);

  useEffect(() => {
    onChangeParameters({ refConnection: refConnection });
  }, [refConnection]);

  useEffect(() => {
    onChangeParameters({ refSchema: refSchema });
  }, [refSchema]);

  useEffect(() => {
    onChangeParameters({ refTable: refTable });
  }, [refTable]);

  return (
    <div className="w-full">
      <TableActionGroup onUpdate={() => undefined} addSaveButton={false} />
      <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8">
        <div className="flex items-center justify-center gap-x-5">
          <div className="font-bold text-center">
            Table comparison configuration name:{' '}
          </div>
          {selectedReference === undefined || selectedReference.length === 0 ? (
            <Input
              className={
                name.length === 0
                  ? 'min-w-80 border border-red-500'
                  : 'min-w-80'
              }
              value={name}
              onChange={onChangeName}
              placeholder="Table comparison configuration name"
            />
          ) : (
            <span className="font-bold">{name}</span>
          )}
        </div>
        {popUp === true && (
          <div className="bg-red-300 p-4 rounded-lg text-white border-2 border-red-500">
            A table comparison with this name already exists
          </div>
        )}
        <div className="flex justify-center items-center gap-x-2">
          <SvgIcon
            name="sync"
            className={clsx(
              'w-4 h-4 mr-3',
              disabledDeleting || deletingData ? 'animate-spin' : 'hidden'
            )}
          />
          {isCreating === false && (
            <Button
              color="primary"
              variant="contained"
              disabled={disabledDeleting || deletingData || disabled}
              label="Delete results"
              onClick={() => setDeleteDataDialogOpened(true)}
            />
          )}
          <SvgIcon
            name="sync"
            className={clsx(
              'w-4 h-4 mr-3',
              disabled ? 'animate-spin' : 'hidden'
            )}
          />
          {isCreating === false && (
            <Button
              label="Compare Tables"
              color="primary"
              variant="contained"
              onClick={saveRun}
              disabled={
                disabledDeleting ||
                deletingData ||
                disabled ||
                canUserCompareTables === false
              }
            />
          )}
          <Button
            onClick={onUpdate}
            label="Save"
            color="primary"
            className="w-40"
            disabled={!isButtonEnabled}
          />
          <Button
            label="Back"
            color="primary"
            variant="text"
            className="px-0"
            leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
            onClick={onBack}
          />
        </div>
      </div>

      <div className="px-8 py-4">
        {isCreating === true ||
        extendRefnames === true ||
        tableExist !== true ? (
          <SectionWrapper
            title="Reference table (the source of truth)"
            className="py-8 mb-10 flex w-full items-center justify-between"
            svgIcon={isCreating ? false : true}
            onClick={() => setExtendRefnames(false)}
          >
            <div className="flex flex-col gap-2 w-1/3 mb-3 mr-4">
              <div>Connection</div>
              <Select
                className="flex-1"
                options={connectionOptions}
                value={refConnection}
                onChange={changePropsConnection}
                triggerClassName={
                  connectionExist ? '' : 'border border-red-500'
                }
              />
            </div>
            <div className="flex flex-col gap-2  w-1/3 mb-3 mr-4">
              <div> Schema</div>
              <Select
                className="flex-1"
                options={schemaOptions}
                value={refSchema}
                onChange={changePropsSchema}
                triggerClassName={schemaExist ? '' : 'border border-red-500'}
              />
            </div>
            <div className="flex flex-col gap-2 w-1/3 mb-3">
              <div>Table</div>
              <Select
                className="flex-1"
                options={tableOptions}
                value={refTable}
                onChange={changePropsTable}
                triggerClassName={tableExist ? '' : 'border border-red-500'}
              />
            </div>
          </SectionWrapper>
        ) : (
          <div className="flex items-center gap-4 mb-8">
            <SvgIcon
              name="chevron-right"
              className="w-5 h-5"
              onClick={() => setExtendRefnames(true)}
            />
            <span>Comparing this table to the reference table:</span>
            <a className="text-teal-500 cursor-pointer" onClick={goToRefTable}>
              {refConnection}.{refSchema}.{refTable}
            </a>
          </div>
        )}
        {calculateTableDistinctCount(
          dataGroupingArray?.map(
            (item) => item?.compared_table_column_name ?? ''
          ) ?? [],
          dataGroupingArray?.map(
            (item) => item?.reference_table_column_name ?? ''
          ) ?? []
        ) ? (
          <div className="text-red-500 mb-5">{warningMessage}</div>
        ) : null}
        {(isCreating || extendDg) && tableExist ? (
          <div className="flex gap-4">
            <div className="mr-20 mt-0 relative">
              {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => (
                <div
                  key={index}
                  className="text-sm py-1.5"
                  style={{
                    whiteSpace: 'nowrap',
                    marginBottom: '12.4px',
                    marginTop: '4.6px'
                  }}
                >
                  {item === 0 ? (
                    isCreating === false ? (
                      <SvgIcon
                        name="chevron-down"
                        className="w-5 h-5 absolute top-0"
                        onClick={() => setExtendDg(false)}
                      />
                    ) : (
                      ''
                    )
                  ) : (
                    'Column' + item
                  )}
                </div>
              ))}
            </div>
            <SelectGroupColumnsTable
              className="flex-1"
              title="Data grouping on compared table"
              placeholder="Select column on compared table"
              onChangeDataGroupingArray={onChangeDataGroupingArray}
              columnOptions={[
                { label: '', value: '' },
                ...columnOptions.comparedColumnsOptions
              ]}
              requiredColumnsIndexes={
                getRequiredColumnsIndexes(dataGroupingArray)
                  .comparedMissingIndexes
              }
              responseList={dataGroupingArray?.map(
                (item) => item?.compared_table_column_name ?? ''
              )}
              warningMessageList={listOfWarnings}
              checkIfDistinctCountIsBiggerThanLimit={
                checkIfDistinctCountIsBiggerThanLimit
              }
              dqoLimit={Number(
                profileSettings?.properties?.[
                  'dqo.sensor.limits.sensor-readout-limit'
                ]
              )}
            />
            <SelectGroupColumnsTable
              className="flex-1"
              title="Data grouping on reference table"
              placeholder='"Select column on reference table"'
              refConnection={refConnection}
              refSchema={refSchema}
              refTable={refTable}
              columnOptions={[
                { label: '', value: '' },
                ...columnOptions.referencedColumnsOptions
              ]}
              onChangeDataGroupingArray={onChangeDataGroupingArray}
              requiredColumnsIndexes={
                getRequiredColumnsIndexes(dataGroupingArray)
                  .referenceMissingIndexes
              }
              responseList={dataGroupingArray?.map(
                (item) => item?.reference_table_column_name ?? ''
              )}
              warningMessageList={listOfReferenceWarnings}
              checkIfDistinctCountIsBiggerThanLimit={
                checkIfDistinctCountIsBiggerThanLimit
              }
              dqoLimit={Number(
                profileSettings?.properties?.[
                  'dqo.sensor.limits.sensor-readout-limit'
                ]
              )}
            />
          </div>
        ) : (
          <div className="flex items-center">
            <SvgIcon
              name="chevron-right"
              className="w-5 h-5"
              onClick={() => setExtendDg(true)}
            />
            <div className="px-4">
              Data grouping on compared table:{' '}
              {dataGroupingArray
                ?.map((item) => item?.compared_table_column_name ?? '')
                .map((x, index) =>
                  index !==
                  (dataGroupingArray?.map(
                    (item) => item?.compared_table_column_name ?? ''
                  ).length ?? 9) -
                    1
                    ? x + ','
                    : x
                )}
            </div>
            <div className="pl-8">
              Data grouping on reference table:{' '}
              {dataGroupingArray
                ?.map((item) => item?.reference_table_column_name ?? '')
                .map((x, index) =>
                  index !==
                  (dataGroupingArray?.map(
                    (item) => item?.reference_table_column_name ?? ''
                  ).length ?? 9) -
                    1
                    ? x + ','
                    : x
                )}
            </div>
          </div>
        )}
      </div>
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={deleteDataFunct}
      />
    </div>
  );
};
export default EditReferenceTable;