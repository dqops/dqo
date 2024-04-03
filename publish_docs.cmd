@echo off
python -m pip install --upgrade --user -r ./requirements.txt
set ENABLE_SEARCH=true
python -m mkdocs build
python tools/documentation/docs_modifier.py site
set ENABLE_SEARCH=
gsutil -m rsync -j html,txt,xml,png,js,css,json,svg,gif -r site gs://docs-dqo-ai/docs/
