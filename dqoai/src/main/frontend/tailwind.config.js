/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
const sizes = {
  0: '0rem',
  '0.25': '0.0625rem',
  '0.5': '0.125rem',
  '0.75': '0.1875rem',
  1: '0.25rem',
  2: '0.5rem',
  '2-05': '0.625rem',
  3: '0.75rem',
  3.5: '0.875rem',
  '5.5': '1.375rem',
  4: '1rem',
  5: '1.25rem',
  6: '1.5rem',
  8: '2rem',
  9: '2.25rem',
  '9.5': '2.375rem',
  10: '2.5rem',
  11: '2.75rem',
  12: '3rem',
  '13.5': '3.375rem',
  14: '3.5rem',
  15: '3.75rem',
  16: '4rem',
  17: '4.25rem',
  18: '4.5rem',
  20: '5rem',
  21: '5.25rem',
  24: '6rem',
  25: '6.25rem',
  28: '7rem',
  29: '7.25rem',
  30: '7.5rem',
  32: '8rem',
  33: '8.25rem',
  36: '9rem',
  37: '9.25rem',
  38: '9.5rem',
  40: '10rem',
  '40-75': '10.1875rem',
  44: '11rem',
  48: '12rem',
  50: '12.5rem',
  52: '13rem',
  56: '14rem',
  60: '15rem',
  64: '16rem',
  68: '17rem',
  70: '17.5rem',
  72: '18rem',
  75: '18.75rem',
  76: '19rem',
  80: '20rem',
  90: '22.5rem',
  100: '25rem',
  120: '30rem',
  130: '32.5rem',
  140: '35rem',
  150: '37.5rem',
  160: '40rem',
  180: '45rem',
  192: '48rem',
  200: '50rem',
  210: '52.5rem',
  240: '60rem',
  400: '100rem',
  420: '105rem',
  440: '110rem'
};

const withMT = require("@material-tailwind/react/utils/withMT");

/** @type {import('tailwindcss').Config} */
module.exports = withMT({
  theme: {
    extend: {
      colors: {
        'sidebar': '#111827',
        gray: {
          50: '#f3f4f6',
          100: '#E1E5E9',
          150: '#BCBCBC',
          200: '#9CA3AF',
          500: '#617280',
          700: '#2D3748'
        },
        green: {
          400: '#00D63A',
          500: '#10B981',
        },
        red: {
          50: '#F9FAFC',
        },
        purple: {
          500: '#5048E5',
        }
      },
      fontSize: {
        xxs: ['11px', '12px'],
        ss: ['12px', '14px'],
        sm: ['14px', '16px'],
        md: ['16px', '18px'],
        lg: ['18px', '20px'],
        xl: ['20px', '24px'],
        '2xl': ['24px', '32px'],
        '3xl': ['36px', '40px'],
        '4xl': '40px',
        '5xl': `48px`,
      },
      spacing: sizes,
      minHeight: sizes,
      minWidth: {
        ...sizes,
        'initial': 'initial'
      },
      maxWidth: {
        ...sizes,
        'tab-wrapper': 'calc(100vw - 340px)',
        'container': 'calc(100vw - 280px)',
        'table': 'calc(100vw - 320px)'
      },
      maxHeight: {
        ...sizes,
        'container': 'calc(100vh - 64px)',
        'table': 'calc(100vh - 220px)'
      },
      backgroundOpacity: {
        4: '0.04',
        8: '0.08'
      },
      borderRadius: sizes,
      lineHeight: {
        1: 1,
        '1.5': 1.5,
        2: 2,
      },
      borderWidth: {
        3: 3,
      },
      boxShadow: {
        header: '0px 1px 4px rgb(100 116 139 / 12%)',
      },
      zIndex: {
        1000: 1000,
      }
    },
  },
  plugins: [],
  content: ["./src/**/*.{js,jsx,ts,tsx,html}", "./public/index.html"],
});
