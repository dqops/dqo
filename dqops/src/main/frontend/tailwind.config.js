/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

const sizes = {};

for (let i = 0; i < 500; i++) {
  sizes[i] = `${i / 4}rem`;
  sizes[`${i}.5`] = `${(i + 0.5) / 4}rem`;
}

const withMT = require('@material-tailwind/react/utils/withMT');

/** @type {import('tailwindcss').Config} */
module.exports = withMT({
  theme: {
    extend: {
      colors: {
        primary: '#029A80',
        sidebar: '#111827',
        'text-main': '#2F2F2F',
        orange: {
          100: '#FFEFD6',
          900: '#FF9900'
        },
        yellow: {
          100: '#FDFDED',
          900: '#EBE51E'
        },
        gray: {
          50: '#F5F5F5',
          100: '#E1E5E9',
          150: '#BCBCBC',
          200: '#9CA3AF',
          250: '#989898',
          400: '#DEDEDE',
          500: '#617280',
          600: '#4c4c4c',
          700: '#2F2F2F'
        },
        green: {
          400: '#00D63A',
          500: '#10B981',
          600: '#00BB9E'
        },
        red: {
          50: '#F9FAFC',
          100: '#FEEDEC',
          900: '#E3170A'
        },
        purple: {
          500: '#5048E5'
        },
        teal: {
          500: '#029A80'
        }
      },
      fontSize: {
        '3xs': ['10px'],
        xxs: ['11px', '12px'],
        ss: ['12px', '14px'],
        sm: ['14px', '16px'],
        md: ['16px', '18px'],
        lg: ['18px', '20px'],
        xl: ['20px', '24px'],
        '2xl': ['24px', '32px'],
        '3xl': ['36px', '40px'],
        '4xl': '40px',
        '5xl': `48px`
      },
      spacing: sizes,
      minHeight: sizes,
      minWidth: {
        ...sizes,
        initial: 'initial'
      },
      maxWidth: {
        ...sizes,
        'tab-wrapper': 'calc(100vw - 340px)',
        container: 'calc(100vw - 280px)',
        table: 'calc(100vw - 320px)'
      },
      maxHeight: {
        ...sizes,
        container: 'calc(100vh - 64px)',
        table: 'calc(100vh - 325px)',
        checks: 'calc(100vh - 325px)',
        'checks-1': 'calc(100vh - 211px)',
        'checks-2': 'calc(100vh - 270px)'
      },
      backgroundOpacity: {
        4: '0.04',
        8: '0.08'
      },
      borderRadius: sizes,
      lineHeight: {
        1: 1,
        1.5: 1.5,
        2: 2
      },
      borderWidth: {
        3: 3
      },
      boxShadow: {
        header: '0px 1px 4px rgb(100 116 139 / 12%)'
      },
      zIndex: {
        1000: 1000
      },
      screens: {
        screen2000: '2000px',
        screen1900: '1900px',
        screen3000: '3000px'
      },
      gridTemplateColumns: {
        24: 'repeat(24, minmax(0, 1fr))' // 24 column grid
      }
    }
  },
  plugins: [require('@tailwindcss/line-clamp')],
  content: ['./src/**/*.{js,jsx,ts,tsx,html}', './public/index.html']
});
