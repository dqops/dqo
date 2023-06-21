from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_profiling_checks_spec import (
        TableAccuracyProfilingChecksSpec,
    )
    from ..models.table_availability_profiling_checks_spec import (
        TableAvailabilityProfilingChecksSpec,
    )
    from ..models.table_profiling_check_categories_spec_custom import (
        TableProfilingCheckCategoriesSpecCustom,
    )
    from ..models.table_schema_profiling_checks_spec import (
        TableSchemaProfilingChecksSpec,
    )
    from ..models.table_sql_profiling_checks_spec import TableSqlProfilingChecksSpec
    from ..models.table_timeliness_profiling_checks_spec import (
        TableTimelinessProfilingChecksSpec,
    )
    from ..models.table_volume_profiling_checks_spec import (
        TableVolumeProfilingChecksSpec,
    )


T = TypeVar("T", bound="TableProfilingCheckCategoriesSpec")


@attr.s(auto_attribs=True)
class TableProfilingCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, TableProfilingCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are check
            names.
        volume (Union[Unset, TableVolumeProfilingChecksSpec]):
        timeliness (Union[Unset, TableTimelinessProfilingChecksSpec]):
        accuracy (Union[Unset, TableAccuracyProfilingChecksSpec]):
        sql (Union[Unset, TableSqlProfilingChecksSpec]):
        availability (Union[Unset, TableAvailabilityProfilingChecksSpec]):
        schema (Union[Unset, TableSchemaProfilingChecksSpec]):
    """

    custom: Union[Unset, "TableProfilingCheckCategoriesSpecCustom"] = UNSET
    volume: Union[Unset, "TableVolumeProfilingChecksSpec"] = UNSET
    timeliness: Union[Unset, "TableTimelinessProfilingChecksSpec"] = UNSET
    accuracy: Union[Unset, "TableAccuracyProfilingChecksSpec"] = UNSET
    sql: Union[Unset, "TableSqlProfilingChecksSpec"] = UNSET
    availability: Union[Unset, "TableAvailabilityProfilingChecksSpec"] = UNSET
    schema: Union[Unset, "TableSchemaProfilingChecksSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom, Unset):
            custom = self.custom.to_dict()

        volume: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.volume, Unset):
            volume = self.volume.to_dict()

        timeliness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.timeliness, Unset):
            timeliness = self.timeliness.to_dict()

        accuracy: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.accuracy, Unset):
            accuracy = self.accuracy.to_dict()

        sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql, Unset):
            sql = self.sql.to_dict()

        availability: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.availability, Unset):
            availability = self.availability.to_dict()

        schema: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schema, Unset):
            schema = self.schema.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom is not UNSET:
            field_dict["custom"] = custom
        if volume is not UNSET:
            field_dict["volume"] = volume
        if timeliness is not UNSET:
            field_dict["timeliness"] = timeliness
        if accuracy is not UNSET:
            field_dict["accuracy"] = accuracy
        if sql is not UNSET:
            field_dict["sql"] = sql
        if availability is not UNSET:
            field_dict["availability"] = availability
        if schema is not UNSET:
            field_dict["schema"] = schema

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_accuracy_profiling_checks_spec import (
            TableAccuracyProfilingChecksSpec,
        )
        from ..models.table_availability_profiling_checks_spec import (
            TableAvailabilityProfilingChecksSpec,
        )
        from ..models.table_profiling_check_categories_spec_custom import (
            TableProfilingCheckCategoriesSpecCustom,
        )
        from ..models.table_schema_profiling_checks_spec import (
            TableSchemaProfilingChecksSpec,
        )
        from ..models.table_sql_profiling_checks_spec import TableSqlProfilingChecksSpec
        from ..models.table_timeliness_profiling_checks_spec import (
            TableTimelinessProfilingChecksSpec,
        )
        from ..models.table_volume_profiling_checks_spec import (
            TableVolumeProfilingChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, TableProfilingCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = TableProfilingCheckCategoriesSpecCustom.from_dict(_custom)

        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeProfilingChecksSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeProfilingChecksSpec.from_dict(_volume)

        _timeliness = d.pop("timeliness", UNSET)
        timeliness: Union[Unset, TableTimelinessProfilingChecksSpec]
        if isinstance(_timeliness, Unset):
            timeliness = UNSET
        else:
            timeliness = TableTimelinessProfilingChecksSpec.from_dict(_timeliness)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, TableAccuracyProfilingChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = TableAccuracyProfilingChecksSpec.from_dict(_accuracy)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, TableSqlProfilingChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = TableSqlProfilingChecksSpec.from_dict(_sql)

        _availability = d.pop("availability", UNSET)
        availability: Union[Unset, TableAvailabilityProfilingChecksSpec]
        if isinstance(_availability, Unset):
            availability = UNSET
        else:
            availability = TableAvailabilityProfilingChecksSpec.from_dict(_availability)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, TableSchemaProfilingChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = TableSchemaProfilingChecksSpec.from_dict(_schema)

        table_profiling_check_categories_spec = cls(
            custom=custom,
            volume=volume,
            timeliness=timeliness,
            accuracy=accuracy,
            sql=sql,
            availability=availability,
            schema=schema,
        )

        table_profiling_check_categories_spec.additional_properties = d
        return table_profiling_check_categories_spec

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
