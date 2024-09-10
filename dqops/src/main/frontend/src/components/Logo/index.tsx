import React from 'react';

interface ILogoProps {
  variant?: string;
  width?: number | string;
  height?: number | string;
  className?: string;
}

const Logo = (props: ILogoProps) => {
  return <img src="/logo.png" {...props} />;
};

export default Logo;
