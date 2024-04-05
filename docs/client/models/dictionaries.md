---
title: DQOps REST API dictionaries models reference
---
# DQOps REST API dictionaries models reference
The references of all objects used by [dictionaries](../operations/dictionaries.md) REST API operations are listed below.


## DataDictionaryListModel
Data dictionary CSV file list model with the basic information about the dictionary.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`dictionary_name`</span>|Dictionary name. It is the name of a file in the dictionaries/ folder inside the DQOps user's home folder.|*string*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the dictionary file.|*boolean*|
|<span class="no-wrap-code">`can_access_dictionary`</span>|Boolean flag that decides if the current user see or download the dictionary file.|*boolean*|


___

## DataDictionaryModel
Data dictionary CSV full model used to create and update the dictionary file. Contains the content of the CSV file as a text field.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`dictionary_name`</span>|Dictionary name. It is the name of a file in the dictionaries/ folder inside the DQOps user's home folder.|*string*|
|<span class="no-wrap-code">`file_content`</span>|Dictionary CSV file content as a single file.|*string*|


___

