import React, { useEffect, useState } from "react";
import { TimezonesApi } from "../../services/apiClient";
import SelectInput from "../SelectInput";

interface ITimezoneSelectProps {
  value?: string;
  onChange: (value: string) => void;
}

const TimezoneSelect = ({ value, onChange }: ITimezoneSelectProps) => {
  const [options, setOptions] = useState([]);

  useEffect(() => {
    TimezonesApi.test().then((res) => {
      setOptions((res.data as any).map((item: string) => ({
        label: item,
        value: item,
      })));
    })
  }, []);

  return (
    <div>
      <SelectInput options={options} value={value} onChange={onChange} limit={10} />
    </div>
  );
};

export default TimezoneSelect;
