import unittest
import shutil
import os 
from scripts.docs_file_modifier import modify_file
from os.path import abspath, dirname, join, realpath

class DocsFileModifierTest(unittest.TestCase):

    def setUp(self) -> None:
        self.maxDiff = None

        temp_folder: str = abspath(dirname(realpath(__file__)) + "/temp")

        if not os.path.isdir(temp_folder):
            os.mkdir(temp_folder)

    def test_modify_file__when_multiple_script_tags__then_modifies_them(self):
        (target_file, copied_source_file) = self._prepare_files("./script_tag_modifier_examples/multiple_script_tags")

        modify_file(copied_source_file)

        modified_file_content = open(copied_source_file, "r").readlines()
        target_file_content = open(target_file, "r").readlines()

        self.assertEqual(target_file_content, modified_file_content)

    def _prepare_files(self, top_folder_relative_path: str):
        absolute_path: str = dirname(realpath(__file__))

        original_file: str = abspath(join(absolute_path, top_folder_relative_path + "/source.html"))
        original_file_copy: str = abspath(join(absolute_path, "./temp/source_temp.html"))
        target_file: str = abspath(join(absolute_path, top_folder_relative_path + "/target.html"))

        shutil.copyfile(original_file, original_file_copy)

        return (target_file, original_file_copy)

    def tearDown(self):
        temp_folder: str = abspath(dirname(realpath(__file__)) + "/temp")

        if os.path.isdir(temp_folder):
            shutil.rmtree(temp_folder)  

if __name__ == '__main__':
    unittest.main()
