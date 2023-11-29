import re
from file_handler import provide_file_content, save_lines_to_file

regex_pattern: str = "<script[^<>]*>"
compiled_regex: re.Pattern = re.compile(regex_pattern)

def modify_script_tags(file_path: str):

    lines: list[str] = provide_file_content(file_path)
    modified_lines: list[str] = list()

    for line in lines:
        if _verify_tag_application(line):
            modified_lines.append(_apply_tag_modification(line))
        else:
            modified_lines.append(line)

    save_lines_to_file(file_path, modified_lines)

def _verify_tag_application(line: str):

    script_tag_opening: str = "<script"

    script_exclusion_string: str = "lazyload.min.js"

    if script_tag_opening not in line or script_exclusion_string in line:
        return False
    return True

def _apply_tag_modification(line: str) -> str:

    replacement: str = " type=\"rocketlazyloadscript\">"

    result = compiled_regex.search(line)
    match_text = result.group(0)

    modified_line: str = line.replace(match_text, match_text.replace(">", replacement))

    return modified_line
