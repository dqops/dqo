from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_population_stddev_in_range_check_spec import ColumnPopulationStddevInRangeCheckSpec
  from ..models.column_value_below_min_value_percent_check_spec import ColumnValueBelowMinValuePercentCheckSpec
  from ..models.column_value_above_max_value_percent_check_spec import ColumnValueAboveMaxValuePercentCheckSpec
  from ..models.column_number_value_in_set_percent_check_spec import ColumnNumberValueInSetPercentCheckSpec
  from ..models.column_negative_count_check_spec import ColumnNegativeCountCheckSpec
  from ..models.column_valid_latitude_percent_check_spec import ColumnValidLatitudePercentCheckSpec
  from ..models.column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
  from ..models.column_sample_stddev_in_range_check_spec import ColumnSampleStddevInRangeCheckSpec
  from ..models.column_value_below_min_value_count_check_spec import ColumnValueBelowMinValueCountCheckSpec
  from ..models.column_percentile_75_in_range_check_spec import ColumnPercentile75InRangeCheckSpec
  from ..models.column_value_above_max_value_count_check_spec import ColumnValueAboveMaxValueCountCheckSpec
  from ..models.column_population_variance_in_range_check_spec import ColumnPopulationVarianceInRangeCheckSpec
  from ..models.column_percentile_25_in_range_check_spec import ColumnPercentile25InRangeCheckSpec
  from ..models.column_invalid_longitude_count_check_spec import ColumnInvalidLongitudeCountCheckSpec
  from ..models.column_values_in_range_numeric_percent_check_spec import ColumnValuesInRangeNumericPercentCheckSpec
  from ..models.column_sample_variance_in_range_check_spec import ColumnSampleVarianceInRangeCheckSpec
  from ..models.column_invalid_latitude_count_check_spec import ColumnInvalidLatitudeCountCheckSpec
  from ..models.column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
  from ..models.column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
  from ..models.column_expected_numbers_in_use_count_check_spec import ColumnExpectedNumbersInUseCountCheckSpec
  from ..models.column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
  from ..models.column_non_negative_count_check_spec import ColumnNonNegativeCountCheckSpec
  from ..models.column_percentile_10_in_range_check_spec import ColumnPercentile10InRangeCheckSpec
  from ..models.column_negative_percent_check_spec import ColumnNegativePercentCheckSpec
  from ..models.column_valid_longitude_percent_check_spec import ColumnValidLongitudePercentCheckSpec
  from ..models.column_percentile_in_range_check_spec import ColumnPercentileInRangeCheckSpec
  from ..models.column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
  from ..models.column_percentile_90_in_range_check_spec import ColumnPercentile90InRangeCheckSpec
  from ..models.column_values_in_range_integers_percent_check_spec import ColumnValuesInRangeIntegersPercentCheckSpec
  from ..models.column_non_negative_percent_check_spec import ColumnNonNegativePercentCheckSpec





T = TypeVar("T", bound="ColumnNumericProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnNumericProfilingChecksSpec:
    """ 
        Attributes:
            negative_count (Union[Unset, ColumnNegativeCountCheckSpec]):
            negative_percent (Union[Unset, ColumnNegativePercentCheckSpec]):
            non_negative_count (Union[Unset, ColumnNonNegativeCountCheckSpec]):
            non_negative_percent (Union[Unset, ColumnNonNegativePercentCheckSpec]):
            expected_numbers_in_use_count (Union[Unset, ColumnExpectedNumbersInUseCountCheckSpec]):
            number_value_in_set_percent (Union[Unset, ColumnNumberValueInSetPercentCheckSpec]):
            values_in_range_numeric_percent (Union[Unset, ColumnValuesInRangeNumericPercentCheckSpec]):
            values_in_range_integers_percent (Union[Unset, ColumnValuesInRangeIntegersPercentCheckSpec]):
            value_below_min_value_count (Union[Unset, ColumnValueBelowMinValueCountCheckSpec]):
            value_below_min_value_percent (Union[Unset, ColumnValueBelowMinValuePercentCheckSpec]):
            value_above_max_value_count (Union[Unset, ColumnValueAboveMaxValueCountCheckSpec]):
            value_above_max_value_percent (Union[Unset, ColumnValueAboveMaxValuePercentCheckSpec]):
            max_in_range (Union[Unset, ColumnMaxInRangeCheckSpec]):
            min_in_range (Union[Unset, ColumnMinInRangeCheckSpec]):
            mean_in_range (Union[Unset, ColumnMeanInRangeCheckSpec]):
            percentile_in_range (Union[Unset, ColumnPercentileInRangeCheckSpec]):
            median_in_range (Union[Unset, ColumnMedianInRangeCheckSpec]):
            percentile_10_in_range (Union[Unset, ColumnPercentile10InRangeCheckSpec]):
            percentile_25_in_range (Union[Unset, ColumnPercentile25InRangeCheckSpec]):
            percentile_75_in_range (Union[Unset, ColumnPercentile75InRangeCheckSpec]):
            percentile_90_in_range (Union[Unset, ColumnPercentile90InRangeCheckSpec]):
            sample_stddev_in_range (Union[Unset, ColumnSampleStddevInRangeCheckSpec]):
            population_stddev_in_range (Union[Unset, ColumnPopulationStddevInRangeCheckSpec]):
            sample_variance_in_range (Union[Unset, ColumnSampleVarianceInRangeCheckSpec]):
            population_variance_in_range (Union[Unset, ColumnPopulationVarianceInRangeCheckSpec]):
            sum_in_range (Union[Unset, ColumnSumInRangeCheckSpec]):
            invalid_latitude_count (Union[Unset, ColumnInvalidLatitudeCountCheckSpec]):
            valid_latitude_percent (Union[Unset, ColumnValidLatitudePercentCheckSpec]):
            invalid_longitude_count (Union[Unset, ColumnInvalidLongitudeCountCheckSpec]):
            valid_longitude_percent (Union[Unset, ColumnValidLongitudePercentCheckSpec]):
     """

    negative_count: Union[Unset, 'ColumnNegativeCountCheckSpec'] = UNSET
    negative_percent: Union[Unset, 'ColumnNegativePercentCheckSpec'] = UNSET
    non_negative_count: Union[Unset, 'ColumnNonNegativeCountCheckSpec'] = UNSET
    non_negative_percent: Union[Unset, 'ColumnNonNegativePercentCheckSpec'] = UNSET
    expected_numbers_in_use_count: Union[Unset, 'ColumnExpectedNumbersInUseCountCheckSpec'] = UNSET
    number_value_in_set_percent: Union[Unset, 'ColumnNumberValueInSetPercentCheckSpec'] = UNSET
    values_in_range_numeric_percent: Union[Unset, 'ColumnValuesInRangeNumericPercentCheckSpec'] = UNSET
    values_in_range_integers_percent: Union[Unset, 'ColumnValuesInRangeIntegersPercentCheckSpec'] = UNSET
    value_below_min_value_count: Union[Unset, 'ColumnValueBelowMinValueCountCheckSpec'] = UNSET
    value_below_min_value_percent: Union[Unset, 'ColumnValueBelowMinValuePercentCheckSpec'] = UNSET
    value_above_max_value_count: Union[Unset, 'ColumnValueAboveMaxValueCountCheckSpec'] = UNSET
    value_above_max_value_percent: Union[Unset, 'ColumnValueAboveMaxValuePercentCheckSpec'] = UNSET
    max_in_range: Union[Unset, 'ColumnMaxInRangeCheckSpec'] = UNSET
    min_in_range: Union[Unset, 'ColumnMinInRangeCheckSpec'] = UNSET
    mean_in_range: Union[Unset, 'ColumnMeanInRangeCheckSpec'] = UNSET
    percentile_in_range: Union[Unset, 'ColumnPercentileInRangeCheckSpec'] = UNSET
    median_in_range: Union[Unset, 'ColumnMedianInRangeCheckSpec'] = UNSET
    percentile_10_in_range: Union[Unset, 'ColumnPercentile10InRangeCheckSpec'] = UNSET
    percentile_25_in_range: Union[Unset, 'ColumnPercentile25InRangeCheckSpec'] = UNSET
    percentile_75_in_range: Union[Unset, 'ColumnPercentile75InRangeCheckSpec'] = UNSET
    percentile_90_in_range: Union[Unset, 'ColumnPercentile90InRangeCheckSpec'] = UNSET
    sample_stddev_in_range: Union[Unset, 'ColumnSampleStddevInRangeCheckSpec'] = UNSET
    population_stddev_in_range: Union[Unset, 'ColumnPopulationStddevInRangeCheckSpec'] = UNSET
    sample_variance_in_range: Union[Unset, 'ColumnSampleVarianceInRangeCheckSpec'] = UNSET
    population_variance_in_range: Union[Unset, 'ColumnPopulationVarianceInRangeCheckSpec'] = UNSET
    sum_in_range: Union[Unset, 'ColumnSumInRangeCheckSpec'] = UNSET
    invalid_latitude_count: Union[Unset, 'ColumnInvalidLatitudeCountCheckSpec'] = UNSET
    valid_latitude_percent: Union[Unset, 'ColumnValidLatitudePercentCheckSpec'] = UNSET
    invalid_longitude_count: Union[Unset, 'ColumnInvalidLongitudeCountCheckSpec'] = UNSET
    valid_longitude_percent: Union[Unset, 'ColumnValidLongitudePercentCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_population_stddev_in_range_check_spec import ColumnPopulationStddevInRangeCheckSpec
        from ..models.column_value_below_min_value_percent_check_spec import ColumnValueBelowMinValuePercentCheckSpec
        from ..models.column_value_above_max_value_percent_check_spec import ColumnValueAboveMaxValuePercentCheckSpec
        from ..models.column_number_value_in_set_percent_check_spec import ColumnNumberValueInSetPercentCheckSpec
        from ..models.column_negative_count_check_spec import ColumnNegativeCountCheckSpec
        from ..models.column_valid_latitude_percent_check_spec import ColumnValidLatitudePercentCheckSpec
        from ..models.column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
        from ..models.column_sample_stddev_in_range_check_spec import ColumnSampleStddevInRangeCheckSpec
        from ..models.column_value_below_min_value_count_check_spec import ColumnValueBelowMinValueCountCheckSpec
        from ..models.column_percentile_75_in_range_check_spec import ColumnPercentile75InRangeCheckSpec
        from ..models.column_value_above_max_value_count_check_spec import ColumnValueAboveMaxValueCountCheckSpec
        from ..models.column_population_variance_in_range_check_spec import ColumnPopulationVarianceInRangeCheckSpec
        from ..models.column_percentile_25_in_range_check_spec import ColumnPercentile25InRangeCheckSpec
        from ..models.column_invalid_longitude_count_check_spec import ColumnInvalidLongitudeCountCheckSpec
        from ..models.column_values_in_range_numeric_percent_check_spec import ColumnValuesInRangeNumericPercentCheckSpec
        from ..models.column_sample_variance_in_range_check_spec import ColumnSampleVarianceInRangeCheckSpec
        from ..models.column_invalid_latitude_count_check_spec import ColumnInvalidLatitudeCountCheckSpec
        from ..models.column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
        from ..models.column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
        from ..models.column_expected_numbers_in_use_count_check_spec import ColumnExpectedNumbersInUseCountCheckSpec
        from ..models.column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
        from ..models.column_non_negative_count_check_spec import ColumnNonNegativeCountCheckSpec
        from ..models.column_percentile_10_in_range_check_spec import ColumnPercentile10InRangeCheckSpec
        from ..models.column_negative_percent_check_spec import ColumnNegativePercentCheckSpec
        from ..models.column_valid_longitude_percent_check_spec import ColumnValidLongitudePercentCheckSpec
        from ..models.column_percentile_in_range_check_spec import ColumnPercentileInRangeCheckSpec
        from ..models.column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
        from ..models.column_percentile_90_in_range_check_spec import ColumnPercentile90InRangeCheckSpec
        from ..models.column_values_in_range_integers_percent_check_spec import ColumnValuesInRangeIntegersPercentCheckSpec
        from ..models.column_non_negative_percent_check_spec import ColumnNonNegativePercentCheckSpec
        negative_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.negative_count, Unset):
            negative_count = self.negative_count.to_dict()

        negative_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.negative_percent, Unset):
            negative_percent = self.negative_percent.to_dict()

        non_negative_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.non_negative_count, Unset):
            non_negative_count = self.non_negative_count.to_dict()

        non_negative_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.non_negative_percent, Unset):
            non_negative_percent = self.non_negative_percent.to_dict()

        expected_numbers_in_use_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.expected_numbers_in_use_count, Unset):
            expected_numbers_in_use_count = self.expected_numbers_in_use_count.to_dict()

        number_value_in_set_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.number_value_in_set_percent, Unset):
            number_value_in_set_percent = self.number_value_in_set_percent.to_dict()

        values_in_range_numeric_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.values_in_range_numeric_percent, Unset):
            values_in_range_numeric_percent = self.values_in_range_numeric_percent.to_dict()

        values_in_range_integers_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.values_in_range_integers_percent, Unset):
            values_in_range_integers_percent = self.values_in_range_integers_percent.to_dict()

        value_below_min_value_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.value_below_min_value_count, Unset):
            value_below_min_value_count = self.value_below_min_value_count.to_dict()

        value_below_min_value_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.value_below_min_value_percent, Unset):
            value_below_min_value_percent = self.value_below_min_value_percent.to_dict()

        value_above_max_value_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.value_above_max_value_count, Unset):
            value_above_max_value_count = self.value_above_max_value_count.to_dict()

        value_above_max_value_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.value_above_max_value_percent, Unset):
            value_above_max_value_percent = self.value_above_max_value_percent.to_dict()

        max_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.max_in_range, Unset):
            max_in_range = self.max_in_range.to_dict()

        min_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.min_in_range, Unset):
            min_in_range = self.min_in_range.to_dict()

        mean_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_in_range, Unset):
            mean_in_range = self.mean_in_range.to_dict()

        percentile_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.percentile_in_range, Unset):
            percentile_in_range = self.percentile_in_range.to_dict()

        median_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_in_range, Unset):
            median_in_range = self.median_in_range.to_dict()

        percentile_10_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.percentile_10_in_range, Unset):
            percentile_10_in_range = self.percentile_10_in_range.to_dict()

        percentile_25_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.percentile_25_in_range, Unset):
            percentile_25_in_range = self.percentile_25_in_range.to_dict()

        percentile_75_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.percentile_75_in_range, Unset):
            percentile_75_in_range = self.percentile_75_in_range.to_dict()

        percentile_90_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.percentile_90_in_range, Unset):
            percentile_90_in_range = self.percentile_90_in_range.to_dict()

        sample_stddev_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sample_stddev_in_range, Unset):
            sample_stddev_in_range = self.sample_stddev_in_range.to_dict()

        population_stddev_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.population_stddev_in_range, Unset):
            population_stddev_in_range = self.population_stddev_in_range.to_dict()

        sample_variance_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sample_variance_in_range, Unset):
            sample_variance_in_range = self.sample_variance_in_range.to_dict()

        population_variance_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.population_variance_in_range, Unset):
            population_variance_in_range = self.population_variance_in_range.to_dict()

        sum_in_range: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_in_range, Unset):
            sum_in_range = self.sum_in_range.to_dict()

        invalid_latitude_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.invalid_latitude_count, Unset):
            invalid_latitude_count = self.invalid_latitude_count.to_dict()

        valid_latitude_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_latitude_percent, Unset):
            valid_latitude_percent = self.valid_latitude_percent.to_dict()

        invalid_longitude_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.invalid_longitude_count, Unset):
            invalid_longitude_count = self.invalid_longitude_count.to_dict()

        valid_longitude_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.valid_longitude_percent, Unset):
            valid_longitude_percent = self.valid_longitude_percent.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if negative_count is not UNSET:
            field_dict["negative_count"] = negative_count
        if negative_percent is not UNSET:
            field_dict["negative_percent"] = negative_percent
        if non_negative_count is not UNSET:
            field_dict["non_negative_count"] = non_negative_count
        if non_negative_percent is not UNSET:
            field_dict["non_negative_percent"] = non_negative_percent
        if expected_numbers_in_use_count is not UNSET:
            field_dict["expected_numbers_in_use_count"] = expected_numbers_in_use_count
        if number_value_in_set_percent is not UNSET:
            field_dict["number_value_in_set_percent"] = number_value_in_set_percent
        if values_in_range_numeric_percent is not UNSET:
            field_dict["values_in_range_numeric_percent"] = values_in_range_numeric_percent
        if values_in_range_integers_percent is not UNSET:
            field_dict["values_in_range_integers_percent"] = values_in_range_integers_percent
        if value_below_min_value_count is not UNSET:
            field_dict["value_below_min_value_count"] = value_below_min_value_count
        if value_below_min_value_percent is not UNSET:
            field_dict["value_below_min_value_percent"] = value_below_min_value_percent
        if value_above_max_value_count is not UNSET:
            field_dict["value_above_max_value_count"] = value_above_max_value_count
        if value_above_max_value_percent is not UNSET:
            field_dict["value_above_max_value_percent"] = value_above_max_value_percent
        if max_in_range is not UNSET:
            field_dict["max_in_range"] = max_in_range
        if min_in_range is not UNSET:
            field_dict["min_in_range"] = min_in_range
        if mean_in_range is not UNSET:
            field_dict["mean_in_range"] = mean_in_range
        if percentile_in_range is not UNSET:
            field_dict["percentile_in_range"] = percentile_in_range
        if median_in_range is not UNSET:
            field_dict["median_in_range"] = median_in_range
        if percentile_10_in_range is not UNSET:
            field_dict["percentile_10_in_range"] = percentile_10_in_range
        if percentile_25_in_range is not UNSET:
            field_dict["percentile_25_in_range"] = percentile_25_in_range
        if percentile_75_in_range is not UNSET:
            field_dict["percentile_75_in_range"] = percentile_75_in_range
        if percentile_90_in_range is not UNSET:
            field_dict["percentile_90_in_range"] = percentile_90_in_range
        if sample_stddev_in_range is not UNSET:
            field_dict["sample_stddev_in_range"] = sample_stddev_in_range
        if population_stddev_in_range is not UNSET:
            field_dict["population_stddev_in_range"] = population_stddev_in_range
        if sample_variance_in_range is not UNSET:
            field_dict["sample_variance_in_range"] = sample_variance_in_range
        if population_variance_in_range is not UNSET:
            field_dict["population_variance_in_range"] = population_variance_in_range
        if sum_in_range is not UNSET:
            field_dict["sum_in_range"] = sum_in_range
        if invalid_latitude_count is not UNSET:
            field_dict["invalid_latitude_count"] = invalid_latitude_count
        if valid_latitude_percent is not UNSET:
            field_dict["valid_latitude_percent"] = valid_latitude_percent
        if invalid_longitude_count is not UNSET:
            field_dict["invalid_longitude_count"] = invalid_longitude_count
        if valid_longitude_percent is not UNSET:
            field_dict["valid_longitude_percent"] = valid_longitude_percent

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_population_stddev_in_range_check_spec import ColumnPopulationStddevInRangeCheckSpec
        from ..models.column_value_below_min_value_percent_check_spec import ColumnValueBelowMinValuePercentCheckSpec
        from ..models.column_value_above_max_value_percent_check_spec import ColumnValueAboveMaxValuePercentCheckSpec
        from ..models.column_number_value_in_set_percent_check_spec import ColumnNumberValueInSetPercentCheckSpec
        from ..models.column_negative_count_check_spec import ColumnNegativeCountCheckSpec
        from ..models.column_valid_latitude_percent_check_spec import ColumnValidLatitudePercentCheckSpec
        from ..models.column_sum_in_range_check_spec import ColumnSumInRangeCheckSpec
        from ..models.column_sample_stddev_in_range_check_spec import ColumnSampleStddevInRangeCheckSpec
        from ..models.column_value_below_min_value_count_check_spec import ColumnValueBelowMinValueCountCheckSpec
        from ..models.column_percentile_75_in_range_check_spec import ColumnPercentile75InRangeCheckSpec
        from ..models.column_value_above_max_value_count_check_spec import ColumnValueAboveMaxValueCountCheckSpec
        from ..models.column_population_variance_in_range_check_spec import ColumnPopulationVarianceInRangeCheckSpec
        from ..models.column_percentile_25_in_range_check_spec import ColumnPercentile25InRangeCheckSpec
        from ..models.column_invalid_longitude_count_check_spec import ColumnInvalidLongitudeCountCheckSpec
        from ..models.column_values_in_range_numeric_percent_check_spec import ColumnValuesInRangeNumericPercentCheckSpec
        from ..models.column_sample_variance_in_range_check_spec import ColumnSampleVarianceInRangeCheckSpec
        from ..models.column_invalid_latitude_count_check_spec import ColumnInvalidLatitudeCountCheckSpec
        from ..models.column_median_in_range_check_spec import ColumnMedianInRangeCheckSpec
        from ..models.column_mean_in_range_check_spec import ColumnMeanInRangeCheckSpec
        from ..models.column_expected_numbers_in_use_count_check_spec import ColumnExpectedNumbersInUseCountCheckSpec
        from ..models.column_min_in_range_check_spec import ColumnMinInRangeCheckSpec
        from ..models.column_non_negative_count_check_spec import ColumnNonNegativeCountCheckSpec
        from ..models.column_percentile_10_in_range_check_spec import ColumnPercentile10InRangeCheckSpec
        from ..models.column_negative_percent_check_spec import ColumnNegativePercentCheckSpec
        from ..models.column_valid_longitude_percent_check_spec import ColumnValidLongitudePercentCheckSpec
        from ..models.column_percentile_in_range_check_spec import ColumnPercentileInRangeCheckSpec
        from ..models.column_max_in_range_check_spec import ColumnMaxInRangeCheckSpec
        from ..models.column_percentile_90_in_range_check_spec import ColumnPercentile90InRangeCheckSpec
        from ..models.column_values_in_range_integers_percent_check_spec import ColumnValuesInRangeIntegersPercentCheckSpec
        from ..models.column_non_negative_percent_check_spec import ColumnNonNegativePercentCheckSpec
        d = src_dict.copy()
        _negative_count = d.pop("negative_count", UNSET)
        negative_count: Union[Unset, ColumnNegativeCountCheckSpec]
        if isinstance(_negative_count,  Unset):
            negative_count = UNSET
        else:
            negative_count = ColumnNegativeCountCheckSpec.from_dict(_negative_count)




        _negative_percent = d.pop("negative_percent", UNSET)
        negative_percent: Union[Unset, ColumnNegativePercentCheckSpec]
        if isinstance(_negative_percent,  Unset):
            negative_percent = UNSET
        else:
            negative_percent = ColumnNegativePercentCheckSpec.from_dict(_negative_percent)




        _non_negative_count = d.pop("non_negative_count", UNSET)
        non_negative_count: Union[Unset, ColumnNonNegativeCountCheckSpec]
        if isinstance(_non_negative_count,  Unset):
            non_negative_count = UNSET
        else:
            non_negative_count = ColumnNonNegativeCountCheckSpec.from_dict(_non_negative_count)




        _non_negative_percent = d.pop("non_negative_percent", UNSET)
        non_negative_percent: Union[Unset, ColumnNonNegativePercentCheckSpec]
        if isinstance(_non_negative_percent,  Unset):
            non_negative_percent = UNSET
        else:
            non_negative_percent = ColumnNonNegativePercentCheckSpec.from_dict(_non_negative_percent)




        _expected_numbers_in_use_count = d.pop("expected_numbers_in_use_count", UNSET)
        expected_numbers_in_use_count: Union[Unset, ColumnExpectedNumbersInUseCountCheckSpec]
        if isinstance(_expected_numbers_in_use_count,  Unset):
            expected_numbers_in_use_count = UNSET
        else:
            expected_numbers_in_use_count = ColumnExpectedNumbersInUseCountCheckSpec.from_dict(_expected_numbers_in_use_count)




        _number_value_in_set_percent = d.pop("number_value_in_set_percent", UNSET)
        number_value_in_set_percent: Union[Unset, ColumnNumberValueInSetPercentCheckSpec]
        if isinstance(_number_value_in_set_percent,  Unset):
            number_value_in_set_percent = UNSET
        else:
            number_value_in_set_percent = ColumnNumberValueInSetPercentCheckSpec.from_dict(_number_value_in_set_percent)




        _values_in_range_numeric_percent = d.pop("values_in_range_numeric_percent", UNSET)
        values_in_range_numeric_percent: Union[Unset, ColumnValuesInRangeNumericPercentCheckSpec]
        if isinstance(_values_in_range_numeric_percent,  Unset):
            values_in_range_numeric_percent = UNSET
        else:
            values_in_range_numeric_percent = ColumnValuesInRangeNumericPercentCheckSpec.from_dict(_values_in_range_numeric_percent)




        _values_in_range_integers_percent = d.pop("values_in_range_integers_percent", UNSET)
        values_in_range_integers_percent: Union[Unset, ColumnValuesInRangeIntegersPercentCheckSpec]
        if isinstance(_values_in_range_integers_percent,  Unset):
            values_in_range_integers_percent = UNSET
        else:
            values_in_range_integers_percent = ColumnValuesInRangeIntegersPercentCheckSpec.from_dict(_values_in_range_integers_percent)




        _value_below_min_value_count = d.pop("value_below_min_value_count", UNSET)
        value_below_min_value_count: Union[Unset, ColumnValueBelowMinValueCountCheckSpec]
        if isinstance(_value_below_min_value_count,  Unset):
            value_below_min_value_count = UNSET
        else:
            value_below_min_value_count = ColumnValueBelowMinValueCountCheckSpec.from_dict(_value_below_min_value_count)




        _value_below_min_value_percent = d.pop("value_below_min_value_percent", UNSET)
        value_below_min_value_percent: Union[Unset, ColumnValueBelowMinValuePercentCheckSpec]
        if isinstance(_value_below_min_value_percent,  Unset):
            value_below_min_value_percent = UNSET
        else:
            value_below_min_value_percent = ColumnValueBelowMinValuePercentCheckSpec.from_dict(_value_below_min_value_percent)




        _value_above_max_value_count = d.pop("value_above_max_value_count", UNSET)
        value_above_max_value_count: Union[Unset, ColumnValueAboveMaxValueCountCheckSpec]
        if isinstance(_value_above_max_value_count,  Unset):
            value_above_max_value_count = UNSET
        else:
            value_above_max_value_count = ColumnValueAboveMaxValueCountCheckSpec.from_dict(_value_above_max_value_count)




        _value_above_max_value_percent = d.pop("value_above_max_value_percent", UNSET)
        value_above_max_value_percent: Union[Unset, ColumnValueAboveMaxValuePercentCheckSpec]
        if isinstance(_value_above_max_value_percent,  Unset):
            value_above_max_value_percent = UNSET
        else:
            value_above_max_value_percent = ColumnValueAboveMaxValuePercentCheckSpec.from_dict(_value_above_max_value_percent)




        _max_in_range = d.pop("max_in_range", UNSET)
        max_in_range: Union[Unset, ColumnMaxInRangeCheckSpec]
        if isinstance(_max_in_range,  Unset):
            max_in_range = UNSET
        else:
            max_in_range = ColumnMaxInRangeCheckSpec.from_dict(_max_in_range)




        _min_in_range = d.pop("min_in_range", UNSET)
        min_in_range: Union[Unset, ColumnMinInRangeCheckSpec]
        if isinstance(_min_in_range,  Unset):
            min_in_range = UNSET
        else:
            min_in_range = ColumnMinInRangeCheckSpec.from_dict(_min_in_range)




        _mean_in_range = d.pop("mean_in_range", UNSET)
        mean_in_range: Union[Unset, ColumnMeanInRangeCheckSpec]
        if isinstance(_mean_in_range,  Unset):
            mean_in_range = UNSET
        else:
            mean_in_range = ColumnMeanInRangeCheckSpec.from_dict(_mean_in_range)




        _percentile_in_range = d.pop("percentile_in_range", UNSET)
        percentile_in_range: Union[Unset, ColumnPercentileInRangeCheckSpec]
        if isinstance(_percentile_in_range,  Unset):
            percentile_in_range = UNSET
        else:
            percentile_in_range = ColumnPercentileInRangeCheckSpec.from_dict(_percentile_in_range)




        _median_in_range = d.pop("median_in_range", UNSET)
        median_in_range: Union[Unset, ColumnMedianInRangeCheckSpec]
        if isinstance(_median_in_range,  Unset):
            median_in_range = UNSET
        else:
            median_in_range = ColumnMedianInRangeCheckSpec.from_dict(_median_in_range)




        _percentile_10_in_range = d.pop("percentile_10_in_range", UNSET)
        percentile_10_in_range: Union[Unset, ColumnPercentile10InRangeCheckSpec]
        if isinstance(_percentile_10_in_range,  Unset):
            percentile_10_in_range = UNSET
        else:
            percentile_10_in_range = ColumnPercentile10InRangeCheckSpec.from_dict(_percentile_10_in_range)




        _percentile_25_in_range = d.pop("percentile_25_in_range", UNSET)
        percentile_25_in_range: Union[Unset, ColumnPercentile25InRangeCheckSpec]
        if isinstance(_percentile_25_in_range,  Unset):
            percentile_25_in_range = UNSET
        else:
            percentile_25_in_range = ColumnPercentile25InRangeCheckSpec.from_dict(_percentile_25_in_range)




        _percentile_75_in_range = d.pop("percentile_75_in_range", UNSET)
        percentile_75_in_range: Union[Unset, ColumnPercentile75InRangeCheckSpec]
        if isinstance(_percentile_75_in_range,  Unset):
            percentile_75_in_range = UNSET
        else:
            percentile_75_in_range = ColumnPercentile75InRangeCheckSpec.from_dict(_percentile_75_in_range)




        _percentile_90_in_range = d.pop("percentile_90_in_range", UNSET)
        percentile_90_in_range: Union[Unset, ColumnPercentile90InRangeCheckSpec]
        if isinstance(_percentile_90_in_range,  Unset):
            percentile_90_in_range = UNSET
        else:
            percentile_90_in_range = ColumnPercentile90InRangeCheckSpec.from_dict(_percentile_90_in_range)




        _sample_stddev_in_range = d.pop("sample_stddev_in_range", UNSET)
        sample_stddev_in_range: Union[Unset, ColumnSampleStddevInRangeCheckSpec]
        if isinstance(_sample_stddev_in_range,  Unset):
            sample_stddev_in_range = UNSET
        else:
            sample_stddev_in_range = ColumnSampleStddevInRangeCheckSpec.from_dict(_sample_stddev_in_range)




        _population_stddev_in_range = d.pop("population_stddev_in_range", UNSET)
        population_stddev_in_range: Union[Unset, ColumnPopulationStddevInRangeCheckSpec]
        if isinstance(_population_stddev_in_range,  Unset):
            population_stddev_in_range = UNSET
        else:
            population_stddev_in_range = ColumnPopulationStddevInRangeCheckSpec.from_dict(_population_stddev_in_range)




        _sample_variance_in_range = d.pop("sample_variance_in_range", UNSET)
        sample_variance_in_range: Union[Unset, ColumnSampleVarianceInRangeCheckSpec]
        if isinstance(_sample_variance_in_range,  Unset):
            sample_variance_in_range = UNSET
        else:
            sample_variance_in_range = ColumnSampleVarianceInRangeCheckSpec.from_dict(_sample_variance_in_range)




        _population_variance_in_range = d.pop("population_variance_in_range", UNSET)
        population_variance_in_range: Union[Unset, ColumnPopulationVarianceInRangeCheckSpec]
        if isinstance(_population_variance_in_range,  Unset):
            population_variance_in_range = UNSET
        else:
            population_variance_in_range = ColumnPopulationVarianceInRangeCheckSpec.from_dict(_population_variance_in_range)




        _sum_in_range = d.pop("sum_in_range", UNSET)
        sum_in_range: Union[Unset, ColumnSumInRangeCheckSpec]
        if isinstance(_sum_in_range,  Unset):
            sum_in_range = UNSET
        else:
            sum_in_range = ColumnSumInRangeCheckSpec.from_dict(_sum_in_range)




        _invalid_latitude_count = d.pop("invalid_latitude_count", UNSET)
        invalid_latitude_count: Union[Unset, ColumnInvalidLatitudeCountCheckSpec]
        if isinstance(_invalid_latitude_count,  Unset):
            invalid_latitude_count = UNSET
        else:
            invalid_latitude_count = ColumnInvalidLatitudeCountCheckSpec.from_dict(_invalid_latitude_count)




        _valid_latitude_percent = d.pop("valid_latitude_percent", UNSET)
        valid_latitude_percent: Union[Unset, ColumnValidLatitudePercentCheckSpec]
        if isinstance(_valid_latitude_percent,  Unset):
            valid_latitude_percent = UNSET
        else:
            valid_latitude_percent = ColumnValidLatitudePercentCheckSpec.from_dict(_valid_latitude_percent)




        _invalid_longitude_count = d.pop("invalid_longitude_count", UNSET)
        invalid_longitude_count: Union[Unset, ColumnInvalidLongitudeCountCheckSpec]
        if isinstance(_invalid_longitude_count,  Unset):
            invalid_longitude_count = UNSET
        else:
            invalid_longitude_count = ColumnInvalidLongitudeCountCheckSpec.from_dict(_invalid_longitude_count)




        _valid_longitude_percent = d.pop("valid_longitude_percent", UNSET)
        valid_longitude_percent: Union[Unset, ColumnValidLongitudePercentCheckSpec]
        if isinstance(_valid_longitude_percent,  Unset):
            valid_longitude_percent = UNSET
        else:
            valid_longitude_percent = ColumnValidLongitudePercentCheckSpec.from_dict(_valid_longitude_percent)




        column_numeric_profiling_checks_spec = cls(
            negative_count=negative_count,
            negative_percent=negative_percent,
            non_negative_count=non_negative_count,
            non_negative_percent=non_negative_percent,
            expected_numbers_in_use_count=expected_numbers_in_use_count,
            number_value_in_set_percent=number_value_in_set_percent,
            values_in_range_numeric_percent=values_in_range_numeric_percent,
            values_in_range_integers_percent=values_in_range_integers_percent,
            value_below_min_value_count=value_below_min_value_count,
            value_below_min_value_percent=value_below_min_value_percent,
            value_above_max_value_count=value_above_max_value_count,
            value_above_max_value_percent=value_above_max_value_percent,
            max_in_range=max_in_range,
            min_in_range=min_in_range,
            mean_in_range=mean_in_range,
            percentile_in_range=percentile_in_range,
            median_in_range=median_in_range,
            percentile_10_in_range=percentile_10_in_range,
            percentile_25_in_range=percentile_25_in_range,
            percentile_75_in_range=percentile_75_in_range,
            percentile_90_in_range=percentile_90_in_range,
            sample_stddev_in_range=sample_stddev_in_range,
            population_stddev_in_range=population_stddev_in_range,
            sample_variance_in_range=sample_variance_in_range,
            population_variance_in_range=population_variance_in_range,
            sum_in_range=sum_in_range,
            invalid_latitude_count=invalid_latitude_count,
            valid_latitude_percent=valid_latitude_percent,
            invalid_longitude_count=invalid_longitude_count,
            valid_longitude_percent=valid_longitude_percent,
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
