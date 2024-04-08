import os
import unittest
from script_tag_modifier import script_tag_modification
import os 
from os.path import abspath, dirname, join, realpath

class ScriptTagModifierTest(unittest.TestCase):

    def setUp(self) -> None:

        self.maxDiff = None

        temp_folder: str = abspath(dirname(realpath(__file__)) + "/temp")

        if not os.path.isdir(temp_folder):
            os.mkdir(temp_folder)
    
    def test_modifier_tag_to_be_replaced(self):
        source_line: str = """<script src="/static/js/a_script.js"></script>"""
        target_line: str = """<script data-rocket-src="/static/js/a_script.js" type="rocketlazyloadscript"></script>"""

        computed_line: str = script_tag_modification(source_line)

        self.assertEqual(target_line, computed_line)

    def test_modifier_tag_to_be_preserved(self):
        source_line: str = """<script src="/static/js/lazyload.min.js"></script>"""

        computed_line: str = script_tag_modification(source_line)

        self.assertEqual(source_line, computed_line)

    def test_modifier_inline_another_tag_with_script(self):

        source_line: str = """              </style> <script src="../../assets/javascripts/glightbox.min.js"></script></head>"""
        target_line: str = """              </style> <script data-rocket-src="../../assets/javascripts/glightbox.min.js" type="rocketlazyloadscript"></script></head>"""

        computed_line: str = script_tag_modification(source_line)

        self.assertEqual(target_line, computed_line)

if __name__ == '__main__':
    unittest.main()
