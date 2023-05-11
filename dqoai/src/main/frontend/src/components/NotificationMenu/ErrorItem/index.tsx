import { IError } from "../../../contexts/errrorContext";
import React, { useState } from "react";
import { Accordion, AccordionBody, AccordionHeader } from "@material-tailwind/react";
import moment from "moment";

const ErrorItem = ({ error }: { error: IError }) => {
  const [open, setOpen] = useState(false);

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
