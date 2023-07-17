@echo off
python3 -m pip install --upgrade --user -r ./requirements.txt
python3 -m mkdocs build
gsutil -m rsync -j html,txt,xml,png,js,css,json,svg,gif -r site gs://docs-dqo-ai/docs/
