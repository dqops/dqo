import unittest
from scripts.content_modifiers.link_modifier import modify_link

class LinkModifierTest(unittest.TestCase):

    # beginning single dot in link and multiple script tags inline are not supported and not covered in tests

    def test_modify_link__when_absolute_link_ends_with_slash__then_dont_modify(self):
        source: str = """<a href="/docs/somewhere/">"""
        output: str = modify_link(source, None)
        self.assertEqual(source, output)

    def test_modify_link__when_link_is_absolute__then_dont_modify(self):
        source: str = """<a href="/docs/somewhere/index.html">"""
        output: str = modify_link(source, None)
        self.assertEqual(source, output)

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

    def test_modify_link__when_two_links_with_trailing_slash_in_one_line__then_dont_modify(self):
        file_path: str = """site/checks/index.html"""
        source: str = """<a href="/docs/checks/column/"> and <a href="/docs/checks/column2/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(source, output)

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

    def test_modify_link__when_link_is_related_to_opened_page__then_expand_to_full_link(self):
        file_path: str = """site/working-with-dqo/configure-scheduling-of-data-quality-checks/index.html"""
        source: str = """<a href="configuring-schedules-by-modifying-yaml-file/">"""
        target: str = """<a href="/docs/working-with-dqo/configure-scheduling-of-data-quality-checks/configuring-schedules-by-modifying-yaml-file/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(target, output)

    def test_modify_link__when_link_starts_with_hash__then_dont_modify(self):
        file_path: str = """site/working-with-dqo/index.html"""
        source: str = """<a href="#some-content/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(source, output)

    def test_modify_link__when_link_starts_with_http__then_dont_modify(self):
        file_path: str = """site/working-with-dqo/index.html"""
        source: str = """<a href="http://www.some-page.com/">"""
        output: str = modify_link(source, file_path)

        self.assertEqual(source, output)

if __name__ == '__main__':
    unittest.main()
