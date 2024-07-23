from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.csv_file_format_spec import CsvFileFormatSpec
    from ..models.delta_lake_file_format_spec import DeltaLakeFileFormatSpec
    from ..models.iceberg_file_format_spec import IcebergFileFormatSpec
    from ..models.json_file_format_spec import JsonFileFormatSpec
    from ..models.parquet_file_format_spec import ParquetFileFormatSpec


T = TypeVar("T", bound="FileFormatSpec")


@_attrs_define
class FileFormatSpec:
    """
    Attributes:
        csv (Union[Unset, CsvFileFormatSpec]):
        json (Union[Unset, JsonFileFormatSpec]):
        parquet (Union[Unset, ParquetFileFormatSpec]):
        iceberg (Union[Unset, IcebergFileFormatSpec]):
        delta_lake (Union[Unset, DeltaLakeFileFormatSpec]):
        file_paths (Union[Unset, List[str]]): The list of paths to files with data that are used as a source.
    """

    csv: Union[Unset, "CsvFileFormatSpec"] = UNSET
    json: Union[Unset, "JsonFileFormatSpec"] = UNSET
    parquet: Union[Unset, "ParquetFileFormatSpec"] = UNSET
    iceberg: Union[Unset, "IcebergFileFormatSpec"] = UNSET
    delta_lake: Union[Unset, "DeltaLakeFileFormatSpec"] = UNSET
    file_paths: Union[Unset, List[str]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        csv: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.csv, Unset):
            csv = self.csv.to_dict()

        json: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.json, Unset):
            json = self.json.to_dict()

        parquet: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parquet, Unset):
            parquet = self.parquet.to_dict()

        iceberg: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.iceberg, Unset):
            iceberg = self.iceberg.to_dict()

        delta_lake: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.delta_lake, Unset):
            delta_lake = self.delta_lake.to_dict()

        file_paths: Union[Unset, List[str]] = UNSET
        if not isinstance(self.file_paths, Unset):
            file_paths = self.file_paths

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if csv is not UNSET:
            field_dict["csv"] = csv
        if json is not UNSET:
            field_dict["json"] = json
        if parquet is not UNSET:
            field_dict["parquet"] = parquet
        if iceberg is not UNSET:
            field_dict["iceberg"] = iceberg
        if delta_lake is not UNSET:
            field_dict["delta_lake"] = delta_lake
        if file_paths is not UNSET:
            field_dict["file_paths"] = file_paths

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.csv_file_format_spec import CsvFileFormatSpec
        from ..models.delta_lake_file_format_spec import DeltaLakeFileFormatSpec
        from ..models.iceberg_file_format_spec import IcebergFileFormatSpec
        from ..models.json_file_format_spec import JsonFileFormatSpec
        from ..models.parquet_file_format_spec import ParquetFileFormatSpec

        d = src_dict.copy()
        _csv = d.pop("csv", UNSET)
        csv: Union[Unset, CsvFileFormatSpec]
        if isinstance(_csv, Unset):
            csv = UNSET
        else:
            csv = CsvFileFormatSpec.from_dict(_csv)

        _json = d.pop("json", UNSET)
        json: Union[Unset, JsonFileFormatSpec]
        if isinstance(_json, Unset):
            json = UNSET
        else:
            json = JsonFileFormatSpec.from_dict(_json)

        _parquet = d.pop("parquet", UNSET)
        parquet: Union[Unset, ParquetFileFormatSpec]
        if isinstance(_parquet, Unset):
            parquet = UNSET
        else:
            parquet = ParquetFileFormatSpec.from_dict(_parquet)

        _iceberg = d.pop("iceberg", UNSET)
        iceberg: Union[Unset, IcebergFileFormatSpec]
        if isinstance(_iceberg, Unset):
            iceberg = UNSET
        else:
            iceberg = IcebergFileFormatSpec.from_dict(_iceberg)

        _delta_lake = d.pop("delta_lake", UNSET)
        delta_lake: Union[Unset, DeltaLakeFileFormatSpec]
        if isinstance(_delta_lake, Unset):
            delta_lake = UNSET
        else:
            delta_lake = DeltaLakeFileFormatSpec.from_dict(_delta_lake)

        file_paths = cast(List[str], d.pop("file_paths", UNSET))

        file_format_spec = cls(
            csv=csv,
            json=json,
            parquet=parquet,
            iceberg=iceberg,
            delta_lake=delta_lake,
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
