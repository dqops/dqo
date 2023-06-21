import React, { useEffect, useMemo, useState } from 'react';
import Checkbox from '../Checkbox';
import { DataGroupingConfigurationBasicModel, CheckModel } from '../../api';
import TextArea from '../TextArea';
import Select from '../Select';
import { DataGroupingConfigurationsApi } from '../../services/apiClient';
import { useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../shared/routes";
import Button from "../Button";
import Input from "../Input";

interface ICheckSettingsTabProps {
  check?: CheckModel;
  onChange: (item: CheckModel) => void;
}

const CheckSettingsTab = ({ check, onChange }: ICheckSettingsTabProps) => {
  const { connection, schema, table }: { connection: string; schema: string; table: string;} = useParams();
  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<DataGroupingConfigurationBasicModel[]>([]);

  useEffect(() => {
    DataGroupingConfigurationsApi.getTableGroupingConfigurations(connection ?? '', schema ?? '', table).then(
      (res) => {
        setDataGroupingConfigurations(res.data);
      }
    );
  }, []);

  const options = useMemo(() => {
    return [
      {
        label: '',
        value: ''
      },
      ...dataGroupingConfigurations.map((item) => ({
        label: item.data_grouping_configuration_name ?? '',
        value: item.data_grouping_configuration_name ?? ''
      }))
    ];
  }, [dataGroupingConfigurations]);

  const onAddDataGroupingConfiguration = () => {
    window.location.href = ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'data-streams');
  };

  return (
    <div>
      <div className="">
        <table className="w-full">
          <tbody>
            <tr>
              <td className="px-4 py-2 w-60">Disable data quality check</td>
              <td className="px-4 py-2">
                <div className="flex">
                  <Checkbox
                    checked={check?.disabled}
                    onChange={(value) => onChange({ ...check, disabled: value })}
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2 w-60">Custom data grouping</td>
              <td className="px-4 py-2">
                <div className="flex items-center space-x-2">
                  <Select
                    className="w-50"
                    menuClassName="min-w-50"
                    options={options}
                    disabled={!check?.supports_grouping}
                    value={check?.data_grouping_configuration}
                    onChange={(value) =>
                      onChange({ ...check, data_grouping_configuration: value })
                    }
                  />
                  <Button
                    color="primary"
                    variant="contained"
                    label="Add new data grouping configuration"
                    className="w-50"
                    onClick={onAddDataGroupingConfiguration}
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2">Exclude from KPI</td>
              <td className="px-4 py-2">
                <div className="flex">
                  <Checkbox
                    checked={check?.exclude_from_kpi}
                    onChange={(value) =>
                      onChange({ ...check, exclude_from_kpi: value })
                    }
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2">Include in SLA</td>
              <td className="px-4 py-2">
                <div className="flex">
                  <Checkbox
                    checked={check?.include_in_sla}
                    onChange={(value) =>
                      onChange({ ...check, include_in_sla: value })
                    }
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2">Quality Dimension</td>
              <td className="px-4 py-2">
                <div className="flex">
                  <Input
                    value={check?.quality_dimension}
                    onChange={(e) =>
                      onChange({ ...check, quality_dimension: e.target.value })
                    }
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2 align-top">SQL WHERE condition</td>
              <td className="px-4 py-2">
                <TextArea
                  className="!bg-white border !border-gray-400 !text-gray-900"
                  rows={5}
                  value={check?.filter}
                  onChange={(e) =>
                    onChange({ ...check, filter: e.target.value })
                  }
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CheckSettingsTab;
