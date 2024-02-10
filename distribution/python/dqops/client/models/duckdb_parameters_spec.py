from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.duckdb_parameters_spec_properties import (
        DuckdbParametersSpecProperties,
    )


T = TypeVar("T", bound="DuckdbParametersSpec")


@_attrs_define
class DuckdbParametersSpec:
    """
    Attributes:
        in_memory (Union[Unset, bool]): To use the special value :memory: to create an in-memory database where no data
            is persisted to disk (i.e., all data is lost when you exit the process). The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        database (Union[Unset, str]): DuckDB database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format
            to use dynamic substitution.
        options (Union[Unset, str]): DuckDB connection 'options' initialization parameter. For example setting this to
            -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also
            a ${DUCKDB_OPTIONS} configuration with a custom environment variable.
        properties (Union[Unset, DuckdbParametersSpecProperties]): A dictionary of custom JDBC parameters that are added
            to the JDBC connection string, a key/value dictionary.
    """

    in_memory: Union[Unset, bool] = UNSET
    database: Union[Unset, str] = UNSET
    options: Union[Unset, str] = UNSET
    properties: Union[Unset, "DuckdbParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        in_memory = self.in_memory
        database = self.database
        options = self.options
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if in_memory is not UNSET:
            field_dict["in_memory"] = in_memory
        if database is not UNSET:
            field_dict["database"] = database
        if options is not UNSET:
            field_dict["options"] = options
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.duckdb_parameters_spec_properties import (
            DuckdbParametersSpecProperties,
        )

        d = src_dict.copy()
        in_memory = d.pop("in_memory", UNSET)

        database = d.pop("database", UNSET)

        options = d.pop("options", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, DuckdbParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = DuckdbParametersSpecProperties.from_dict(_properties)

        duckdb_parameters_spec = cls(
            in_memory=in_memory,
            database=database,
            options=options,
            properties=properties,
        )

        duckdb_parameters_spec.additional_properties = d
        return duckdb_parameters_spec

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
