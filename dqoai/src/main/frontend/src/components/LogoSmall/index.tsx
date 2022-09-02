import React from "react";

interface ILogoSmallProps {
  variant?: string;
  width?: number | string;
  height?: number | string;
  className?: string;
}

const LogoSmall: React.FC<ILogoSmallProps> = (props) => {

  return (
    <img src="/logoSmall.png" {...props} />
  );
};

export default LogoSmall;
