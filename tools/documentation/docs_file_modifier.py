from file_handler import provide_file_content, save_lines_to_file
from content_modifiers.script_tag_modifier import modify_script_tag
from content_modifiers.link_modifier import modify_link

def modify_file(file_path: str):

    lines: list[str] = provide_file_content(file_path)
    modified_lines: list[str] = list()

    for line in lines:
        script_tag_modified_line = modify_script_tag(line)
        link_modified_line = modify_link(script_tag_modified_line, file_path)

        modified_lines.append(link_modified_line)

    save_lines_to_file(file_path, modified_lines)
