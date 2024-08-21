from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_text_length_above_max_length_check_spec import (
        ColumnTextLengthAboveMaxLengthCheckSpec,
    )
    from ..models.column_text_length_above_max_length_percent_check_spec import (
        ColumnTextLengthAboveMaxLengthPercentCheckSpec,
    )
    from ..models.column_text_length_below_min_length_check_spec import (
        ColumnTextLengthBelowMinLengthCheckSpec,
    )
    from ..models.column_text_length_below_min_length_percent_check_spec import (
        ColumnTextLengthBelowMinLengthPercentCheckSpec,
    )
    from ..models.column_text_length_in_range_percent_check_spec import (
        ColumnTextLengthInRangePercentCheckSpec,
    )
    from ..models.column_text_max_length_check_spec import ColumnTextMaxLengthCheckSpec
    from ..models.column_text_max_word_count_check_spec import (
        ColumnTextMaxWordCountCheckSpec,
    )
    from ..models.column_text_mean_length_check_spec import (
        ColumnTextMeanLengthCheckSpec,
    )
    from ..models.column_text_min_length_check_spec import ColumnTextMinLengthCheckSpec
    from ..models.column_text_min_word_count_check_spec import (
        ColumnTextMinWordCountCheckSpec,
    )
    from ..models.column_text_monthly_partitioned_checks_spec_custom_checks import (
        ColumnTextMonthlyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnTextMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnTextMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnTextMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_text_min_length (Union[Unset, ColumnTextMinLengthCheckSpec]):
        monthly_partition_text_max_length (Union[Unset, ColumnTextMaxLengthCheckSpec]):
        monthly_partition_text_mean_length (Union[Unset, ColumnTextMeanLengthCheckSpec]):
        monthly_partition_text_length_below_min_length (Union[Unset, ColumnTextLengthBelowMinLengthCheckSpec]):
        monthly_partition_text_length_below_min_length_percent (Union[Unset,
            ColumnTextLengthBelowMinLengthPercentCheckSpec]):
        monthly_partition_text_length_above_max_length (Union[Unset, ColumnTextLengthAboveMaxLengthCheckSpec]):
        monthly_partition_text_length_above_max_length_percent (Union[Unset,
            ColumnTextLengthAboveMaxLengthPercentCheckSpec]):
        monthly_partition_text_length_in_range_percent (Union[Unset, ColumnTextLengthInRangePercentCheckSpec]):
        monthly_partition_min_word_count (Union[Unset, ColumnTextMinWordCountCheckSpec]):
        monthly_partition_max_word_count (Union[Unset, ColumnTextMaxWordCountCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnTextMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_text_min_length: Union[Unset, "ColumnTextMinLengthCheckSpec"] = (
        UNSET
    )
    monthly_partition_text_max_length: Union[Unset, "ColumnTextMaxLengthCheckSpec"] = (
        UNSET
    )
    monthly_partition_text_mean_length: Union[
        Unset, "ColumnTextMeanLengthCheckSpec"
    ] = UNSET
    monthly_partition_text_length_below_min_length: Union[
        Unset, "ColumnTextLengthBelowMinLengthCheckSpec"
    ] = UNSET
    monthly_partition_text_length_below_min_length_percent: Union[
        Unset, "ColumnTextLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_length_above_max_length: Union[
        Unset, "ColumnTextLengthAboveMaxLengthCheckSpec"
    ] = UNSET
    monthly_partition_text_length_above_max_length_percent: Union[
        Unset, "ColumnTextLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_length_in_range_percent: Union[
        Unset, "ColumnTextLengthInRangePercentCheckSpec"
    ] = UNSET
    monthly_partition_min_word_count: Union[
        Unset, "ColumnTextMinWordCountCheckSpec"
    ] = UNSET
    monthly_partition_max_word_count: Union[
        Unset, "ColumnTextMaxWordCountCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_text_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_text_min_length, Unset):
            monthly_partition_text_min_length = (
                self.monthly_partition_text_min_length.to_dict()
            )

        monthly_partition_text_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_text_max_length, Unset):
            monthly_partition_text_max_length = (
                self.monthly_partition_text_max_length.to_dict()
            )

        monthly_partition_text_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_text_mean_length, Unset):
            monthly_partition_text_mean_length = (
                self.monthly_partition_text_mean_length.to_dict()
            )

        monthly_partition_text_length_below_min_length: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_text_length_below_min_length, Unset):
            monthly_partition_text_length_below_min_length = (
                self.monthly_partition_text_length_below_min_length.to_dict()
            )

        monthly_partition_text_length_below_min_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_length_below_min_length_percent, Unset
        ):
            monthly_partition_text_length_below_min_length_percent = (
                self.monthly_partition_text_length_below_min_length_percent.to_dict()
            )

        monthly_partition_text_length_above_max_length: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_text_length_above_max_length, Unset):
            monthly_partition_text_length_above_max_length = (
                self.monthly_partition_text_length_above_max_length.to_dict()
            )

        monthly_partition_text_length_above_max_length_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_length_above_max_length_percent, Unset
        ):
            monthly_partition_text_length_above_max_length_percent = (
                self.monthly_partition_text_length_above_max_length_percent.to_dict()
            )

        monthly_partition_text_length_in_range_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_text_length_in_range_percent, Unset):
            monthly_partition_text_length_in_range_percent = (
                self.monthly_partition_text_length_in_range_percent.to_dict()
            )

        monthly_partition_min_word_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_min_word_count, Unset):
            monthly_partition_min_word_count = (
                self.monthly_partition_min_word_count.to_dict()
            )

        monthly_partition_max_word_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_max_word_count, Unset):
            monthly_partition_max_word_count = (
                self.monthly_partition_max_word_count.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_text_min_length is not UNSET:
            field_dict["monthly_partition_text_min_length"] = (
                monthly_partition_text_min_length
            )
        if monthly_partition_text_max_length is not UNSET:
            field_dict["monthly_partition_text_max_length"] = (
                monthly_partition_text_max_length
            )
        if monthly_partition_text_mean_length is not UNSET:
            field_dict["monthly_partition_text_mean_length"] = (
                monthly_partition_text_mean_length
            )
        if monthly_partition_text_length_below_min_length is not UNSET:
            field_dict["monthly_partition_text_length_below_min_length"] = (
                monthly_partition_text_length_below_min_length
            )
        if monthly_partition_text_length_below_min_length_percent is not UNSET:
            field_dict["monthly_partition_text_length_below_min_length_percent"] = (
                monthly_partition_text_length_below_min_length_percent
            )
        if monthly_partition_text_length_above_max_length is not UNSET:
            field_dict["monthly_partition_text_length_above_max_length"] = (
                monthly_partition_text_length_above_max_length
            )
        if monthly_partition_text_length_above_max_length_percent is not UNSET:
            field_dict["monthly_partition_text_length_above_max_length_percent"] = (
                monthly_partition_text_length_above_max_length_percent
            )
        if monthly_partition_text_length_in_range_percent is not UNSET:
            field_dict["monthly_partition_text_length_in_range_percent"] = (
                monthly_partition_text_length_in_range_percent
            )
        if monthly_partition_min_word_count is not UNSET:
            field_dict["monthly_partition_min_word_count"] = (
                monthly_partition_min_word_count
            )
        if monthly_partition_max_word_count is not UNSET:
            field_dict["monthly_partition_max_word_count"] = (
                monthly_partition_max_word_count
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_text_length_above_max_length_check_spec import (
            ColumnTextLengthAboveMaxLengthCheckSpec,
        )
        from ..models.column_text_length_above_max_length_percent_check_spec import (
            ColumnTextLengthAboveMaxLengthPercentCheckSpec,
        )
        from ..models.column_text_length_below_min_length_check_spec import (
            ColumnTextLengthBelowMinLengthCheckSpec,
        )
        from ..models.column_text_length_below_min_length_percent_check_spec import (
            ColumnTextLengthBelowMinLengthPercentCheckSpec,
        )
        from ..models.column_text_length_in_range_percent_check_spec import (
            ColumnTextLengthInRangePercentCheckSpec,
        )
        from ..models.column_text_max_length_check_spec import (
            ColumnTextMaxLengthCheckSpec,
        )
        from ..models.column_text_max_word_count_check_spec import (
            ColumnTextMaxWordCountCheckSpec,
        )
        from ..models.column_text_mean_length_check_spec import (
            ColumnTextMeanLengthCheckSpec,
        )
        from ..models.column_text_min_length_check_spec import (
            ColumnTextMinLengthCheckSpec,
        )
        from ..models.column_text_min_word_count_check_spec import (
            ColumnTextMinWordCountCheckSpec,
        )
        from ..models.column_text_monthly_partitioned_checks_spec_custom_checks import (
            ColumnTextMonthlyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnTextMonthlyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnTextMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_text_min_length = d.pop(
            "monthly_partition_text_min_length", UNSET
        )
        monthly_partition_text_min_length: Union[Unset, ColumnTextMinLengthCheckSpec]
        if isinstance(_monthly_partition_text_min_length, Unset):
            monthly_partition_text_min_length = UNSET
        else:
            monthly_partition_text_min_length = ColumnTextMinLengthCheckSpec.from_dict(
                _monthly_partition_text_min_length
            )

        _monthly_partition_text_max_length = d.pop(
            "monthly_partition_text_max_length", UNSET
        )
        monthly_partition_text_max_length: Union[Unset, ColumnTextMaxLengthCheckSpec]
        if isinstance(_monthly_partition_text_max_length, Unset):
            monthly_partition_text_max_length = UNSET
        else:
            monthly_partition_text_max_length = ColumnTextMaxLengthCheckSpec.from_dict(
                _monthly_partition_text_max_length
            )

        _monthly_partition_text_mean_length = d.pop(
            "monthly_partition_text_mean_length", UNSET
        )
        monthly_partition_text_mean_length: Union[Unset, ColumnTextMeanLengthCheckSpec]
        if isinstance(_monthly_partition_text_mean_length, Unset):
            monthly_partition_text_mean_length = UNSET
        else:
            monthly_partition_text_mean_length = (
                ColumnTextMeanLengthCheckSpec.from_dict(
                    _monthly_partition_text_mean_length
                )
            )

        _monthly_partition_text_length_below_min_length = d.pop(
            "monthly_partition_text_length_below_min_length", UNSET
        )
        monthly_partition_text_length_below_min_length: Union[
            Unset, ColumnTextLengthBelowMinLengthCheckSpec
        ]
        if isinstance(_monthly_partition_text_length_below_min_length, Unset):
            monthly_partition_text_length_below_min_length = UNSET
        else:
            monthly_partition_text_length_below_min_length = (
                ColumnTextLengthBelowMinLengthCheckSpec.from_dict(
                    _monthly_partition_text_length_below_min_length
                )
            )

        _monthly_partition_text_length_below_min_length_percent = d.pop(
            "monthly_partition_text_length_below_min_length_percent", UNSET
        )
        monthly_partition_text_length_below_min_length_percent: Union[
            Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_length_below_min_length_percent, Unset):
            monthly_partition_text_length_below_min_length_percent = UNSET
        else:
            monthly_partition_text_length_below_min_length_percent = (
                ColumnTextLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _monthly_partition_text_length_below_min_length_percent
                )
            )

        _monthly_partition_text_length_above_max_length = d.pop(
            "monthly_partition_text_length_above_max_length", UNSET
        )
        monthly_partition_text_length_above_max_length: Union[
            Unset, ColumnTextLengthAboveMaxLengthCheckSpec
        ]
        if isinstance(_monthly_partition_text_length_above_max_length, Unset):
            monthly_partition_text_length_above_max_length = UNSET
        else:
            monthly_partition_text_length_above_max_length = (
                ColumnTextLengthAboveMaxLengthCheckSpec.from_dict(
                    _monthly_partition_text_length_above_max_length
                )
            )

        _monthly_partition_text_length_above_max_length_percent = d.pop(
            "monthly_partition_text_length_above_max_length_percent", UNSET
        )
        monthly_partition_text_length_above_max_length_percent: Union[
            Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_length_above_max_length_percent, Unset):
            monthly_partition_text_length_above_max_length_percent = UNSET
        else:
            monthly_partition_text_length_above_max_length_percent = (
                ColumnTextLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _monthly_partition_text_length_above_max_length_percent
                )
            )

        _monthly_partition_text_length_in_range_percent = d.pop(
            "monthly_partition_text_length_in_range_percent", UNSET
        )
        monthly_partition_text_length_in_range_percent: Union[
            Unset, ColumnTextLengthInRangePercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_length_in_range_percent, Unset):
            monthly_partition_text_length_in_range_percent = UNSET
        else:
            monthly_partition_text_length_in_range_percent = (
                ColumnTextLengthInRangePercentCheckSpec.from_dict(
                    _monthly_partition_text_length_in_range_percent
                )
            )

        _monthly_partition_min_word_count = d.pop(
            "monthly_partition_min_word_count", UNSET
        )
        monthly_partition_min_word_count: Union[Unset, ColumnTextMinWordCountCheckSpec]
        if isinstance(_monthly_partition_min_word_count, Unset):
            monthly_partition_min_word_count = UNSET
        else:
            monthly_partition_min_word_count = (
                ColumnTextMinWordCountCheckSpec.from_dict(
                    _monthly_partition_min_word_count
                )
            )

        _monthly_partition_max_word_count = d.pop(
            "monthly_partition_max_word_count", UNSET
        )
        monthly_partition_max_word_count: Union[Unset, ColumnTextMaxWordCountCheckSpec]
        if isinstance(_monthly_partition_max_word_count, Unset):
            monthly_partition_max_word_count = UNSET
        else:
            monthly_partition_max_word_count = (
                ColumnTextMaxWordCountCheckSpec.from_dict(
                    _monthly_partition_max_word_count
                )
            )

        column_text_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_text_min_length=monthly_partition_text_min_length,
            monthly_partition_text_max_length=monthly_partition_text_max_length,
            monthly_partition_text_mean_length=monthly_partition_text_mean_length,
            monthly_partition_text_length_below_min_length=monthly_partition_text_length_below_min_length,
            monthly_partition_text_length_below_min_length_percent=monthly_partition_text_length_below_min_length_percent,
            monthly_partition_text_length_above_max_length=monthly_partition_text_length_above_max_length,
            monthly_partition_text_length_above_max_length_percent=monthly_partition_text_length_above_max_length_percent,
            monthly_partition_text_length_in_range_percent=monthly_partition_text_length_in_range_percent,
            monthly_partition_min_word_count=monthly_partition_min_word_count,
            monthly_partition_max_word_count=monthly_partition_max_word_count,
        )

        column_text_monthly_partitioned_checks_spec.additional_properties = d
        return column_text_monthly_partitioned_checks_spec

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
