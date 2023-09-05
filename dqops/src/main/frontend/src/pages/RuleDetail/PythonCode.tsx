import React, { useCallback } from 'react';
import CodeMirror from '@uiw/react-codemirror';
import { RuleModel } from '../../api';
import { setUpdatedRule } from '../../redux/actions/definition.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { StreamLanguage } from '@codemirror/language';
import { python } from '@codemirror/legacy-modes/mode/python';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

type PythonCodeProps = {
  rule: RuleModel;
};

const PythonCode = ({ rule }: PythonCodeProps) => {
  const dispatch = useActionDispatch();
  const {  userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const onChange = useCallback((value: string) => {
    dispatch(
      setUpdatedRule({
        ...rule,
        ...{ rule_python_module_content: value }
      })
    );
  }, []);

  return (
    <div
      className="flex-1 overflow-auto"
      style={{ maxHeight: `calc(100vh - 255px)` }}
    >
      <CodeMirror
        value={rule?.rule_python_module_content}
        onChange={onChange}
        extensions={[StreamLanguage.define(python)]}
        readOnly={userProfile.can_manage_definitions === false}
      />
    </div>
  );
};

export default PythonCode;
