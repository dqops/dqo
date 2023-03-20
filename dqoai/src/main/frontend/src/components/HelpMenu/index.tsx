import React, { useState } from "react";
import { IconButton, Popover, PopoverContent, PopoverHandler } from "@material-tailwind/react";
import SvgIcon from "../SvgIcon";

const HelpMenu = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <IconButton className="!mr-3 !bg-transparent" variant="text" ripple={false}>
          <div className="relative">
            <SvgIcon name="help-circle" className="w-5 h-5 text-gray-700" />
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 px-4">
        <a
          href="https://dqo.ai/support/"
          target="_blank"
          rel="noreferrer"
          className="block mb-3 text-gray-700"
        >
          Get support
        </a>
        <a
          href="https://docs.dqo.ai/latest"
          target="_blank"
          rel="noreferrer"
          className="block mb-3 text-gray-700"
        >
          Browse documentation
        </a>
        <a
          href="https://cloud.dqo.ai/account"
          target="_blank"
          rel="noreferrer"
          className="block text-gray-700"
        >
          Manage cloud account
        </a>
      </PopoverContent>
    </Popover>
  );
};

export default HelpMenu;
