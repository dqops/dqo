import { IError } from "../../../contexts/errrorContext";
import React, { useState } from "react";
import { Accordion, AccordionBody, AccordionHeader } from "@material-tailwind/react";
import moment from "moment";
import { useSelector } from "react-redux";
import { IRootState } from "../../../redux/reducers";

const ErrorItem = ({ errorId }: { errorId: string }) => {
  const {job_dictionary_state, } = useSelector(
    (state: IRootState) => state.job || {}
    );
  const [open, setOpen] = useState(false);

  const error = job_dictionary_state[errorId] as unknown as IError

  return (
    <Accordion open={open}>
      <AccordionHeader onClick={() => setOpen(!open)}>
        <div className="flex justify-between items-center text-sm w-full text-gray-700">
          <div className="flex space-x-1 items-center">
            <div>{error.name}</div>
          </div>
          <div>
            {moment(error.date).format('YYYY-MM-DD HH:mm:ss')}
          </div>
        </div>
      </AccordionHeader>
      <AccordionBody>
        <div className="text-gray-700">
          {error.message}
        </div>
      </AccordionBody>
    </Accordion>
  );
};

export default ErrorItem;
