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
        file_path: str = """site/data-sources/athena/index.html"""
        source: str = """<a href="../">"""
        target: str = """<a href="/docs/data-sources/index.html">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_two_in_one_line__then_modifies_both(self):
        file_path: str = """site/checks/index.html"""
        source: str = """<td style="text-align: left;">This<a href="../checks/column/numeric/valid-latitude-percent/index.html">valid_latitude_percent</a> and <a href="../checks/column/numeric/valid-longitude-percent/">valid_longitude_percent</a>checks.</td>"""
        target: str = """<td style="text-align: left;">This<a href="/docs/checks/column/numeric/valid-latitude-percent/index.html">valid_latitude_percent</a> and <a href="/docs/checks/column/numeric/valid-longitude-percent/index.html">valid_longitude_percent</a>checks.</td>"""
        self.maxDiff = None
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)


if __name__ == '__main__':
    unittest.main()
