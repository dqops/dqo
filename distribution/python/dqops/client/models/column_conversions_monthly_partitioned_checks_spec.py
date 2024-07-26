from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_conversions_monthly_partitioned_checks_spec_custom_checks import (
        ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_text_parsable_to_boolean_percent_check_spec import (
        ColumnTextParsableToBooleanPercentCheckSpec,
    )
    from ..models.column_text_parsable_to_date_percent_check_spec import (
        ColumnTextParsableToDatePercentCheckSpec,
    )
    from ..models.column_text_parsable_to_float_percent_check_spec import (
        ColumnTextParsableToFloatPercentCheckSpec,
    )
    from ..models.column_text_parsable_to_integer_percent_check_spec import (
        ColumnTextParsableToIntegerPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnConversionsMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnConversionsMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of
            additional custom checks within this category. The keys are check names defined in the definition section. The
            sensor parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_text_parsable_to_boolean_percent (Union[Unset, ColumnTextParsableToBooleanPercentCheckSpec]):
        monthly_partition_text_parsable_to_integer_percent (Union[Unset, ColumnTextParsableToIntegerPercentCheckSpec]):
        monthly_partition_text_parsable_to_float_percent (Union[Unset, ColumnTextParsableToFloatPercentCheckSpec]):
        monthly_partition_text_parsable_to_date_percent (Union[Unset, ColumnTextParsableToDatePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_text_parsable_to_boolean_percent: Union[
        Unset, "ColumnTextParsableToBooleanPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_parsable_to_integer_percent: Union[
        Unset, "ColumnTextParsableToIntegerPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_parsable_to_float_percent: Union[
        Unset, "ColumnTextParsableToFloatPercentCheckSpec"
    ] = UNSET
    monthly_partition_text_parsable_to_date_percent: Union[
        Unset, "ColumnTextParsableToDatePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_text_parsable_to_boolean_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_parsable_to_boolean_percent, Unset
        ):
            monthly_partition_text_parsable_to_boolean_percent = (
                self.monthly_partition_text_parsable_to_boolean_percent.to_dict()
            )

        monthly_partition_text_parsable_to_integer_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_text_parsable_to_integer_percent, Unset
        ):
            monthly_partition_text_parsable_to_integer_percent = (
                self.monthly_partition_text_parsable_to_integer_percent.to_dict()
            )

        monthly_partition_text_parsable_to_float_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_text_parsable_to_float_percent, Unset):
            monthly_partition_text_parsable_to_float_percent = (
                self.monthly_partition_text_parsable_to_float_percent.to_dict()
            )

        monthly_partition_text_parsable_to_date_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_text_parsable_to_date_percent, Unset):
            monthly_partition_text_parsable_to_date_percent = (
                self.monthly_partition_text_parsable_to_date_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_text_parsable_to_boolean_percent is not UNSET:
            field_dict["monthly_partition_text_parsable_to_boolean_percent"] = (
                monthly_partition_text_parsable_to_boolean_percent
            )
        if monthly_partition_text_parsable_to_integer_percent is not UNSET:
            field_dict["monthly_partition_text_parsable_to_integer_percent"] = (
                monthly_partition_text_parsable_to_integer_percent
            )
        if monthly_partition_text_parsable_to_float_percent is not UNSET:
            field_dict["monthly_partition_text_parsable_to_float_percent"] = (
                monthly_partition_text_parsable_to_float_percent
            )
        if monthly_partition_text_parsable_to_date_percent is not UNSET:
            field_dict["monthly_partition_text_parsable_to_date_percent"] = (
                monthly_partition_text_parsable_to_date_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_conversions_monthly_partitioned_checks_spec_custom_checks import (
            ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_text_parsable_to_boolean_percent_check_spec import (
            ColumnTextParsableToBooleanPercentCheckSpec,
        )
        from ..models.column_text_parsable_to_date_percent_check_spec import (
            ColumnTextParsableToDatePercentCheckSpec,
        )
        from ..models.column_text_parsable_to_float_percent_check_spec import (
            ColumnTextParsableToFloatPercentCheckSpec,
        )
        from ..models.column_text_parsable_to_integer_percent_check_spec import (
            ColumnTextParsableToIntegerPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnConversionsMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_text_parsable_to_boolean_percent = d.pop(
            "monthly_partition_text_parsable_to_boolean_percent", UNSET
        )
        monthly_partition_text_parsable_to_boolean_percent: Union[
            Unset, ColumnTextParsableToBooleanPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_parsable_to_boolean_percent, Unset):
            monthly_partition_text_parsable_to_boolean_percent = UNSET
        else:
            monthly_partition_text_parsable_to_boolean_percent = (
                ColumnTextParsableToBooleanPercentCheckSpec.from_dict(
                    _monthly_partition_text_parsable_to_boolean_percent
                )
            )

        _monthly_partition_text_parsable_to_integer_percent = d.pop(
            "monthly_partition_text_parsable_to_integer_percent", UNSET
        )
        monthly_partition_text_parsable_to_integer_percent: Union[
            Unset, ColumnTextParsableToIntegerPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_parsable_to_integer_percent, Unset):
            monthly_partition_text_parsable_to_integer_percent = UNSET
        else:
            monthly_partition_text_parsable_to_integer_percent = (
                ColumnTextParsableToIntegerPercentCheckSpec.from_dict(
                    _monthly_partition_text_parsable_to_integer_percent
                )
            )

        _monthly_partition_text_parsable_to_float_percent = d.pop(
            "monthly_partition_text_parsable_to_float_percent", UNSET
        )
        monthly_partition_text_parsable_to_float_percent: Union[
            Unset, ColumnTextParsableToFloatPercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_parsable_to_float_percent, Unset):
            monthly_partition_text_parsable_to_float_percent = UNSET
        else:
            monthly_partition_text_parsable_to_float_percent = (
                ColumnTextParsableToFloatPercentCheckSpec.from_dict(
                    _monthly_partition_text_parsable_to_float_percent
                )
            )

        _monthly_partition_text_parsable_to_date_percent = d.pop(
            "monthly_partition_text_parsable_to_date_percent", UNSET
        )
        monthly_partition_text_parsable_to_date_percent: Union[
            Unset, ColumnTextParsableToDatePercentCheckSpec
        ]
        if isinstance(_monthly_partition_text_parsable_to_date_percent, Unset):
            monthly_partition_text_parsable_to_date_percent = UNSET
        else:
            monthly_partition_text_parsable_to_date_percent = (
                ColumnTextParsableToDatePercentCheckSpec.from_dict(
                    _monthly_partition_text_parsable_to_date_percent
                )
            )

        column_conversions_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_text_parsable_to_boolean_percent=monthly_partition_text_parsable_to_boolean_percent,
            monthly_partition_text_parsable_to_integer_percent=monthly_partition_text_parsable_to_integer_percent,
            monthly_partition_text_parsable_to_float_percent=monthly_partition_text_parsable_to_float_percent,
            monthly_partition_text_parsable_to_date_percent=monthly_partition_text_parsable_to_date_percent,
        )

        column_conversions_monthly_partitioned_checks_spec.additional_properties = d
        return column_conversions_monthly_partitioned_checks_spec

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
