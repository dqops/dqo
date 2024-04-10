import unittest
from scripts.content_modifiers.search_script_link_modifier import modify_link

class SearchScriptLinkModifierTest(unittest.TestCase):
    
    def test_modify_link__when_link_in_base__then_replaced(self):
        source: str = """    <script id="__config" type="application/json">{"base": "..", "features": ["navigation.indexes", "navigation.footer"}}</script>"""
        target: str = """    <script id="__config" type="application/json">{"base": "/", "features": ["navigation.indexes", "navigation.footer"}}</script>"""

        output: str = modify_link(source)

        self.assertEqual(target, output)

    def test_modify_link__when_link_in_base_multilevel__then_replaced(self):
        source: str = """    <script id="__config" type="application/json">{"base": "../../..", "features": ["navigation.indexes", "navigation.footer"}}</script>"""
        target: str = """    <script id="__config" type="application/json">{"base": "/", "features": ["navigation.indexes", "navigation.footer"}}</script>"""

        output: str = modify_link(source)

        self.assertEqual(target, output)

if __name__ == '__main__':
    unittest.main()
