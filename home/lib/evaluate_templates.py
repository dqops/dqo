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

import json
import sys
import traceback
import os
from pathlib import Path
from datetime import datetime
import streaming
from jinja2 import Environment, FileSystemLoader, ChainableUndefined

class ParsedTemplate:
    jinja_template: any
    template_last_modified: any

    def __init__(self, jinja_template, template_last_modified):
        self.jinja_template = jinja_template
        self.template_last_modified = template_last_modified

class TemplateRunner:
    templates = {}
    loader_configured = False

    def __init__(self):
        self.environment = Environment(loader=FileSystemLoader(Path(__file__).parent.joinpath("../sensors").absolute()),
                                       undefined=ChainableUndefined)

    def process_template_request(self, request):
        try:
            parsing_started_at = datetime.now()
            template_parameters = request.get("parameters")
            template_id = None
            template_text = request.get("template_text")
            template_home_path = request.get("template_home_path")
            template_last_modified = request.get("template_last_modified")

            if template_text is not None:
                template_id = template_text
                if template_id not in self.templates or self.templates[template_id].template_last_modified != template_last_modified:
                    template_object = self.environment.from_string(template_text)
                    self.templates[template_id] = ParsedTemplate(template_object, template_last_modified)
                else:
                    template_object = self.templates[template_id].jinja_template

            if template_home_path is not None:
                template_id = template_home_path
                if template_id not in self.templates or self.templates[template_id].template_last_modified != template_last_modified:
                    if request.get("home_type") == "DQO_HOME":
                        template_object = self.environment.get_template(template_home_path)
                    else:
                        template_absolute_path = Path(request.get("user_home_path")).joinpath("sensors").joinpath(
                            template_home_path)
                        template_text = Path(template_absolute_path).read_text()
                        template_object = self.environment.from_string(template_text)
                    self.templates[template_id] = ParsedTemplate(template_object, template_last_modified)
                else:
                    template_object = self.templates[template_id].jinja_template

            parsing_millis = int((datetime.now() - parsing_started_at).total_seconds() * 1000)

            rendering_started_at = datetime.now()
            rendered_result = template_object.render(**template_parameters)
            rendering_millis = int((datetime.now() - rendering_started_at).total_seconds() * 1000)
            return {"template": template_id, "parameters": template_parameters, "result": rendered_result,
                    "parsing_template_millis" : parsing_millis, "rendering_millis": rendering_millis}
        except Exception as ex:
            return {"template": template_id, "parameters": template_parameters, "error": traceback.format_exc()}


def main():
    template_runner = TemplateRunner()
    post_response_padding = " " * 1024
    try:
        stdin_small_buffer = os.fdopen(sys.stdin.fileno(), 'r', 1024)
        for request, receiving_millis in streaming.stream_json_dicts(stdin_small_buffer):
            started_at = datetime.now()
            response = template_runner.process_template_request(request)
            response["receiving_millis"] = receiving_millis
            response["total_processing_millis"] = int((datetime.now() - started_at).total_seconds() * 1000.0) + receiving_millis
            sys.stdout.write(json.dumps(response))
            sys.stdout.write("\n")
            sys.stdout.write(post_response_padding)  # padding
            sys.stdout.flush()
    except Exception as ex:
        print ('Error rendering a sensor: ' + traceback.format_exc(), file=sys.stderr)
        sys.stdout.write(json.dumps({"error": traceback.format_exc()}))
        sys.stdout.write("\n")
        sys.stdout.flush()


if __name__ == "__main__":
    main()
