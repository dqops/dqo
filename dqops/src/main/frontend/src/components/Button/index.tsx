import React, { ReactNode } from 'react';

import clsx from 'clsx';

import Loader from '../Loader';

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
  ref?: any;
}

const colorsMap = {
  primary: {
    contained: 'bg-primary text-white disabled:bg-gray-500',
    outlined: 'bg-white text-primary border border-primary',
    text: 'bg-white text-primary'
  },
  secondary: {
    contained: 'bg-gray-100',
    outlined: 'bg-white text-gray-300 border border-gray-300',
    text: 'bg-white text-gray-300'
  },
  success: {
    contained: 'bg-green-700 text-white',
    outlined: 'bg-white text-primary border border-green-700',
    text: 'bg-white text-primary'
  },
  info: {
    contained: 'bg-green-400 text-white',
    outlined: 'bg-white text-green-400 border border-green-400',
    text: 'bg-white text-gray-700 hover:bg-blue-50'
  },
  error: {
    contained: 'bg-red-500 text-white',
    outlined: 'bg-white text-red-500 border border-red-500',
    text: 'bg-white text-red-500'
  }
};

const Button = ({
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
  ref
}: IButtonProps) => {
  const classes = color ? colorsMap[color][variant] : '';

  return (
    <button
      ref={ref}
      type={type}
      onClick={onClick}
      className={clsx(
        classes,
        className,
        textSize ? `text-${textSize}` : 'text-sm',
        'rounded-lg focus:outline-none flex items-center justify-center',
        (!className || className.indexOf('py-') === -1) && 'py-2',
        (!className || className.indexOf('px-') === -1) && 'px-6',
        { 'cursor-not-allowed': disabled }
      )}
      disabled={disabled}
      data-testid={dataTestId}
    >
      {loading ? (
        <Loader className="w-6 h-6" isFull={false} />
      ) : (
        <>
          {leftIcon}
          {label}
          {rightIcon}
        </>
      )}
    </button>
  );
};

export default Button;
