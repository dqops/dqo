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
    from ..models.column_numeric_daily_partitioned_checks_spec_custom_checks import (
        ColumnNumericDailyPartitionedChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnNumericDailyPartitionedChecksSpec")


@_attrs_define
class ColumnNumericDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnNumericDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_number_below_min_value (Union[Unset, ColumnNumberBelowMinValueCheckSpec]):
        daily_partition_number_above_max_value (Union[Unset, ColumnNumberAboveMaxValueCheckSpec]):
        daily_partition_negative_values (Union[Unset, ColumnNegativeCountCheckSpec]):
        daily_partition_negative_values_percent (Union[Unset, ColumnNegativePercentCheckSpec]):
        daily_partition_number_below_min_value_percent (Union[Unset, ColumnNumberBelowMinValuePercentCheckSpec]):
        daily_partition_number_above_max_value_percent (Union[Unset, ColumnNumberAboveMaxValuePercentCheckSpec]):
        daily_partition_number_in_range_percent (Union[Unset, ColumnNumberInRangePercentCheckSpec]):
        daily_partition_integer_in_range_percent (Union[Unset, ColumnIntegerInRangePercentCheckSpec]):
        daily_partition_min_in_range (Union[Unset, ColumnMinInRangeCheckSpec]):
        daily_partition_max_in_range (Union[Unset, ColumnMaxInRangeCheckSpec]):
        daily_partition_sum_in_range (Union[Unset, ColumnSumInRangeCheckSpec]):
        daily_partition_mean_in_range (Union[Unset, ColumnMeanInRangeCheckSpec]):
        daily_partition_median_in_range (Union[Unset, ColumnMedianInRangeCheckSpec]):
        daily_partition_percentile_in_range (Union[Unset, ColumnPercentileInRangeCheckSpec]):
        daily_partition_percentile_10_in_range (Union[Unset, ColumnPercentile10InRangeCheckSpec]):
        daily_partition_percentile_25_in_range (Union[Unset, ColumnPercentile25InRangeCheckSpec]):
        daily_partition_percentile_75_in_range (Union[Unset, ColumnPercentile75InRangeCheckSpec]):
        daily_partition_percentile_90_in_range (Union[Unset, ColumnPercentile90InRangeCheckSpec]):
        daily_partition_sample_stddev_in_range (Union[Unset, ColumnSampleStddevInRangeCheckSpec]):
        daily_partition_population_stddev_in_range (Union[Unset, ColumnPopulationStddevInRangeCheckSpec]):
        daily_partition_sample_variance_in_range (Union[Unset, ColumnSampleVarianceInRangeCheckSpec]):
        daily_partition_population_variance_in_range (Union[Unset, ColumnPopulationVarianceInRangeCheckSpec]):
        daily_partition_invalid_latitude (Union[Unset, ColumnInvalidLatitudeCountCheckSpec]):
        daily_partition_valid_latitude_percent (Union[Unset, ColumnValidLatitudePercentCheckSpec]):
        daily_partition_invalid_longitude (Union[Unset, ColumnInvalidLongitudeCountCheckSpec]):
        daily_partition_valid_longitude_percent (Union[Unset, ColumnValidLongitudePercentCheckSpec]):
        daily_partition_non_negative_values (Union[Unset, ColumnNonNegativeCountCheckSpec]):
        daily_partition_non_negative_values_percent (Union[Unset, ColumnNonNegativePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnNumericDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_number_below_min_value: Union[
        Unset, "ColumnNumberBelowMinValueCheckSpec"
    ] = UNSET
    daily_partition_number_above_max_value: Union[
        Unset, "ColumnNumberAboveMaxValueCheckSpec"
    ] = UNSET
    daily_partition_negative_values: Union[Unset, "ColumnNegativeCountCheckSpec"] = (
        UNSET
    )
    daily_partition_negative_values_percent: Union[
        Unset, "ColumnNegativePercentCheckSpec"
    ] = UNSET
    daily_partition_number_below_min_value_percent: Union[
        Unset, "ColumnNumberBelowMinValuePercentCheckSpec"
    ] = UNSET
    daily_partition_number_above_max_value_percent: Union[
        Unset, "ColumnNumberAboveMaxValuePercentCheckSpec"
    ] = UNSET
    daily_partition_number_in_range_percent: Union[
        Unset, "ColumnNumberInRangePercentCheckSpec"
    ] = UNSET
    daily_partition_integer_in_range_percent: Union[
        Unset, "ColumnIntegerInRangePercentCheckSpec"
    ] = UNSET
    daily_partition_min_in_range: Union[Unset, "ColumnMinInRangeCheckSpec"] = UNSET
    daily_partition_max_in_range: Union[Unset, "ColumnMaxInRangeCheckSpec"] = UNSET
    daily_partition_sum_in_range: Union[Unset, "ColumnSumInRangeCheckSpec"] = UNSET
    daily_partition_mean_in_range: Union[Unset, "ColumnMeanInRangeCheckSpec"] = UNSET
    daily_partition_median_in_range: Union[Unset, "ColumnMedianInRangeCheckSpec"] = (
        UNSET
    )
    daily_partition_percentile_in_range: Union[
        Unset, "ColumnPercentileInRangeCheckSpec"
    ] = UNSET
    daily_partition_percentile_10_in_range: Union[
        Unset, "ColumnPercentile10InRangeCheckSpec"
    ] = UNSET
    daily_partition_percentile_25_in_range: Union[
        Unset, "ColumnPercentile25InRangeCheckSpec"
    ] = UNSET
    daily_partition_percentile_75_in_range: Union[
        Unset, "ColumnPercentile75InRangeCheckSpec"
    ] = UNSET
    daily_partition_percentile_90_in_range: Union[
        Unset, "ColumnPercentile90InRangeCheckSpec"
    ] = UNSET
    daily_partition_sample_stddev_in_range: Union[
        Unset, "ColumnSampleStddevInRangeCheckSpec"
    ] = UNSET
    daily_partition_population_stddev_in_range: Union[
        Unset, "ColumnPopulationStddevInRangeCheckSpec"
    ] = UNSET
    daily_partition_sample_variance_in_range: Union[
        Unset, "ColumnSampleVarianceInRangeCheckSpec"
    ] = UNSET
    daily_partition_population_variance_in_range: Union[
        Unset, "ColumnPopulationVarianceInRangeCheckSpec"
    ] = UNSET
    daily_partition_invalid_latitude: Union[
        Unset, "ColumnInvalidLatitudeCountCheckSpec"
    ] = UNSET
    daily_partition_valid_latitude_percent: Union[
        Unset, "ColumnValidLatitudePercentCheckSpec"
    ] = UNSET
    daily_partition_invalid_longitude: Union[
        Unset, "ColumnInvalidLongitudeCountCheckSpec"
    ] = UNSET
    daily_partition_valid_longitude_percent: Union[
        Unset, "ColumnValidLongitudePercentCheckSpec"
    ] = UNSET
    daily_partition_non_negative_values: Union[
        Unset, "ColumnNonNegativeCountCheckSpec"
    ] = UNSET
    daily_partition_non_negative_values_percent: Union[
        Unset, "ColumnNonNegativePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_number_below_min_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_number_below_min_value, Unset):
            daily_partition_number_below_min_value = (
                self.daily_partition_number_below_min_value.to_dict()
            )

        daily_partition_number_above_max_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_number_above_max_value, Unset):
            daily_partition_number_above_max_value = (
                self.daily_partition_number_above_max_value.to_dict()
            )

        daily_partition_negative_values: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_negative_values, Unset):
            daily_partition_negative_values = (
                self.daily_partition_negative_values.to_dict()
            )

        daily_partition_negative_values_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_negative_values_percent, Unset):
            daily_partition_negative_values_percent = (
                self.daily_partition_negative_values_percent.to_dict()
            )

        daily_partition_number_below_min_value_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_partition_number_below_min_value_percent, Unset):
            daily_partition_number_below_min_value_percent = (
                self.daily_partition_number_below_min_value_percent.to_dict()
            )

        daily_partition_number_above_max_value_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_partition_number_above_max_value_percent, Unset):
            daily_partition_number_above_max_value_percent = (
                self.daily_partition_number_above_max_value_percent.to_dict()
            )

        daily_partition_number_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_number_in_range_percent, Unset):
            daily_partition_number_in_range_percent = (
                self.daily_partition_number_in_range_percent.to_dict()
            )

        daily_partition_integer_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_integer_in_range_percent, Unset):
            daily_partition_integer_in_range_percent = (
                self.daily_partition_integer_in_range_percent.to_dict()
            )

        daily_partition_min_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_min_in_range, Unset):
            daily_partition_min_in_range = self.daily_partition_min_in_range.to_dict()

        daily_partition_max_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_max_in_range, Unset):
            daily_partition_max_in_range = self.daily_partition_max_in_range.to_dict()

        daily_partition_sum_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_in_range, Unset):
            daily_partition_sum_in_range = self.daily_partition_sum_in_range.to_dict()

        daily_partition_mean_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_in_range, Unset):
            daily_partition_mean_in_range = self.daily_partition_mean_in_range.to_dict()

        daily_partition_median_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_in_range, Unset):
            daily_partition_median_in_range = (
                self.daily_partition_median_in_range.to_dict()
            )

        daily_partition_percentile_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_percentile_in_range, Unset):
            daily_partition_percentile_in_range = (
                self.daily_partition_percentile_in_range.to_dict()
            )

        daily_partition_percentile_10_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_percentile_10_in_range, Unset):
            daily_partition_percentile_10_in_range = (
                self.daily_partition_percentile_10_in_range.to_dict()
            )

        daily_partition_percentile_25_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_percentile_25_in_range, Unset):
            daily_partition_percentile_25_in_range = (
                self.daily_partition_percentile_25_in_range.to_dict()
            )

        daily_partition_percentile_75_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_percentile_75_in_range, Unset):
            daily_partition_percentile_75_in_range = (
                self.daily_partition_percentile_75_in_range.to_dict()
            )

        daily_partition_percentile_90_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_percentile_90_in_range, Unset):
            daily_partition_percentile_90_in_range = (
                self.daily_partition_percentile_90_in_range.to_dict()
            )

        daily_partition_sample_stddev_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sample_stddev_in_range, Unset):
            daily_partition_sample_stddev_in_range = (
                self.daily_partition_sample_stddev_in_range.to_dict()
            )

        daily_partition_population_stddev_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_population_stddev_in_range, Unset):
            daily_partition_population_stddev_in_range = (
                self.daily_partition_population_stddev_in_range.to_dict()
            )

        daily_partition_sample_variance_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sample_variance_in_range, Unset):
            daily_partition_sample_variance_in_range = (
                self.daily_partition_sample_variance_in_range.to_dict()
            )

        daily_partition_population_variance_in_range: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_partition_population_variance_in_range, Unset):
            daily_partition_population_variance_in_range = (
                self.daily_partition_population_variance_in_range.to_dict()
            )

        daily_partition_invalid_latitude: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_invalid_latitude, Unset):
            daily_partition_invalid_latitude = (
                self.daily_partition_invalid_latitude.to_dict()
            )

        daily_partition_valid_latitude_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_valid_latitude_percent, Unset):
            daily_partition_valid_latitude_percent = (
                self.daily_partition_valid_latitude_percent.to_dict()
            )

        daily_partition_invalid_longitude: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_invalid_longitude, Unset):
            daily_partition_invalid_longitude = (
                self.daily_partition_invalid_longitude.to_dict()
            )

        daily_partition_valid_longitude_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_valid_longitude_percent, Unset):
            daily_partition_valid_longitude_percent = (
                self.daily_partition_valid_longitude_percent.to_dict()
            )

        daily_partition_non_negative_values: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_non_negative_values, Unset):
            daily_partition_non_negative_values = (
                self.daily_partition_non_negative_values.to_dict()
            )

        daily_partition_non_negative_values_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_partition_non_negative_values_percent, Unset):
            daily_partition_non_negative_values_percent = (
                self.daily_partition_non_negative_values_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_number_below_min_value is not UNSET:
            field_dict["daily_partition_number_below_min_value"] = (
                daily_partition_number_below_min_value
            )
        if daily_partition_number_above_max_value is not UNSET:
            field_dict["daily_partition_number_above_max_value"] = (
                daily_partition_number_above_max_value
            )
        if daily_partition_negative_values is not UNSET:
            field_dict["daily_partition_negative_values"] = (
                daily_partition_negative_values
            )
        if daily_partition_negative_values_percent is not UNSET:
            field_dict["daily_partition_negative_values_percent"] = (
                daily_partition_negative_values_percent
            )
        if daily_partition_number_below_min_value_percent is not UNSET:
            field_dict["daily_partition_number_below_min_value_percent"] = (
                daily_partition_number_below_min_value_percent
            )
        if daily_partition_number_above_max_value_percent is not UNSET:
            field_dict["daily_partition_number_above_max_value_percent"] = (
                daily_partition_number_above_max_value_percent
            )
        if daily_partition_number_in_range_percent is not UNSET:
            field_dict["daily_partition_number_in_range_percent"] = (
                daily_partition_number_in_range_percent
            )
        if daily_partition_integer_in_range_percent is not UNSET:
            field_dict["daily_partition_integer_in_range_percent"] = (
                daily_partition_integer_in_range_percent
            )
        if daily_partition_min_in_range is not UNSET:
            field_dict["daily_partition_min_in_range"] = daily_partition_min_in_range
        if daily_partition_max_in_range is not UNSET:
            field_dict["daily_partition_max_in_range"] = daily_partition_max_in_range
        if daily_partition_sum_in_range is not UNSET:
            field_dict["daily_partition_sum_in_range"] = daily_partition_sum_in_range
        if daily_partition_mean_in_range is not UNSET:
            field_dict["daily_partition_mean_in_range"] = daily_partition_mean_in_range
        if daily_partition_median_in_range is not UNSET:
            field_dict["daily_partition_median_in_range"] = (
                daily_partition_median_in_range
            )
        if daily_partition_percentile_in_range is not UNSET:
            field_dict["daily_partition_percentile_in_range"] = (
                daily_partition_percentile_in_range
            )
        if daily_partition_percentile_10_in_range is not UNSET:
            field_dict["daily_partition_percentile_10_in_range"] = (
                daily_partition_percentile_10_in_range
            )
        if daily_partition_percentile_25_in_range is not UNSET:
            field_dict["daily_partition_percentile_25_in_range"] = (
                daily_partition_percentile_25_in_range
            )
        if daily_partition_percentile_75_in_range is not UNSET:
            field_dict["daily_partition_percentile_75_in_range"] = (
                daily_partition_percentile_75_in_range
            )
        if daily_partition_percentile_90_in_range is not UNSET:
            field_dict["daily_partition_percentile_90_in_range"] = (
                daily_partition_percentile_90_in_range
            )
        if daily_partition_sample_stddev_in_range is not UNSET:
            field_dict["daily_partition_sample_stddev_in_range"] = (
                daily_partition_sample_stddev_in_range
            )
        if daily_partition_population_stddev_in_range is not UNSET:
            field_dict["daily_partition_population_stddev_in_range"] = (
                daily_partition_population_stddev_in_range
            )
        if daily_partition_sample_variance_in_range is not UNSET:
            field_dict["daily_partition_sample_variance_in_range"] = (
                daily_partition_sample_variance_in_range
            )
        if daily_partition_population_variance_in_range is not UNSET:
            field_dict["daily_partition_population_variance_in_range"] = (
                daily_partition_population_variance_in_range
            )
        if daily_partition_invalid_latitude is not UNSET:
            field_dict["daily_partition_invalid_latitude"] = (
                daily_partition_invalid_latitude
            )
        if daily_partition_valid_latitude_percent is not UNSET:
            field_dict["daily_partition_valid_latitude_percent"] = (
                daily_partition_valid_latitude_percent
            )
        if daily_partition_invalid_longitude is not UNSET:
            field_dict["daily_partition_invalid_longitude"] = (
                daily_partition_invalid_longitude
            )
        if daily_partition_valid_longitude_percent is not UNSET:
            field_dict["daily_partition_valid_longitude_percent"] = (
                daily_partition_valid_longitude_percent
            )
        if daily_partition_non_negative_values is not UNSET:
            field_dict["daily_partition_non_negative_values"] = (
                daily_partition_non_negative_values
            )
        if daily_partition_non_negative_values_percent is not UNSET:
            field_dict["daily_partition_non_negative_values_percent"] = (
                daily_partition_non_negative_values_percent
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
        from ..models.column_numeric_daily_partitioned_checks_spec_custom_checks import (
            ColumnNumericDailyPartitionedChecksSpecCustomChecks,
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
        custom_checks: Union[Unset, ColumnNumericDailyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnNumericDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_partition_number_below_min_value = d.pop(
            "daily_partition_number_below_min_value", UNSET
        )
        daily_partition_number_below_min_value: Union[
            Unset, ColumnNumberBelowMinValueCheckSpec
        ]
        if isinstance(_daily_partition_number_below_min_value, Unset):
            daily_partition_number_below_min_value = UNSET
        else:
            daily_partition_number_below_min_value = (
                ColumnNumberBelowMinValueCheckSpec.from_dict(
                    _daily_partition_number_below_min_value
                )
            )

        _daily_partition_number_above_max_value = d.pop(
            "daily_partition_number_above_max_value", UNSET
        )
        daily_partition_number_above_max_value: Union[
            Unset, ColumnNumberAboveMaxValueCheckSpec
        ]
        if isinstance(_daily_partition_number_above_max_value, Unset):
            daily_partition_number_above_max_value = UNSET
        else:
            daily_partition_number_above_max_value = (
                ColumnNumberAboveMaxValueCheckSpec.from_dict(
                    _daily_partition_number_above_max_value
                )
            )

        _daily_partition_negative_values = d.pop(
            "daily_partition_negative_values", UNSET
        )
        daily_partition_negative_values: Union[Unset, ColumnNegativeCountCheckSpec]
        if isinstance(_daily_partition_negative_values, Unset):
            daily_partition_negative_values = UNSET
        else:
            daily_partition_negative_values = ColumnNegativeCountCheckSpec.from_dict(
                _daily_partition_negative_values
            )

        _daily_partition_negative_values_percent = d.pop(
            "daily_partition_negative_values_percent", UNSET
        )
        daily_partition_negative_values_percent: Union[
            Unset, ColumnNegativePercentCheckSpec
        ]
        if isinstance(_daily_partition_negative_values_percent, Unset):
            daily_partition_negative_values_percent = UNSET
        else:
            daily_partition_negative_values_percent = (
                ColumnNegativePercentCheckSpec.from_dict(
                    _daily_partition_negative_values_percent
                )
            )

        _daily_partition_number_below_min_value_percent = d.pop(
            "daily_partition_number_below_min_value_percent", UNSET
        )
        daily_partition_number_below_min_value_percent: Union[
            Unset, ColumnNumberBelowMinValuePercentCheckSpec
        ]
        if isinstance(_daily_partition_number_below_min_value_percent, Unset):
            daily_partition_number_below_min_value_percent = UNSET
        else:
            daily_partition_number_below_min_value_percent = (
                ColumnNumberBelowMinValuePercentCheckSpec.from_dict(
                    _daily_partition_number_below_min_value_percent
                )
            )

        _daily_partition_number_above_max_value_percent = d.pop(
            "daily_partition_number_above_max_value_percent", UNSET
        )
        daily_partition_number_above_max_value_percent: Union[
            Unset, ColumnNumberAboveMaxValuePercentCheckSpec
        ]
        if isinstance(_daily_partition_number_above_max_value_percent, Unset):
            daily_partition_number_above_max_value_percent = UNSET
        else:
            daily_partition_number_above_max_value_percent = (
                ColumnNumberAboveMaxValuePercentCheckSpec.from_dict(
                    _daily_partition_number_above_max_value_percent
                )
            )

        _daily_partition_number_in_range_percent = d.pop(
            "daily_partition_number_in_range_percent", UNSET
        )
        daily_partition_number_in_range_percent: Union[
            Unset, ColumnNumberInRangePercentCheckSpec
        ]
        if isinstance(_daily_partition_number_in_range_percent, Unset):
            daily_partition_number_in_range_percent = UNSET
        else:
            daily_partition_number_in_range_percent = (
                ColumnNumberInRangePercentCheckSpec.from_dict(
                    _daily_partition_number_in_range_percent
                )
            )

        _daily_partition_integer_in_range_percent = d.pop(
            "daily_partition_integer_in_range_percent", UNSET
        )
        daily_partition_integer_in_range_percent: Union[
            Unset, ColumnIntegerInRangePercentCheckSpec
        ]
        if isinstance(_daily_partition_integer_in_range_percent, Unset):
            daily_partition_integer_in_range_percent = UNSET
        else:
            daily_partition_integer_in_range_percent = (
                ColumnIntegerInRangePercentCheckSpec.from_dict(
                    _daily_partition_integer_in_range_percent
                )
            )

        _daily_partition_min_in_range = d.pop("daily_partition_min_in_range", UNSET)
        daily_partition_min_in_range: Union[Unset, ColumnMinInRangeCheckSpec]
        if isinstance(_daily_partition_min_in_range, Unset):
            daily_partition_min_in_range = UNSET
        else:
            daily_partition_min_in_range = ColumnMinInRangeCheckSpec.from_dict(
                _daily_partition_min_in_range
            )

        _daily_partition_max_in_range = d.pop("daily_partition_max_in_range", UNSET)
        daily_partition_max_in_range: Union[Unset, ColumnMaxInRangeCheckSpec]
        if isinstance(_daily_partition_max_in_range, Unset):
            daily_partition_max_in_range = UNSET
        else:
            daily_partition_max_in_range = ColumnMaxInRangeCheckSpec.from_dict(
                _daily_partition_max_in_range
            )

        _daily_partition_sum_in_range = d.pop("daily_partition_sum_in_range", UNSET)
        daily_partition_sum_in_range: Union[Unset, ColumnSumInRangeCheckSpec]
        if isinstance(_daily_partition_sum_in_range, Unset):
            daily_partition_sum_in_range = UNSET
        else:
            daily_partition_sum_in_range = ColumnSumInRangeCheckSpec.from_dict(
                _daily_partition_sum_in_range
            )

        _daily_partition_mean_in_range = d.pop("daily_partition_mean_in_range", UNSET)
        daily_partition_mean_in_range: Union[Unset, ColumnMeanInRangeCheckSpec]
        if isinstance(_daily_partition_mean_in_range, Unset):
            daily_partition_mean_in_range = UNSET
        else:
            daily_partition_mean_in_range = ColumnMeanInRangeCheckSpec.from_dict(
                _daily_partition_mean_in_range
            )

        _daily_partition_median_in_range = d.pop(
            "daily_partition_median_in_range", UNSET
        )
        daily_partition_median_in_range: Union[Unset, ColumnMedianInRangeCheckSpec]
        if isinstance(_daily_partition_median_in_range, Unset):
            daily_partition_median_in_range = UNSET
        else:
            daily_partition_median_in_range = ColumnMedianInRangeCheckSpec.from_dict(
                _daily_partition_median_in_range
            )

        _daily_partition_percentile_in_range = d.pop(
            "daily_partition_percentile_in_range", UNSET
        )
        daily_partition_percentile_in_range: Union[
            Unset, ColumnPercentileInRangeCheckSpec
        ]
        if isinstance(_daily_partition_percentile_in_range, Unset):
            daily_partition_percentile_in_range = UNSET
        else:
            daily_partition_percentile_in_range = (
                ColumnPercentileInRangeCheckSpec.from_dict(
                    _daily_partition_percentile_in_range
                )
            )

        _daily_partition_percentile_10_in_range = d.pop(
            "daily_partition_percentile_10_in_range", UNSET
        )
        daily_partition_percentile_10_in_range: Union[
            Unset, ColumnPercentile10InRangeCheckSpec
        ]
        if isinstance(_daily_partition_percentile_10_in_range, Unset):
            daily_partition_percentile_10_in_range = UNSET
        else:
            daily_partition_percentile_10_in_range = (
                ColumnPercentile10InRangeCheckSpec.from_dict(
                    _daily_partition_percentile_10_in_range
                )
            )

        _daily_partition_percentile_25_in_range = d.pop(
            "daily_partition_percentile_25_in_range", UNSET
        )
        daily_partition_percentile_25_in_range: Union[
            Unset, ColumnPercentile25InRangeCheckSpec
        ]
        if isinstance(_daily_partition_percentile_25_in_range, Unset):
            daily_partition_percentile_25_in_range = UNSET
        else:
            daily_partition_percentile_25_in_range = (
                ColumnPercentile25InRangeCheckSpec.from_dict(
                    _daily_partition_percentile_25_in_range
                )
            )

        _daily_partition_percentile_75_in_range = d.pop(
            "daily_partition_percentile_75_in_range", UNSET
        )
        daily_partition_percentile_75_in_range: Union[
            Unset, ColumnPercentile75InRangeCheckSpec
        ]
        if isinstance(_daily_partition_percentile_75_in_range, Unset):
            daily_partition_percentile_75_in_range = UNSET
        else:
            daily_partition_percentile_75_in_range = (
                ColumnPercentile75InRangeCheckSpec.from_dict(
                    _daily_partition_percentile_75_in_range
                )
            )

        _daily_partition_percentile_90_in_range = d.pop(
            "daily_partition_percentile_90_in_range", UNSET
        )
        daily_partition_percentile_90_in_range: Union[
            Unset, ColumnPercentile90InRangeCheckSpec
        ]
        if isinstance(_daily_partition_percentile_90_in_range, Unset):
            daily_partition_percentile_90_in_range = UNSET
        else:
            daily_partition_percentile_90_in_range = (
                ColumnPercentile90InRangeCheckSpec.from_dict(
                    _daily_partition_percentile_90_in_range
                )
            )

        _daily_partition_sample_stddev_in_range = d.pop(
            "daily_partition_sample_stddev_in_range", UNSET
        )
        daily_partition_sample_stddev_in_range: Union[
            Unset, ColumnSampleStddevInRangeCheckSpec
        ]
        if isinstance(_daily_partition_sample_stddev_in_range, Unset):
            daily_partition_sample_stddev_in_range = UNSET
        else:
            daily_partition_sample_stddev_in_range = (
                ColumnSampleStddevInRangeCheckSpec.from_dict(
                    _daily_partition_sample_stddev_in_range
                )
            )

        _daily_partition_population_stddev_in_range = d.pop(
            "daily_partition_population_stddev_in_range", UNSET
        )
        daily_partition_population_stddev_in_range: Union[
            Unset, ColumnPopulationStddevInRangeCheckSpec
        ]
        if isinstance(_daily_partition_population_stddev_in_range, Unset):
            daily_partition_population_stddev_in_range = UNSET
        else:
            daily_partition_population_stddev_in_range = (
                ColumnPopulationStddevInRangeCheckSpec.from_dict(
                    _daily_partition_population_stddev_in_range
                )
            )

        _daily_partition_sample_variance_in_range = d.pop(
            "daily_partition_sample_variance_in_range", UNSET
        )
        daily_partition_sample_variance_in_range: Union[
            Unset, ColumnSampleVarianceInRangeCheckSpec
        ]
        if isinstance(_daily_partition_sample_variance_in_range, Unset):
            daily_partition_sample_variance_in_range = UNSET
        else:
            daily_partition_sample_variance_in_range = (
                ColumnSampleVarianceInRangeCheckSpec.from_dict(
                    _daily_partition_sample_variance_in_range
                )
            )

        _daily_partition_population_variance_in_range = d.pop(
            "daily_partition_population_variance_in_range", UNSET
        )
        daily_partition_population_variance_in_range: Union[
            Unset, ColumnPopulationVarianceInRangeCheckSpec
        ]
        if isinstance(_daily_partition_population_variance_in_range, Unset):
            daily_partition_population_variance_in_range = UNSET
        else:
            daily_partition_population_variance_in_range = (
                ColumnPopulationVarianceInRangeCheckSpec.from_dict(
                    _daily_partition_population_variance_in_range
                )
            )

        _daily_partition_invalid_latitude = d.pop(
            "daily_partition_invalid_latitude", UNSET
        )
        daily_partition_invalid_latitude: Union[
            Unset, ColumnInvalidLatitudeCountCheckSpec
        ]
        if isinstance(_daily_partition_invalid_latitude, Unset):
            daily_partition_invalid_latitude = UNSET
        else:
            daily_partition_invalid_latitude = (
                ColumnInvalidLatitudeCountCheckSpec.from_dict(
                    _daily_partition_invalid_latitude
                )
            )

        _daily_partition_valid_latitude_percent = d.pop(
            "daily_partition_valid_latitude_percent", UNSET
        )
        daily_partition_valid_latitude_percent: Union[
            Unset, ColumnValidLatitudePercentCheckSpec
        ]
        if isinstance(_daily_partition_valid_latitude_percent, Unset):
            daily_partition_valid_latitude_percent = UNSET
        else:
            daily_partition_valid_latitude_percent = (
                ColumnValidLatitudePercentCheckSpec.from_dict(
                    _daily_partition_valid_latitude_percent
                )
            )

        _daily_partition_invalid_longitude = d.pop(
            "daily_partition_invalid_longitude", UNSET
        )
        daily_partition_invalid_longitude: Union[
            Unset, ColumnInvalidLongitudeCountCheckSpec
        ]
        if isinstance(_daily_partition_invalid_longitude, Unset):
            daily_partition_invalid_longitude = UNSET
        else:
            daily_partition_invalid_longitude = (
                ColumnInvalidLongitudeCountCheckSpec.from_dict(
                    _daily_partition_invalid_longitude
                )
            )

        _daily_partition_valid_longitude_percent = d.pop(
            "daily_partition_valid_longitude_percent", UNSET
        )
        daily_partition_valid_longitude_percent: Union[
            Unset, ColumnValidLongitudePercentCheckSpec
        ]
        if isinstance(_daily_partition_valid_longitude_percent, Unset):
            daily_partition_valid_longitude_percent = UNSET
        else:
            daily_partition_valid_longitude_percent = (
                ColumnValidLongitudePercentCheckSpec.from_dict(
                    _daily_partition_valid_longitude_percent
                )
            )

        _daily_partition_non_negative_values = d.pop(
            "daily_partition_non_negative_values", UNSET
        )
        daily_partition_non_negative_values: Union[
            Unset, ColumnNonNegativeCountCheckSpec
        ]
        if isinstance(_daily_partition_non_negative_values, Unset):
            daily_partition_non_negative_values = UNSET
        else:
            daily_partition_non_negative_values = (
                ColumnNonNegativeCountCheckSpec.from_dict(
                    _daily_partition_non_negative_values
                )
            )

        _daily_partition_non_negative_values_percent = d.pop(
            "daily_partition_non_negative_values_percent", UNSET
        )
        daily_partition_non_negative_values_percent: Union[
            Unset, ColumnNonNegativePercentCheckSpec
        ]
        if isinstance(_daily_partition_non_negative_values_percent, Unset):
            daily_partition_non_negative_values_percent = UNSET
        else:
            daily_partition_non_negative_values_percent = (
                ColumnNonNegativePercentCheckSpec.from_dict(
                    _daily_partition_non_negative_values_percent
                )
            )

        column_numeric_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_number_below_min_value=daily_partition_number_below_min_value,
            daily_partition_number_above_max_value=daily_partition_number_above_max_value,
            daily_partition_negative_values=daily_partition_negative_values,
            daily_partition_negative_values_percent=daily_partition_negative_values_percent,
            daily_partition_number_below_min_value_percent=daily_partition_number_below_min_value_percent,
            daily_partition_number_above_max_value_percent=daily_partition_number_above_max_value_percent,
            daily_partition_number_in_range_percent=daily_partition_number_in_range_percent,
            daily_partition_integer_in_range_percent=daily_partition_integer_in_range_percent,
            daily_partition_min_in_range=daily_partition_min_in_range,
            daily_partition_max_in_range=daily_partition_max_in_range,
            daily_partition_sum_in_range=daily_partition_sum_in_range,
            daily_partition_mean_in_range=daily_partition_mean_in_range,
            daily_partition_median_in_range=daily_partition_median_in_range,
            daily_partition_percentile_in_range=daily_partition_percentile_in_range,
            daily_partition_percentile_10_in_range=daily_partition_percentile_10_in_range,
            daily_partition_percentile_25_in_range=daily_partition_percentile_25_in_range,
            daily_partition_percentile_75_in_range=daily_partition_percentile_75_in_range,
            daily_partition_percentile_90_in_range=daily_partition_percentile_90_in_range,
            daily_partition_sample_stddev_in_range=daily_partition_sample_stddev_in_range,
            daily_partition_population_stddev_in_range=daily_partition_population_stddev_in_range,
            daily_partition_sample_variance_in_range=daily_partition_sample_variance_in_range,
            daily_partition_population_variance_in_range=daily_partition_population_variance_in_range,
            daily_partition_invalid_latitude=daily_partition_invalid_latitude,
            daily_partition_valid_latitude_percent=daily_partition_valid_latitude_percent,
            daily_partition_invalid_longitude=daily_partition_invalid_longitude,
            daily_partition_valid_longitude_percent=daily_partition_valid_longitude_percent,
            daily_partition_non_negative_values=daily_partition_non_negative_values,
            daily_partition_non_negative_values_percent=daily_partition_non_negative_values_percent,
        )

        column_numeric_daily_partitioned_checks_spec.additional_properties = d
        return column_numeric_daily_partitioned_checks_spec

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
