import React, { useState } from "react";
import { IconButton, Popover, PopoverContent, PopoverHandler } from "@material-tailwind/react";
import SvgIcon from "../../SvgIcon";
import Button from "../../Button";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { IRootState } from "../../../redux/reducers";

export type CategoryMenuProps = {
  onRunChecks: () => void;
  onDeleteChecks: () => void;
}

const CategoryMenu = ({ onRunChecks, onDeleteChecks }: CategoryMenuProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const { column }: { column: string } = useParams();
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Popover placement="bottom-start" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <IconButton className="!mr-3 !bg-transparent h-6 w-6" variant="text" ripple={false}>
          <div className="relative">
            <SvgIcon name="chevron-down" className="w-5 h-5 text-gray-700" />
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 p-0 py-2">
        <Button
          onClick={onRunChecks}
          className="block text-gray-700 w-full !text-left !justify-start hover:bg-gray-100 rounded-none"
          label={column ? "Run all column checks" : "Run all table checks"}
          disabled={userProfile.can_run_checks !== true}
        />
        <Button
          onClick={onDeleteChecks}
          className="block text-gray-700 w-full !text-left !justify-start hover:bg-gray-100 rounded-none"
          label={column ? "Delete data for all column checks" : "Delete data for all table checks"}
          disabled={userProfile.can_delete_data === false}
        />
      </PopoverContent>
    </Popover>
  );
};

export default CategoryMenu;
