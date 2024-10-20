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

import importlib.util
import json
import os
import sys
import traceback
import types
from datetime import datetime
from typing import Sequence
import streaming


class HistoricDataPoint:
    timestamp_utc_epoch: int
    local_datetime_epoch: int
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


class RuleExecutionRunParameters:
    actual_value: float
    parameters: any
    time_period_local_epoch: int
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


class PythonRuleCallInput:
    data_domain_module: str
    rule_module_path: str
    home_path: str
    dqo_home_path: str
    dqo_root_user_home_path: str
    rule_parameters: any
    rule_module_last_modified_epoch: int


class PythonRuleCallOutput:
    result: any
    parameters: any
    error: str

    def __init__(self, result, parameters, error):
        self.result = result
        self.parameters = parameters
        self.error = error


class LoadedModule:
    rule_module: any
    rule_module_last_modified: int

    def __init__(self, rule_module, rule_module_last_modified):
        self.rule_module = rule_module
        self.rule_module_last_modified = rule_module_last_modified


class RuleRunner:
    rule_modules = {}

    def import_source_file(self, file_name: str, modname: str) -> "types.ModuleType":
        spec = importlib.util.spec_from_file_location(modname, file_name)
        if spec is None:
            raise ImportError(f"Could not load spec for module '{modname}' at: {file_name}")
        module = importlib.util.module_from_spec(spec)
        sys.modules[modname] = module
        try:
            spec.loader.exec_module(module)
        except FileNotFoundError as e:
            raise ImportError(f"{e.strerror}: {file_name}") from e
        return module

    def process_rule_request(self, request: PythonRuleCallInput):
        try:
            rule_module_path = request.rule_module_path
            rule_parameters = request.rule_parameters
            rule_module_last_modified = request.rule_module_last_modified_epoch

            if rule_module_path not in self.rule_modules or self.rule_modules[rule_module_path].rule_module_last_modified != rule_module_last_modified:
                rules_folder_index = rule_module_path.rfind('rules')
                rule_module_name = request.data_domain_module + '.' + \
                    rule_module_path[rules_folder_index + len('rules') + 1: -3] \
                    .replace('\\', '.').replace('/', '.')
                rule_module = self.import_source_file(rule_module_path, rule_module_name)
                self.rule_modules[rule_module_path] = LoadedModule(rule_module, rule_module_last_modified)

            rule_module = self.rule_modules[rule_module_path].rule_module
            rule_function = getattr(rule_module, 'evaluate_rule')
            rule_result = rule_function(rule_parameters)
            return PythonRuleCallOutput(rule_result, rule_parameters, None)
        except Exception as ex:
            return PythonRuleCallOutput(None, rule_parameters, str(traceback.format_exception(ex)))


def main():
    try:
        rule_runner = RuleRunner()
        home_paths_configured = False

        request: PythonRuleCallInput
        for request, duration_millis in streaming.stream_json_objects(sys.stdin):
            if not home_paths_configured:
                sys.path.append(request.dqo_home_path)
                sys.path.append(request.dqo_root_user_home_path)
                home_paths_configured = True

            response = rule_runner.process_rule_request(request)
            sys.stdout.write(json.dumps(response, cls=streaming.ObjectEncoder))
            sys.stdout.write("\n")
            sys.stdout.flush()
    except Exception as ex:
        print('Error processing a rule: ' + traceback.format_exception(ex), file=sys.stderr)
        sys.stdout.write(json.dumps(PythonRuleCallOutput(None, None, str(traceback.format_exception(ex))), cls=streaming.ObjectEncoder))
        sys.stdout.write("\n")
        sys.stdout.flush()


if __name__ == "__main__":
    main()
