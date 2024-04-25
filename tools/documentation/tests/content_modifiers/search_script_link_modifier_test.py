import unittest
from scripts.content_modifiers.search_script_link_modifier import modify_link

class SearchScriptLinkModifierTest(unittest.TestCase):
    
    def test_modify_link__when_link_in_base__then_replaced(self):
        source: str = """    <script id="__config" type="application/json">{"base": "..", "features": ["navigation.indexes", "navigation.footer"}}</script>"""
        target: str = """    <script id="__config" type="application/json">{"base": "/docs/", "features": ["navigation.indexes", "navigation.footer"}}</script>"""

        output: str = modify_link(source)

        self.assertEqual(target, output)

    def test_modify_link__when_link_in_base_multilevel__then_replaced(self):
        source: str = """    <script id="__config" type="application/json">{"base": "../../..", "features": ["navigation.indexes", "navigation.footer"}}</script>"""
        target: str = """    <script id="__config" type="application/json">{"base": "/docs/", "features": ["navigation.indexes", "navigation.footer"}}</script>"""

        output: str = modify_link(source)

        self.assertEqual(target, output)

    def test_modify_link__when_search_key_value_not_touched__then_replaced(self):
        source: str = """<script id="__config" type="application/json">{"base": ".", "search": "assets/javascripts/workers/search.b8dbb3d2.min.js"}}</script>"""
        target: str = """<script id="__config" type="application/json">{"base": "/docs/", "search": "assets/javascripts/workers/search.b8dbb3d2.min.js"}}</script>"""

        output: str = modify_link(source)

        self.assertEqual(target, output)



if __name__ == '__main__':
    unittest.main()
