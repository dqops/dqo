import React from "react";
import CodeMirror from '@uiw/react-codemirror';
import { ProviderSensorModel } from "../../api";
import { RuleActionGroup } from "../../components/Sensors/RuleActionGroup";
import { StreamLanguage } from '@codemirror/language';
import { jinja2 } from '@codemirror/legacy-modes/mode/jinja2';

type Jinja2Props = {
  providerSensor?: ProviderSensorModel;
  onChange: (value: Partial<ProviderSensorModel>) => void;
}

const Jinja2Code = ({ providerSensor, onChange }: Jinja2Props) => {
  return (
    <div className="flex-1 overflow-auto max-h-100 min-h-100">
      <RuleActionGroup />

      <CodeMirror
        value={providerSensor?.sqlTemplate}
        onChange={(value) => onChange({ sqlTemplate: value })}
        extensions={[StreamLanguage.define(jinja2)]}
      />
    </div>
  );
};

export default Jinja2Code;
