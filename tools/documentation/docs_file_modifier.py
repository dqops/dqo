from file_handler import provide_file_content, save_lines_to_file
from script_tag_modifier import script_tag_modification

def modify_file(file_path: str):

    lines: list[str] = provide_file_content(file_path)
    modified_lines: list[str] = list()

    for line in lines:
        script_tag_modified_line = script_tag_modification(line)
        
        
        modified_lines.append(script_tag_modified_line)

    save_lines_to_file(file_path, modified_lines)
