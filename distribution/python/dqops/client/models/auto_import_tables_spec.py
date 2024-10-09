from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.cron_schedule_spec import CronScheduleSpec


T = TypeVar("T", bound="AutoImportTablesSpec")


@_attrs_define
class AutoImportTablesSpec:
    """
    Attributes:
        schema_filter (Union[Unset, str]): Source schema name filter. Accepts filters in the form of *s, s* and *s* to
            restrict import to selected schemas.
        table_name_contains (Union[Unset, str]): Source table name filter. It is a table name or a text that must be
            present inside the table name.
        schedule (Union[Unset, CronScheduleSpec]):
    """

    schema_filter: Union[Unset, str] = UNSET
    table_name_contains: Union[Unset, str] = UNSET
    schedule: Union[Unset, "CronScheduleSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        schema_filter = self.schema_filter
        table_name_contains = self.table_name_contains
        schedule: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schedule, Unset):
            schedule = self.schedule.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if schema_filter is not UNSET:
            field_dict["schema_filter"] = schema_filter
        if table_name_contains is not UNSET:
            field_dict["table_name_contains"] = table_name_contains
        if schedule is not UNSET:
            field_dict["schedule"] = schedule

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.cron_schedule_spec import CronScheduleSpec

        d = src_dict.copy()
        schema_filter = d.pop("schema_filter", UNSET)

        table_name_contains = d.pop("table_name_contains", UNSET)

        _schedule = d.pop("schedule", UNSET)
        schedule: Union[Unset, CronScheduleSpec]
        if isinstance(_schedule, Unset):
            schedule = UNSET
        else:
            schedule = CronScheduleSpec.from_dict(_schedule)

        auto_import_tables_spec = cls(
            schema_filter=schema_filter,
            table_name_contains=table_name_contains,
            schedule=schedule,
        )

        auto_import_tables_spec.additional_properties = d
        return auto_import_tables_spec

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
