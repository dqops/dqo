import re

regex_pattern: str = "<script[^<>]*>"
opening_script_tag_pattern: re.Pattern = re.compile(regex_pattern)

def modify_script_tag(line: str) -> str:
    if _verify_application(line):
        return _apply_modification(line)
    return line

def _verify_application(line: str):

    script_tag_opening: str = "<script"
    excluded_script_nowprocket_string: str = "nowprocket"
    excluded_config_script_tag_opening: str = "<script id=\"__config\" type=\"application/json\""
    excluded_schema_script: str = "type=\"application/ld+json\""
    excluded_inline_script_tag_with_no_attibutes: str = "<script>"
    excluded_bundle_script: str = "/bundle."

    if script_tag_opening not in line \
            or excluded_config_script_tag_opening in line \
            or excluded_schema_script in line \
            or excluded_script_nowprocket_string in line \
            or (excluded_inline_script_tag_with_no_attibutes in line and "const lightbox" not in line) \
            or excluded_bundle_script in line:
        return False
    return True

def _apply_modification(line: str) -> str:

    lazy_load_script: str = "lazyload.min.js"
    if lazy_load_script in line: 
        return line.replace("<script ", "<script async ")

    line_with_type: str = _replace_in_opening_script_tag(line, ">", " type=\"rocketlazyloadscript\">")
    line_with_type_and_src_modification: str = _replace_in_opening_script_tag(line_with_type, "src", "data-rocket-src")
    line_with_defer_modification: str = line_with_type_and_src_modification if " defer" in line_with_type_and_src_modification else _replace_in_opening_script_tag(line_with_type_and_src_modification, "<script", "<script defer")
    return line_with_defer_modification

def _replace_in_opening_script_tag(line: str, replaced: str, replacement: str) -> str:
    result = opening_script_tag_pattern.search(line)
    if result is None:
        return line

    match_text = result.group(0)
    modified_line: str = line.replace(match_text, match_text.replace(replaced, replacement))
    return modified_line