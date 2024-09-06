from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_daily_monitoring_checks_spec import (
        TableAccuracyDailyMonitoringChecksSpec,
    )
    from ..models.table_availability_daily_monitoring_checks_spec import (
        TableAvailabilityDailyMonitoringChecksSpec,
    )
    from ..models.table_custom_sql_daily_monitoring_checks_spec import (
        TableCustomSqlDailyMonitoringChecksSpec,
    )
    from ..models.table_daily_monitoring_check_categories_spec_comparisons import (
        TableDailyMonitoringCheckCategoriesSpecComparisons,
    )
    from ..models.table_daily_monitoring_check_categories_spec_custom import (
        TableDailyMonitoringCheckCategoriesSpecCustom,
    )
    from ..models.table_schema_daily_monitoring_checks_spec import (
        TableSchemaDailyMonitoringChecksSpec,
    )
    from ..models.table_timeliness_daily_monitoring_checks_spec import (
        TableTimelinessDailyMonitoringChecksSpec,
    )
    from ..models.table_uniqueness_daily_monitoring_checks_spec import (
        TableUniquenessDailyMonitoringChecksSpec,
    )
    from ..models.table_volume_daily_monitoring_checks_spec import (
        TableVolumeDailyMonitoringChecksSpec,
    )


T = TypeVar("T", bound="TableDailyMonitoringCheckCategoriesSpec")


@_attrs_define
class TableDailyMonitoringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, TableDailyMonitoringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are
            check names within this category.
        volume (Union[Unset, TableVolumeDailyMonitoringChecksSpec]):
        timeliness (Union[Unset, TableTimelinessDailyMonitoringChecksSpec]):
        accuracy (Union[Unset, TableAccuracyDailyMonitoringChecksSpec]):
        custom_sql (Union[Unset, TableCustomSqlDailyMonitoringChecksSpec]):
        availability (Union[Unset, TableAvailabilityDailyMonitoringChecksSpec]):
        schema (Union[Unset, TableSchemaDailyMonitoringChecksSpec]):
        uniqueness (Union[Unset, TableUniquenessDailyMonitoringChecksSpec]):
        comparisons (Union[Unset, TableDailyMonitoringCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons. The key that identifies each comparison must match the name of a data comparison
            that is configured on the parent table.
    """

    custom: Union[Unset, "TableDailyMonitoringCheckCategoriesSpecCustom"] = UNSET
    volume: Union[Unset, "TableVolumeDailyMonitoringChecksSpec"] = UNSET
    timeliness: Union[Unset, "TableTimelinessDailyMonitoringChecksSpec"] = UNSET
    accuracy: Union[Unset, "TableAccuracyDailyMonitoringChecksSpec"] = UNSET
    custom_sql: Union[Unset, "TableCustomSqlDailyMonitoringChecksSpec"] = UNSET
    availability: Union[Unset, "TableAvailabilityDailyMonitoringChecksSpec"] = UNSET
    schema: Union[Unset, "TableSchemaDailyMonitoringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "TableUniquenessDailyMonitoringChecksSpec"] = UNSET
    comparisons: Union[Unset, "TableDailyMonitoringCheckCategoriesSpecComparisons"] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

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

        custom_sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_sql, Unset):
            custom_sql = self.custom_sql.to_dict()

        availability: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.availability, Unset):
            availability = self.availability.to_dict()

        schema: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schema, Unset):
            schema = self.schema.to_dict()

        uniqueness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.uniqueness, Unset):
            uniqueness = self.uniqueness.to_dict()

        comparisons: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.comparisons, Unset):
            comparisons = self.comparisons.to_dict()

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
        if custom_sql is not UNSET:
            field_dict["custom_sql"] = custom_sql
        if availability is not UNSET:
            field_dict["availability"] = availability
        if schema is not UNSET:
            field_dict["schema"] = schema
        if uniqueness is not UNSET:
            field_dict["uniqueness"] = uniqueness
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_accuracy_daily_monitoring_checks_spec import (
            TableAccuracyDailyMonitoringChecksSpec,
        )
        from ..models.table_availability_daily_monitoring_checks_spec import (
            TableAvailabilityDailyMonitoringChecksSpec,
        )
        from ..models.table_custom_sql_daily_monitoring_checks_spec import (
            TableCustomSqlDailyMonitoringChecksSpec,
        )
        from ..models.table_daily_monitoring_check_categories_spec_comparisons import (
            TableDailyMonitoringCheckCategoriesSpecComparisons,
        )
        from ..models.table_daily_monitoring_check_categories_spec_custom import (
            TableDailyMonitoringCheckCategoriesSpecCustom,
        )
        from ..models.table_schema_daily_monitoring_checks_spec import (
            TableSchemaDailyMonitoringChecksSpec,
        )
        from ..models.table_timeliness_daily_monitoring_checks_spec import (
            TableTimelinessDailyMonitoringChecksSpec,
        )
        from ..models.table_uniqueness_daily_monitoring_checks_spec import (
            TableUniquenessDailyMonitoringChecksSpec,
        )
        from ..models.table_volume_daily_monitoring_checks_spec import (
            TableVolumeDailyMonitoringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, TableDailyMonitoringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = TableDailyMonitoringCheckCategoriesSpecCustom.from_dict(_custom)

        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeDailyMonitoringChecksSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeDailyMonitoringChecksSpec.from_dict(_volume)

        _timeliness = d.pop("timeliness", UNSET)
        timeliness: Union[Unset, TableTimelinessDailyMonitoringChecksSpec]
        if isinstance(_timeliness, Unset):
            timeliness = UNSET
        else:
            timeliness = TableTimelinessDailyMonitoringChecksSpec.from_dict(_timeliness)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, TableAccuracyDailyMonitoringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = TableAccuracyDailyMonitoringChecksSpec.from_dict(_accuracy)

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, TableCustomSqlDailyMonitoringChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = TableCustomSqlDailyMonitoringChecksSpec.from_dict(_custom_sql)

        _availability = d.pop("availability", UNSET)
        availability: Union[Unset, TableAvailabilityDailyMonitoringChecksSpec]
        if isinstance(_availability, Unset):
            availability = UNSET
        else:
            availability = TableAvailabilityDailyMonitoringChecksSpec.from_dict(
                _availability
            )

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, TableSchemaDailyMonitoringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = TableSchemaDailyMonitoringChecksSpec.from_dict(_schema)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, TableUniquenessDailyMonitoringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = TableUniquenessDailyMonitoringChecksSpec.from_dict(_uniqueness)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, TableDailyMonitoringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = TableDailyMonitoringCheckCategoriesSpecComparisons.from_dict(
                _comparisons
            )

        table_daily_monitoring_check_categories_spec = cls(
            custom=custom,
            volume=volume,
            timeliness=timeliness,
            accuracy=accuracy,
            custom_sql=custom_sql,
            availability=availability,
            schema=schema,
            uniqueness=uniqueness,
            comparisons=comparisons,
        )

        table_daily_monitoring_check_categories_spec.additional_properties = d
        return table_daily_monitoring_check_categories_spec

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
