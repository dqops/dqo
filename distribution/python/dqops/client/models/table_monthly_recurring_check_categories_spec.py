from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_monthly_recurring_checks_spec import (
        TableAccuracyMonthlyRecurringChecksSpec,
    )
    from ..models.table_availability_monthly_recurring_checks_spec import (
        TableAvailabilityMonthlyRecurringChecksSpec,
    )
    from ..models.table_monthly_recurring_check_categories_spec_comparisons import (
        TableMonthlyRecurringCheckCategoriesSpecComparisons,
    )
    from ..models.table_monthly_recurring_check_categories_spec_custom import (
        TableMonthlyRecurringCheckCategoriesSpecCustom,
    )
    from ..models.table_schema_monthly_recurring_checks_spec import (
        TableSchemaMonthlyRecurringChecksSpec,
    )
    from ..models.table_sql_monthly_recurring_checks_spec import (
        TableSqlMonthlyRecurringChecksSpec,
    )
    from ..models.table_timeliness_monthly_recurring_checks_spec import (
        TableTimelinessMonthlyRecurringChecksSpec,
    )
    from ..models.table_volume_monthly_recurring_checks_spec import (
        TableVolumeMonthlyRecurringChecksSpec,
    )


T = TypeVar("T", bound="TableMonthlyRecurringCheckCategoriesSpec")


@attr.s(auto_attribs=True)
class TableMonthlyRecurringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, TableMonthlyRecurringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are
            check names.
        volume (Union[Unset, TableVolumeMonthlyRecurringChecksSpec]):
        timeliness (Union[Unset, TableTimelinessMonthlyRecurringChecksSpec]):
        accuracy (Union[Unset, TableAccuracyMonthlyRecurringChecksSpec]):
        sql (Union[Unset, TableSqlMonthlyRecurringChecksSpec]):
        availability (Union[Unset, TableAvailabilityMonthlyRecurringChecksSpec]):
        schema (Union[Unset, TableSchemaMonthlyRecurringChecksSpec]):
        comparisons (Union[Unset, TableMonthlyRecurringCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons. The key that identifies each comparison must match the name of a data comparison
            that is configured on the parent table.
    """

    custom: Union[Unset, "TableMonthlyRecurringCheckCategoriesSpecCustom"] = UNSET
    volume: Union[Unset, "TableVolumeMonthlyRecurringChecksSpec"] = UNSET
    timeliness: Union[Unset, "TableTimelinessMonthlyRecurringChecksSpec"] = UNSET
    accuracy: Union[Unset, "TableAccuracyMonthlyRecurringChecksSpec"] = UNSET
    sql: Union[Unset, "TableSqlMonthlyRecurringChecksSpec"] = UNSET
    availability: Union[Unset, "TableAvailabilityMonthlyRecurringChecksSpec"] = UNSET
    schema: Union[Unset, "TableSchemaMonthlyRecurringChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "TableMonthlyRecurringCheckCategoriesSpecComparisons"
    ] = UNSET
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
        if sql is not UNSET:
            field_dict["sql"] = sql
        if availability is not UNSET:
            field_dict["availability"] = availability
        if schema is not UNSET:
            field_dict["schema"] = schema
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_accuracy_monthly_recurring_checks_spec import (
            TableAccuracyMonthlyRecurringChecksSpec,
        )
        from ..models.table_availability_monthly_recurring_checks_spec import (
            TableAvailabilityMonthlyRecurringChecksSpec,
        )
        from ..models.table_monthly_recurring_check_categories_spec_comparisons import (
            TableMonthlyRecurringCheckCategoriesSpecComparisons,
        )
        from ..models.table_monthly_recurring_check_categories_spec_custom import (
            TableMonthlyRecurringCheckCategoriesSpecCustom,
        )
        from ..models.table_schema_monthly_recurring_checks_spec import (
            TableSchemaMonthlyRecurringChecksSpec,
        )
        from ..models.table_sql_monthly_recurring_checks_spec import (
            TableSqlMonthlyRecurringChecksSpec,
        )
        from ..models.table_timeliness_monthly_recurring_checks_spec import (
            TableTimelinessMonthlyRecurringChecksSpec,
        )
        from ..models.table_volume_monthly_recurring_checks_spec import (
            TableVolumeMonthlyRecurringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, TableMonthlyRecurringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = TableMonthlyRecurringCheckCategoriesSpecCustom.from_dict(_custom)

        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeMonthlyRecurringChecksSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeMonthlyRecurringChecksSpec.from_dict(_volume)

        _timeliness = d.pop("timeliness", UNSET)
        timeliness: Union[Unset, TableTimelinessMonthlyRecurringChecksSpec]
        if isinstance(_timeliness, Unset):
            timeliness = UNSET
        else:
            timeliness = TableTimelinessMonthlyRecurringChecksSpec.from_dict(
                _timeliness
            )

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, TableAccuracyMonthlyRecurringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = TableAccuracyMonthlyRecurringChecksSpec.from_dict(_accuracy)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, TableSqlMonthlyRecurringChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = TableSqlMonthlyRecurringChecksSpec.from_dict(_sql)

        _availability = d.pop("availability", UNSET)
        availability: Union[Unset, TableAvailabilityMonthlyRecurringChecksSpec]
        if isinstance(_availability, Unset):
            availability = UNSET
        else:
            availability = TableAvailabilityMonthlyRecurringChecksSpec.from_dict(
                _availability
            )

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, TableSchemaMonthlyRecurringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = TableSchemaMonthlyRecurringChecksSpec.from_dict(_schema)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, TableMonthlyRecurringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = TableMonthlyRecurringCheckCategoriesSpecComparisons.from_dict(
                _comparisons
            )

        table_monthly_recurring_check_categories_spec = cls(
            custom=custom,
            volume=volume,
            timeliness=timeliness,
            accuracy=accuracy,
            sql=sql,
            availability=availability,
            schema=schema,
            comparisons=comparisons,
        )

        table_monthly_recurring_check_categories_spec.additional_properties = d
        return table_monthly_recurring_check_categories_spec

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
