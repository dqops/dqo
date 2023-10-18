from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_daily_partitioned_check_categories_spec_comparisons import (
        TableDailyPartitionedCheckCategoriesSpecComparisons,
    )
    from ..models.table_daily_partitioned_check_categories_spec_custom import (
        TableDailyPartitionedCheckCategoriesSpecCustom,
    )
    from ..models.table_sql_daily_partitioned_checks_spec import (
        TableSqlDailyPartitionedChecksSpec,
    )
    from ..models.table_timeliness_daily_partitioned_checks_spec import (
        TableTimelinessDailyPartitionedChecksSpec,
    )
    from ..models.table_volume_daily_partitioned_checks_spec import (
        TableVolumeDailyPartitionedChecksSpec,
    )


T = TypeVar("T", bound="TableDailyPartitionedCheckCategoriesSpec")


@_attrs_define
class TableDailyPartitionedCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, TableDailyPartitionedCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are
            check names within this category.
        volume (Union[Unset, TableVolumeDailyPartitionedChecksSpec]):
        timeliness (Union[Unset, TableTimelinessDailyPartitionedChecksSpec]):
        sql (Union[Unset, TableSqlDailyPartitionedChecksSpec]):
        comparisons (Union[Unset, TableDailyPartitionedCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons. The key that identifies each comparison must match the name of a data comparison
            that is configured on the parent table.
    """

    custom: Union[Unset, "TableDailyPartitionedCheckCategoriesSpecCustom"] = UNSET
    volume: Union[Unset, "TableVolumeDailyPartitionedChecksSpec"] = UNSET
    timeliness: Union[Unset, "TableTimelinessDailyPartitionedChecksSpec"] = UNSET
    sql: Union[Unset, "TableSqlDailyPartitionedChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "TableDailyPartitionedCheckCategoriesSpecComparisons"
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

        sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql, Unset):
            sql = self.sql.to_dict()

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
        if sql is not UNSET:
            field_dict["sql"] = sql
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_daily_partitioned_check_categories_spec_comparisons import (
            TableDailyPartitionedCheckCategoriesSpecComparisons,
        )
        from ..models.table_daily_partitioned_check_categories_spec_custom import (
            TableDailyPartitionedCheckCategoriesSpecCustom,
        )
        from ..models.table_sql_daily_partitioned_checks_spec import (
            TableSqlDailyPartitionedChecksSpec,
        )
        from ..models.table_timeliness_daily_partitioned_checks_spec import (
            TableTimelinessDailyPartitionedChecksSpec,
        )
        from ..models.table_volume_daily_partitioned_checks_spec import (
            TableVolumeDailyPartitionedChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, TableDailyPartitionedCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = TableDailyPartitionedCheckCategoriesSpecCustom.from_dict(_custom)

        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeDailyPartitionedChecksSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeDailyPartitionedChecksSpec.from_dict(_volume)

        _timeliness = d.pop("timeliness", UNSET)
        timeliness: Union[Unset, TableTimelinessDailyPartitionedChecksSpec]
        if isinstance(_timeliness, Unset):
            timeliness = UNSET
        else:
            timeliness = TableTimelinessDailyPartitionedChecksSpec.from_dict(
                _timeliness
            )

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, TableSqlDailyPartitionedChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = TableSqlDailyPartitionedChecksSpec.from_dict(_sql)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, TableDailyPartitionedCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = TableDailyPartitionedCheckCategoriesSpecComparisons.from_dict(
                _comparisons
            )

        table_daily_partitioned_check_categories_spec = cls(
            custom=custom,
            volume=volume,
            timeliness=timeliness,
            sql=sql,
            comparisons=comparisons,
        )

        table_daily_partitioned_check_categories_spec.additional_properties = d
        return table_daily_partitioned_check_categories_spec

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
