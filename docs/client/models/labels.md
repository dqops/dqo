---
title: DQOps REST API labels models reference
---
# DQOps REST API labels models reference
The references of all objects used by [labels](../operations/labels.md) REST API operations are listed below.


## LabelModel
Label model that is returned by the REST API. A label is a tag that was assigned to a data source, table, column or a single check.
 Labels play the role of a business glossary.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`label`</span>|Label text.|*string*|
|<span class="no-wrap-code">`labels_count`</span>|The number of data assets tagged with this label.|*integer*|
|<span class="no-wrap-code">`nested_labels_count`</span>|The number of data assets tagged with nested labels below this prefix node. For example, if the current label is "address", and there are nested labels "address/city" and "address/zipcode", this value returns the count of data assets tagged with these nested tags.|*integer*|


___

