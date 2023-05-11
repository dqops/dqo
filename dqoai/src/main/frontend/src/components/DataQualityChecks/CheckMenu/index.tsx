import React, { useState } from "react";
import { IconButton, Popover, PopoverContent, PopoverHandler } from "@material-tailwind/react";
import SvgIcon from "../../SvgIcon";
import Button from "../../Button";


export type CheckMenuProps = {
  onRunChecks: () => void;
  onDeleteChecks: () => void;
}

const CheckMenu = ({ onRunChecks, onDeleteChecks }: CheckMenuProps) => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Popover placement="bottom-start" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <IconButton className="!mr-3 !bg-transparent" variant="text" ripple={false}>
          <div className="relative">
            <SvgIcon name="chevron-down" className="w-5 h-5 text-gray-700" />
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 p-0 py-2">
        <Button
          onClick={onRunChecks}
          className="block text-gray-700 w-full !text-left !justify-start hover:bg-gray-100 rounded-none"
          label="Run checks for category"
        />
        <Button
          onClick={onDeleteChecks}
          className="block text-gray-700 w-full !text-left !justify-start hover:bg-gray-100 rounded-none"
          label="Delete results for category"
        />
      </PopoverContent>
    </Popover>
  );
};

export default CheckMenu;
