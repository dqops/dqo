import React, { ChangeEvent, useEffect, useState } from 'react';
import {
  ConnectionApiClient,
  JobApiClient,
  SchemaApiClient,
  TableApiClient,
  TableComparisonsApi
} from '../../../services/apiClient';
import {
  DeleteStoredDataQueueJobParameters,
  DqoJobHistoryEntryModelStatusEnum,
  TableComparisonConfigurationModelCheckTypeEnum,
  TableComparisonGroupingColumnPairModel,
  TableComparisonModel
} from '../../../api';
import Button from '../../Button';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Select, { Option } from '../../Select';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  setCurrentJobId
} from '../../../redux/actions/source.actions';
import TableActionGroup from './TableActionGroup';
import { SelectGroupColumnsTable } from './SelectGroupColumnsTable';
import clsx from 'clsx';
import DeleteOnlyDataDialog from '../../CustomTree/DeleteOnlyDataDialog';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import useConnectionSchemaTableExists from '../../../hooks/useConnectionSchemaTableExists';

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
  canUserCompareTables
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
  const [normalObj, setNormalObj] = useState<{ [key: number]: number }>();
  const [refObj, setRefObj] = useState<{ [key: number]: number }>();
  const [normalList, setNormalList] = useState<Array<string>>();
  const [refList, setRefList] = useState<Array<string>>();
  const [doubleArray, setDoubleArray] =
    useState<Array<TableComparisonGroupingColumnPairModel>>();
  const [trueArray, setTrueArray] =
    useState<Array<TableComparisonGroupingColumnPairModel>>();
  const [extendRefnames, setExtendRefnames] = useState(false);
  const [extendDg, setExtendDg] = useState(false);
  const [deletingData, setDeletingData] = useState(false);
  const [isButtonEnabled, setIsButtonEnabled] = useState<boolean | undefined>(
    false
  );
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [popUp, setPopUp] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const history = useHistory();
  const dispatch = useActionDispatch();

  const { tableExist, schemaExist, connectionExist } =
    useConnectionSchemaTableExists(refConnection, refSchema, refTable);
  const onSetNormalList = (obj: Array<string>): void => {
    setNormalList(obj);
  };
  const onSetRefList = (obj: Array<string>): void => {
    setRefList(obj);
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
          setTrueArray(res.data.grouping_columns ?? []);
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

  const goToCreateNew = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkTypes,
      connection,
      schema,
      table,
      'data-groupings'
    );

    history.push(`${url}?isEditing=true`);
  };

  const goToRefCreateNew = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkTypes,
      refConnection,
      refSchema,
      refTable,
      'data-groupings'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      checkTypes,
      refConnection,
      refSchema,
      refTable
    );

    dispatch(
      addFirstLevelTab(checkTypes, {
        url,
        value,
        label: refTable,
        state: {}
      })
    );

    history.push(`${url}?isEditing=true`);
  };

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
          grouping_columns: doubleArray ?? [],
          check_type: checkTypes as TableComparisonConfigurationModelCheckTypeEnum,
          time_scale: timePartitioned
        }
      )
        .then(() => {
          onBack(false);
        })
        .catch((err) => {
          console.log('err', err);
        })
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
              grouping_columns: doubleArray ?? []
            }
          )
            .then(() => {
              onBack(false);
            })
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
              grouping_columns: doubleArray ?? []
            }
          )
            .then(() => {
              onBack(false);
            })
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
              grouping_columns: doubleArray ?? []
            }
          )
            .then(() => {
              onBack(false);
            })
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
              grouping_columns: doubleArray ?? []
            }
          )
            .then(() => {
              onBack(false);
            })
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
              grouping_columns: doubleArray ?? []
            }
          )
            .then(() => {
              onBack(false);
            })
        }
        combinedFunc(name);
        setPopUp(false);
      }
    }
    setIsButtonEnabled(false);
    setIsUpdated(false)
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
      // if(value !== refTable){
      //   setDeleteDataDialogOpened(true)
      //   onChangeRefTableChanged(!refTableChanged)
      //  }
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

  const workOnMyObj = (
    listOfColumns: Array<string>
  ): { [key: number]: number } => {
    const initialObject: { [key: number]: number } = {};
    let check = false;
    if (listOfColumns && listOfColumns.length) {
      for (let i = listOfColumns.length - 1; i >= 0; i--) {
        if (listOfColumns[i]?.length === 0 && !check) {
          initialObject[i] = 2;
        } else if (listOfColumns[i]?.length !== 0 && !check) {
          check = true;
          initialObject[i] = 2;
        } else if (check && listOfColumns[i]?.length === 0) {
          initialObject[i] = 1;
        } else if (check && listOfColumns[i]?.length !== 0) {
          initialObject[i] = 2;
        }
        if (listOfColumns[i]?.length !== 0) {
          initialObject[i] = 3;
        }
      }
    }
    return initialObject;
  };

  const algorith = (
    normalListF: { [key: number]: number },
    refListF: { [key: number]: number }
  ) => {
    const normalList = normalListF;
    const refList = refListF;

    let checkNormal = false;
    let checkRef = false;

    for (let i = 8; i >= 0; i--) {
      if (normalList[i] === 2 && refList[i] === 3) {
        normalList[i] = 1;
      } else if (normalList[i] === 3 && refList[i] === 2) {
        refList[i] = 1;
      }

      if (normalList[i] === 1) {
        checkNormal = true;
      }
      if (checkNormal === true && normalList[i] === 2) {
        normalList[i] = 1;
      }

      if (refList[i] === 1) {
        checkRef = true;
      } else if (checkRef === true && refList[i] === 2) {
        refList[i] = 1;
      }
    }
    setRefObj(refList);
    setNormalObj(normalList);
  };
  const combinedArray = () => {
    if (refList && normalList) {
      const combinedArray: Array<TableComparisonGroupingColumnPairModel> =
        normalList &&
        refList &&
        normalList.map((item, index) => ({
          compared_table_column_name: item,
          reference_table_column_name: refList[index]
        }));
      const trim = combinedArray.filter(
        (item) =>
          item.compared_table_column_name !== '' ||
          item.reference_table_column_name !== ''
      );

      setDoubleArray(trim);
      return trim;
    }
    return [];
  };

  const splitArrays = () => {
    if (trueArray) {
      const comparedArr = trueArray.map((x) =>
        typeof x.compared_table_column_name === 'string'
          ? x.compared_table_column_name
          : ''
      );
      const refArr = trueArray.map((x) =>
        typeof x.reference_table_column_name === 'string'
          ? x.reference_table_column_name
          : ''
      );
      return { comparedArr, refArr };
    }
  };

  useEffect(() => {
    if (
      normalList &&
      refList &&
      normalList.length !== 0 &&
      refList.length !== 0
    ) {
      algorith(workOnMyObj(normalList ?? []), workOnMyObj(refList ?? []));
      splitArrays();
      onChange({ grouping_columns: combinedArray() });
    }
  }, [normalList, refList]);

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
        refList?.filter((c) =>c.length!==0 ).length === normalList?.filter((c) =>c.length!==0 ).length &&
          (isUpdated ||
        isUpdatedParent)
    ));
  }, [normalList, refList, isUpdated, isUpdatedParent]);

  const deleteDataFunct = async (params: {
    [key: string]: string | boolean;
  }) => {
    setDeleteDataDialogOpened(false);
    try {
      setDeletingData(!isDataDeleted);
      const res = await JobApiClient.deleteStoredData({
        ...(cleanDataTemplate || {}),
        ...params
      });
      dispatch(
        setCurrentJobId(
          checkTypes,
          firstLevelActiveTab,
          (res.data as any)?.jobId
        )
      );
      setJobId((res.data as any)?.jobId);
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

  console.log(isButtonEnabled)

  return (
    <div className="w-full">
      <TableActionGroup
        onUpdate={() => undefined}
        addSaveButton={false}
      />
      <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8">
        <div className="flex items-center justify-center gap-x-5">
          <div className="font-bold text-center">
            Table comparison configuration name:{' '}
          </div>{(selectedReference === undefined || selectedReference.length === 0) ? 
          <Input
            className={
              name.length === 0 ? 'min-w-80 border border-red-500' : 'min-w-80'
            }
            value={name}
            onChange={onChangeName}
            placeholder="Table comparison configuration name"
          />
          : <span className='font-bold'>{name}</span>}
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
          className='w-40'
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
              goToCreateNew={goToCreateNew}
              placeholder="Select column on compared table"
              onSetNormalList={onSetNormalList}
              object={normalObj}
              responseList={splitArrays()?.comparedArr}
            />

            <SelectGroupColumnsTable
              className="flex-1"
              title="Data grouping on reference table"
              goToCreateNew={goToRefCreateNew}
              placeholder='"Select column on reference table"'
              refConnection={refConnection}
              refSchema={refSchema}
              refTable={refTable}
              onSetRefList={onSetRefList}
              object={refObj}
              responseList={splitArrays()?.refArr}
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
              {splitArrays()?.comparedArr.map((x, index) =>
                index !== (splitArrays()?.comparedArr.length ?? 9) - 1
                  ? x + ','
                  : x
              )}
            </div>
            <div className="pl-8">
              Data grouping on reference table:{' '}
              {splitArrays()?.refArr.map((x, index) =>
                index !== (splitArrays()?.refArr.length ?? 9) - 1 ? x + ',' : x
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
