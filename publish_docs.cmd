@echo off

@REM
@REM Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
@REM
@REM This file is licensed under the Business Source License 1.1,
@REM which can be found in the root directory of this repository.
@REM
@REM Change Date: This file will be licensed under the Apache License, Version 2.0,
@REM four (4) years from its last modification date.
@REM

python -m pip install --upgrade --user -r ./requirements.txt
set ENABLE_SEARCH=true
python -m mkdocs build
set ENABLE_SEARCH=
python tools/documentation/docs_modifier.py site
gsutil -m rsync -j html,txt,xml,png,js,css,json,svg,gif -r site gs://docs-dqo-ai/docs/
