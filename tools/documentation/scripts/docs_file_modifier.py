from scripts.file_handler import provide_file_content, save_lines_to_file
from scripts.content_modifiers.script_tag_modifier import modify_script_tag
import scripts.content_modifiers.link_modifier as link_modifier
import scripts.content_modifiers.search_script_link_modifier as search_script_link_modifier

def modify_file(file_path: str):

    lines: list[str] = provide_file_content(file_path)
    modified_lines: list[str] = list()

    for line in lines:
        script_tag_modified_line = modify_script_tag(line)
        link_modified_line = link_modifier.modify_link(script_tag_modified_line, file_path)
        search_links_modified_line = search_script_link_modifier.modify_link(link_modified_line)

        modified_lines.append(search_links_modified_line)

    save_lines_to_file(file_path, modified_lines)
