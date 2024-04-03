import unittest
from files_provider import get_all_files
import os 
from os.path import abspath, dirname, join, realpath

class FilesProviderTest(unittest.TestCase):

    def test_get_all_files_returns_only_5_html_elements(self):
        
        absolute_path: str = dirname(realpath(__file__))
        root_dir: str = abspath(join(absolute_path, "./files_provider_examples"))
        
        files: list[str] = get_all_files(root_dir)

        self.assertEqual(5, len(files))

if __name__ == '__main__':
    unittest.main()
