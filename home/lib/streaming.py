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

import re
import sys
import os
import signal
if os.name != 'nt':
    import fcntl

import threading
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


if os.name != 'nt':
    io_event = threading.Event()
def handle_io(signal, frame):
    io_event.set()

def stream_json_objects(file_obj: TextIO, buf_size=1024):
    if os.name != 'nt':
        # invoke handle_io on a SIGIO event
        signal.signal(signal.SIGIO, handle_io)
        # send io events on stdin (fd 0) to our process
        assert fcntl.fcntl(file_obj.fileno(), fcntl.F_SETOWN, os.getpid()) == 0
        # tell the os to produce SIGIO events when data is written to stdin
        assert fcntl.fcntl(file_obj.fileno(), fcntl.F_SETFL, os.O_ASYNC) == 0

    decoder = JSONDecoder(object_hook=ObjectHook)
    buf = ""
    ex = None
    started_at = None
    while True:
        if os.name != 'nt':
            io_event.wait()

        block = file_obj.read(buf_size)
        if os.name != 'nt':
            io_event.clear()

        if not block:
            break

        if not started_at:
            started_at = datetime.now()
        buf += block
        pos = 0
        while True:
            match = NOT_WHITESPACE.search(buf, pos)
            if not match:
                break
            pos = match.start()

            if not started_at and pos < len(block):
                started_at = datetime.now()

            try:
                obj, pos = decoder.raw_decode(buf, pos)
            except JSONDecodeError as e:
                ex = e
                break
            else:
                ex = None
                duration_millis = int((datetime.now() - started_at).total_seconds() * 1000)
                started_at = None
                yield obj, duration_millis
        buf = buf[pos:]
        started_at = None
    if ex is not None:
        raise ex


def stream_json_dicts(file_obj: TextIO, buf_size=1024):
    if os.name != 'nt':
        # invoke handle_io on a SIGIO event
        signal.signal(signal.SIGIO, handle_io)
        # send io events on stdin (fd 0) to our process
        assert fcntl.fcntl(file_obj.fileno(), fcntl.F_SETOWN, os.getpid()) == 0
        # tell the os to produce SIGIO events when data is written to stdin
        assert fcntl.fcntl(file_obj.fileno(), fcntl.F_SETFL, os.O_ASYNC) == 0

    decoder = JSONDecoder()
    buf = ""
    ex = None
    started_at = None
    while True:
        if os.name != 'nt':
            io_event.wait()

        block = file_obj.read(buf_size)
        if os.name != 'nt':
            io_event.clear()

        if not block:
            break
        if not started_at:
            started_at = datetime.now()
        buf += block
        pos = 0
        while True:
            match = NOT_WHITESPACE.search(buf, pos)
            if not match:
                break
            pos = match.start()

            if not started_at and pos < len(block):
                started_at = datetime.now()

            try:
                obj, pos = decoder.raw_decode(buf, pos)
            except JSONDecodeError as e:
                ex = e
                break
            else:
                ex = None
                duration_millis = int((datetime.now() - started_at).total_seconds() * 1000)
                started_at = None
                yield obj, duration_millis
        buf = buf[pos:]
        started_at = None
    if ex is not None:
        raise ex
