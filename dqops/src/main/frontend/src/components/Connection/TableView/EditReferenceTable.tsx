import React, { ChangeEvent, useEffect, useState } from 'react';
import {
  ConnectionApiClient,
  DataGroupingConfigurationsApi,
  SchemaApiClient,
  TableApiClient,
  TableComparisonsApi
} from '../../../services/apiClient';
import {
  DataGroupingConfigurationBasicModel,
  TableComparisonGroupingColumnPairModel
} from '../../../api';
import Button from '../../Button';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Select, { Option } from '../../Select';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import TableActionGroup from './TableActionGroup';
import { SelectGroupColumnsTable } from './SelectGroupColumnsTable';

type EditReferenceTableProps = {
  onBack: () => void;
  selectedReference?: string;
};

const EditReferenceTable = ({
  onBack,
  selectedReference
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
  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<
    DataGroupingConfigurationBasicModel[]
  >([]);
  const [refDataGroupingConfigurations, setRefDataGroupingConfigurations] =
    useState<DataGroupingConfigurationBasicModel[]>([]);
  const [dataGroupingConfiguration, setDataGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const [refDataGroupingConfiguration, setRefDataGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
  const [normalObj, setNormalObj] = useState<{ [key: number]: number }>();
  const [refObj, setRefObj] = useState<{ [key: number]: number }>();
  const [normalList, setNormalList] = useState<Array<string>>();
  const [refList, setRefList] = useState<Array<string>>();
  const [bool, setBool] = useState(false);
  const [doubleArray, setDoubleArray] =
    useState<Array<TableComparisonGroupingColumnPairModel>>();
  const history = useHistory();
  const dispatch = useActionDispatch();

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
        setName(res.data?.table_comparison_configuration_name ?? '');
        setRefConnection(res.data?.reference_connection ?? '');
        setRefSchema(res.data?.reference_table?.schema_name ?? '');
        setRefTable(res.data?.reference_table?.table_name ?? '');
      });
    }
  }, [selectedReference]);

  useEffect(() => {
    DataGroupingConfigurationsApi.getTableGroupingConfigurations(
      connection,
      schema,
      table
    ).then((res) => {
      setDataGroupingConfigurations(res.data);
      setDataGroupingConfiguration(
        res.data.find((item) => item.default_data_grouping_configuration)
      );
    });
  }, [connection, schema, table]);

  useEffect(() => {
    if (refConnection && refSchema && refTable) {
      DataGroupingConfigurationsApi.getTableGroupingConfigurations(
        refConnection,
        refSchema,
        refTable
      ).then((res) => {
        setRefDataGroupingConfigurations(res.data);
        setRefDataGroupingConfiguration(
          res.data.find((item) => item.default_data_grouping_configuration)
        );
      });
    }
  }, [refConnection, refSchema, refTable]);
  useEffect(() => {
    if (refConnection) {
      SchemaApiClient.getSchemas(refConnection).then((res) => {
        setSchemaOptions(
          res.data.map((item) => ({
            label: item.schema_name ?? '',
            value: item.schema_name ?? ''
          }))
        );
      });
    }
  }, [refConnection]);

  useEffect(() => {
    if (refConnection && refSchema) {
      TableApiClient.getTables(refConnection, refSchema).then((res) => {
        setTableOptions(
          res.data.map((item) => ({
            label: item.target?.table_name ?? '',
            value: item.target?.table_name ?? ''
          }))
        );
      });
    }
  }, [refConnection, refSchema]);

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

  const onUpdate = () => {
    setIsUpdating(true);
    if (selectedReference) {
      TableComparisonsApi.updateTableComparisonConfiguration(
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
          compared_table_grouping_name:
            dataGroupingConfiguration?.data_grouping_configuration_name ?? '',
          reference_table_grouping_name:
            refDataGroupingConfiguration?.data_grouping_configuration_name ??
            '',
          grouping_columns: doubleArray ?? []
        }
      )
        .then((res) => console.log(res.data))
        .then(() => {
          onBack();
        })
        .catch((err) => {
          console.log('err', err);
        })
        .finally(() => {
          setIsUpdating(false);
        });
    } else {
      TableComparisonsApi.createTableComparisonConfiguration(
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
          compared_table_grouping_name:
            dataGroupingConfiguration?.data_grouping_configuration_name ?? '',
          reference_table_grouping_name:
            refDataGroupingConfiguration?.data_grouping_configuration_name ??
            '',
          grouping_columns: doubleArray ?? []
        }
      )
        .then((res) => console.log(res.request))
        .then(() => {
          onBack();
        })
        .finally(() => {
          setIsUpdating(false);
        });
    }
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
    setIsUpdated(true);
  };

  const changePropsTable = (value: string) => {
    setRefTable(value);
    setIsUpdated(true);
  };
  const changePropsSchema = (value: string) => {
    setRefSchema(value);
    setIsUpdated(true);
  };
  const changePropsConnection = (value: string) => {
    setRefConnection(value);
    setIsUpdated(true);
  };

  const changeDataGroupingProps = (
    value?: DataGroupingConfigurationBasicModel | undefined
  ) => {
    setDataGroupingConfiguration(value);
    setIsUpdated(true);
  };

  const changeRefDataGroupingProps = (
    value?: DataGroupingConfigurationBasicModel | undefined
  ) => {
    setRefDataGroupingConfiguration(value);
    setIsUpdated(true);
  };

  const workOnMyObj = (
    listOfColumns: Array<string>
  ): { [key: number]: number } => {
    const initialObject: { [key: number]: number } = {};
    let check = false;

    for (let i = listOfColumns.length - 1; i >= 0; i--) {
      if (listOfColumns[i].length === 0 && !check) {
        initialObject[i] = 2;
      } else if (listOfColumns[i].length !== 0 && !check) {
        check = true;
        initialObject[i] = 2;
      } else if (check && listOfColumns[i].length === 0) {
        initialObject[i] = 1;
      } else if (check && listOfColumns[i].length !== 0) {
        initialObject[i] = 2;
      }
      if (listOfColumns[i].length !== 0) {
        initialObject[i] = 3;
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
      if (
        trim.find(
          (x) =>
            x.compared_table_column_name?.length === 0 ||
            x.reference_table_column_name?.length === 0
        )
      ) {
        setBool(false);
      } else {
        setBool(true);
      }
    }
  };
  console.log(bool);

  useEffect(() => {
    algorith(workOnMyObj(normalList ?? []), workOnMyObj(refList ?? []));
    combinedArray();
  }, [normalList, refList]);

  return (
    <div>
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated && bool}
        isUpdating={isUpdating}
      />
      <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8">
        <div className="flex items-center justify-center gap-x-5">
          <div className="font-bold text-center">
            Table comparison configuration name:{' '}
          </div>
          <Input
            className="min-w-80"
            value={name}
            onChange={onChangeName}
            placeholder="Table comparison configuration name"
          />
        </div>
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>

      <div className="px-8 py-4">
        <SectionWrapper
          title="Reference table (the source of truth)"
          className="py-8 mb-10"
        >
          <div className="flex gap-2 items-center mb-3">
            <div className="w-60">Connection</div>
            <Select
              className="flex-1"
              options={connectionOptions}
              value={refConnection}
              onChange={changePropsConnection}
            />
          </div>
          <div className="flex gap-2 items-center mb-3">
            <div className="w-60">Schema</div>
            <Select
              className="flex-1"
              options={schemaOptions}
              value={refSchema}
              onChange={changePropsSchema}
            />
          </div>
          <div className="flex gap-2 items-center">
            <div className="w-60">Table</div>
            <Select
              className="flex-1"
              options={tableOptions}
              value={refTable}
              onChange={changePropsTable}
            />
          </div>
        </SectionWrapper>

        <div className="flex gap-4">
          <div className=" mr-20 mt-3">
            {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => (
              <div
                key={index}
                className="text-sm py-1.5"
                style={{
                  whiteSpace: 'nowrap',
                  marginBottom: '12.6px',
                  marginTop: '12.6px'
                }}
              >
                Column {item}
              </div>
            ))}
          </div>
          <SelectGroupColumnsTable
            className="flex-1"
            title="Data grouping on compared table"
            dataGroupingConfigurations={dataGroupingConfigurations}
            dataGroupingConfiguration={dataGroupingConfiguration}
            setDataGroupingConfiguration={changeDataGroupingProps}
            goToCreateNew={goToCreateNew}
            placeholder="Select column on compared table"
            onSetNormalList={onSetNormalList}
            object={normalObj}
          />

          <SelectGroupColumnsTable
            className="flex-1"
            title="Data grouping on reference table"
            dataGroupingConfigurations={refDataGroupingConfigurations}
            dataGroupingConfiguration={refDataGroupingConfiguration}
            setDataGroupingConfiguration={changeRefDataGroupingProps}
            goToCreateNew={goToRefCreateNew}
            placeholder='"Select column on reference table"'
            refConnection={refConnection}
            refSchema={refSchema}
            refTable={refTable}
            onSetRefList={onSetRefList}
            object={refObj}
          />
        </div>
      </div>
    </div>
  );
};

export default EditReferenceTable;
