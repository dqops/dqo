---
title: DQOps REST API search models reference
---
# DQOps REST API search models reference
The references of all objects used by [search](../operations/search.md) REST API operations are listed below.


## DataTypeCategory
Enumeration of common data type categories of data types. The providers will use this information to answer
 which of their native data types matches a category. Some sensors (and profilers) cannot operate on some data types.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|numeric_integer<br/>numeric_decimal<br/>numeric_float<br/>datetime_timestamp<br/>datetime_datetime<br/>datetime_date<br/>datetime_time<br/>text<br/>clob<br/>json<br/>bool<br/>binary<br/>array<br/>other<br/>|

___

