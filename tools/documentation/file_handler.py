from typing import List


def provide_file_content(file_path: str) -> List[str]:
    with open(file_path, encoding="utf8") as file:
        lines = [line.rstrip() for line in file]

    return lines

def save_lines_to_file(file_path: str, lines: list[str]):
    with open(file_path, 'w', encoding="utf8") as fp:
        line_number: int = 0
        for line in lines:
            if line_number == len(lines) - 1: # last line which do not need a new line
                fp.write(line)
            else:
                fp.write("%s\n" % line)
            line_number+=1