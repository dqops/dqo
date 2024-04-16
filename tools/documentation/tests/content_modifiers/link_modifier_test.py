import unittest
from scripts.content_modifiers.link_modifier import modify_link

class LinkModifierTest(unittest.TestCase):
    
    def test_modify_link__when_a_href_ends_with_slash__then_preserve_tag(self):
        source: str = """<a href="somewhere/">"""
        target: str = """<a href="somewhere/">"""

        output: str = modify_link(source, None)

        self.assertEqual(target, output)

    def test_modify_link__when_nothing_to_do__then_preserve_tag(self):
        source: str = """<a href="somewhere/index.html">"""

        output: str = modify_link(source, None)

        self.assertEqual(source, output)

    # def test_modify_link__when_inline_another_tag__then_works(self):
    #     source: str = """              </style> <a href="somewhere/"></script></head>"""
    #     target: str = """              </style> <a href="somewhere/index.html"></script></head>"""

    #     output: str = modify_link(source, None)

    #     self.assertEqual(target, output)

    def test_modify_link__when_path_is_relative__then_expand_path(self):
        file_path: str = """site/data-sources/athena/index.html"""
        source: str = """<a href="../index.html">"""
        target: str = """<a href="/docs/data-sources/index.html">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_site_name_in_middle_of_link__then_do_not_touch_the_middle_site_name(self):
        file_path: str = """site/somewhere/site/athena/index.html"""
        source: str = """<a href="../../site/index.html">"""
        target: str = """<a href="/docs/somewhere/site/index.html">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_path_is_relative_with_depth_two__then_expand_path(self):
        file_path: str = """site/data-sources/athena/index.html"""
        source: str = """<a href="../../images/favicon.ico">"""
        target: str = """<a href="/docs/images/favicon.ico">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_path_is_relative_and_end_wth_slash__then_expands_path(self):
        file_path: str = """site/data-sources/athena/index.html"""
        source: str = """<a href="../">"""
        target: str = """<a href="/docs/data-sources/">"""

        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_two_in_one_line__then_expands_both(self):
        file_path: str = """site/checks/index.html"""
        source: str = """<a href="../checks/column/numeric/"></a> and <a href="../checks/column/numeric/"></a>checks.</td>"""
        target: str = """<a href="/docs/checks/column/numeric/"></a> and <a href="/docs/checks/column/numeric/"></a>checks.</td>"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_two_links_with_trailing_slash_in_one_line__then_not_touched(self):
        file_path: str = """site/checks/index.html"""
        source: str = """<a href="/docs/checks/column/"> and <a href="/docs/checks/column2/">"""
        target: str = """<a href="/docs/checks/column/"> and <a href="/docs/checks/column2/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_just_two_dots__then_modifies(self):
        file_path: str = """site/checks/index.html"""
        source: str = """<a href="..">"""
        target: str = """<a href="/docs/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_just_one_dot__then_modifies(self):
        file_path: str = """site/index.html"""
        source: str = """<a href=".">"""
        target: str = """<a href="/docs/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    # def test_modify_link__when_one_dot_with_filename__then_modifies(self):
    #     file_path: str = """site/index.html"""
    #     source: str = """<a href="./index.html">"""
    #     target: str = """<a href="/docs/index.html">"""
    #     output: str = modify_link(source, file_path)

    #     self.assertEqual(target, output)

    # def test_modify_link__when_one_dot_with_filename_under_folder_structure__then_modifies(self):
    #     file_path: str = """site/data-sources/mysql/index.html"""
    #     source: str = """<a href="./index.html">"""
    #     target: str = """<a href="/docs/data-sources/mysql/index.html">"""
    #     output: str = modify_link(source, file_path)

    #     self.assertEqual(target, output)
        
    def test_modify_link__when_one_dot_ends_link__then_modifies(self):
        file_path: str = "site"
        source: str = """<a href="/docs/.">"""
        target: str = """<a href="/docs/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_two_dots_ends_link__then_modifies(self):
        file_path: str = """site/dqo-concepts/definition-of-data-quality-checks/data-profiling-checks/index.html"""
        source: str = """<a href="../..">"""
        target: str = """<a href="/docs/dqo-concepts/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

if __name__ == '__main__':
    unittest.main()
