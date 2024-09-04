from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_monthly_monitoring_checks_spec import (
        TableAccuracyMonthlyMonitoringChecksSpec,
    )
    from ..models.table_availability_monthly_monitoring_checks_spec import (
        TableAvailabilityMonthlyMonitoringChecksSpec,
    )
    from ..models.table_custom_sql_monthly_monitoring_checks_spec import (
        TableCustomSqlMonthlyMonitoringChecksSpec,
    )
    from ..models.table_monthly_monitoring_check_categories_spec_comparisons import (
        TableMonthlyMonitoringCheckCategoriesSpecComparisons,
    )
    from ..models.table_monthly_monitoring_check_categories_spec_custom import (
        TableMonthlyMonitoringCheckCategoriesSpecCustom,
    )
    from ..models.table_schema_monthly_monitoring_checks_spec import (
        TableSchemaMonthlyMonitoringChecksSpec,
    )
    from ..models.table_timeliness_monthly_monitoring_checks_spec import (
        TableTimelinessMonthlyMonitoringChecksSpec,
    )
    from ..models.table_uniqueness_monthly_monitoring_checks_spec import (
        TableUniquenessMonthlyMonitoringChecksSpec,
    )
    from ..models.table_volume_monthly_monitoring_checks_spec import (
        TableVolumeMonthlyMonitoringChecksSpec,
    )


T = TypeVar("T", bound="TableMonthlyMonitoringCheckCategoriesSpec")


@_attrs_define
class TableMonthlyMonitoringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, TableMonthlyMonitoringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        volume (Union[Unset, TableVolumeMonthlyMonitoringChecksSpec]):
        timeliness (Union[Unset, TableTimelinessMonthlyMonitoringChecksSpec]):
        accuracy (Union[Unset, TableAccuracyMonthlyMonitoringChecksSpec]):
        custom_sql (Union[Unset, TableCustomSqlMonthlyMonitoringChecksSpec]):
        availability (Union[Unset, TableAvailabilityMonthlyMonitoringChecksSpec]):
        schema (Union[Unset, TableSchemaMonthlyMonitoringChecksSpec]):
        uniqueness (Union[Unset, TableUniquenessMonthlyMonitoringChecksSpec]):
        comparisons (Union[Unset, TableMonthlyMonitoringCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons. The key that identifies each comparison must match the name of a data comparison
            that is configured on the parent table.
    """

    custom: Union[Unset, "TableMonthlyMonitoringCheckCategoriesSpecCustom"] = UNSET
    volume: Union[Unset, "TableVolumeMonthlyMonitoringChecksSpec"] = UNSET
    timeliness: Union[Unset, "TableTimelinessMonthlyMonitoringChecksSpec"] = UNSET
    accuracy: Union[Unset, "TableAccuracyMonthlyMonitoringChecksSpec"] = UNSET
    custom_sql: Union[Unset, "TableCustomSqlMonthlyMonitoringChecksSpec"] = UNSET
    availability: Union[Unset, "TableAvailabilityMonthlyMonitoringChecksSpec"] = UNSET
    schema: Union[Unset, "TableSchemaMonthlyMonitoringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "TableUniquenessMonthlyMonitoringChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "TableMonthlyMonitoringCheckCategoriesSpecComparisons"
    ] = UNSET
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
        from ..models.table_accuracy_monthly_monitoring_checks_spec import (
            TableAccuracyMonthlyMonitoringChecksSpec,
        )
        from ..models.table_availability_monthly_monitoring_checks_spec import (
            TableAvailabilityMonthlyMonitoringChecksSpec,
        )
        from ..models.table_custom_sql_monthly_monitoring_checks_spec import (
            TableCustomSqlMonthlyMonitoringChecksSpec,
        )
        from ..models.table_monthly_monitoring_check_categories_spec_comparisons import (
            TableMonthlyMonitoringCheckCategoriesSpecComparisons,
        )
        from ..models.table_monthly_monitoring_check_categories_spec_custom import (
            TableMonthlyMonitoringCheckCategoriesSpecCustom,
        )
        from ..models.table_schema_monthly_monitoring_checks_spec import (
            TableSchemaMonthlyMonitoringChecksSpec,
        )
        from ..models.table_timeliness_monthly_monitoring_checks_spec import (
            TableTimelinessMonthlyMonitoringChecksSpec,
        )
        from ..models.table_uniqueness_monthly_monitoring_checks_spec import (
            TableUniquenessMonthlyMonitoringChecksSpec,
        )
        from ..models.table_volume_monthly_monitoring_checks_spec import (
            TableVolumeMonthlyMonitoringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, TableMonthlyMonitoringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = TableMonthlyMonitoringCheckCategoriesSpecCustom.from_dict(_custom)

        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeMonthlyMonitoringChecksSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeMonthlyMonitoringChecksSpec.from_dict(_volume)

        _timeliness = d.pop("timeliness", UNSET)
        timeliness: Union[Unset, TableTimelinessMonthlyMonitoringChecksSpec]
        if isinstance(_timeliness, Unset):
            timeliness = UNSET
        else:
            timeliness = TableTimelinessMonthlyMonitoringChecksSpec.from_dict(
                _timeliness
            )

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, TableAccuracyMonthlyMonitoringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = TableAccuracyMonthlyMonitoringChecksSpec.from_dict(_accuracy)

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, TableCustomSqlMonthlyMonitoringChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = TableCustomSqlMonthlyMonitoringChecksSpec.from_dict(
                _custom_sql
            )

        _availability = d.pop("availability", UNSET)
        availability: Union[Unset, TableAvailabilityMonthlyMonitoringChecksSpec]
        if isinstance(_availability, Unset):
            availability = UNSET
        else:
            availability = TableAvailabilityMonthlyMonitoringChecksSpec.from_dict(
                _availability
            )

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, TableSchemaMonthlyMonitoringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = TableSchemaMonthlyMonitoringChecksSpec.from_dict(_schema)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, TableUniquenessMonthlyMonitoringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = TableUniquenessMonthlyMonitoringChecksSpec.from_dict(
                _uniqueness
            )

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, TableMonthlyMonitoringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = (
                TableMonthlyMonitoringCheckCategoriesSpecComparisons.from_dict(
                    _comparisons
                )
            )

        table_monthly_monitoring_check_categories_spec = cls(
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

        table_monthly_monitoring_check_categories_spec.additional_properties = d
        return table_monthly_monitoring_check_categories_spec

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
