@REM
@REM Copyright © 2021 DQO.ai (support@dqo.ai)
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM

@echo off
rem Downloads sample tables from Google Sheets
rem First run the login.cmd command
rem All sample tables defined as Google Sheets are here: https://drive.google.com/drive/folders/1525b-Em0kc774l_dG8gHLnjoKr7Cdu0z

for /f "usebackq delims==" %%v in (`gcloud auth application-default print-access-token`) do set access_token=%%v

REM Spreadsheet: https://docs.google.com/spreadsheets/d/17xnAklqd6cRRfVBcCbkkgarzFLqVaRt3pLFRLu6CeBE
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/17xnAklqd6cRRfVBcCbkkgarzFLqVaRt3pLFRLu6CeBE/export?exportFormat=csv" > continuous_days_one_row_per_day.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1xYmGgrARH0TCt78aju0B32KhJ8CrDhwZwFCUPio-vos
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1xYmGgrARH0TCt78aju0B32KhJ8CrDhwZwFCUPio-vos/export?exportFormat=csv" > continuous_days_non_negative_floats.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1esR1Sun2ck7lV49tZd9b7oyI90dW0HMo-eb7tXqAjy8
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1esR1Sun2ck7lV49tZd9b7oyI90dW0HMo-eb7tXqAjy8/export?exportFormat=csv" > continuous_days_different_time_data_types.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1pS-wj5AGFdpcnHGjNk9sp6DFA8Po5mZd3x39BPwR07c
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1pS-wj5AGFdpcnHGjNk9sp6DFA8Po5mZd3x39BPwR07c/export?exportFormat=csv" > continuous_days_date_and_string_formats.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1esR1Sun2ck7lV49tZd9b7oyI90dW0HMo-eb7tXqAjy8
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1esR1Sun2ck7lV49tZd9b7oyI90dW0HMo-eb7tXqAjy8/export?exportFormat=csv" > continuous_days_different_time_data_types.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1B-Nd7tfQjXh55S0b1Ti4-mplFwve_py3iVPrhv0T_ws
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1B-Nd7tfQjXh55S0b1Ti4-mplFwve_py3iVPrhv0T_ws/export?exportFormat=csv" > test_data_values_in_set.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1B-Nd7tfQjXh55S0b1Ti4-mplFwve_py3iVPrhv0T_ws
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1gXvHncgRCFgzOxA5zG1IUgJ6dAY4iRtshGojs-E20eI/export?exportFormat=csv" > test_average_delay.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1CSav2r36Z9RSEX-Bg739v9oYEaqZ_B-tKFRdRwppYek
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1CSav2r36Z9RSEX-Bg739v9oYEaqZ_B-tKFRdRwppYek/export?exportFormat=csv" > now_datetime.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1xcvYNjAiG97MKDOTXbI0e1UeDwiyTRxvLY-6GRZ8qow
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1xcvYNjAiG97MKDOTXbI0e1UeDwiyTRxvLY-6GRZ8qow/export?exportFormat=csv" > string_test_data.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/179H56-ooJJAv4_CM81ejVZu3U5LibuiuzeI5nkDOXwc
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/179H56-ooJJAv4_CM81ejVZu3U5LibuiuzeI5nkDOXwc/export?exportFormat=csv" > nulls_and_uniqueness.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1Jy0jlXlAt1t3bSpVkrd3pgbv62nJVU3ueXf5jjIk3Ps
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1Jy0jlXlAt1t3bSpVkrd3pgbv62nJVU3ueXf5jjIk3Ps/export?exportFormat=csv" > full_name_test.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1uFv2hUPzNlfPoGZt1dSNfxxeJ45Decy2SekW9RvwSeI
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1uFv2hUPzNlfPoGZt1dSNfxxeJ45Decy2SekW9RvwSeI/export?exportFormat=csv" > uuid_test.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1b7x33WH7NHX2jMpud6ISuQrX9mAU33byk9OM-77d7ps
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1b7x33WH7NHX2jMpud6ISuQrX9mAU33byk9OM-77d7ps/export?exportFormat=csv" > ip4_test.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/160zswzfKyJGvR1EdKE59zDbLMc4atTw_eh7qNO9ObWc/
curl -L --header "Authorization: Bearer %access_token%" "https://docs.google.com/spreadsheets/d/160zswzfKyJGvR1EdKE59zDbLMc4atTw_eh7qNO9ObWc/export?exportFormat=csv" > test_data_timeliness_sensors.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1JUlg3cdTQBZk4TXbK_Vpn8d4vwWLOeAl2D4Ry0zoOxM
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1JUlg3cdTQBZk4TXbK_Vpn8d4vwWLOeAl2D4Ry0zoOxM/export?exportFormat=csv" > ip6_test.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1GBB-HsXz7QP7IX1Dh7df7afeSIRvnQ_1tlLCk8Mq_N0
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1GBB-HsXz7QP7IX1Dh7df7afeSIRvnQ_1tlLCk8Mq_N0/export?exportFormat=csv" > string_min_length_test.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1Nq-URy9ew6MZCGOkSlset5ziYCJbNHC0TFArOhz5Amc
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1Nq-URy9ew6MZCGOkSlset5ziYCJbNHC0TFArOhz5Amc/export?exportFormat=csv" > below_above_value_test.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1bKI4BJKZ2KQtyQ14x2pSwrHYs_-bLuUjWV2nt412R3I
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1bKI4BJKZ2KQtyQ14x2pSwrHYs_-bLuUjWV2nt412R3I/export?exportFormat=csv" > value_match_left_table.csv

REM Spreadsheet: https://docs.google.com/spreadsheets/d/1viQXuM9cSpzOhKl_js3UuRajo-84nWUrcy98qKn9U9g
curl -L --header "Authorization: Bearer %access_token%"  "https://docs.google.com/spreadsheets/d/1viQXuM9cSpzOhKl_js3UuRajo-84nWUrcy98qKn9U9g/export?exportFormat=csv" > value_match_right_table.csv
