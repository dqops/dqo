
## **column exists**
**Full sensor name**
```
column/schema/column_exists
```
**Description**
Column level data quality sensor that reads the metadata of the table from the data source and checks if the column name exists on the table.
 Returns 1.0 when the column was found, 0.0 when the column is missing.




**SQL Template (Jinja2)**
___

## **column type hash**
**Full sensor name**
```
column/schema/column_type_hash
```
**Description**
Column level data quality sensor that reads the metadata of the table from the data source and calculates a hash of the detected data type (also including the length, scale and precision)
 of the target colum.
 Returns a 15-16 decimal digit hash of the column data type.




**SQL Template (Jinja2)**
___
