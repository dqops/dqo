#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

import re
from datetime import datetime
from json import JSONDecoder, JSONDecodeError, JSONEncoder
from typing import TextIO


NOT_WHITESPACE = re.compile(r'[^\s]')


class ObjectHook(object):
    def __init__(self, dict_):
        if isinstance(self, datetime):
            return
        self.__dict__.update(dict_)
        for attr, value in dict_.items():
            if isinstance(value, str):
                try:
                    instant_value = datetime.strptime(value, '%Y-%m-%dT%H:%M:%S%z')
                    self.__setattr__(attr, instant_value)
                    continue
                except ValueError:
                    pass

                try:
                    datetime_value = datetime.strptime(value, '%Y-%m-%dT%H:%M:%S')
                    self.__setattr__(attr, datetime_value)
                    continue
                except ValueError:
                    pass


class ObjectEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, datetime):
            if obj.tzinfo is None:
                return obj.strftime('%Y-%m-%dT%H:%M:%S')
            else:
                return obj.strftime('%Y-%m-%dT%H:%M:%S%z')
        return obj.__dict__


def stream_json_objects(file_obj: TextIO, buf_size=1024):
    decoder = JSONDecoder(object_hook=ObjectHook)
    buf = ""
    ex = None
    while True:
        block = file_obj.read(buf_size)
        if not block:
            break
        buf += block
        pos = 0
        while True:
            match = NOT_WHITESPACE.search(buf, pos)
            if not match:
                break
            pos = match.start()
            try:
                obj, pos = decoder.raw_decode(buf, pos)
            except JSONDecodeError as e:
                ex = e
                break
            else:
                ex = None
                yield obj
        buf = buf[pos:]
    if ex is not None:
        raise ex


def stream_json_dicts(file_obj: TextIO, buf_size=1024):
    decoder = JSONDecoder()
    buf = ""
    ex = None
    while True:
        block = file_obj.read(buf_size)
        if not block:
            break
        buf += block
        pos = 0
        while True:
            match = NOT_WHITESPACE.search(buf, pos)
            if not match:
                break
            pos = match.start()
            try:
                obj, pos = decoder.raw_decode(buf, pos)
            except JSONDecodeError as e:
                ex = e
                break
            else:
                ex = None
                yield obj
        buf = buf[pos:]
    if ex is not None:
        raise ex
