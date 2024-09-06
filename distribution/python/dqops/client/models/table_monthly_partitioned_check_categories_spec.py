from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_custom_sql_monthly_partitioned_checks_spec import (
        TableCustomSqlMonthlyPartitionedChecksSpec,
    )
    from ..models.table_monthly_partitioned_check_categories_spec_comparisons import (
        TableMonthlyPartitionedCheckCategoriesSpecComparisons,
    )
    from ..models.table_monthly_partitioned_check_categories_spec_custom import (
        TableMonthlyPartitionedCheckCategoriesSpecCustom,
    )
    from ..models.table_timeliness_monthly_partitioned_checks_spec import (
        TableTimelinessMonthlyPartitionedChecksSpec,
    )
    from ..models.table_uniqueness_monthly_partition_checks_spec import (
        TableUniquenessMonthlyPartitionChecksSpec,
    )
    from ..models.table_volume_monthly_partitioned_checks_spec import (
        TableVolumeMonthlyPartitionedChecksSpec,
    )


T = TypeVar("T", bound="TableMonthlyPartitionedCheckCategoriesSpec")


@_attrs_define
class TableMonthlyPartitionedCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, TableMonthlyPartitionedCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        volume (Union[Unset, TableVolumeMonthlyPartitionedChecksSpec]):
        timeliness (Union[Unset, TableTimelinessMonthlyPartitionedChecksSpec]):
        custom_sql (Union[Unset, TableCustomSqlMonthlyPartitionedChecksSpec]):
        uniqueness (Union[Unset, TableUniquenessMonthlyPartitionChecksSpec]):
        comparisons (Union[Unset, TableMonthlyPartitionedCheckCategoriesSpecComparisons]): Dictionary of configuration
            of checks for table comparisons. The key that identifies each comparison must match the name of a data
            comparison that is configured on the parent table.
    """

    custom: Union[Unset, "TableMonthlyPartitionedCheckCategoriesSpecCustom"] = UNSET
    volume: Union[Unset, "TableVolumeMonthlyPartitionedChecksSpec"] = UNSET
    timeliness: Union[Unset, "TableTimelinessMonthlyPartitionedChecksSpec"] = UNSET
    custom_sql: Union[Unset, "TableCustomSqlMonthlyPartitionedChecksSpec"] = UNSET
    uniqueness: Union[Unset, "TableUniquenessMonthlyPartitionChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "TableMonthlyPartitionedCheckCategoriesSpecComparisons"
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

        custom_sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_sql, Unset):
            custom_sql = self.custom_sql.to_dict()

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
        if custom_sql is not UNSET:
            field_dict["custom_sql"] = custom_sql
        if uniqueness is not UNSET:
            field_dict["uniqueness"] = uniqueness
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_custom_sql_monthly_partitioned_checks_spec import (
            TableCustomSqlMonthlyPartitionedChecksSpec,
        )
        from ..models.table_monthly_partitioned_check_categories_spec_comparisons import (
            TableMonthlyPartitionedCheckCategoriesSpecComparisons,
        )
        from ..models.table_monthly_partitioned_check_categories_spec_custom import (
            TableMonthlyPartitionedCheckCategoriesSpecCustom,
        )
        from ..models.table_timeliness_monthly_partitioned_checks_spec import (
            TableTimelinessMonthlyPartitionedChecksSpec,
        )
        from ..models.table_uniqueness_monthly_partition_checks_spec import (
            TableUniquenessMonthlyPartitionChecksSpec,
        )
        from ..models.table_volume_monthly_partitioned_checks_spec import (
            TableVolumeMonthlyPartitionedChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, TableMonthlyPartitionedCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = TableMonthlyPartitionedCheckCategoriesSpecCustom.from_dict(_custom)

        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeMonthlyPartitionedChecksSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeMonthlyPartitionedChecksSpec.from_dict(_volume)

        _timeliness = d.pop("timeliness", UNSET)
        timeliness: Union[Unset, TableTimelinessMonthlyPartitionedChecksSpec]
        if isinstance(_timeliness, Unset):
            timeliness = UNSET
        else:
            timeliness = TableTimelinessMonthlyPartitionedChecksSpec.from_dict(
                _timeliness
            )

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, TableCustomSqlMonthlyPartitionedChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = TableCustomSqlMonthlyPartitionedChecksSpec.from_dict(
                _custom_sql
            )

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, TableUniquenessMonthlyPartitionChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = TableUniquenessMonthlyPartitionChecksSpec.from_dict(
                _uniqueness
            )

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, TableMonthlyPartitionedCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = (
                TableMonthlyPartitionedCheckCategoriesSpecComparisons.from_dict(
                    _comparisons
                )
            )

        table_monthly_partitioned_check_categories_spec = cls(
            custom=custom,
            volume=volume,
            timeliness=timeliness,
            custom_sql=custom_sql,
            uniqueness=uniqueness,
            comparisons=comparisons,
        )

        table_monthly_partitioned_check_categories_spec.additional_properties = d
        return table_monthly_partitioned_check_categories_spec

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
