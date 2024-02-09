from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.csv_file_format_spec import CsvFileFormatSpec
    from ..models.json_file_format_spec import JsonFileFormatSpec
    from ..models.parquet_file_format_spec import ParquetFileFormatSpec


T = TypeVar("T", bound="FileFormatSpec")


@_attrs_define
class FileFormatSpec:
    """
    Attributes:
        csv_file_format (Union[Unset, CsvFileFormatSpec]):
        json_file_format (Union[Unset, JsonFileFormatSpec]):
        parquet_file_format (Union[Unset, ParquetFileFormatSpec]):
        file_path_list (Union[Unset, List[str]]): The list of paths to files with data that are used as a source.
        file_paths (Union[Unset, List[str]]):
    """

    csv_file_format: Union[Unset, "CsvFileFormatSpec"] = UNSET
    json_file_format: Union[Unset, "JsonFileFormatSpec"] = UNSET
    parquet_file_format: Union[Unset, "ParquetFileFormatSpec"] = UNSET
    file_path_list: Union[Unset, List[str]] = UNSET
    file_paths: Union[Unset, List[str]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        csv_file_format: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.csv_file_format, Unset):
            csv_file_format = self.csv_file_format.to_dict()

        json_file_format: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.json_file_format, Unset):
            json_file_format = self.json_file_format.to_dict()

        parquet_file_format: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parquet_file_format, Unset):
            parquet_file_format = self.parquet_file_format.to_dict()

        file_path_list: Union[Unset, List[str]] = UNSET
        if not isinstance(self.file_path_list, Unset):
            file_path_list = self.file_path_list

        file_paths: Union[Unset, List[str]] = UNSET
        if not isinstance(self.file_paths, Unset):
            file_paths = self.file_paths

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if csv_file_format is not UNSET:
            field_dict["csv_file_format"] = csv_file_format
        if json_file_format is not UNSET:
            field_dict["json_file_format"] = json_file_format
        if parquet_file_format is not UNSET:
            field_dict["parquet_file_format"] = parquet_file_format
        if file_path_list is not UNSET:
            field_dict["file_path_list"] = file_path_list
        if file_paths is not UNSET:
            field_dict["file_paths"] = file_paths

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.csv_file_format_spec import CsvFileFormatSpec
        from ..models.json_file_format_spec import JsonFileFormatSpec
        from ..models.parquet_file_format_spec import ParquetFileFormatSpec

        d = src_dict.copy()
        _csv_file_format = d.pop("csv_file_format", UNSET)
        csv_file_format: Union[Unset, CsvFileFormatSpec]
        if isinstance(_csv_file_format, Unset):
            csv_file_format = UNSET
        else:
            csv_file_format = CsvFileFormatSpec.from_dict(_csv_file_format)

        _json_file_format = d.pop("json_file_format", UNSET)
        json_file_format: Union[Unset, JsonFileFormatSpec]
        if isinstance(_json_file_format, Unset):
            json_file_format = UNSET
        else:
            json_file_format = JsonFileFormatSpec.from_dict(_json_file_format)

        _parquet_file_format = d.pop("parquet_file_format", UNSET)
        parquet_file_format: Union[Unset, ParquetFileFormatSpec]
        if isinstance(_parquet_file_format, Unset):
            parquet_file_format = UNSET
        else:
            parquet_file_format = ParquetFileFormatSpec.from_dict(_parquet_file_format)

        file_path_list = cast(List[str], d.pop("file_path_list", UNSET))

        file_paths = cast(List[str], d.pop("file_paths", UNSET))

        file_format_spec = cls(
            csv_file_format=csv_file_format,
            json_file_format=json_file_format,
            parquet_file_format=parquet_file_format,
            file_path_list=file_path_list,
            file_paths=file_paths,
        )

        file_format_spec.additional_properties = d
        return file_format_spec

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
