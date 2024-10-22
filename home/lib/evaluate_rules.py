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
from pathlib import Path
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
    model_path: str
    data_group: str


class PythonRuleCallInput:
    data_domain_module: str
    rule_module_path: str
    home_path: str
    dqo_home_path: str
    dqo_root_user_home_path: str
    rule_parameters: RuleExecutionRunParameters
    rule_module_last_modified_epoch: int
    debug_mode: str


class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float
    upper_bound: float


class PythonRuleCallOutput:
    result: RuleExecutionResult
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
                sys.path.append(request.dqo_root_user_home_path)
                sys.path.append(request.dqo_home_path)
                home_paths_configured = True

            response = rule_runner.process_rule_request(request)
            write_log = request.debug_mode == 'all' or (request.debug_mode == 'failed' and response.result is not None and \
                                                       (response.result.passed == False or response.error is not None)) or \
                        (request.debug_mode == 'exception' and response.error is not None)
            if write_log:
                time_period_local = datetime.fromtimestamp(request.rule_parameters.time_period_local_epoch)
                result_file_name = request.rule_parameters.data_group.replace(' ', '_') + '_' + \
                                   time_period_local.strftime('%Y-%m-%dT%H%M%S') + '.log.json'
                log_file_name = os.path.join(request.rule_parameters.model_path, result_file_name)
                log_content = {
                    'request': request,
                    'response': response
                }
                log_content_json = json.dumps(log_content, cls=streaming.ObjectEncoder)
                if not os.path.exists(request.rule_parameters.model_path):
                    module_path = Path(request.rule_parameters.model_path)
                    module_path.mkdir(parents=True, exist_ok=True)

                with open(log_file_name, "w") as f:
                    f.write(log_content_json)

            sys.stdout.write(json.dumps(response, cls=streaming.ObjectEncoder))
            sys.stdout.write("\n")
            sys.stdout.flush()
    except Exception as ex:
        print('Error processing a rule: ' + str(traceback.format_exception(ex)), file=sys.stderr)
        sys.stdout.write(json.dumps(PythonRuleCallOutput(None, None, str(traceback.format_exception(ex))), cls=streaming.ObjectEncoder))
        sys.stdout.write("\n")
        sys.stdout.flush()


if __name__ == "__main__":
    main()
