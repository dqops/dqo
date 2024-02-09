import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import Input from '../Input';

type TFileFormatConfigurationProps = {
  paths: string[];
  onChangePath: (val: string) => void;
  onAddPath: () => void;
};

export default function FileFormatConfiguration({
  paths,
  onAddPath,
  onChangePath
}: TFileFormatConfigurationProps) {
  return (
    <SectionWrapper title="File format configuration">
      <div className="text-sm">
        {paths.slice(0, paths.length - 1).map((x, index) => (
          <div key={index} className="text-black py-1">
            {x}
          </div>
        ))}
        <div className="flex items-center w-full">
          <div className="pr-4 min-w-40 py-2 w-11/12">
            <Input
              className="focus:!ring-0 focus:!border"
              value={paths.length ? paths[paths.length - 1] : ''}
              onChange={(e) => onChangePath(e.target.value)}
            />
          </div>
          <div className="px-8 max-w-34 min-w-34 py-2">
            <div className="flex justify-center">
              <IconButton size="sm" className="bg-teal-500" onClick={onAddPath}>
                <SvgIcon name="add" className="w-4" />
              </IconButton>
            </div>
          </div>
        </div>
      </div>
    </SectionWrapper>
  );
}
