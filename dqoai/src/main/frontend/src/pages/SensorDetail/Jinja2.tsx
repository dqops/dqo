import React from "react";
import CodeMirror from '@uiw/react-codemirror';
import { ProviderSensorModel } from "../../api";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { RuleActionGroup } from "../../components/Sensors/RuleActionGroup";

type Jinja2Props = {
  providerSensor?: ProviderSensorModel;
}

const Jinja2Code = ({ providerSensor }: Jinja2Props) => {
  const dispatch = useActionDispatch();

  return (
    <div className="flex-1 overflow-auto max-h-100 min-h-100">
      <RuleActionGroup />

      <CodeMirror
        value={providerSensor?.sqlTemplate}
        onChange={() => {}}
      />
    </div>
  );
};

export default Jinja2Code;
