import React, { ChangeEvent, useEffect, useState } from 'react';
import {
  ConnectionApiClient,
  DataGroupingConfigurationsApi,
  SchemaApiClient,
  TableApiClient,
  TableComparisonsApi
} from '../../../services/apiClient';
import { DataGroupingConfigurationBasicModel } from '../../../api';
import Button from '../../Button';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Select, { Option } from '../../Select';
import { useHistory, useParams } from 'react-router-dom';
import { SelectDataGroupingForTable } from './SelectDataGroupingForTable';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import TableActionGroup from './TableActionGroup';

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
  const history = useHistory();
  const dispatch = useActionDispatch();

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
        setName(res.data?.reference_table_configuration_name ?? '');
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
      'data-streams'
    );

    history.push(`${url}?isEditing=true`);
  };

  const goToRefCreateNew = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkTypes,
      refConnection,
      refSchema,
      refTable,
      'data-streams'
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
      TableComparisonsApi.updateReferenceTable(
        connection,
        schema,
        table,
        selectedReference ?? '',
        {
          reference_table_configuration_name: name,
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
            refDataGroupingConfiguration?.data_grouping_configuration_name ?? ''
        }
      )
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
      TableComparisonsApi.createReferenceTable(connection, schema, table, {
        reference_table_configuration_name: name,
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
          refDataGroupingConfiguration?.data_grouping_configuration_name ?? ''
      })
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

  return (
    <div>
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8">
        <Input
          className="min-w-80"
          value={name}
          onChange={onChangeName}
          placeholder="Ref table name"
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
          <div className="mt-26 mr-20">
            {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => (
              <div key={index} className="text-sm py-1.5">
                Grouping dimension level {item}
              </div>
            ))}
          </div>
          <SelectDataGroupingForTable
            className="flex-1"
            title="Data grouping on compared table"
            dataGroupingConfigurations={dataGroupingConfigurations}
            dataGroupingConfiguration={dataGroupingConfiguration}
            setDataGroupingConfiguration={changeDataGroupingProps}
            goToCreateNew={goToCreateNew}
          />
          <div className="flex flex-col justify-center">
            <SvgIcon name="not-equal" className="w-6 h-6 text-red-700" />
          </div>
          <SelectDataGroupingForTable
            className="flex-1"
            title="Data grouping on reference table"
            dataGroupingConfigurations={refDataGroupingConfigurations}
            dataGroupingConfiguration={refDataGroupingConfiguration}
            setDataGroupingConfiguration={changeRefDataGroupingProps}
            goToCreateNew={goToRefCreateNew}
          />
        </div>
      </div>
    </div>
  );
};

export default EditReferenceTable;
