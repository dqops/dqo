import unittest
from scripts.content_modifiers.link_modifier import modify_link

class LinkModifierTest(unittest.TestCase):
    
    def test_modify_link__when_a_tag_with_href_given__then_modify_link(self):
        source: str = """<a href="somewhere/">"""
        target: str = """<a href="somewhere/index.html">"""

        output: str = modify_link(source, None)

        self.assertEqual(target, output)

    def test_modify_link__when_nothing_to_do__then_preserve_tag(self):
        source: str = """<a href="somewhere/index.html">"""

        output: str = modify_link(source, None)

        self.assertEqual(source, output)

    def test_modify_link__when_inline_another_tag__then_works(self):
        source: str = """              </style> <a href="somewhere/"></script></head>"""
        target: str = """              </style> <a href="somewhere/index.html"></script></head>"""

        output: str = modify_link(source, None)

        self.assertEqual(target, output)

    def test_modify_link__when_path_is_relative__then_expand_path(self):
        file_path: str = """site/data-sources/athena/index.html"""
        source: str = """<a href="../index.html">"""
        target: str = """<a href="/docs/data-sources/index.html">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_path_is_relative_with_depth_two__then_expand_path(self):
        file_path: str = """site/data-sources/athena/index.html"""
        source: str = """<a href="../../images/favicon.ico">"""
        target: str = """<a href="/docs/images/favicon.ico">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_path_is_relative_and_end_wth_slash__then_expand_path_and_add_index_name(self):
        file_path: str = """/usr/share/docs/data-sources/athena/index.html"""
        source: str = """<a href="../">"""
        target: str = """<a href="/docs/data-sources/index.html">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

if __name__ == '__main__':
    unittest.main()
