#!/bin/sh
#
# Copyright © 2021 DQOps (support@dqops.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Downloads sample tables from Google Sheets
# First run the 'sh login.sh' command
# All sample tables defined as Google Sheets are here: https://drive.google.com/drive/folders/1525b-Em0kc774l_dG8gHLnjoKr7Cdu0z

# Spreadsheet: https://docs.google.com/spreadsheets/d/17xnAklqd6cRRfVBcCbkkgarzFLqVaRt3pLFRLu6CeBE
curl "https://docs.google.com/spreadsheets/d/17xnAklqd6cRRfVBcCbkkgarzFLqVaRt3pLFRLu6CeBE/export?exportFormat=csv" -o continuous_days_one_row_per_day.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1xYmGgrARH0TCt78aju0B32KhJ8CrDhwZwFCUPio-vos
curl "https://docs.google.com/spreadsheets/d/1xYmGgrARH0TCt78aju0B32KhJ8CrDhwZwFCUPio-vos/export?exportFormat=csv" -o continuous_days_non_negative_floats.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1pS-wj5AGFdpcnHGjNk9sp6DFA8Po5mZd3x39BPwR07c
curl "https://docs.google.com/spreadsheets/d/1pS-wj5AGFdpcnHGjNk9sp6DFA8Po5mZd3x39BPwR07c/export?exportFormat=csv" -o continuous_days_date_and_string_formats.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1esR1Sun2ck7lV49tZd9b7oyI90dW0HMo-eb7tXqAjy8
curl "https://docs.google.com/spreadsheets/d/1esR1Sun2ck7lV49tZd9b7oyI90dW0HMo-eb7tXqAjy8/export?exportFormat=csv" -o continuous_days_different_time_data_types.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/184MFXa_KduqF0M-RPx7i1Ov7J4oBOFSBfH2aA58IzpA/
curl "https://docs.google.com/spreadsheets/d/184MFXa_KduqF0M-RPx7i1Ov7J4oBOFSBfH2aA58IzpA/export?exportFormat=csv" -o test_data_regex_sensor.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/160zswzfKyJGvR1EdKE59zDbLMc4atTw_eh7qNO9ObWc/
curl "https://docs.google.com/spreadsheets/d/160zswzfKyJGvR1EdKE59zDbLMc4atTw_eh7qNO9ObWc/export?exportFormat=csv" -o test_data_timeliness_sensors.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1SoscUN4hDG8Hltr5xehjwcyWRNP-LRxn4XSb7OADew8/
curl "https://docs.google.com/spreadsheets/d/1SoscUN4hDG8Hltr5xehjwcyWRNP-LRxn4XSb7OADew8/export?exportFormat=csv" -o test_data_time_series.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1gXvHncgRCFgzOxA5zG1IUgJ6dAY4iRtshGojs-E20eI/
curl "https://docs.google.com/spreadsheets/d/1gXvHncgRCFgzOxA5zG1IUgJ6dAY4iRtshGojs-E20eI/export?exportFormat=csv" -o test_average_delay.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1bKI4BJKZ2KQtyQ14x2pSwrHYs_-bLuUjWV2nt412R3I
curl "https://docs.google.com/spreadsheets/d/1bKI4BJKZ2KQtyQ14x2pSwrHYs_-bLuUjWV2nt412R3I/export?exportFormat=csv" -o value_match_left_table.csv -L

# Spreadsheet: https://docs.google.com/spreadsheets/d/1viQXuM9cSpzOhKl_js3UuRajo-84nWUrcy98qKn9U9g
curl "https://docs.google.com/spreadsheets/d/1viQXuM9cSpzOhKl_js3UuRajo-84nWUrcy98qKn9U9g/export?exportFormat=csv" -o value_match_right_table.csv -L

echo "This scirpt is obsolete !!! Please refer to the .cmd and update this script."
