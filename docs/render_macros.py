import json
import yaml
from jinja2 import Environment, FileSystemLoader, ChainableUndefined
import traceback
from pathlib import Path


def define_env(env):
    templates = {}
    loader_configured = False

    environment = Environment(loader=FileSystemLoader(Path("home/sensors")),
                              undefined=ChainableUndefined)

    @env.macro
    def get_request(file: str):
        """
        Use this function to load check configuration.
        :param file: Check configuration JSON, file path should be specified from "docs" directory.
        :return: Interpretable JSON check configuration
        """
        return json.load(open(f"{file}"))


    @env.macro
    def process_template_request(request):
        try:
            template_parameters = request.get("parameters")
            template_id = None
            template_text = request.get("template_text")
            template_home_path = request.get("template_home_path")

            if template_text is not None:
                template_id = template_text
                if template_id not in templates:
                    template_object = environment.from_string(template_text)
                    templates[template_id] = template_object
                else:
                    template_object = templates[template_id]

            if template_home_path is not None:
                template_id = template_home_path
                if template_id not in templates:
                    if request.get("home_type") == "DQO_HOME":
                        template_object = environment.get_template(template_home_path)
                    else:
                        template_absolute_path = Path(request.get("user_home_path")).joinpath("sensors").joinpath(
                            template_home_path)
                        template_text = Path(template_absolute_path).read_text()
                        template_object = environment.from_string(template_text)
                    templates[template_id] = template_object
                else:
                    template_object = templates[template_id]

            rendered_result = template_object.render(**template_parameters)
            return rendered_result
        except Exception as ex:
            return {"template": template_id, "parameters": template_parameters, "error": traceback.format_exc()}

    @env.macro
    def generate_yaml(request):
        try:
            return yaml.dump(request)
        except Exception as ex:
            return traceback.format_exc()