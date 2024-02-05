from enum import Enum


class DetectedDatatypeCategory(str, Enum):
    BOOLEANS = "booleans"
    DATES = "dates"
    DATETIMES = "datetimes"
    FLOATS = "floats"
    INTEGERS = "integers"
    MIXED = "mixed"
    TEXTS = "texts"

    def __str__(self) -> str:
        return str(self.value)
