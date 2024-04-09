import re

# a link -> href
# img script -> src

tag_regex_pattern: str = "<(((a)|(img))[^<>]*href=[^<>]*)|(((script)|(link))[^<>]*src=[^<>]*)>"
link_tag_pattern: re.Pattern = re.compile(tag_regex_pattern)

attribute_regex_pattern: str = """(?:href=\"([^<>]*)\")|(?:src=\"([^<>]*)\")"""
link_pattern: re.Pattern = re.compile(attribute_regex_pattern)

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

    result = link_pattern.search(line)
    groups = result.groups()

    for link in groups:
        if link is None:
            continue

        if link.endswith("/"):
            line = line.replace(link, link + "index.html")

        if link.startswith("../"):
            directory_path: str = file_path[:file_path.rfind("/")]                      # removes filename
            directory_path_from_docs = directory_path[directory_path.find("/docs/"):]   # removes path before "/docs"
            folders: list[str] = directory_path_from_docs.split("/")

            relative_folders_number: int = link.count("../")

            while relative_folders_number > 0:
                folders.pop()
                relative_folders_number -= 1

            new_link = "/".join(folders) + "/" + link.replace("../","")

            line = line.replace(link, new_link)       

    return line
