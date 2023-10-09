
import {
    Dialog,
    DialogBody,
    DialogFooter,
    DialogHeader,
    Radio
  } from '@material-tailwind/react';
  import DatePicker from '../DatePicker';
  import Button from '../Button';
  import React, { useEffect, useMemo, useState } from 'react';
  import moment from 'moment';
  import Checkbox from '../Checkbox';
  import { useSelector } from 'react-redux';
  import { IRootState } from '../../redux/reducers';
import Select from '../Select';
import { CheckTypes } from '../../shared/routes';
import { getFirstLevelState } from '../../redux/selectors';
import { useParams } from 'react-router-dom';
import SelectInput from '../SelectInput';
import { DeleteStoredDataQueueJobParameters, SensorListModel } from '../../api';
import { SensorsApi } from '../../services/apiClient';
  
  type DeleteOnlyDataDialogProps = {
    open: boolean;
    onClose: VoidFunction;
    onDelete: (
      params: DeleteStoredDataQueueJobParameters
    ) => void;
    columnBool?: boolean;
    nameOfCol?: string;
    nodeLevel?: string;
    checkTypes: CheckTypes
  };
  const DeleteStoredDataExtendedPopUp = ({
    open,
    onClose,
    onDelete,
    nameOfCol,
    nodeLevel,
  }: DeleteOnlyDataDialogProps) => {
    const { checkTypes }: { checkTypes: CheckTypes } = useParams();
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [mode, setMode] = useState('all');
    const [allSensors, setAllSensors] = useState<SensorListModel[]>([])
    const [params, setParams] = useState<Partial<DeleteStoredDataQueueJobParameters>>({
      deleteErrors: true,
      deleteStatistics: true,
      deleteCheckResults: true,
      deleteSensorReadouts: true
    });
    const { checksUI } = useSelector(
        getFirstLevelState(checkTypes)
      );
    const { userProfile } = useSelector(
      (state: IRootState) => state.job || {}
    );
  
    const myArr: string[] = [];
    useEffect(() => {
      if (nameOfCol && nameOfCol?.length > 0) {
        myArr.push(nameOfCol);
      }
    }, [myArr]);
  
    const toUTCString = (date: Date) => moment(date).utc().format('YYYY-MM-DD');
    const onConfirm = () => {
      if (mode === 'part') {
        onDelete({
          ...params,
          dateStart: toUTCString(startDate),
          dateEnd: toUTCString(endDate)
        });
      } else {
        onDelete({ ...params });
      }
    };
  
    const onChangeParams = (obj: { [key: string]: boolean }) => {
      setParams({
        ...params,
        ...obj
      });
    };
  
    const isDisabled = useMemo(() => mode === 'all', [mode]);


    console.log(params)
    console.log(allSensors)

    const getAllSensors = async () => {
        await SensorsApi.getAllSensors()
         .then((res) => setAllSensors(res.data))
         .catch((err) => console.error(err))
 
    }
 
    useEffect(() => {
        getAllSensors()
    }, [allSensors])

  
    return (
      <Dialog open={open} handler={onClose} className="min-w-300 p-4">
        <DialogHeader className="font-bold text-center justify-center">
          Delete data {nodeLevel}
        </DialogHeader>
        <DialogBody>
          <div className="flex flex-col">
            <div>
              <Radio
                id="all"
                name="delete_mode"
                value="all"
                label="All"
                checked={mode === 'all'}
                onChange={() => setMode('all')}
                className="outline-none"
                color="teal"
              />
            </div>
            <div>
              <div className="flex items-start text-gray-700">
                <Radio
                  id="part"
                  name="delete_mode"
                  value="part"
                  checked={mode === 'part'}
                  onChange={() => setMode('part')}
                  color="teal"
                />
                <div className="mt-2">
                  <p>For the time range:</p>
                  <div className="flex space-x-6 items-center">
                    <DatePicker
                      showIcon
                      placeholderText="Select date start"
                      onChange={setStartDate}
                      selected={startDate}
                      disabled={isDisabled}
                      dateFormat="yyyy-MM-dd"
                    />
                    <span>to</span>
                    <DatePicker
                      showIcon
                      placeholderText="Select date end"
                      onChange={setEndDate}
                      selected={endDate}
                      disabled={isDisabled}
                      dateFormat="yyyy-MM-dd"
                    />
                  </div>
                </div>
              </div>
            </div>
        </div>
              <div className="flex w-full gap-4 px-4 my-4 text-gray-700 ml-7">
                <div>
                <Checkbox
                  checked={params.deleteStatistics}
                  onChange={(deleteStatistics) =>
                    onChangeParams({ deleteStatistics })
                }
                label="All basic statistics results"
                checkClassName="bg-teal-500"
                />
                </div>
                <div className='flex flex-col space-y-3'>
                    <Checkbox
                      checked={params.deleteCheckResults}
                      onChange={(deleteCheckResults) =>
                        onChangeParams({ deleteCheckResults })
                      }
                      label="All check results"
                      checkClassName="bg-teal-500"
                    />
                    <Checkbox
                      checked={params.deleteCheckResults}
                      onChange={(deleteCheckResults) =>
                        onChangeParams({ deleteCheckResults })
                      }
                      label="Check results from category"
                      checkClassName="bg-teal-500"
                    />
                    <SelectInput options={checksUI?.categories?.map((item: any ) => 
                    ({label: item.category, value: item.category}))} 
                    value={params.checkCategory} 
                    onChange={(value) => onChangeParams({checkCategory: value})}/> 
                    <Checkbox
                      checked={params.deleteCheckResults}
                      onChange={(deleteCheckResults) =>
                        onChangeParams({})
                      }
                      label="Single Check results from category"
                      checkClassName="bg-teal-500"
                    />
                   <SelectInput options={checksUI?.categories?.find((item: any ) => 
                   (item?.category === params.checkCategory))?.checks?.map((item : any) => 
                    ({label: item.check_name, value: item.check_name}))} 
                    value={params.checkName} 
                    onChange={(value) => onChangeParams({checkName: value})}/> 
                    </div>
                <div className='flex flex-col space-y-3'>
                    <Checkbox
                      checked={params.deleteSensorReadouts}
                      onChange={(deleteSensorReadouts) =>
                        onChangeParams({ deleteSensorReadouts })
                      }
                      label="All sensor readouts"
                      checkClassName="bg-teal-500"
                    />
                     <Checkbox
                      checked={params.deleteCheckResults}
                      onChange={(deleteCheckResults) =>
                        onChangeParams({ deleteCheckResults })
                      }
                      label="Single sensor readout"
                      checkClassName="bg-teal-500"
                    />
                    <SelectInput 
                    options={allSensors?.map((x: any) => ({
                        label: x.full_sensor_name, value: x.full_sensor_name ?? ""
                    }))} 
                    value={params.sensorName} 
                    onChange={(value) => onChangeParams({sensorName: value})}/> 

                    </div>
                    <div>
                    <Checkbox
                      checked={params.deleteErrors}
                      onChange={(deleteErrors) => onChangeParams({ deleteErrors })}
                      label="All execution errors"
                      checkClassName="bg-teal-500"
                    />
                </div>
            
          </div>
        </DialogBody>
        <DialogFooter className="flex gap-6 items-center mt-10">
          <Button
            color="primary"
            variant="outlined"
            className="px-8"
            onClick={onClose}
            label="Cancel"
          />
          <Button
            color="primary"
            className="px-8"
            onClick={onConfirm}
            label="Delete"
            disabled={userProfile.can_delete_data !== true}
          />
        </DialogFooter>
      </Dialog>
    );
  };
  
  export default DeleteStoredDataExtendedPopUp;
  