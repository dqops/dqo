import re
from typing import List

# a link -> href
# img script -> src



tag_regex_string: str = "<(?:(?:(?:a)|(?:link))[^<>]*href)|(?:(?:(?:script)|(?:img))[^<>]*src)=[^<>]*>"
link_tag_pattern: re.Pattern = re.compile(tag_regex_string)

attribute_regex_string: str = """(?:(?:(?:href)|(?:src))=\"((?:[.]{1,2}[^<>"]*)|(?:[^<>"]*\/)|(?:[^<>"]*\/[.])|(?:[^<>"]*#[^<>"]+))\")"""
link_pattern: re.Pattern = re.compile(attribute_regex_string)

def modify_link(line: str, file_path: str) -> str:
    if _verify_application(line):
        return _apply_modification(line, file_path)
    return line

def _verify_application(line: str):
    result = link_tag_pattern.search(line)
    if result is None:
        return False
    return True

def _apply_modification(line: str, file_path: str) -> str:

    file_path_fixed: str = file_path.replace("\\", "/") if file_path is not None else file_path

    results = link_pattern.findall(line)
    if results is None:
        return line
    
    for link in results:
        if link is None or link == '':
            continue

        if link.endswith("/") or link.endswith("/."):
            new_link = link.rstrip(".")
            line = line.replace(link, new_link)
            link = new_link

        if link == "." or link == ".." or link == "./":
            absolute_prefix = _get_missing_absolute_path(link, file_path_fixed)
            new_link = absolute_prefix
            line = line.replace(link, new_link)
        
        if link.startswith("../"):
            absolute_prefix = _get_missing_absolute_path(link, file_path_fixed)
            new_link = absolute_prefix + link.replace("../", "").replace("..", "")
            line = line.replace(link, new_link)

        # relative link with no dots
        if not link.startswith(("/", "http")):
            folders: list[str] = _get_docs_folders_list_of_file_path(file_path_fixed)
            new_link = "/".join(folders) + "/" + link
            line = line.replace(link, new_link)

    return line

def _get_missing_absolute_path(relative_link: str, file_path: str) -> str:
    folders: list[str] = _get_docs_folders_list_of_file_path(file_path)
    relative_folders_number: int = relative_link.count("..")
    while relative_folders_number > 0:
        folders.pop()
        relative_folders_number -= 1

    return "/".join(folders) + "/"

def _get_docs_folders_list_of_file_path(file_path: str) -> List[str]:
    directory_path: str = file_path[:file_path.rfind("/")] # removes filename
    directory_path_from_docs = re.sub(r'^(site)', '/docs', directory_path)
    folders: list[str] = directory_path_from_docs.split("/")
    return folders
