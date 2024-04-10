import unittest
from scripts.content_modifiers.script_tag_modifier import modify_script_tag

class ScriptTagModifierTest(unittest.TestCase):
    
    def test_modify_script_tag__when_tag_present__then_replaced(self):
        source: str = """<script src="/static/js/a_script.js"></script>"""
        target: str = """<script data-rocket-src="/static/js/a_script.js" type="rocketlazyloadscript"></script>"""

        output: str = modify_script_tag(source)

        self.assertEqual(target, output)

    def test_modify_script_tag__when_special_tag_present__then_preserved(self):
        source: str = """<script src="/static/js/lazyload.min.js"></script>"""

        output: str = modify_script_tag(source)

        self.assertEqual(source, output)

    def test_modify_script_tag__when_inline_another_tag_with_script__then_works(self):
        source: str = """              </style> <script src="../../assets/javascripts/glightbox.min.js"></script></head>"""
        target: str = """              </style> <script data-rocket-src="../../assets/javascripts/glightbox.min.js" type="rocketlazyloadscript"></script></head>"""

        output: str = modify_script_tag(source)

        self.assertEqual(target, output)

if __name__ == '__main__':
    unittest.main()
