import React, { useEffect, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../../components/Button';
import { CheckConfigurationModel, FieldModel } from '../../api';
import SensorParameters from '../../components/DataQualityChecks/SensorParameters';
import Checkbox from '../../components/Checkbox';
import CheckRuleItem from '../../components/DataQualityChecks/CheckRuleItem';

interface UpdateCheckModelProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (value: CheckConfigurationModel) => void;

  check?: CheckConfigurationModel;
}

export const UpdateCheckModel = ({
  open,
  onClose,
  onSubmit,
  check
}: UpdateCheckModelProps) => {
  const [updatedCheck, setUpdatedCheck] = useState<CheckConfigurationModel>();

  useEffect(() => {
    setUpdatedCheck(check);
  }, [check]);

  const handleSubmit = () => {
    if (updatedCheck) {
      onSubmit(updatedCheck);
      onClose();
    }
  };

  const handleChange = (obj: any) => {
    setUpdatedCheck((prev) => ({
      ...prev,
      ...obj
    }));
  };



  return (
    <div>
      <Dialog open={open} handler={onClose} className="min-w-150 max-w-150">
        <DialogBody className="pt-10 pb-2 px-8">
          <div className="w-full flex flex-col items-center">
            <h1 className="text-center mb-4 text-gray-700 text-2xl">
              Update Check: {updatedCheck?.check_name}
            </h1>
          </div>
          <div className="relative z-10 border rounded text-gray-700 border-gray-300 px-4 py-4">
            <p className="text-gray-700 text-lg mb-4">Sensor parameters</p>

            <SensorParameters
              parameters={updatedCheck?.sensor_parameters || []}
              onChange={(parameters: FieldModel[]) =>
                handleChange({ sensor_parameters: parameters })
              }
              onUpdate={() => undefined}
            />
          </div>

          <div className="grid grid-cols-3 my-4">
            <div className="bg-yellow-100 border border-gray-300 py-2">
              <CheckRuleItem
                parameters={updatedCheck?.warning}
                onChange={(warning) =>
                  handleChange({
                    warning
                  })
                }
                type="warning"
                onUpdate={() => {}}
              />
            </div>

            <div className="bg-orange-100 border border-gray-300 py-2">
              <CheckRuleItem
                parameters={updatedCheck?.error}
                onChange={(error) =>
                  handleChange({
                    error
                  })
                }
                type="error"
                onUpdate={() => {}}
              />
            </div>

            <div className="bg-red-100 border border-gray-300 py-2">
              <CheckRuleItem
                parameters={updatedCheck?.fatal}
                onChange={(fatal) =>
                  handleChange({
                    fatal
                  })
                }
                type="fatal"
                onUpdate={() => {}}
              />
            </div>
          </div>
          <div className="text-gray-700">
            <Checkbox
              label="Override existing configurations"
              checked={true}
              onChange={() => {}}
            />
          </div>
        </DialogBody>
        <DialogFooter className="flex justify-center space-x-6 px-8 pb-8">
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
            onClick={handleSubmit}
            label="Update all selected checks"
          />
        </DialogFooter>
      </Dialog>
    </div>
  );
};
