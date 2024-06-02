from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.compression_type import CompressionType
from ..models.new_line_character_type import NewLineCharacterType
from ..types import UNSET, Unset

T = TypeVar("T", bound="CsvFileFormatSpec")


@_attrs_define
class CsvFileFormatSpec:
    """
    Attributes:
        all_varchar (Union[Unset, bool]): Option to skip type detection for CSV parsing and assume all columns to be of
            type VARCHAR.
        allow_quoted_nulls (Union[Unset, bool]): Option to allow the conversion of quoted values to NULL values.
        auto_detect (Union[Unset, bool]): Enables auto detection of CSV parameters.
        compression (Union[Unset, CompressionType]):
        no_compression_extension (Union[Unset, bool]): Whether the compression extension is present at the end of the
            file name.
        dateformat (Union[Unset, str]): Specifies the date format to use when parsing dates.
        decimal_separator (Union[Unset, str]): The decimal separator of numbers.
        delim (Union[Unset, str]): Specifies the string that separates columns within each row (line) of the file.
        escape (Union[Unset, str]): Specifies the string that should appear before a data character sequence that
            matches the quote value.
        filename (Union[Unset, bool]): Whether or not an extra filename column should be included in the result.
        header (Union[Unset, bool]): Specifies that the file contains a header line with the names of each column in the
            file.
        hive_partitioning (Union[Unset, bool]): Whether or not to interpret the path as a hive partitioned path.
        ignore_errors (Union[Unset, bool]): Option to ignore any parsing errors encountered - and instead ignore rows
            with errors.
        new_line (Union[Unset, NewLineCharacterType]):
        quote (Union[Unset, str]): Specifies the quoting string to be used when a data value is quoted.
        sample_size (Union[Unset, int]): The number of sample rows for auto detection of parameters.
        skip (Union[Unset, int]): The number of lines at the top of the file to skip.
        timestampformat (Union[Unset, str]): Specifies the date format to use when parsing timestamps.
    """

    all_varchar: Union[Unset, bool] = UNSET
    allow_quoted_nulls: Union[Unset, bool] = UNSET
    auto_detect: Union[Unset, bool] = UNSET
    compression: Union[Unset, CompressionType] = UNSET
    no_compression_extension: Union[Unset, bool] = UNSET
    dateformat: Union[Unset, str] = UNSET
    decimal_separator: Union[Unset, str] = UNSET
    delim: Union[Unset, str] = UNSET
    escape: Union[Unset, str] = UNSET
    filename: Union[Unset, bool] = UNSET
    header: Union[Unset, bool] = UNSET
    hive_partitioning: Union[Unset, bool] = UNSET
    ignore_errors: Union[Unset, bool] = UNSET
    new_line: Union[Unset, NewLineCharacterType] = UNSET
    quote: Union[Unset, str] = UNSET
    sample_size: Union[Unset, int] = UNSET
    skip: Union[Unset, int] = UNSET
    timestampformat: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        all_varchar = self.all_varchar
        allow_quoted_nulls = self.allow_quoted_nulls
        auto_detect = self.auto_detect
        compression: Union[Unset, str] = UNSET
        if not isinstance(self.compression, Unset):
            compression = self.compression.value

        no_compression_extension = self.no_compression_extension
        dateformat = self.dateformat
        decimal_separator = self.decimal_separator
        delim = self.delim
        escape = self.escape
        filename = self.filename
        header = self.header
        hive_partitioning = self.hive_partitioning
        ignore_errors = self.ignore_errors
        new_line: Union[Unset, str] = UNSET
        if not isinstance(self.new_line, Unset):
            new_line = self.new_line.value

        quote = self.quote
        sample_size = self.sample_size
        skip = self.skip
        timestampformat = self.timestampformat

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if all_varchar is not UNSET:
            field_dict["all_varchar"] = all_varchar
        if allow_quoted_nulls is not UNSET:
            field_dict["allow_quoted_nulls"] = allow_quoted_nulls
        if auto_detect is not UNSET:
            field_dict["auto_detect"] = auto_detect
        if compression is not UNSET:
            field_dict["compression"] = compression
        if no_compression_extension is not UNSET:
            field_dict["no_compression_extension"] = no_compression_extension
        if dateformat is not UNSET:
            field_dict["dateformat"] = dateformat
        if decimal_separator is not UNSET:
            field_dict["decimal_separator"] = decimal_separator
        if delim is not UNSET:
            field_dict["delim"] = delim
        if escape is not UNSET:
            field_dict["escape"] = escape
        if filename is not UNSET:
            field_dict["filename"] = filename
        if header is not UNSET:
            field_dict["header"] = header
        if hive_partitioning is not UNSET:
            field_dict["hive_partitioning"] = hive_partitioning
        if ignore_errors is not UNSET:
            field_dict["ignore_errors"] = ignore_errors
        if new_line is not UNSET:
            field_dict["new_line"] = new_line
        if quote is not UNSET:
            field_dict["quote"] = quote
        if sample_size is not UNSET:
            field_dict["sample_size"] = sample_size
        if skip is not UNSET:
            field_dict["skip"] = skip
        if timestampformat is not UNSET:
            field_dict["timestampformat"] = timestampformat

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        all_varchar = d.pop("all_varchar", UNSET)

        allow_quoted_nulls = d.pop("allow_quoted_nulls", UNSET)

        auto_detect = d.pop("auto_detect", UNSET)

        _compression = d.pop("compression", UNSET)
        compression: Union[Unset, CompressionType]
        if isinstance(_compression, Unset):
            compression = UNSET
        else:
            compression = CompressionType(_compression)

        no_compression_extension = d.pop("no_compression_extension", UNSET)

        dateformat = d.pop("dateformat", UNSET)

        decimal_separator = d.pop("decimal_separator", UNSET)

        delim = d.pop("delim", UNSET)

        escape = d.pop("escape", UNSET)

        filename = d.pop("filename", UNSET)

        header = d.pop("header", UNSET)

        hive_partitioning = d.pop("hive_partitioning", UNSET)

        ignore_errors = d.pop("ignore_errors", UNSET)

        _new_line = d.pop("new_line", UNSET)
        new_line: Union[Unset, NewLineCharacterType]
        if isinstance(_new_line, Unset):
            new_line = UNSET
        else:
            new_line = NewLineCharacterType(_new_line)

        quote = d.pop("quote", UNSET)

        sample_size = d.pop("sample_size", UNSET)

        skip = d.pop("skip", UNSET)

        timestampformat = d.pop("timestampformat", UNSET)

        csv_file_format_spec = cls(
            all_varchar=all_varchar,
            allow_quoted_nulls=allow_quoted_nulls,
            auto_detect=auto_detect,
            compression=compression,
            no_compression_extension=no_compression_extension,
            dateformat=dateformat,
            decimal_separator=decimal_separator,
            delim=delim,
            escape=escape,
            filename=filename,
            header=header,
            hive_partitioning=hive_partitioning,
            ignore_errors=ignore_errors,
            new_line=new_line,
            quote=quote,
            sample_size=sample_size,
            skip=skip,
            timestampformat=timestampformat,
        )

        csv_file_format_spec.additional_properties = d
        return csv_file_format_spec

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
