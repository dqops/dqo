import { StreamLanguage } from '@codemirror/language';
import { jinja2 } from '@codemirror/legacy-modes/mode/jinja2';
import CodeMirror from '@uiw/react-codemirror';
import React from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

type Jinja2Props = {
  value?: string;
  onChange: (value: string) => void;
};

const Jinja2Code = ({ value, onChange }: Jinja2Props) => {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  return (
    <div className="flex-1 overflow-auto max-h-100 min-h-100">
      <CodeMirror
        value={value}
        onChange={onChange}
        extensions={[StreamLanguage.define(jinja2)]}
        readOnly={userProfile.can_manage_definitions !== true}
      />
    </div>
  );
};

export default Jinja2Code;
