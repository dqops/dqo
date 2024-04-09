from files_provider import get_all_files
from docs_file_modifier import modify_file
import sys


def main():

    root_path: str = sys.argv[1]

    if root_path == None:
        raise Exception("Please provide a root path that script will work on.")

    print("Run scripts on path : " + root_path)
    file_paths: list[str] = get_all_files(root_path)

    number = 0
    for file_path in file_paths:
        print("modifying file : " + file_path)
        modify_file(file_path)
        number+=1

    print("Total modified files : " + str(number))

main()

# python docs_modifier.py C:\dev\dqoado\site