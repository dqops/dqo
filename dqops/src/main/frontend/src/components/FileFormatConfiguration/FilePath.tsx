import { IconButton } from '@material-tailwind/react';
import React from 'react';
import Input from '../Input';
import SvgIcon from '../SvgIcon';

type TFilePathProps = {
  paths: string[];
  onAddPath: () => void;
  onChangePath: (str: string, index?: number) => void;
  onDeletePath: (index: number) => void;
};
export default function FilePath({
  paths = [],
  onAddPath,
  onChangePath,
  onDeletePath
}: TFilePathProps) {
  return (
    <div className="w-full">
      <div className="flex items-center font-bold ">
        <div className="text-left min-w-40 w-11/12 pr-4 py-2">
          File path or file name pattern
        </div>
        <div className=" py-2 text-center max-w-34 min-w-34 w-34">Action</div>
      </div>
      {paths.slice(0, paths.length - 1).map((x, index) => (
        <div className="flex items-center w-full " key={index}>
          <div className="pr-4 min-w-40 py-2 w-11/12">
            <Input
              className="focus:!ring-0 focus:!border"
              value={x}
              onChange={(e) => onChangePath(e.target.value, index)}
            />
          </div>
          <div className="max-w-34 min-w-34 py-2">
            <div className="flex justify-center">
              {' '}
              <IconButton
                color="teal"
                size="sm"
                onClick={() => onDeletePath(index)}
                className="!shadow-none"
              >
                <SvgIcon name="delete" className="w-4" />
              </IconButton>
            </div>
          </div>
        </div>
      ))}
      <div className="flex items-center w-full">
        <div className="pr-4 min-w-40 py-2 w-11/12">
          <Input
            className="focus:!ring-0 focus:!border"
            value={paths?.length ? paths[paths?.length - 1] : ''}
            onChange={(e) => onChangePath(e.target.value)}
          />
        </div>
        <div className="px-8 max-w-34 min-w-34 py-2">
          <div className="flex justify-center">
            <IconButton
              size="sm"
              className="bg-teal-500"
              onClick={onAddPath}
              disabled={!paths?.[paths?.length - 1]?.length}
            >
              <SvgIcon name="add" className="w-4" />
            </IconButton>
          </div>
        </div>
      </div>
    </div>
  );
}
