from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.json_format_type import JsonFormatType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.json_file_format_spec_columns import JsonFileFormatSpecColumns


T = TypeVar("T", bound="JsonFileFormatSpec")


@_attrs_define
class JsonFileFormatSpec:
    """
    Attributes:
        auto_detect (Union[Unset, bool]): Whether to auto-detect detect the names of the keys and data types of the
            values automatically
        columns (Union[Unset, JsonFileFormatSpecColumns]): A struct that specifies the key names and value types
            contained within the JSON file (e.g., {key1: 'INTEGER', key2: 'VARCHAR'}). If auto_detect is enabled these will
            be inferred
        compression (Union[Unset, str]): The compression type for the file. By default this will be detected
            automatically from the file extension (e.g., t.json.gz will use gzip, t.json will use none). Options are 'none',
            'gzip', 'zstd', and 'auto'.
        convert_strings_to_integers (Union[Unset, bool]): 	Whether strings representing integer values should be
            converted to a numerical type.
        dateformat (Union[Unset, str]): Specifies the date format to use when parsing dates.
        filename (Union[Unset, bool]): Whether or not an extra filename column should be included in the result.
        format_ (Union[Unset, JsonFormatType]):
        hive_partitioning (Union[Unset, bool]): 	Whether or not to interpret the path as a hive partitioned path.
        ignore_errors (Union[Unset, bool]): Whether to ignore parse errors (only possible when format is
            'newline_delimited').
        maximum_depth (Union[Unset, int]): Maximum nesting depth to which the automatic schema detection detects types.
            Set to -1 to fully detect nested JSON types
        maximum_object_size (Union[Unset, int]): 	The maximum size of a JSON object (in bytes)
        records (Union[Unset, str]): Can be one of ['auto', 'true', 'false']
        timestampformat (Union[Unset, str]): 	Specifies the date format to use when parsing timestamps.
    """

    auto_detect: Union[Unset, bool] = UNSET
    columns: Union[Unset, "JsonFileFormatSpecColumns"] = UNSET
    compression: Union[Unset, str] = UNSET
    convert_strings_to_integers: Union[Unset, bool] = UNSET
    dateformat: Union[Unset, str] = UNSET
    filename: Union[Unset, bool] = UNSET
    format_: Union[Unset, JsonFormatType] = UNSET
    hive_partitioning: Union[Unset, bool] = UNSET
    ignore_errors: Union[Unset, bool] = UNSET
    maximum_depth: Union[Unset, int] = UNSET
    maximum_object_size: Union[Unset, int] = UNSET
    records: Union[Unset, str] = UNSET
    timestampformat: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        auto_detect = self.auto_detect
        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        compression = self.compression
        convert_strings_to_integers = self.convert_strings_to_integers
        dateformat = self.dateformat
        filename = self.filename
        format_: Union[Unset, str] = UNSET
        if not isinstance(self.format_, Unset):
            format_ = self.format_.value

        hive_partitioning = self.hive_partitioning
        ignore_errors = self.ignore_errors
        maximum_depth = self.maximum_depth
        maximum_object_size = self.maximum_object_size
        records = self.records
        timestampformat = self.timestampformat

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if auto_detect is not UNSET:
            field_dict["auto_detect"] = auto_detect
        if columns is not UNSET:
            field_dict["columns"] = columns
        if compression is not UNSET:
            field_dict["compression"] = compression
        if convert_strings_to_integers is not UNSET:
            field_dict["convert_strings_to_integers"] = convert_strings_to_integers
        if dateformat is not UNSET:
            field_dict["dateformat"] = dateformat
        if filename is not UNSET:
            field_dict["filename"] = filename
        if format_ is not UNSET:
            field_dict["format"] = format_
        if hive_partitioning is not UNSET:
            field_dict["hive_partitioning"] = hive_partitioning
        if ignore_errors is not UNSET:
            field_dict["ignore_errors"] = ignore_errors
        if maximum_depth is not UNSET:
            field_dict["maximum_depth"] = maximum_depth
        if maximum_object_size is not UNSET:
            field_dict["maximum_object_size"] = maximum_object_size
        if records is not UNSET:
            field_dict["records"] = records
        if timestampformat is not UNSET:
            field_dict["timestampformat"] = timestampformat

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.json_file_format_spec_columns import JsonFileFormatSpecColumns

        d = src_dict.copy()
        auto_detect = d.pop("auto_detect", UNSET)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, JsonFileFormatSpecColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = JsonFileFormatSpecColumns.from_dict(_columns)

        compression = d.pop("compression", UNSET)

        convert_strings_to_integers = d.pop("convert_strings_to_integers", UNSET)

        dateformat = d.pop("dateformat", UNSET)

        filename = d.pop("filename", UNSET)

        _format_ = d.pop("format", UNSET)
        format_: Union[Unset, JsonFormatType]
        if isinstance(_format_, Unset):
            format_ = UNSET
        else:
            format_ = JsonFormatType(_format_)

        hive_partitioning = d.pop("hive_partitioning", UNSET)

        ignore_errors = d.pop("ignore_errors", UNSET)

        maximum_depth = d.pop("maximum_depth", UNSET)

        maximum_object_size = d.pop("maximum_object_size", UNSET)

        records = d.pop("records", UNSET)

        timestampformat = d.pop("timestampformat", UNSET)

        json_file_format_spec = cls(
            auto_detect=auto_detect,
            columns=columns,
            compression=compression,
            convert_strings_to_integers=convert_strings_to_integers,
            dateformat=dateformat,
            filename=filename,
            format_=format_,
            hive_partitioning=hive_partitioning,
            ignore_errors=ignore_errors,
            maximum_depth=maximum_depth,
            maximum_object_size=maximum_object_size,
            records=records,
            timestampformat=timestampformat,
        )

        json_file_format_spec.additional_properties = d
        return json_file_format_spec

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> Any:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: Any) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
