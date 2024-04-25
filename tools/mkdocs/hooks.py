#  Copyright © 2021 DQOps (support@dqops.com)
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

import datetime
import os, platform


def format_file_created_date(absolute_file_path: str, date_format: str) -> datetime:
    if platform.system() == 'Windows':
        ts = os.path.getctime(absolute_file_path)
    else:
        stat = os.stat(absolute_file_path)
        try:
            ts = stat.st_birthtime
        except AttributeError:
            ts = stat.st_mtime
    return datetime.datetime.fromtimestamp(ts).strftime(date_format)


def format_file_modified_date(absolute_file_path: str, date_format: str) -> datetime:
    ts = os.path.getmtime(absolute_file_path)
    return datetime.datetime.fromtimestamp(ts).strftime(date_format)


def on_env(env, config, files, **kwargs):
    env.filters['format_file_created_date'] = format_file_created_date
    env.filters['format_file_modified_date'] = format_file_modified_date
    return env