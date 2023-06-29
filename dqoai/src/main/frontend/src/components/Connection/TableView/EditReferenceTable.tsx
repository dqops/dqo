import React, { useEffect, useState } from 'react';
import { TableComparisonsApi } from "../../../services/apiClient";
import { ReferenceTableModel } from "../../../api";
import Button from "../../Button";
import Input from "../../Input";
import SvgIcon from "../../SvgIcon";
import SectionWrapper from "../../Dashboard/SectionWrapper";
import Select from "../../Select";

type EditReferenceTableProps = {
  onBack: () => void;
};

const EditReferenceTable = ({ onBack }: EditReferenceTableProps) => {
  const [name, setName] = useState('');
  return (
    <div>
      <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8">
        <Input
          className="min-w-80"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Ref table name"
        />
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>

      <div className="px-8 py-4">
        <SectionWrapper title="Reference table(the source of truth)" className="py-8 mb-10">

          <div className="flex gap-2 items-center mb-3">
            <div className="w-60">Connection</div>
            <Select className="flex-1" options={[]} />
          </div>
          <div className="flex gap-2 items-center mb-3">
            <div className="w-60">Schema</div>
            <Select className="flex-1" options={[]} />
          </div>
          <div className="flex gap-2 items-center">
            <div className="w-60">Table</div>
            <Select className="flex-1" options={[]} />
          </div>
        </SectionWrapper>

        <div className="flex gap-8">
          <div>
            {
              [1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => (
                <div key={index} className="mb-3">
                  Grouping dimension level {item}
                </div>
              ))
            }
          </div>
          <SectionWrapper className="flex-1" title="Data grouping on compared table">

          </SectionWrapper>
          <SectionWrapper className="flex-1" title="Data grouping on reference table">

          </SectionWrapper>
        </div>
      </div>
    </div>
  );
};

export default EditReferenceTable;
