import React from "react";
import BaseDatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";

const DatePicker = ({ ...props }: any) => {
  return (
    <div className="relative flex items-center">
      {
        props.showIcon && (
          <div className="flex absolute left-2 w-4 h-4 z-10" style={{ top: '50%', transform: 'translateY(-50%)'}}>
            <svg
              className="react-datepicker__calendar-icon"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 448 512"
            >
              <path d="M96 32V64H48C21.5 64 0 85.5 0 112v48H448V112c0-26.5-21.5-48-48-48H352V32c0-17.7-14.3-32-32-32s-32 14.3-32 32V64H160V32c0-17.7-14.3-32-32-32S96 14.3 96 32zM448 192H0V464c0 26.5 21.5 48 48 48H400c26.5 0 48-21.5 48-48V192z" />
            </svg>
          </div>
        )
      }
      <BaseDatePicker
        className='border py-2 pl-8 pr-2 rounded'
        autoFocus={false}
        {...props}
      />

    </div>
  )
}

export default DatePicker;