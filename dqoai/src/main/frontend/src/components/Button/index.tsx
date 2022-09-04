import React, { ReactNode } from 'react';

import clsx from 'clsx';
import Loader from "../Loader";

interface IButtonProps {
  label?: string;
  onClick?: () => void;
  type?: 'submit' | 'reset' | 'button';
  className?: string;
  leftIcon?: ReactNode;
  rightIcon?: ReactNode;
  disabled?: boolean;
  loading?: boolean;
  color?: 'primary' | 'secondary' | 'success' | 'info' | 'error';
  variant?: 'contained' | 'outlined' | 'text';
  textSize?: 'sm' | 'base' | 'lg' | 'xl';
  dataTestId?: string;
}

const colorsMap = {
  primary: {
    contained: 'bg-indigo-700 text-white',
    outlined: 'bg-white text-indigo-700 border border-indigo-700',
    text: 'bg-white text-indigo-700'
  },
  secondary: {
    contained: 'bg-gray-100 text-black',
    outlined: 'bg-white text-gray-300 border border-gray-300',
    text: 'bg-white text-gray-300'
  },
  success: {
    contained: 'bg-green-700 text-white',
    outlined: 'bg-white text-green-700 border border-green-700',
    text: 'bg-white text-green-700'
  },
  info: {
    contained: 'bg-green-400 text-white',
    outlined: 'bg-white text-green-400 border border-green-400',
    text: 'bg-white text-green-400'
  },
  error: {
    contained: 'bg-red-500 text-white',
    outlined: 'bg-white text-red-500 border border-red-500',
    text: 'bg-white text-red-500'
  },
}

const Button: React.FC<IButtonProps> = ({
  label,
  type = 'button',
  onClick,
  className,
  leftIcon,
  rightIcon,
  disabled,
  loading,
  color,
  variant = 'contained',
  textSize,
  dataTestId,
}) => {
  const classes = color ? colorsMap[color][variant] : ''

  return (
    <button
      type={type}
      onClick={onClick}
      className={clsx(
        classes,
        className,
        textSize ? `text-${textSize}` : 'text-base',
        'rounded-lg focus:outline-none flex items-center justify-center',
        (!className || className.indexOf('py-') === -1) && 'py-2',
        (!className || className.indexOf('px-') === -1) && 'px-6',
        { 'cursor-not-allowed': disabled },
      )}
      disabled={disabled}
      data-testid={dataTestId}
    >
      {loading ? (
        <Loader className="w-6 h-6" />
      ) : (
        <>
          {leftIcon}
          {label}
          {rightIcon}
        </>
      )}
    </button>
  );
}

export default Button;
