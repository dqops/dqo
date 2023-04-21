import React, { useCallback } from "react";
import CodeMirror from '@uiw/react-codemirror';
import { ViewUpdate } from "@codemirror/view";
import { RuleModel } from "../../api";
import { setUpdatedRule } from "../../redux/actions/sensor.actions";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { SensorActionGroup } from "../../components/Sensors/SensorActionGroup";

type PythonCodeProps = {
  rule: RuleModel;
}

const PythonCode = ({ rule }: PythonCodeProps) => {
  const dispatch = useActionDispatch();

  const onChange = useCallback((value: string) => {
    dispatch(setUpdatedRule({
      ...rule,
      ...{ rule_python_module_content: value }
    }));
  }, []);

  return (
    <div className="flex-1 overflow-auto" style={{ maxHeight: `calc(100vh - 255px)` }}>
      <SensorActionGroup />

      <CodeMirror
        value={rule.rule_python_module_content}
        onChange={onChange}
      />
    </div>
  );
};

export default PythonCode;
