from enum import Enum


class DetectedDatatypeCategory(str, Enum):
    BOOLEANS = "booleans"
    DATES = "dates"
    DATETIMES = "datetimes"
    FLOATS = "floats"
    INTEGERS = "integers"
    MIXED = "mixed"
    TEXTS = "texts"
    TIMESTAMPS = "timestamps"

    def __str__(self) -> str:
        return str(self.value)
