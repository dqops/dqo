from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_integer_in_range_percent_check_spec import (
        ColumnIntegerInRangePercentCheckSpec,
    )
    from ..models.column_invalid_latitude_count_check_spec import (
        ColumnInvalidLatitudeCountCheckSpec,
    )
    from ..models.column_invalid_longitude_count_check_spec import (
        ColumnInvalidLongitudeCountCheckSpec,
    )
    from ..models.column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
    from ..models.column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
    from ..models.column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
    from ..models.column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
    from ..models.column_negative_count_check_spec import ColumnNegativeCountCheckSpec
    from ..models.column_negative_percent_check_spec import (
        ColumnNegativePercentCheckSpec,
    )
    from ..models.column_non_negative_count_check_spec import (
        ColumnNonNegativeCountCheckSpec,
    )
    from ..models.column_non_negative_percent_check_spec import (
        ColumnNonNegativePercentCheckSpec,
    )
    from ..models.column_number_above_max_value_check_spec import (
        ColumnNumberAboveMaxValueCheckSpec,
    )
    from ..models.column_number_above_max_value_percent_check_spec import (
        ColumnNumberAboveMaxValuePercentCheckSpec,
    )
    from ..models.column_number_below_min_value_check_spec import (
        ColumnNumberBelowMinValueCheckSpec,
    )
    from ..models.column_number_below_min_value_percent_check_spec import (
        ColumnNumberBelowMinValuePercentCheckSpec,
    )
    from ..models.column_number_in_range_percent_check_spec import (
        ColumnNumberInRangePercentCheckSpec,
    )
    from ..models.column_numeric_profiling_checks_spec_custom_checks import (
        ColumnNumericProfilingChecksSpecCustomChecks,
    )
    from ..models.column_percentile_10_in_range_check_spec import (
        ColumnPercentile10InRangeCheckSpec,
    )
    from ..models.column_percentile_25_in_range_check_spec import (
        ColumnPercentile25InRangeCheckSpec,
    )
    from ..models.column_percentile_75_in_range_check_spec import (
        ColumnPercentile75InRangeCheckSpec,
    )
    from ..models.column_percentile_90_in_range_check_spec import (
        ColumnPercentile90InRangeCheckSpec,
    )
    from ..models.column_percentile_in_range_check_spec import (
        ColumnPercentileInRangeCheckSpec,
    )
    from ..models.column_population_stddev_in_range_check_spec import (
        ColumnPopulationStddevInRangeCheckSpec,
    )
    from ..models.column_population_variance_in_range_check_spec import (
        ColumnPopulationVarianceInRangeCheckSpec,
    )
    from ..models.column_sample_stddev_in_range_check_spec import (
        ColumnSampleStddevInRangeCheckSpec,
    )
    from ..models.column_sample_variance_in_range_check_spec import (
        ColumnSampleVarianceInRangeCheckSpec,
    )
    from ..models.column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
    from ..models.column_valid_latitude_percent_check_spec import (
        ColumnValidLatitudePercentCheckSpec,
    )
    from ..models.column_valid_longitude_percent_check_spec import (
        ColumnValidLongitudePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnNumericProfilingChecksSpec")


@_attrs_define
class ColumnNumericProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnNumericProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_number_below_min_value (Union[Unset, ColumnNumberBelowMinValueCheckSpec]):
        profile_number_above_max_value (Union[Unset, ColumnNumberAboveMaxValueCheckSpec]):
        profile_negative_values (Union[Unset, ColumnNegativeCountCheckSpec]):
        profile_negative_values_percent (Union[Unset, ColumnNegativePercentCheckSpec]):
        profile_number_below_min_value_percent (Union[Unset, ColumnNumberBelowMinValuePercentCheckSpec]):
        profile_number_above_max_value_percent (Union[Unset, ColumnNumberAboveMaxValuePercentCheckSpec]):
        profile_number_in_range_percent (Union[Unset, ColumnNumberInRangePercentCheckSpec]):
        profile_integer_in_range_percent (Union[Unset, ColumnIntegerInRangePercentCheckSpec]):
        profile_min_in_range (Union[Unset, ColumnMinInRangeCheckSpec]):
        profile_max_in_range (Union[Unset, ColumnMaxInRangeCheckSpec]):
        profile_sum_in_range (Union[Unset, ColumnSumInRangeCheckSpec]):
        profile_mean_in_range (Union[Unset, ColumnMeanInRangeCheckSpec]):
        profile_median_in_range (Union[Unset, ColumnMedianInRangeCheckSpec]):
        profile_percentile_in_range (Union[Unset, ColumnPercentileInRangeCheckSpec]):
        profile_percentile_10_in_range (Union[Unset, ColumnPercentile10InRangeCheckSpec]):
        profile_percentile_25_in_range (Union[Unset, ColumnPercentile25InRangeCheckSpec]):
        profile_percentile_75_in_range (Union[Unset, ColumnPercentile75InRangeCheckSpec]):
        profile_percentile_90_in_range (Union[Unset, ColumnPercentile90InRangeCheckSpec]):
        profile_sample_stddev_in_range (Union[Unset, ColumnSampleStddevInRangeCheckSpec]):
        profile_population_stddev_in_range (Union[Unset, ColumnPopulationStddevInRangeCheckSpec]):
        profile_sample_variance_in_range (Union[Unset, ColumnSampleVarianceInRangeCheckSpec]):
        profile_population_variance_in_range (Union[Unset, ColumnPopulationVarianceInRangeCheckSpec]):
        profile_invalid_latitude (Union[Unset, ColumnInvalidLatitudeCountCheckSpec]):
        profile_valid_latitude_percent (Union[Unset, ColumnValidLatitudePercentCheckSpec]):
        profile_invalid_longitude (Union[Unset, ColumnInvalidLongitudeCountCheckSpec]):
        profile_valid_longitude_percent (Union[Unset, ColumnValidLongitudePercentCheckSpec]):
        profile_non_negative_values (Union[Unset, ColumnNonNegativeCountCheckSpec]):
        profile_non_negative_values_percent (Union[Unset, ColumnNonNegativePercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnNumericProfilingChecksSpecCustomChecks"] = UNSET
    profile_number_below_min_value: Union[
        Unset, "ColumnNumberBelowMinValueCheckSpec"
    ] = UNSET
    profile_number_above_max_value: Union[
        Unset, "ColumnNumberAboveMaxValueCheckSpec"
    ] = UNSET
    profile_negative_values: Union[Unset, "ColumnNegativeCountCheckSpec"] = UNSET
    profile_negative_values_percent: Union[Unset, "ColumnNegativePercentCheckSpec"] = (
        UNSET
    )
    profile_number_below_min_value_percent: Union[
        Unset, "ColumnNumberBelowMinValuePercentCheckSpec"
    ] = UNSET
    profile_number_above_max_value_percent: Union[
        Unset, "ColumnNumberAboveMaxValuePercentCheckSpec"
    ] = UNSET
    profile_number_in_range_percent: Union[
        Unset, "ColumnNumberInRangePercentCheckSpec"
    ] = UNSET
    profile_integer_in_range_percent: Union[
        Unset, "ColumnIntegerInRangePercentCheckSpec"
    ] = UNSET
    profile_min_in_range: Union[Unset, "ColumnMinInRangeCheckSpec"] = UNSET
    profile_max_in_range: Union[Unset, "ColumnMaxInRangeCheckSpec"] = UNSET
    profile_sum_in_range: Union[Unset, "ColumnSumInRangeCheckSpec"] = UNSET
    profile_mean_in_range: Union[Unset, "ColumnMeanInRangeCheckSpec"] = UNSET
    profile_median_in_range: Union[Unset, "ColumnMedianInRangeCheckSpec"] = UNSET
    profile_percentile_in_range: Union[Unset, "ColumnPercentileInRangeCheckSpec"] = (
        UNSET
    )
    profile_percentile_10_in_range: Union[
        Unset, "ColumnPercentile10InRangeCheckSpec"
    ] = UNSET
    profile_percentile_25_in_range: Union[
        Unset, "ColumnPercentile25InRangeCheckSpec"
    ] = UNSET
    profile_percentile_75_in_range: Union[
        Unset, "ColumnPercentile75InRangeCheckSpec"
    ] = UNSET
    profile_percentile_90_in_range: Union[
        Unset, "ColumnPercentile90InRangeCheckSpec"
    ] = UNSET
    profile_sample_stddev_in_range: Union[
        Unset, "ColumnSampleStddevInRangeCheckSpec"
    ] = UNSET
    profile_population_stddev_in_range: Union[
        Unset, "ColumnPopulationStddevInRangeCheckSpec"
    ] = UNSET
    profile_sample_variance_in_range: Union[
        Unset, "ColumnSampleVarianceInRangeCheckSpec"
    ] = UNSET
    profile_population_variance_in_range: Union[
        Unset, "ColumnPopulationVarianceInRangeCheckSpec"
    ] = UNSET
    profile_invalid_latitude: Union[Unset, "ColumnInvalidLatitudeCountCheckSpec"] = (
        UNSET
    )
    profile_valid_latitude_percent: Union[
        Unset, "ColumnValidLatitudePercentCheckSpec"
    ] = UNSET
    profile_invalid_longitude: Union[Unset, "ColumnInvalidLongitudeCountCheckSpec"] = (
        UNSET
    )
    profile_valid_longitude_percent: Union[
        Unset, "ColumnValidLongitudePercentCheckSpec"
    ] = UNSET
    profile_non_negative_values: Union[Unset, "ColumnNonNegativeCountCheckSpec"] = UNSET
    profile_non_negative_values_percent: Union[
        Unset, "ColumnNonNegativePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_number_below_min_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_number_below_min_value, Unset):
            profile_number_below_min_value = (
                self.profile_number_below_min_value.to_dict()
            )

        profile_number_above_max_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_number_above_max_value, Unset):
            profile_number_above_max_value = (
                self.profile_number_above_max_value.to_dict()
            )

        profile_negative_values: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_negative_values, Unset):
            profile_negative_values = self.profile_negative_values.to_dict()

        profile_negative_values_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_negative_values_percent, Unset):
            profile_negative_values_percent = (
                self.profile_negative_values_percent.to_dict()
            )

        profile_number_below_min_value_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_number_below_min_value_percent, Unset):
            profile_number_below_min_value_percent = (
                self.profile_number_below_min_value_percent.to_dict()
            )

        profile_number_above_max_value_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_number_above_max_value_percent, Unset):
            profile_number_above_max_value_percent = (
                self.profile_number_above_max_value_percent.to_dict()
            )

        profile_number_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_number_in_range_percent, Unset):
            profile_number_in_range_percent = (
                self.profile_number_in_range_percent.to_dict()
            )

        profile_integer_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_integer_in_range_percent, Unset):
            profile_integer_in_range_percent = (
                self.profile_integer_in_range_percent.to_dict()
            )

        profile_min_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_min_in_range, Unset):
            profile_min_in_range = self.profile_min_in_range.to_dict()

        profile_max_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_max_in_range, Unset):
            profile_max_in_range = self.profile_max_in_range.to_dict()

        profile_sum_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_in_range, Unset):
            profile_sum_in_range = self.profile_sum_in_range.to_dict()

        profile_mean_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_in_range, Unset):
            profile_mean_in_range = self.profile_mean_in_range.to_dict()

        profile_median_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_in_range, Unset):
            profile_median_in_range = self.profile_median_in_range.to_dict()

        profile_percentile_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_percentile_in_range, Unset):
            profile_percentile_in_range = self.profile_percentile_in_range.to_dict()

        profile_percentile_10_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_percentile_10_in_range, Unset):
            profile_percentile_10_in_range = (
                self.profile_percentile_10_in_range.to_dict()
            )

        profile_percentile_25_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_percentile_25_in_range, Unset):
            profile_percentile_25_in_range = (
                self.profile_percentile_25_in_range.to_dict()
            )

        profile_percentile_75_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_percentile_75_in_range, Unset):
            profile_percentile_75_in_range = (
                self.profile_percentile_75_in_range.to_dict()
            )

        profile_percentile_90_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_percentile_90_in_range, Unset):
            profile_percentile_90_in_range = (
                self.profile_percentile_90_in_range.to_dict()
            )

        profile_sample_stddev_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sample_stddev_in_range, Unset):
            profile_sample_stddev_in_range = (
                self.profile_sample_stddev_in_range.to_dict()
            )

        profile_population_stddev_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_population_stddev_in_range, Unset):
            profile_population_stddev_in_range = (
                self.profile_population_stddev_in_range.to_dict()
            )

        profile_sample_variance_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sample_variance_in_range, Unset):
            profile_sample_variance_in_range = (
                self.profile_sample_variance_in_range.to_dict()
            )

        profile_population_variance_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_population_variance_in_range, Unset):
            profile_population_variance_in_range = (
                self.profile_population_variance_in_range.to_dict()
            )

        profile_invalid_latitude: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_invalid_latitude, Unset):
            profile_invalid_latitude = self.profile_invalid_latitude.to_dict()

        profile_valid_latitude_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_valid_latitude_percent, Unset):
            profile_valid_latitude_percent = (
                self.profile_valid_latitude_percent.to_dict()
            )

        profile_invalid_longitude: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_invalid_longitude, Unset):
            profile_invalid_longitude = self.profile_invalid_longitude.to_dict()

        profile_valid_longitude_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_valid_longitude_percent, Unset):
            profile_valid_longitude_percent = (
                self.profile_valid_longitude_percent.to_dict()
            )

        profile_non_negative_values: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_non_negative_values, Unset):
            profile_non_negative_values = self.profile_non_negative_values.to_dict()

        profile_non_negative_values_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_non_negative_values_percent, Unset):
            profile_non_negative_values_percent = (
                self.profile_non_negative_values_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_number_below_min_value is not UNSET:
            field_dict["profile_number_below_min_value"] = (
                profile_number_below_min_value
            )
        if profile_number_above_max_value is not UNSET:
            field_dict["profile_number_above_max_value"] = (
                profile_number_above_max_value
            )
        if profile_negative_values is not UNSET:
            field_dict["profile_negative_values"] = profile_negative_values
        if profile_negative_values_percent is not UNSET:
            field_dict["profile_negative_values_percent"] = (
                profile_negative_values_percent
            )
        if profile_number_below_min_value_percent is not UNSET:
            field_dict["profile_number_below_min_value_percent"] = (
                profile_number_below_min_value_percent
            )
        if profile_number_above_max_value_percent is not UNSET:
            field_dict["profile_number_above_max_value_percent"] = (
                profile_number_above_max_value_percent
            )
        if profile_number_in_range_percent is not UNSET:
            field_dict["profile_number_in_range_percent"] = (
                profile_number_in_range_percent
            )
        if profile_integer_in_range_percent is not UNSET:
            field_dict["profile_integer_in_range_percent"] = (
                profile_integer_in_range_percent
            )
        if profile_min_in_range is not UNSET:
            field_dict["profile_min_in_range"] = profile_min_in_range
        if profile_max_in_range is not UNSET:
            field_dict["profile_max_in_range"] = profile_max_in_range
        if profile_sum_in_range is not UNSET:
            field_dict["profile_sum_in_range"] = profile_sum_in_range
        if profile_mean_in_range is not UNSET:
            field_dict["profile_mean_in_range"] = profile_mean_in_range
        if profile_median_in_range is not UNSET:
            field_dict["profile_median_in_range"] = profile_median_in_range
        if profile_percentile_in_range is not UNSET:
            field_dict["profile_percentile_in_range"] = profile_percentile_in_range
        if profile_percentile_10_in_range is not UNSET:
            field_dict["profile_percentile_10_in_range"] = (
                profile_percentile_10_in_range
            )
        if profile_percentile_25_in_range is not UNSET:
            field_dict["profile_percentile_25_in_range"] = (
                profile_percentile_25_in_range
            )
        if profile_percentile_75_in_range is not UNSET:
            field_dict["profile_percentile_75_in_range"] = (
                profile_percentile_75_in_range
            )
        if profile_percentile_90_in_range is not UNSET:
            field_dict["profile_percentile_90_in_range"] = (
                profile_percentile_90_in_range
            )
        if profile_sample_stddev_in_range is not UNSET:
            field_dict["profile_sample_stddev_in_range"] = (
                profile_sample_stddev_in_range
            )
        if profile_population_stddev_in_range is not UNSET:
            field_dict["profile_population_stddev_in_range"] = (
                profile_population_stddev_in_range
            )
        if profile_sample_variance_in_range is not UNSET:
            field_dict["profile_sample_variance_in_range"] = (
                profile_sample_variance_in_range
            )
        if profile_population_variance_in_range is not UNSET:
            field_dict["profile_population_variance_in_range"] = (
                profile_population_variance_in_range
            )
        if profile_invalid_latitude is not UNSET:
            field_dict["profile_invalid_latitude"] = profile_invalid_latitude
        if profile_valid_latitude_percent is not UNSET:
            field_dict["profile_valid_latitude_percent"] = (
                profile_valid_latitude_percent
            )
        if profile_invalid_longitude is not UNSET:
            field_dict["profile_invalid_longitude"] = profile_invalid_longitude
        if profile_valid_longitude_percent is not UNSET:
            field_dict["profile_valid_longitude_percent"] = (
                profile_valid_longitude_percent
            )
        if profile_non_negative_values is not UNSET:
            field_dict["profile_non_negative_values"] = profile_non_negative_values
        if profile_non_negative_values_percent is not UNSET:
            field_dict["profile_non_negative_values_percent"] = (
                profile_non_negative_values_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_integer_in_range_percent_check_spec import (
            ColumnIntegerInRangePercentCheckSpec,
        )
        from ..models.column_invalid_latitude_count_check_spec import (
            ColumnInvalidLatitudeCountCheckSpec,
        )
        from ..models.column_invalid_longitude_count_check_spec import (
            ColumnInvalidLongitudeCountCheckSpec,
        )
        from ..models.column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
        from ..models.column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
        from ..models.column_median_in_range_check_spec import (
            ColumnMedianInRangeCheckSpec,
        )
        from ..models.column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
        from ..models.column_negative_count_check_spec import (
            ColumnNegativeCountCheckSpec,
        )
        from ..models.column_negative_percent_check_spec import (
            ColumnNegativePercentCheckSpec,
        )
        from ..models.column_non_negative_count_check_spec import (
            ColumnNonNegativeCountCheckSpec,
        )
        from ..models.column_non_negative_percent_check_spec import (
            ColumnNonNegativePercentCheckSpec,
        )
        from ..models.column_number_above_max_value_check_spec import (
            ColumnNumberAboveMaxValueCheckSpec,
        )
        from ..models.column_number_above_max_value_percent_check_spec import (
            ColumnNumberAboveMaxValuePercentCheckSpec,
        )
        from ..models.column_number_below_min_value_check_spec import (
            ColumnNumberBelowMinValueCheckSpec,
        )
        from ..models.column_number_below_min_value_percent_check_spec import (
            ColumnNumberBelowMinValuePercentCheckSpec,
        )
        from ..models.column_number_in_range_percent_check_spec import (
            ColumnNumberInRangePercentCheckSpec,
        )
        from ..models.column_numeric_profiling_checks_spec_custom_checks import (
            ColumnNumericProfilingChecksSpecCustomChecks,
        )
        from ..models.column_percentile_10_in_range_check_spec import (
            ColumnPercentile10InRangeCheckSpec,
        )
        from ..models.column_percentile_25_in_range_check_spec import (
            ColumnPercentile25InRangeCheckSpec,
        )
        from ..models.column_percentile_75_in_range_check_spec import (
            ColumnPercentile75InRangeCheckSpec,
        )
        from ..models.column_percentile_90_in_range_check_spec import (
            ColumnPercentile90InRangeCheckSpec,
        )
        from ..models.column_percentile_in_range_check_spec import (
            ColumnPercentileInRangeCheckSpec,
        )
        from ..models.column_population_stddev_in_range_check_spec import (
            ColumnPopulationStddevInRangeCheckSpec,
        )
        from ..models.column_population_variance_in_range_check_spec import (
            ColumnPopulationVarianceInRangeCheckSpec,
        )
        from ..models.column_sample_stddev_in_range_check_spec import (
            ColumnSampleStddevInRangeCheckSpec,
        )
        from ..models.column_sample_variance_in_range_check_spec import (
            ColumnSampleVarianceInRangeCheckSpec,
        )
        from ..models.column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
        from ..models.column_valid_latitude_percent_check_spec import (
            ColumnValidLatitudePercentCheckSpec,
        )
        from ..models.column_valid_longitude_percent_check_spec import (
            ColumnValidLongitudePercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnNumericProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnNumericProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_number_below_min_value = d.pop("profile_number_below_min_value", UNSET)
        profile_number_below_min_value: Union[Unset, ColumnNumberBelowMinValueCheckSpec]
        if isinstance(_profile_number_below_min_value, Unset):
            profile_number_below_min_value = UNSET
        else:
            profile_number_below_min_value = (
                ColumnNumberBelowMinValueCheckSpec.from_dict(
                    _profile_number_below_min_value
                )
            )

        _profile_number_above_max_value = d.pop("profile_number_above_max_value", UNSET)
        profile_number_above_max_value: Union[Unset, ColumnNumberAboveMaxValueCheckSpec]
        if isinstance(_profile_number_above_max_value, Unset):
            profile_number_above_max_value = UNSET
        else:
            profile_number_above_max_value = (
                ColumnNumberAboveMaxValueCheckSpec.from_dict(
                    _profile_number_above_max_value
                )
            )

        _profile_negative_values = d.pop("profile_negative_values", UNSET)
        profile_negative_values: Union[Unset, ColumnNegativeCountCheckSpec]
        if isinstance(_profile_negative_values, Unset):
            profile_negative_values = UNSET
        else:
            profile_negative_values = ColumnNegativeCountCheckSpec.from_dict(
                _profile_negative_values
            )

        _profile_negative_values_percent = d.pop(
            "profile_negative_values_percent", UNSET
        )
        profile_negative_values_percent: Union[Unset, ColumnNegativePercentCheckSpec]
        if isinstance(_profile_negative_values_percent, Unset):
            profile_negative_values_percent = UNSET
        else:
            profile_negative_values_percent = ColumnNegativePercentCheckSpec.from_dict(
                _profile_negative_values_percent
            )

        _profile_number_below_min_value_percent = d.pop(
            "profile_number_below_min_value_percent", UNSET
        )
        profile_number_below_min_value_percent: Union[
            Unset, ColumnNumberBelowMinValuePercentCheckSpec
        ]
        if isinstance(_profile_number_below_min_value_percent, Unset):
            profile_number_below_min_value_percent = UNSET
        else:
            profile_number_below_min_value_percent = (
                ColumnNumberBelowMinValuePercentCheckSpec.from_dict(
                    _profile_number_below_min_value_percent
                )
            )

        _profile_number_above_max_value_percent = d.pop(
            "profile_number_above_max_value_percent", UNSET
        )
        profile_number_above_max_value_percent: Union[
            Unset, ColumnNumberAboveMaxValuePercentCheckSpec
        ]
        if isinstance(_profile_number_above_max_value_percent, Unset):
            profile_number_above_max_value_percent = UNSET
        else:
            profile_number_above_max_value_percent = (
                ColumnNumberAboveMaxValuePercentCheckSpec.from_dict(
                    _profile_number_above_max_value_percent
                )
            )

        _profile_number_in_range_percent = d.pop(
            "profile_number_in_range_percent", UNSET
        )
        profile_number_in_range_percent: Union[
            Unset, ColumnNumberInRangePercentCheckSpec
        ]
        if isinstance(_profile_number_in_range_percent, Unset):
            profile_number_in_range_percent = UNSET
        else:
            profile_number_in_range_percent = (
                ColumnNumberInRangePercentCheckSpec.from_dict(
                    _profile_number_in_range_percent
                )
            )

        _profile_integer_in_range_percent = d.pop(
            "profile_integer_in_range_percent", UNSET
        )
        profile_integer_in_range_percent: Union[
            Unset, ColumnIntegerInRangePercentCheckSpec
        ]
        if isinstance(_profile_integer_in_range_percent, Unset):
            profile_integer_in_range_percent = UNSET
        else:
            profile_integer_in_range_percent = (
                ColumnIntegerInRangePercentCheckSpec.from_dict(
                    _profile_integer_in_range_percent
                )
            )

        _profile_min_in_range = d.pop("profile_min_in_range", UNSET)
        profile_min_in_range: Union[Unset, ColumnMinInRangeCheckSpec]
        if isinstance(_profile_min_in_range, Unset):
            profile_min_in_range = UNSET
        else:
            profile_min_in_range = ColumnMinInRangeCheckSpec.from_dict(
                _profile_min_in_range
            )

        _profile_max_in_range = d.pop("profile_max_in_range", UNSET)
        profile_max_in_range: Union[Unset, ColumnMaxInRangeCheckSpec]
        if isinstance(_profile_max_in_range, Unset):
            profile_max_in_range = UNSET
        else:
            profile_max_in_range = ColumnMaxInRangeCheckSpec.from_dict(
                _profile_max_in_range
            )

        _profile_sum_in_range = d.pop("profile_sum_in_range", UNSET)
        profile_sum_in_range: Union[Unset, ColumnSumInRangeCheckSpec]
        if isinstance(_profile_sum_in_range, Unset):
            profile_sum_in_range = UNSET
        else:
            profile_sum_in_range = ColumnSumInRangeCheckSpec.from_dict(
                _profile_sum_in_range
            )

        _profile_mean_in_range = d.pop("profile_mean_in_range", UNSET)
        profile_mean_in_range: Union[Unset, ColumnMeanInRangeCheckSpec]
        if isinstance(_profile_mean_in_range, Unset):
            profile_mean_in_range = UNSET
        else:
            profile_mean_in_range = ColumnMeanInRangeCheckSpec.from_dict(
                _profile_mean_in_range
            )

        _profile_median_in_range = d.pop("profile_median_in_range", UNSET)
        profile_median_in_range: Union[Unset, ColumnMedianInRangeCheckSpec]
        if isinstance(_profile_median_in_range, Unset):
            profile_median_in_range = UNSET
        else:
            profile_median_in_range = ColumnMedianInRangeCheckSpec.from_dict(
                _profile_median_in_range
            )

        _profile_percentile_in_range = d.pop("profile_percentile_in_range", UNSET)
        profile_percentile_in_range: Union[Unset, ColumnPercentileInRangeCheckSpec]
        if isinstance(_profile_percentile_in_range, Unset):
            profile_percentile_in_range = UNSET
        else:
            profile_percentile_in_range = ColumnPercentileInRangeCheckSpec.from_dict(
                _profile_percentile_in_range
            )

        _profile_percentile_10_in_range = d.pop("profile_percentile_10_in_range", UNSET)
        profile_percentile_10_in_range: Union[Unset, ColumnPercentile10InRangeCheckSpec]
        if isinstance(_profile_percentile_10_in_range, Unset):
            profile_percentile_10_in_range = UNSET
        else:
            profile_percentile_10_in_range = (
                ColumnPercentile10InRangeCheckSpec.from_dict(
                    _profile_percentile_10_in_range
                )
            )

        _profile_percentile_25_in_range = d.pop("profile_percentile_25_in_range", UNSET)
        profile_percentile_25_in_range: Union[Unset, ColumnPercentile25InRangeCheckSpec]
        if isinstance(_profile_percentile_25_in_range, Unset):
            profile_percentile_25_in_range = UNSET
        else:
            profile_percentile_25_in_range = (
                ColumnPercentile25InRangeCheckSpec.from_dict(
                    _profile_percentile_25_in_range
                )
            )

        _profile_percentile_75_in_range = d.pop("profile_percentile_75_in_range", UNSET)
        profile_percentile_75_in_range: Union[Unset, ColumnPercentile75InRangeCheckSpec]
        if isinstance(_profile_percentile_75_in_range, Unset):
            profile_percentile_75_in_range = UNSET
        else:
            profile_percentile_75_in_range = (
                ColumnPercentile75InRangeCheckSpec.from_dict(
                    _profile_percentile_75_in_range
                )
            )

        _profile_percentile_90_in_range = d.pop("profile_percentile_90_in_range", UNSET)
        profile_percentile_90_in_range: Union[Unset, ColumnPercentile90InRangeCheckSpec]
        if isinstance(_profile_percentile_90_in_range, Unset):
            profile_percentile_90_in_range = UNSET
        else:
            profile_percentile_90_in_range = (
                ColumnPercentile90InRangeCheckSpec.from_dict(
                    _profile_percentile_90_in_range
                )
            )

        _profile_sample_stddev_in_range = d.pop("profile_sample_stddev_in_range", UNSET)
        profile_sample_stddev_in_range: Union[Unset, ColumnSampleStddevInRangeCheckSpec]
        if isinstance(_profile_sample_stddev_in_range, Unset):
            profile_sample_stddev_in_range = UNSET
        else:
            profile_sample_stddev_in_range = (
                ColumnSampleStddevInRangeCheckSpec.from_dict(
                    _profile_sample_stddev_in_range
                )
            )

        _profile_population_stddev_in_range = d.pop(
            "profile_population_stddev_in_range", UNSET
        )
        profile_population_stddev_in_range: Union[
            Unset, ColumnPopulationStddevInRangeCheckSpec
        ]
        if isinstance(_profile_population_stddev_in_range, Unset):
            profile_population_stddev_in_range = UNSET
        else:
            profile_population_stddev_in_range = (
                ColumnPopulationStddevInRangeCheckSpec.from_dict(
                    _profile_population_stddev_in_range
                )
            )

        _profile_sample_variance_in_range = d.pop(
            "profile_sample_variance_in_range", UNSET
        )
        profile_sample_variance_in_range: Union[
            Unset, ColumnSampleVarianceInRangeCheckSpec
        ]
        if isinstance(_profile_sample_variance_in_range, Unset):
            profile_sample_variance_in_range = UNSET
        else:
            profile_sample_variance_in_range = (
                ColumnSampleVarianceInRangeCheckSpec.from_dict(
                    _profile_sample_variance_in_range
                )
            )

        _profile_population_variance_in_range = d.pop(
            "profile_population_variance_in_range", UNSET
        )
        profile_population_variance_in_range: Union[
            Unset, ColumnPopulationVarianceInRangeCheckSpec
        ]
        if isinstance(_profile_population_variance_in_range, Unset):
            profile_population_variance_in_range = UNSET
        else:
            profile_population_variance_in_range = (
                ColumnPopulationVarianceInRangeCheckSpec.from_dict(
                    _profile_population_variance_in_range
                )
            )

        _profile_invalid_latitude = d.pop("profile_invalid_latitude", UNSET)
        profile_invalid_latitude: Union[Unset, ColumnInvalidLatitudeCountCheckSpec]
        if isinstance(_profile_invalid_latitude, Unset):
            profile_invalid_latitude = UNSET
        else:
            profile_invalid_latitude = ColumnInvalidLatitudeCountCheckSpec.from_dict(
                _profile_invalid_latitude
            )

        _profile_valid_latitude_percent = d.pop("profile_valid_latitude_percent", UNSET)
        profile_valid_latitude_percent: Union[
            Unset, ColumnValidLatitudePercentCheckSpec
        ]
        if isinstance(_profile_valid_latitude_percent, Unset):
            profile_valid_latitude_percent = UNSET
        else:
            profile_valid_latitude_percent = (
                ColumnValidLatitudePercentCheckSpec.from_dict(
                    _profile_valid_latitude_percent
                )
            )

        _profile_invalid_longitude = d.pop("profile_invalid_longitude", UNSET)
        profile_invalid_longitude: Union[Unset, ColumnInvalidLongitudeCountCheckSpec]
        if isinstance(_profile_invalid_longitude, Unset):
            profile_invalid_longitude = UNSET
        else:
            profile_invalid_longitude = ColumnInvalidLongitudeCountCheckSpec.from_dict(
                _profile_invalid_longitude
            )

        _profile_valid_longitude_percent = d.pop(
            "profile_valid_longitude_percent", UNSET
        )
        profile_valid_longitude_percent: Union[
            Unset, ColumnValidLongitudePercentCheckSpec
        ]
        if isinstance(_profile_valid_longitude_percent, Unset):
            profile_valid_longitude_percent = UNSET
        else:
            profile_valid_longitude_percent = (
                ColumnValidLongitudePercentCheckSpec.from_dict(
                    _profile_valid_longitude_percent
                )
            )

        _profile_non_negative_values = d.pop("profile_non_negative_values", UNSET)
        profile_non_negative_values: Union[Unset, ColumnNonNegativeCountCheckSpec]
        if isinstance(_profile_non_negative_values, Unset):
            profile_non_negative_values = UNSET
        else:
            profile_non_negative_values = ColumnNonNegativeCountCheckSpec.from_dict(
                _profile_non_negative_values
            )

        _profile_non_negative_values_percent = d.pop(
            "profile_non_negative_values_percent", UNSET
        )
        profile_non_negative_values_percent: Union[
            Unset, ColumnNonNegativePercentCheckSpec
        ]
        if isinstance(_profile_non_negative_values_percent, Unset):
            profile_non_negative_values_percent = UNSET
        else:
            profile_non_negative_values_percent = (
                ColumnNonNegativePercentCheckSpec.from_dict(
                    _profile_non_negative_values_percent
                )
            )

        column_numeric_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_number_below_min_value=profile_number_below_min_value,
            profile_number_above_max_value=profile_number_above_max_value,
            profile_negative_values=profile_negative_values,
            profile_negative_values_percent=profile_negative_values_percent,
            profile_number_below_min_value_percent=profile_number_below_min_value_percent,
            profile_number_above_max_value_percent=profile_number_above_max_value_percent,
            profile_number_in_range_percent=profile_number_in_range_percent,
            profile_integer_in_range_percent=profile_integer_in_range_percent,
            profile_min_in_range=profile_min_in_range,
            profile_max_in_range=profile_max_in_range,
            profile_sum_in_range=profile_sum_in_range,
            profile_mean_in_range=profile_mean_in_range,
            profile_median_in_range=profile_median_in_range,
            profile_percentile_in_range=profile_percentile_in_range,
            profile_percentile_10_in_range=profile_percentile_10_in_range,
            profile_percentile_25_in_range=profile_percentile_25_in_range,
            profile_percentile_75_in_range=profile_percentile_75_in_range,
            profile_percentile_90_in_range=profile_percentile_90_in_range,
            profile_sample_stddev_in_range=profile_sample_stddev_in_range,
            profile_population_stddev_in_range=profile_population_stddev_in_range,
            profile_sample_variance_in_range=profile_sample_variance_in_range,
            profile_population_variance_in_range=profile_population_variance_in_range,
            profile_invalid_latitude=profile_invalid_latitude,
            profile_valid_latitude_percent=profile_valid_latitude_percent,
            profile_invalid_longitude=profile_invalid_longitude,
            profile_valid_longitude_percent=profile_valid_longitude_percent,
            profile_non_negative_values=profile_non_negative_values,
            profile_non_negative_values_percent=profile_non_negative_values_percent,
        )

        column_numeric_profiling_checks_spec.additional_properties = d
        return column_numeric_profiling_checks_spec

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
