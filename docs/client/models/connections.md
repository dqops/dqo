# DQOps REST API connections models reference
The references of all objects used by [connections](../operations/connections.md) REST API operations are listed below.


## AllChecksPatchParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_search_filters`](./common.md#checksearchfilters)</span>|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`check_model_patch`](./common.md#checkmodel)</span>|Sample configured check model which will pasted onto selected checks.|*[CheckModel](./common.md#checkmodel)*|
|<span class="no-wrap-code">`selected_tables_to_columns`</span>|List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.|*Dict[string, List[string]]*|
|<span class="no-wrap-code">`override_conflicts`</span>|Override existing configurations if they're present. If false, apply updates only to the fields for which no configuration exists.|*boolean*|


___

## BulkCheckDeactivateParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_search_filters`](./common.md#checksearchfilters)</span>|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">`selected_tables_to_columns`</span>|List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.|*Dict[string, List[string]]*|


___

## ConnectionSpecificationModel
Connection model returned by the rest api.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`connection_hash`</span>|Connection hash that identifies the connection using a unique hash code.|*long*|
|<span class="no-wrap-code">[`spec`](../../reference/yaml/ConnectionYaml.md#connectionspec)</span>|Full connection specification, including all nested objects (but not a list of tables).|*[ConnectionSpec](../../reference/yaml/ConnectionYaml.md#connectionspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

