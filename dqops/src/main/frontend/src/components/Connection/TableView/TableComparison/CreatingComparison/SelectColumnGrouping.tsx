import React, { useEffect, useState } from 'react';
import {
  DqoSettingsModel,
  TableColumnsStatisticsModel
} from '../../../../../api';
import {
  ColumnApiClient,
  EnviromentApiClient
} from '../../../../../services/apiClient';
import { TParameters } from '../../../../../shared/constants';
import { useDecodedParams } from '../../../../../utils';
import { Option } from '../../../../Select';
import SvgIcon from '../../../../SvgIcon';
import { SelectGroupColumnsTable } from '../../SelectGroupColumnsTable';
import { getRequiredColumnsIndexes } from '../TableComparisonUtils';

type TSelectDataGrouping = {
  onChangeParameters: (obj: Partial<TParameters>) => void;
  onChangeEditColumnGrouping: (open: boolean) => void;
  columnOptions: {
    comparedColumnsOptions: Option[];
    referencedColumnsOptions: Option[];
  };
  editConfigurationParameters: TParameters;
};

export default function SelectColumnGrouping({
  onChangeParameters,
  onChangeEditColumnGrouping,
  columnOptions,
  editConfigurationParameters
}: TSelectDataGrouping) {
  const {
    connection,
    schema,
    table
  }: {
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const [profileSettings, setProfileSettings] = useState<DqoSettingsModel>();
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [listOfWarnings, setListOfWarnings] = useState<Array<boolean>>(
    Array(8).fill(false)
  );
  const [listOfReferenceWarnings, setListOfReferenceWarnings] = useState<
    Array<boolean>
  >(Array(8).fill(false));
  const [referenceTableStatistics, setReferenceTableStatistics] =
    useState<TableColumnsStatisticsModel>();

  const fetchProfileSettings = async () => {
    try {
      const res = await EnviromentApiClient.getDqoSettings();
      setProfileSettings(res.data);
    } catch (err) {
      console.error(err);
    }
  };

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
      if (
        editConfigurationParameters.refTable &&
        editConfigurationParameters.refSchema &&
        editConfigurationParameters.refConnection
      ) {
        await ColumnApiClient.getColumnsStatistics(
          editConfigurationParameters.refConnection,
          editConfigurationParameters.refSchema,
          editConfigurationParameters.refTable
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

  const onChangeDataGroupingArray = (
    reference: boolean,
    index: number,
    columnName: string
  ) => {
    const data = [...(editConfigurationParameters.dataGroupingArray ?? [])];
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
    onChangeParameters({ dataGroupingArray: data });
  };

  useEffect(() => {
    if (editConfigurationParameters.refTable) {
      fetchProfileSettings();
      getColumnsStatistics();
      getReferenceTableStatistics();
    }
  }, [
    editConfigurationParameters.refTable,
    editConfigurationParameters.refSchema,
    editConfigurationParameters.refConnection
  ]);

  return (
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
              <SvgIcon
                name="chevron-down"
                className="w-5 h-5 absolute top-0"
                onClick={() => onChangeEditColumnGrouping(false)}
              />
            ) : (
              'Column' + item
            )}
          </div>
        ))}
        <div className="mt-8">WHERE filters</div>
      </div>
      <SelectGroupColumnsTable
        className="flex-1"
        title="Data grouping on compared table"
        placeholder="Select column on compared table"
        onChangeDataGroupingArray={onChangeDataGroupingArray}
        onChangeParameters={onChangeParameters}
        columnOptions={[
          { label: '', value: '' },
          ...columnOptions.comparedColumnsOptions
        ]}
        requiredColumnsIndexes={
          getRequiredColumnsIndexes(
            editConfigurationParameters.dataGroupingArray ?? []
          ).comparedMissingIndexes
        }
        responseList={(
          editConfigurationParameters.dataGroupingArray ?? []
        )?.map((item) => item?.compared_table_column_name ?? '')}
        warningMessageList={listOfWarnings}
        checkIfDistinctCountIsBiggerThanLimit={
          checkIfDistinctCountIsBiggerThanLimit
        }
        dqoLimit={Number(
          profileSettings?.properties?.[
            'dqo.sensor.limits.sensor-readout-limit'
          ]
        )}
        filter={editConfigurationParameters.compared_table_filter}
      />
      <SelectGroupColumnsTable
        className="flex-1"
        title="Data grouping on reference table"
        placeholder="Select column on reference table"
        columnOptions={[
          { label: '', value: '' },
          ...columnOptions.referencedColumnsOptions
        ]}
        onChangeDataGroupingArray={onChangeDataGroupingArray}
        onChangeParameters={onChangeParameters}
        requiredColumnsIndexes={
          getRequiredColumnsIndexes(
            editConfigurationParameters.dataGroupingArray ?? []
          ).referenceMissingIndexes
        }
        refTable={editConfigurationParameters.refTable}
        responseList={(
          editConfigurationParameters.dataGroupingArray ?? []
        )?.map((item) => item?.reference_table_column_name ?? '')}
        warningMessageList={listOfReferenceWarnings}
        checkIfDistinctCountIsBiggerThanLimit={
          checkIfDistinctCountIsBiggerThanLimit
        }
        dqoLimit={Number(
          profileSettings?.properties?.[
            'dqo.sensor.limits.sensor-readout-limit'
          ]
        )}
        filter={editConfigurationParameters.reference_table_filter}
      />
    </div>
  );
}
