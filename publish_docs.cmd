python -m pip install --user -r ./requirements.txt
python -m mkdocs build
gsutil -m rsync -j html,txt,xml,png,js,css,json,svg,gif -r site gs://docs-dqo-ai/docs/
