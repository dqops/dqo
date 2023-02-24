import React, { useState } from "react";
import { chunkString } from "../../../utils/object";

type ErrorTextProps = {
  text: string;
};
const ErrorText = ({ text }: ErrorTextProps) => {
  const [showMore, setShowMore] = useState(false);
  const arrays: string[] = chunkString(text, 120);

  return (
    <div>
      {(showMore ? arrays : arrays.slice(0, 2)).map((item, index) => (
        <div key={index}>
          {item}
        </div>
      ))}
      {arrays.length > 2 && (
        <div className="underline cursor-pointer text-blue-500 mt-2" onClick={() => setShowMore(!showMore)}>
          {showMore ? 'Show less' : 'Show more'}
        </div>
      )}
    </div>
  );
};

export default ErrorText;
