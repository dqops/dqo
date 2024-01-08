
## **column count**
**Full sensor name**
```
table/schema/column_count
```
**Description**
Table schema data quality sensor that reads the metadata from a monitored data source and counts the number of columns.




**SQL Template (Jinja2)**
___

## **column list ordered hash**
**Full sensor name**
```
table/schema/column_list_ordered_hash
```
**Description**
Table schema data quality sensor detects if the list and order of columns have changed on the table.
 The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.




**SQL Template (Jinja2)**
___

## **column list unordered hash**
**Full sensor name**
```
table/schema/column_list_unordered_hash
```
**Description**
Table schema data quality sensor detects if the list of columns have changed on the table.
 The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns, but not on the order of columns.




**SQL Template (Jinja2)**
___

## **column types hash**
**Full sensor name**
```
table/schema/column_types_hash
```
**Description**
Table schema data quality sensor detects if the list of columns has changed or any of the column has a new data type, length, scale, precision or nullability.
 The sensor calculates a hash of the list of column names and all components of the column&#x27;s type (the type name, length, scale, precision, nullability).
 The hash value does not depend on the order of columns.




**SQL Template (Jinja2)**
___
