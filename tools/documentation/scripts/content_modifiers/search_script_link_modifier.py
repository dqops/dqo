import re

search_script_regex_string: str = """\"base\": \"([^,"]*)\","""
search_script_link_pattern: re.Pattern = re.compile(search_script_regex_string)

def modify_link(line: str) -> str:
    if _verify_application(line):
        return _apply_modification(line)
    return line

def _verify_application(line: str):
    result = search_script_link_pattern.search(line)
    if result is None:
        return False
    return True

def _apply_modification(line: str) -> str:

    result = search_script_link_pattern.search(line)
    groups = result.groups()

    for link in groups:
        if link is None:
            continue

        line.replace(link, "/")

    return line
