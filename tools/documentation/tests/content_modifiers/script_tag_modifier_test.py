import unittest
from scripts.content_modifiers.script_tag_modifier import modify_script_tag

class ScriptTagModifierTest(unittest.TestCase):
    
    def test_modify_script_tag__when_tag_present__then_replaced(self):
        source: str = """<script src="/static/js/a_script.js"></script>"""
        target: str = """<script defer data-rocket-src="/static/js/a_script.js" type="rocketlazyloadscript"></script>"""

        output: str = modify_script_tag(source)

        self.assertEqual(target, output)

    def test_modify_script_tag__when_special_tag_present__then_add_async_attribute(self):
        source: str = """<script src="/static/js/lazyload.min.js"></script>"""
        target: str = """<script async src="/static/js/lazyload.min.js"></script>"""

        output: str = modify_script_tag(source)

        self.assertEqual(target, output)

    def test_modify_script_tag__when_inline_another_tag_with_script__then_works(self):
        source: str = """              </style> <script src="../../assets/javascripts/glightbox.min.js"></script></head>"""
        target: str = """              </style> <script defer data-rocket-src="../../assets/javascripts/glightbox.min.js" type="rocketlazyloadscript"></script></head>"""

        output: str = modify_script_tag(source)

        self.assertEqual(target, output)

    def test_modify_script_tag__when_defer_exists__then_do_not_duplicate_it(self):
        source: str = """<script defer src="/static/js/a_script.js"></script>"""
        target: str = """<script defer data-rocket-src="/static/js/a_script.js" type="rocketlazyloadscript"></script>"""

        output: str = modify_script_tag(source)

        self.assertEqual(target, output)

    def test_modify_script_tag__when_inline_script_with_no_attributes__then_dont_modify(self):
        source: str = """<script>foo bar</script>"""
        output: str = modify_script_tag(source)
        self.assertEqual(source, output)

    def test_modify_script_tag__when_bundle_script__then_dont_modify(self):
        source: str = """<script src="assets/javascripts/bundle.c18c5fb9.min.js"></script>"""
        output: str = modify_script_tag(source)
        self.assertEqual(source, output)

    def test_modify_script_tag__when_inline_script_with_no_attributes_but_glightbox_script__then_modify(self):
        source: str = """<script>document$.subscribe(() => {const lightbox = GLightbox({"touchNavigation": true, "loop": false});})</script></body>"""
        target: str = """<script defer type="rocketlazyloadscript">document$.subscribe(() => {const lightbox = GLightbox({"touchNavigation": true, "loop": false});})</script></body>"""
        output: str = modify_script_tag(source)
        self.assertEqual(target, output)

if __name__ == '__main__':
    unittest.main()
