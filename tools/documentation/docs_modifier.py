from files_provider import get_all_files
from script_tag_modifier import modify_script_tags
import sys


def main():

    root_path: str = sys.argv[1]

    if root_path == None:
        raise Exception("Please provide a root path that script will work on.")

    print("Run scripts on path : " + root_path)
    file_paths: list[str] = get_all_files(root_path)

    number = 0
    for item in file_paths:
        modify_script_tags(item)
        print("modifying file : " + item)
        number+=1

    print("Total modified files : " + str(number))

main()

# python docs_modificator.py C:\dev\dqoado\site