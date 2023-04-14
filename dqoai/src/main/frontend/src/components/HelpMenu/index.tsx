import React, { useState } from "react";
import { IconButton, Popover, PopoverContent, PopoverHandler } from "@material-tailwind/react";
import SvgIcon from "../SvgIcon";
import GitHubButton from 'react-github-btn';

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
      <PopoverContent className="z-50 px-0">
        <div className="px-4">
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
            className="block text-gray-700 mb-3"
          >
            Manage cloud account
          </a>
        </div>
        <div className="px-4 pt-3 border-t border-gray-300">
          <h6 className="mb-3">Promote DQO on GitHub</h6>
          <div className="mb-2">
            <GitHubButton
              href="https://github.com/dqoai/dqo"
              data-color-scheme="no-preference: light_high_contrast; light: light; dark: light;"
              data-icon="octicon-star"
              data-size="large"
              data-show-count="true"
              aria-label="Star dqoai/dqo on GitHub"
            >
              Star
            </GitHubButton>
          </div>
          <div className="mb-2">
            <GitHubButton
              href="https://github.com/dqoai/dqo/fork"
              data-color-scheme="no-preference: light_high_contrast; light: light; dark: light;"
              data-icon="octicon-repo-forked"
              data-size="large"
              data-show-count="true"
              aria-label="Fork dqoai/dqo on GitHub"
            >
              Fork
            </GitHubButton>
          </div>
          <div>
            <GitHubButton
              href="https://github.com/dqoai/dqo/subscription"
              data-color-scheme="no-preference: light_high_contrast; light: light; dark: light;"
              data-icon="octicon-eye"
              data-size="large"
              data-show-count="true"
              aria-label="Watch dqoai/dqo on GitHub"
            >
              Watch
            </GitHubButton>
          </div>
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default HelpMenu;
