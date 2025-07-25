#  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
#  This file is licensed under the Business Source License 1.1,
#  which can be found in the root directory of this repository.
#
#  Change Date: This file will be licensed under the Apache License, Version 2.0,
#  four (4) years from its last modification date.

import re
from datetime import datetime
from json import JSONDecoder, JSONDecodeError, JSONEncoder
from typing import TextIO


NOT_WHITESPACE = re.compile(r'[^\s]')


class ObjectHook(object):
    def __init__(self, dict_):
        # if isinstance(self, datetime):
        #     return
        self.__dict__.update(dict_)
        # for attr, value in dict_.items():
        #     if isinstance(value, str):
        #         try:
        #             instant_value = datetime.strptime(value, '%Y-%m-%dT%H:%M:%S%z')
        #             self.__setattr__(attr, instant_value)
        #             continue
        #         except ValueError:
        #             pass
        #
        #         try:
        #             datetime_value = datetime.strptime(value, '%Y-%m-%dT%H:%M:%S')
        #             self.__setattr__(attr, datetime_value)
        #             continue
        #         except ValueError:
        #             pass


class ObjectEncoder(JSONEncoder):
    def default(self, obj):
        # if isinstance(obj, datetime):
        #     if obj.tzinfo is None:
        #         return obj.strftime('%Y-%m-%dT%H:%M:%S')
        #     else:
        #         return obj.strftime('%Y-%m-%dT%H:%M:%S%z')
        return obj.__dict__


def stream_json_objects(file_obj: TextIO, buf_size=1024):
    decoder = JSONDecoder(object_hook=ObjectHook)
    buf = ""
    ex = None
    started_at = None
    while True:
        block = file_obj.read(buf_size)

        if file_obj.closed:
            break

        if not block:
            break

        if not started_at:
            started_at = datetime.now()
        buf += block
        pos = 0

        if block[-1] != ' ':
            continue

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


def stream_json_dicts(file_obj: TextIO, buf_size=512):
    decoder = JSONDecoder()
    buf = ""
    ex = None
    started_at = None
    while True:
        block = file_obj.read(buf_size)

        if file_obj.closed:
            break

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
