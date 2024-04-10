@echo off
python -m pip install --upgrade --user -r ./requirements.txt
set ENABLE_SEARCH=true
python -m mkdocs build
set ENABLE_SEARCH=
python tools/documentation/docs_modifier.py site
gsutil -m rsync -j html,txt,xml,png,js,css,json,svg,gif -r site gs://docs-dqo-ai/docs/
