import os
from typing import List


def get_all_files(root_path: str) -> List[str]:

    file_paths: list[str] = list()

    for path, _, files in os.walk(root_path):
        for file_name in files:
            if file_name.endswith(".html"):
                file_paths.append(os.path.join(path, file_name))

    return file_paths