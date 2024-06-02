from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.compression_type import CompressionType
from ..models.json_format_type import JsonFormatType
from ..models.json_records_type import JsonRecordsType
from ..types import UNSET, Unset

T = TypeVar("T", bound="JsonFileFormatSpec")


@_attrs_define
class JsonFileFormatSpec:
    """
    Attributes:
        auto_detect (Union[Unset, bool]): Whether to auto-detect detect the names of the keys and data types of the
            values automatically.
        compression (Union[Unset, CompressionType]):
        no_compression_extension (Union[Unset, bool]): Whether the compression extension is present at the end of the
            file name.
        convert_strings_to_integers (Union[Unset, bool]): Whether strings representing integer values should be
            converted to a numerical type.
        dateformat (Union[Unset, str]): Specifies the date format to use when parsing dates.
        filename (Union[Unset, bool]): Whether or not an extra filename column should be included in the result.
        format_ (Union[Unset, JsonFormatType]):
        hive_partitioning (Union[Unset, bool]): Whether or not to interpret the path as a hive partitioned path.
        ignore_errors (Union[Unset, bool]): Whether to ignore parse errors (only possible when format is
            'newline_delimited').
        maximum_depth (Union[Unset, int]): Maximum nesting depth to which the automatic schema detection detects types.
            Set to -1 to fully detect nested JSON types.
        maximum_object_size (Union[Unset, int]): The maximum size of a JSON object (in bytes).
        records (Union[Unset, JsonRecordsType]):
        sample_size (Union[Unset, int]): The number of sample rows for auto detection of parameters.
        timestampformat (Union[Unset, str]): Specifies the date format to use when parsing timestamps.
    """

    auto_detect: Union[Unset, bool] = UNSET
    compression: Union[Unset, CompressionType] = UNSET
    no_compression_extension: Union[Unset, bool] = UNSET
    convert_strings_to_integers: Union[Unset, bool] = UNSET
    dateformat: Union[Unset, str] = UNSET
    filename: Union[Unset, bool] = UNSET
    format_: Union[Unset, JsonFormatType] = UNSET
    hive_partitioning: Union[Unset, bool] = UNSET
    ignore_errors: Union[Unset, bool] = UNSET
    maximum_depth: Union[Unset, int] = UNSET
    maximum_object_size: Union[Unset, int] = UNSET
    records: Union[Unset, JsonRecordsType] = UNSET
    sample_size: Union[Unset, int] = UNSET
    timestampformat: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        auto_detect = self.auto_detect
        compression: Union[Unset, str] = UNSET
        if not isinstance(self.compression, Unset):
            compression = self.compression.value

        no_compression_extension = self.no_compression_extension
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
        records: Union[Unset, str] = UNSET
        if not isinstance(self.records, Unset):
            records = self.records.value

        sample_size = self.sample_size
        timestampformat = self.timestampformat

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if auto_detect is not UNSET:
            field_dict["auto_detect"] = auto_detect
        if compression is not UNSET:
            field_dict["compression"] = compression
        if no_compression_extension is not UNSET:
            field_dict["no_compression_extension"] = no_compression_extension
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
        if sample_size is not UNSET:
            field_dict["sample_size"] = sample_size
        if timestampformat is not UNSET:
            field_dict["timestampformat"] = timestampformat

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        auto_detect = d.pop("auto_detect", UNSET)

        _compression = d.pop("compression", UNSET)
        compression: Union[Unset, CompressionType]
        if isinstance(_compression, Unset):
            compression = UNSET
        else:
            compression = CompressionType(_compression)

        no_compression_extension = d.pop("no_compression_extension", UNSET)

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

        _records = d.pop("records", UNSET)
        records: Union[Unset, JsonRecordsType]
        if isinstance(_records, Unset):
            records = UNSET
        else:
            records = JsonRecordsType(_records)

        sample_size = d.pop("sample_size", UNSET)

        timestampformat = d.pop("timestampformat", UNSET)

        json_file_format_spec = cls(
            auto_detect=auto_detect,
            compression=compression,
            no_compression_extension=no_compression_extension,
            convert_strings_to_integers=convert_strings_to_integers,
            dateformat=dateformat,
            filename=filename,
            format_=format_,
            hive_partitioning=hive_partitioning,
            ignore_errors=ignore_errors,
            maximum_depth=maximum_depth,
            maximum_object_size=maximum_object_size,
            records=records,
            sample_size=sample_size,
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
