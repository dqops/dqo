from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.csv_file_format_spec_columns import CsvFileFormatSpecColumns


T = TypeVar("T", bound="CsvFileFormatSpec")


@_attrs_define
class CsvFileFormatSpec:
    """
    Attributes:
        all_varchar (Union[Unset, bool]):
        allow_quoted_nulls (Union[Unset, bool]):
        auto_detect (Union[Unset, bool]):
        columns (Union[Unset, CsvFileFormatSpecColumns]):
        compression (Union[Unset, str]):
        dateformat (Union[Unset, str]):
        decimal_separator (Union[Unset, str]):
        delim (Union[Unset, str]):
        escape (Union[Unset, str]):
        filename (Union[Unset, bool]):
        header (Union[Unset, bool]):
        hive_partitioning (Union[Unset, bool]):
        ignore_errors (Union[Unset, bool]):
        new_line (Union[Unset, str]):
        quote (Union[Unset, str]):
        skip (Union[Unset, int]):
        timestampformat (Union[Unset, str]):
    """

    all_varchar: Union[Unset, bool] = UNSET
    allow_quoted_nulls: Union[Unset, bool] = UNSET
    auto_detect: Union[Unset, bool] = UNSET
    columns: Union[Unset, "CsvFileFormatSpecColumns"] = UNSET
    compression: Union[Unset, str] = UNSET
    dateformat: Union[Unset, str] = UNSET
    decimal_separator: Union[Unset, str] = UNSET
    delim: Union[Unset, str] = UNSET
    escape: Union[Unset, str] = UNSET
    filename: Union[Unset, bool] = UNSET
    header: Union[Unset, bool] = UNSET
    hive_partitioning: Union[Unset, bool] = UNSET
    ignore_errors: Union[Unset, bool] = UNSET
    new_line: Union[Unset, str] = UNSET
    quote: Union[Unset, str] = UNSET
    skip: Union[Unset, int] = UNSET
    timestampformat: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        all_varchar = self.all_varchar
        allow_quoted_nulls = self.allow_quoted_nulls
        auto_detect = self.auto_detect
        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        compression = self.compression
        dateformat = self.dateformat
        decimal_separator = self.decimal_separator
        delim = self.delim
        escape = self.escape
        filename = self.filename
        header = self.header
        hive_partitioning = self.hive_partitioning
        ignore_errors = self.ignore_errors
        new_line = self.new_line
        quote = self.quote
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
        if columns is not UNSET:
            field_dict["columns"] = columns
        if compression is not UNSET:
            field_dict["compression"] = compression
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
        if skip is not UNSET:
            field_dict["skip"] = skip
        if timestampformat is not UNSET:
            field_dict["timestampformat"] = timestampformat

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.csv_file_format_spec_columns import CsvFileFormatSpecColumns

        d = src_dict.copy()
        all_varchar = d.pop("all_varchar", UNSET)

        allow_quoted_nulls = d.pop("allow_quoted_nulls", UNSET)

        auto_detect = d.pop("auto_detect", UNSET)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, CsvFileFormatSpecColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = CsvFileFormatSpecColumns.from_dict(_columns)

        compression = d.pop("compression", UNSET)

        dateformat = d.pop("dateformat", UNSET)

        decimal_separator = d.pop("decimal_separator", UNSET)

        delim = d.pop("delim", UNSET)

        escape = d.pop("escape", UNSET)

        filename = d.pop("filename", UNSET)

        header = d.pop("header", UNSET)

        hive_partitioning = d.pop("hive_partitioning", UNSET)

        ignore_errors = d.pop("ignore_errors", UNSET)

        new_line = d.pop("new_line", UNSET)

        quote = d.pop("quote", UNSET)

        skip = d.pop("skip", UNSET)

        timestampformat = d.pop("timestampformat", UNSET)

        csv_file_format_spec = cls(
            all_varchar=all_varchar,
            allow_quoted_nulls=allow_quoted_nulls,
            auto_detect=auto_detect,
            columns=columns,
            compression=compression,
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
