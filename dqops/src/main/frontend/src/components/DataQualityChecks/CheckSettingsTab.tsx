import React, { useEffect, useMemo, useState } from 'react';
import Checkbox from '../Checkbox';
import { DataGroupingConfigurationListModel, CheckModel } from '../../api';
import TextArea from '../TextArea';
import Select from '../Select';
import { DataGroupingConfigurationsApi } from '../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../shared/routes";
import Button from "../Button";
import Input from "../Input";
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import clsx from 'clsx';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

interface ICheckSettingsTabProps {
  check?: CheckModel;
  onChange: (item: CheckModel) => void;
  isDefaultEditing?: boolean
}

const CheckSettingsTab = ({ check, onChange, isDefaultEditing }: ICheckSettingsTabProps) => {
  const { connection, schema, table }: { connection: string; schema: string; table: string;} = useParams();
  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<DataGroupingConfigurationListModel[]>([]);
  const history = useHistory()
  const dispatch = useActionDispatch();
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  useEffect(() => {
    if(isDefaultEditing !== true){
      DataGroupingConfigurationsApi.getTableGroupingConfigurations(connection ?? '', schema ?? '', table).then(
        (res) => {
          setDataGroupingConfigurations(res.data);
        }
        );
      }
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
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'data-groupings'
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        state: {},
        label: table
      })
    );
    history.push(url);
  };

  return (
    <div>
      <div className={clsx("", userProfile.can_manage_scheduler !== true ? "pointer-events-none cursor-not-allowed" : "")}>
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
            {isDefaultEditing !== true && 
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
                    onClick={onAddDataGroupingConfiguration}
                  />
                </div>
              </td>  
            </tr>
                  }
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
              <td className="px-4 py-2">Include in SLA (Data Contract)</td>
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
              <td className="px-4 py-2">Data Quality Dimension</td>
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
