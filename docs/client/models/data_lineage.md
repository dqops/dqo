---
title: DQOps REST API data_lineage models reference
---
# DQOps REST API data_lineage models reference
The references of all objects used by [data_lineage](../operations/data_lineage.md) REST API operations are listed below.


## DomainConnectionTableKey
A key object that identifies every table. These keys are used in a cache to store the most recent
 table quality status for each table or a data lineage cache.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`data_domain`</span>|Data domain name.|*string*|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`physical_table_name`](./common.md#physicaltablename)</span>|Full table name, including the schema and the table names.|*[PhysicalTableName](./common.md#physicaltablename)*|


___

## TableLineageFlowModel
Table lineage flow model that describes the data flow from one table to another table, and the data quality status of the source table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`source_table`](./data_lineage.md#domainconnectiontablekey)</span>|The source table.|*[DomainConnectionTableKey](./data_lineage.md#domainconnectiontablekey)*|
|<span class="no-wrap-code">[`target_table`](./data_lineage.md#domainconnectiontablekey)</span>|The target table.|*[DomainConnectionTableKey](./data_lineage.md#domainconnectiontablekey)*|
|<span class="no-wrap-code">[`source_table_quality_status`](./check_results.md#tablecurrentdataqualitystatusmodel)</span>|The current data quality status of the source table.|*[TableCurrentDataQualityStatusModel](./check_results.md#tablecurrentdataqualitystatusmodel)*|
|<span class="no-wrap-code">[`target_table_quality_status`](./check_results.md#tablecurrentdataqualitystatusmodel)</span>|The current data quality status of the target table.|*[TableCurrentDataQualityStatusModel](./check_results.md#tablecurrentdataqualitystatusmodel)*|
|<span class="no-wrap-code">[`upstream_combined_quality_status`](./check_results.md#tablecurrentdataqualitystatusmodel)</span>|The data quality status identified from the data quality status of all upstream tables and the target table.|*[TableCurrentDataQualityStatusModel](./check_results.md#tablecurrentdataqualitystatusmodel)*|
|<span class="no-wrap-code">`weight`</span>|Weight of the flow calculated from the row count of the source table. It is a logarithm of the row count, but never less than 1.|*integer*|


___

## TableLineageModel
The table lineage model that returns all upstream tables, downstream tables, or both.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`relative_table`](#domainconnectiontablekey)</span>|The table for which the data lineage is generated.|*[DomainConnectionTableKey](#domainconnectiontablekey)*|
|<span class="no-wrap-code">`flows`</span>|A list of data flows from source tables to direct target tables. Describes the data quality status of the source table.|*List[[TableLineageFlowModel](#tablelineageflowmodel)]*|


___

## TableLineageSourceListModel
Data lineage model that describes one source table of the current table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`target_connection`</span>|The connection name where the target table is defined.|*string*|
|<span class="no-wrap-code">`target_schema`</span>|The schema name in the target connection where the target table is defined.|*string*|
|<span class="no-wrap-code">`target_table`</span>|The name of the target table inside the target schema.|*string*|
|<span class="no-wrap-code">`source_connection`</span>|The name of a source connection that is defined in DQOps and contains a source table from which the current table receives data.|*string*|
|<span class="no-wrap-code">`source_schema`</span>|The name of a source schema within the source connection that contains a source table from which the current table receives data.|*string*|
|<span class="no-wrap-code">`source_table`</span>|The name of a source schema within the source connection that contains a source table from which the current table receives data.|*string*|
|<span class="no-wrap-code">`data_lineage_source_tool`</span>|The name of a source tool from which this data lineage information was copied. This field should be filled when the data lineage was imported from another data catalog or a data lineage tracking platform.|*string*|
|<span class="no-wrap-code">`properties`</span>|A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.|*Dict[string, string]*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">[`source_table_data_quality_status`](./check_results.md#tablecurrentdataqualitystatusmodel)</span>|The current data quality status for the table, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache. In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the table.|*[TableCurrentDataQualityStatusModel](./check_results.md#tablecurrentdataqualitystatusmodel)*|


___

## SourceColumnsSetSpec
A collection of unique names of source columns from which the current column receives data. This information is used to track column-level data lineage.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|


___

## ColumnLineageSourceSpec
Describes the list of source columns for a column in the current table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`source_columns`](#sourcecolumnssetspec)</span>|A list of source columns from the source table name from which this column receives data.|*[SourceColumnsSetSpec](#sourcecolumnssetspec)*|
|<span class="no-wrap-code">`properties`</span>|A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.|*Dict[string, string]*|


___

## ColumnLineageSourceSpecMap
Dictionary of mapping of source columns to the columns in the current table.
 The keys in this dictionary are the column names in the current table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>||*Dict[string, [ColumnLineageSourceSpec](./data_lineage.md#columnlineagesourcespec)]*|


___

## TableLineageSourceSpec
Data lineage specification for a table to identify a source table of the current table where this object is stored.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`source_connection`</span>|The name of a source connection that is defined in DQOps and contains a source table from which the current table receives data.|*string*|
|<span class="no-wrap-code">`source_schema`</span>|The name of a source schema within the source connection that contains a source table from which the current table receives data.|*string*|
|<span class="no-wrap-code">`source_table`</span>|The name of a source table in the source schema from which the current table receives data.|*string*|
|<span class="no-wrap-code">`data_lineage_source_tool`</span>|The name of a source tool from which this data lineage information was copied. This field should be filled when the data lineage was imported from another data catalog or a data lineage tracking platform.|*string*|
|<span class="no-wrap-code">`properties`</span>|A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.|*Dict[string, string]*|
|<span class="no-wrap-code">[`columns`](#columnlineagesourcespecmap)</span>|Configuration of source columns for each column in the current table. The keys in this dictionary are column names in the current table. The object stored in the dictionary contain a list of source columns.|*[ColumnLineageSourceSpecMap](#columnlineagesourcespecmap)*|


___

