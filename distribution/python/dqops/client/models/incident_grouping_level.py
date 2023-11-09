from enum import Enum


class IncidentGroupingLevel(str, Enum):
    TABLE = "table"
    TABLE_DIMENSION = "table_dimension"
    TABLE_DIMENSION_CATEGORY = "table_dimension_category"
    TABLE_DIMENSION_CATEGORY_NAME = "table_dimension_category_name"
    TABLE_DIMENSION_CATEGORY_TYPE = "table_dimension_category_type"

    def __str__(self) -> str:
        return str(self.value)
