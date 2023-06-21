import React from 'react';
import { FieldModel } from '../../api';
import FieldValue from "./FieldValue";

interface IDisplaySensorParametersProps {
  parameters: FieldModel[];
}

const DisplaySensorParameters = ({
  parameters,
}: IDisplaySensorParametersProps) => {
  return (
    <div className="w-full pr-8">
      {parameters.length ? (
        <div className="flex space-x-2">
          {parameters.map((item, index) => (
            <div key={index} className="">
              <FieldValue field={item} />
            </div>
          ))}
        </div>
      ) : (
        <div>No sensor parameters</div>
      )}
    </div>
  );
};

export default DisplaySensorParameters;
