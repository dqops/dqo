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

import json
import sys
import traceback
from pathlib import Path
import streaming
from jinja2 import Environment, FileSystemLoader, ChainableUndefined


class TemplateRunner:
    templates = {}
    loader_configured = False

    def __init__(self):
        self.environment = Environment(loader=FileSystemLoader(Path(__file__).parent.joinpath("../sensors").absolute()),
                                       undefined=ChainableUndefined)

    def process_template_request(self, request):
        try:
            template_parameters = request.get("parameters")
            template_id = None
            template_text = request.get("template_text")
            template_home_path = request.get("template_home_path")

            if template_text is not None:
                template_id = template_text
                if template_id not in self.templates:
                    template_object = self.environment.from_string(template_text)
                    self.templates[template_id] = template_object
                else:
                    template_object = self.templates[template_id]

            if template_home_path is not None:
                template_id = template_home_path
                if template_id not in self.templates:
                    if request.get("home_type") == "DQO_HOME":
                        template_object = self.environment.get_template(template_home_path)
                    else:
                        template_absolute_path = Path(request.get("user_home_path")).joinpath("sensors").joinpath(
                            template_home_path)
                        template_text = Path(template_absolute_path).read_text()
                        template_object = self.environment.from_string(template_text)
                    self.templates[template_id] = template_object
                else:
                    template_object = self.templates[template_id]

            rendered_result = template_object.render(**template_parameters)
            return {"template": template_id, "parameters": template_parameters, "result": rendered_result}
        except Exception as ex:
            return {"template": template_id, "parameters": template_parameters, "error": traceback.format_exc()}


def main():
    template_runner = TemplateRunner()
    for request in streaming.stream_json_dicts(sys.stdin):
        response = template_runner.process_template_request(request)
        sys.stdout.write(json.dumps(response))
        sys.stdout.write("\n")
        sys.stdout.flush()


if __name__ == "__main__":
    main()
