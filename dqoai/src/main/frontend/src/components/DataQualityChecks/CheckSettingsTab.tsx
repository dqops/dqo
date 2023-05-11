import React, { useEffect, useMemo, useState } from 'react';
import Checkbox from '../Checkbox';
import { DataStreamBasicModel, UICheckModel } from '../../api';
import TextArea from '../TextArea';
import Select from '../Select';
import { DataStreamsApi } from '../../services/apiClient';
import { useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../shared/routes";
import Button from "../Button";

interface ICheckSettingsTabProps {
  check?: UICheckModel;
  onChange: (item: UICheckModel) => void;
}

const CheckSettingsTab = ({ check, onChange }: ICheckSettingsTabProps) => {
  const { connection, schema, table }: { connection: string; schema: string; table: string;} = useParams();
  const [dataStreams, setDataStreams] = useState<DataStreamBasicModel[]>([]);

  useEffect(() => {
    DataStreamsApi.getDataStreams(connection ?? '', schema ?? '', table).then(
      (res) => {
        setDataStreams(res.data);
      }
    );
  }, []);

  const options = useMemo(() => {
    return [
      {
        label: '',
        value: ''
      },
      ...dataStreams.map((item) => ({
        label: item.data_stream_name ?? '',
        value: item.data_stream_name ?? ''
      }))
    ];
  }, [dataStreams]);

  const onAddDataStream = () => {
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
              <td className="px-4 py-2 w-60">Custom data stream</td>
              <td className="px-4 py-2">
                <div className="flex items-center space-x-2">
                  <Select
                    className="w-50"
                    menuClassName="min-w-50"
                    options={options}
                    disabled={!check?.supports_data_streams}
                    value={check?.data_stream}
                    onChange={(value) =>
                      onChange({ ...check, data_stream: value })
                    }
                  />
                  <Button
                    color="primary"
                    variant="contained"
                    label="Add new data stream"
                    className="w-50"
                    onClick={onAddDataStream}
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
