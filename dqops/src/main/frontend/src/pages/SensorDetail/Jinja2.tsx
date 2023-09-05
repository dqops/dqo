import React from "react";
import CodeMirror from '@uiw/react-codemirror';
import { ProviderSensorModel } from "../../api";
import { StreamLanguage } from '@codemirror/language';
import { jinja2 } from '@codemirror/legacy-modes/mode/jinja2';
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";

type Jinja2Props = {
  providerSensor?: ProviderSensorModel;
  onChange: (value: Partial<ProviderSensorModel>) => void;
}

const Jinja2Code = ({ providerSensor, onChange }: Jinja2Props) => {
  const {  userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  return (
    <div className="flex-1 overflow-auto max-h-100 min-h-100">
      <CodeMirror
        value={providerSensor?.sqlTemplate}
        onChange={(value) => onChange({ sqlTemplate: value })}
        extensions={[StreamLanguage.define(jinja2)]}
        readOnly={userProfile.can_manage_definitions === false}
      />
    </div>
  );
};

export default Jinja2Code;
