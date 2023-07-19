from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_anomaly_stationary_median_check_spec import ColumnAnomalyStationaryMedianCheckSpec
  from ..models.column_anomaly_stationary_mean_check_spec import ColumnAnomalyStationaryMeanCheckSpec
  from ..models.column_change_median_since_30_days_check_spec import ColumnChangeMedianSince30DaysCheckSpec
  from ..models.column_change_mean_since_7_days_check_spec import ColumnChangeMeanSince7DaysCheckSpec
  from ..models.column_anomaly_stationary_mean_30_days_check_spec import ColumnAnomalyStationaryMean30DaysCheckSpec
  from ..models.column_anomaly_differencing_sum_30_days_check_spec import ColumnAnomalyDifferencingSum30DaysCheckSpec
  from ..models.column_change_median_since_7_days_check_spec import ColumnChangeMedianSince7DaysCheckSpec
  from ..models.column_anomaly_stationary_median_30_days_check_spec import ColumnAnomalyStationaryMedian30DaysCheckSpec
  from ..models.column_change_mean_since_yesterday_check_spec import ColumnChangeMeanSinceYesterdayCheckSpec
  from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec
  from ..models.column_anomaly_differencing_sum_check_spec import ColumnAnomalyDifferencingSumCheckSpec
  from ..models.column_change_sum_since_yesterday_check_spec import ColumnChangeSumSinceYesterdayCheckSpec
  from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
  from ..models.column_change_median_since_yesterday_check_spec import ColumnChangeMedianSinceYesterdayCheckSpec
  from ..models.column_change_sum_since_30_days_check_spec import ColumnChangeSumSince30DaysCheckSpec
  from ..models.column_change_mean_since_30_days_check_spec import ColumnChangeMeanSince30DaysCheckSpec
  from ..models.column_change_sum_since_7_days_check_spec import ColumnChangeSumSince7DaysCheckSpec
  from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec





T = TypeVar("T", bound="ColumnAnomalyProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAnomalyProfilingChecksSpec:
    """ 
        Attributes:
            mean_change (Union[Unset, ColumnChangeMeanCheckSpec]):
            mean_change_yesterday (Union[Unset, ColumnChangeMeanSinceYesterdayCheckSpec]):
            median_change (Union[Unset, ColumnChangeMedianCheckSpec]):
            median_change_yesterday (Union[Unset, ColumnChangeMedianSinceYesterdayCheckSpec]):
            sum_change (Union[Unset, ColumnChangeSumCheckSpec]):
            sum_change_yesterday (Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]):
            mean_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryMean30DaysCheckSpec]):
            mean_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryMeanCheckSpec]):
            median_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryMedian30DaysCheckSpec]):
            median_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryMedianCheckSpec]):
            sum_anomaly_differencing_30_days (Union[Unset, ColumnAnomalyDifferencingSum30DaysCheckSpec]):
            sum_anomaly_differencing (Union[Unset, ColumnAnomalyDifferencingSumCheckSpec]):
            mean_change_7_days (Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]):
            mean_change_30_days (Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]):
            median_change_7_days (Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]):
            median_change_30_days (Union[Unset, ColumnChangeMedianSince30DaysCheckSpec]):
            sum_change_7_days (Union[Unset, ColumnChangeSumSince7DaysCheckSpec]):
            sum_change_30_days (Union[Unset, ColumnChangeSumSince30DaysCheckSpec]):
     """

    mean_change: Union[Unset, 'ColumnChangeMeanCheckSpec'] = UNSET
    mean_change_yesterday: Union[Unset, 'ColumnChangeMeanSinceYesterdayCheckSpec'] = UNSET
    median_change: Union[Unset, 'ColumnChangeMedianCheckSpec'] = UNSET
    median_change_yesterday: Union[Unset, 'ColumnChangeMedianSinceYesterdayCheckSpec'] = UNSET
    sum_change: Union[Unset, 'ColumnChangeSumCheckSpec'] = UNSET
    sum_change_yesterday: Union[Unset, 'ColumnChangeSumSinceYesterdayCheckSpec'] = UNSET
    mean_anomaly_stationary_30_days: Union[Unset, 'ColumnAnomalyStationaryMean30DaysCheckSpec'] = UNSET
    mean_anomaly_stationary: Union[Unset, 'ColumnAnomalyStationaryMeanCheckSpec'] = UNSET
    median_anomaly_stationary_30_days: Union[Unset, 'ColumnAnomalyStationaryMedian30DaysCheckSpec'] = UNSET
    median_anomaly_stationary: Union[Unset, 'ColumnAnomalyStationaryMedianCheckSpec'] = UNSET
    sum_anomaly_differencing_30_days: Union[Unset, 'ColumnAnomalyDifferencingSum30DaysCheckSpec'] = UNSET
    sum_anomaly_differencing: Union[Unset, 'ColumnAnomalyDifferencingSumCheckSpec'] = UNSET
    mean_change_7_days: Union[Unset, 'ColumnChangeMeanSince7DaysCheckSpec'] = UNSET
    mean_change_30_days: Union[Unset, 'ColumnChangeMeanSince30DaysCheckSpec'] = UNSET
    median_change_7_days: Union[Unset, 'ColumnChangeMedianSince7DaysCheckSpec'] = UNSET
    median_change_30_days: Union[Unset, 'ColumnChangeMedianSince30DaysCheckSpec'] = UNSET
    sum_change_7_days: Union[Unset, 'ColumnChangeSumSince7DaysCheckSpec'] = UNSET
    sum_change_30_days: Union[Unset, 'ColumnChangeSumSince30DaysCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_anomaly_stationary_median_check_spec import ColumnAnomalyStationaryMedianCheckSpec
        from ..models.column_anomaly_stationary_mean_check_spec import ColumnAnomalyStationaryMeanCheckSpec
        from ..models.column_change_median_since_30_days_check_spec import ColumnChangeMedianSince30DaysCheckSpec
        from ..models.column_change_mean_since_7_days_check_spec import ColumnChangeMeanSince7DaysCheckSpec
        from ..models.column_anomaly_stationary_mean_30_days_check_spec import ColumnAnomalyStationaryMean30DaysCheckSpec
        from ..models.column_anomaly_differencing_sum_30_days_check_spec import ColumnAnomalyDifferencingSum30DaysCheckSpec
        from ..models.column_change_median_since_7_days_check_spec import ColumnChangeMedianSince7DaysCheckSpec
        from ..models.column_anomaly_stationary_median_30_days_check_spec import ColumnAnomalyStationaryMedian30DaysCheckSpec
        from ..models.column_change_mean_since_yesterday_check_spec import ColumnChangeMeanSinceYesterdayCheckSpec
        from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec
        from ..models.column_anomaly_differencing_sum_check_spec import ColumnAnomalyDifferencingSumCheckSpec
        from ..models.column_change_sum_since_yesterday_check_spec import ColumnChangeSumSinceYesterdayCheckSpec
        from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
        from ..models.column_change_median_since_yesterday_check_spec import ColumnChangeMedianSinceYesterdayCheckSpec
        from ..models.column_change_sum_since_30_days_check_spec import ColumnChangeSumSince30DaysCheckSpec
        from ..models.column_change_mean_since_30_days_check_spec import ColumnChangeMeanSince30DaysCheckSpec
        from ..models.column_change_sum_since_7_days_check_spec import ColumnChangeSumSince7DaysCheckSpec
        from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec
        mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_change, Unset):
            mean_change = self.mean_change.to_dict()

        mean_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_change_yesterday, Unset):
            mean_change_yesterday = self.mean_change_yesterday.to_dict()

        median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_change, Unset):
            median_change = self.median_change.to_dict()

        median_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_change_yesterday, Unset):
            median_change_yesterday = self.median_change_yesterday.to_dict()

        sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_change, Unset):
            sum_change = self.sum_change.to_dict()

        sum_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_change_yesterday, Unset):
            sum_change_yesterday = self.sum_change_yesterday.to_dict()

        mean_anomaly_stationary_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_anomaly_stationary_30_days, Unset):
            mean_anomaly_stationary_30_days = self.mean_anomaly_stationary_30_days.to_dict()

        mean_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_anomaly_stationary, Unset):
            mean_anomaly_stationary = self.mean_anomaly_stationary.to_dict()

        median_anomaly_stationary_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_anomaly_stationary_30_days, Unset):
            median_anomaly_stationary_30_days = self.median_anomaly_stationary_30_days.to_dict()

        median_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_anomaly_stationary, Unset):
            median_anomaly_stationary = self.median_anomaly_stationary.to_dict()

        sum_anomaly_differencing_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_anomaly_differencing_30_days, Unset):
            sum_anomaly_differencing_30_days = self.sum_anomaly_differencing_30_days.to_dict()

        sum_anomaly_differencing: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_anomaly_differencing, Unset):
            sum_anomaly_differencing = self.sum_anomaly_differencing.to_dict()

        mean_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_change_7_days, Unset):
            mean_change_7_days = self.mean_change_7_days.to_dict()

        mean_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_change_30_days, Unset):
            mean_change_30_days = self.mean_change_30_days.to_dict()

        median_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_change_7_days, Unset):
            median_change_7_days = self.median_change_7_days.to_dict()

        median_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_change_30_days, Unset):
            median_change_30_days = self.median_change_30_days.to_dict()

        sum_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_change_7_days, Unset):
            sum_change_7_days = self.sum_change_7_days.to_dict()

        sum_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_change_30_days, Unset):
            sum_change_30_days = self.sum_change_30_days.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if mean_change is not UNSET:
            field_dict["mean_change"] = mean_change
        if mean_change_yesterday is not UNSET:
            field_dict["mean_change_yesterday"] = mean_change_yesterday
        if median_change is not UNSET:
            field_dict["median_change"] = median_change
        if median_change_yesterday is not UNSET:
            field_dict["median_change_yesterday"] = median_change_yesterday
        if sum_change is not UNSET:
            field_dict["sum_change"] = sum_change
        if sum_change_yesterday is not UNSET:
            field_dict["sum_change_yesterday"] = sum_change_yesterday
        if mean_anomaly_stationary_30_days is not UNSET:
            field_dict["mean_anomaly_stationary_30_days"] = mean_anomaly_stationary_30_days
        if mean_anomaly_stationary is not UNSET:
            field_dict["mean_anomaly_stationary"] = mean_anomaly_stationary
        if median_anomaly_stationary_30_days is not UNSET:
            field_dict["median_anomaly_stationary_30_days"] = median_anomaly_stationary_30_days
        if median_anomaly_stationary is not UNSET:
            field_dict["median_anomaly_stationary"] = median_anomaly_stationary
        if sum_anomaly_differencing_30_days is not UNSET:
            field_dict["sum_anomaly_differencing_30_days"] = sum_anomaly_differencing_30_days
        if sum_anomaly_differencing is not UNSET:
            field_dict["sum_anomaly_differencing"] = sum_anomaly_differencing
        if mean_change_7_days is not UNSET:
            field_dict["mean_change_7_days"] = mean_change_7_days
        if mean_change_30_days is not UNSET:
            field_dict["mean_change_30_days"] = mean_change_30_days
        if median_change_7_days is not UNSET:
            field_dict["median_change_7_days"] = median_change_7_days
        if median_change_30_days is not UNSET:
            field_dict["median_change_30_days"] = median_change_30_days
        if sum_change_7_days is not UNSET:
            field_dict["sum_change_7_days"] = sum_change_7_days
        if sum_change_30_days is not UNSET:
            field_dict["sum_change_30_days"] = sum_change_30_days

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_stationary_median_check_spec import ColumnAnomalyStationaryMedianCheckSpec
        from ..models.column_anomaly_stationary_mean_check_spec import ColumnAnomalyStationaryMeanCheckSpec
        from ..models.column_change_median_since_30_days_check_spec import ColumnChangeMedianSince30DaysCheckSpec
        from ..models.column_change_mean_since_7_days_check_spec import ColumnChangeMeanSince7DaysCheckSpec
        from ..models.column_anomaly_stationary_mean_30_days_check_spec import ColumnAnomalyStationaryMean30DaysCheckSpec
        from ..models.column_anomaly_differencing_sum_30_days_check_spec import ColumnAnomalyDifferencingSum30DaysCheckSpec
        from ..models.column_change_median_since_7_days_check_spec import ColumnChangeMedianSince7DaysCheckSpec
        from ..models.column_anomaly_stationary_median_30_days_check_spec import ColumnAnomalyStationaryMedian30DaysCheckSpec
        from ..models.column_change_mean_since_yesterday_check_spec import ColumnChangeMeanSinceYesterdayCheckSpec
        from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec
        from ..models.column_anomaly_differencing_sum_check_spec import ColumnAnomalyDifferencingSumCheckSpec
        from ..models.column_change_sum_since_yesterday_check_spec import ColumnChangeSumSinceYesterdayCheckSpec
        from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
        from ..models.column_change_median_since_yesterday_check_spec import ColumnChangeMedianSinceYesterdayCheckSpec
        from ..models.column_change_sum_since_30_days_check_spec import ColumnChangeSumSince30DaysCheckSpec
        from ..models.column_change_mean_since_30_days_check_spec import ColumnChangeMeanSince30DaysCheckSpec
        from ..models.column_change_sum_since_7_days_check_spec import ColumnChangeSumSince7DaysCheckSpec
        from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec
        d = src_dict.copy()
        _mean_change = d.pop("mean_change", UNSET)
        mean_change: Union[Unset, ColumnChangeMeanCheckSpec]
        if isinstance(_mean_change,  Unset):
            mean_change = UNSET
        else:
            mean_change = ColumnChangeMeanCheckSpec.from_dict(_mean_change)




        _mean_change_yesterday = d.pop("mean_change_yesterday", UNSET)
        mean_change_yesterday: Union[Unset, ColumnChangeMeanSinceYesterdayCheckSpec]
        if isinstance(_mean_change_yesterday,  Unset):
            mean_change_yesterday = UNSET
        else:
            mean_change_yesterday = ColumnChangeMeanSinceYesterdayCheckSpec.from_dict(_mean_change_yesterday)




        _median_change = d.pop("median_change", UNSET)
        median_change: Union[Unset, ColumnChangeMedianCheckSpec]
        if isinstance(_median_change,  Unset):
            median_change = UNSET
        else:
            median_change = ColumnChangeMedianCheckSpec.from_dict(_median_change)




        _median_change_yesterday = d.pop("median_change_yesterday", UNSET)
        median_change_yesterday: Union[Unset, ColumnChangeMedianSinceYesterdayCheckSpec]
        if isinstance(_median_change_yesterday,  Unset):
            median_change_yesterday = UNSET
        else:
            median_change_yesterday = ColumnChangeMedianSinceYesterdayCheckSpec.from_dict(_median_change_yesterday)




        _sum_change = d.pop("sum_change", UNSET)
        sum_change: Union[Unset, ColumnChangeSumCheckSpec]
        if isinstance(_sum_change,  Unset):
            sum_change = UNSET
        else:
            sum_change = ColumnChangeSumCheckSpec.from_dict(_sum_change)




        _sum_change_yesterday = d.pop("sum_change_yesterday", UNSET)
        sum_change_yesterday: Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]
        if isinstance(_sum_change_yesterday,  Unset):
            sum_change_yesterday = UNSET
        else:
            sum_change_yesterday = ColumnChangeSumSinceYesterdayCheckSpec.from_dict(_sum_change_yesterday)




        _mean_anomaly_stationary_30_days = d.pop("mean_anomaly_stationary_30_days", UNSET)
        mean_anomaly_stationary_30_days: Union[Unset, ColumnAnomalyStationaryMean30DaysCheckSpec]
        if isinstance(_mean_anomaly_stationary_30_days,  Unset):
            mean_anomaly_stationary_30_days = UNSET
        else:
            mean_anomaly_stationary_30_days = ColumnAnomalyStationaryMean30DaysCheckSpec.from_dict(_mean_anomaly_stationary_30_days)




        _mean_anomaly_stationary = d.pop("mean_anomaly_stationary", UNSET)
        mean_anomaly_stationary: Union[Unset, ColumnAnomalyStationaryMeanCheckSpec]
        if isinstance(_mean_anomaly_stationary,  Unset):
            mean_anomaly_stationary = UNSET
        else:
            mean_anomaly_stationary = ColumnAnomalyStationaryMeanCheckSpec.from_dict(_mean_anomaly_stationary)




        _median_anomaly_stationary_30_days = d.pop("median_anomaly_stationary_30_days", UNSET)
        median_anomaly_stationary_30_days: Union[Unset, ColumnAnomalyStationaryMedian30DaysCheckSpec]
        if isinstance(_median_anomaly_stationary_30_days,  Unset):
            median_anomaly_stationary_30_days = UNSET
        else:
            median_anomaly_stationary_30_days = ColumnAnomalyStationaryMedian30DaysCheckSpec.from_dict(_median_anomaly_stationary_30_days)




        _median_anomaly_stationary = d.pop("median_anomaly_stationary", UNSET)
        median_anomaly_stationary: Union[Unset, ColumnAnomalyStationaryMedianCheckSpec]
        if isinstance(_median_anomaly_stationary,  Unset):
            median_anomaly_stationary = UNSET
        else:
            median_anomaly_stationary = ColumnAnomalyStationaryMedianCheckSpec.from_dict(_median_anomaly_stationary)




        _sum_anomaly_differencing_30_days = d.pop("sum_anomaly_differencing_30_days", UNSET)
        sum_anomaly_differencing_30_days: Union[Unset, ColumnAnomalyDifferencingSum30DaysCheckSpec]
        if isinstance(_sum_anomaly_differencing_30_days,  Unset):
            sum_anomaly_differencing_30_days = UNSET
        else:
            sum_anomaly_differencing_30_days = ColumnAnomalyDifferencingSum30DaysCheckSpec.from_dict(_sum_anomaly_differencing_30_days)




        _sum_anomaly_differencing = d.pop("sum_anomaly_differencing", UNSET)
        sum_anomaly_differencing: Union[Unset, ColumnAnomalyDifferencingSumCheckSpec]
        if isinstance(_sum_anomaly_differencing,  Unset):
            sum_anomaly_differencing = UNSET
        else:
            sum_anomaly_differencing = ColumnAnomalyDifferencingSumCheckSpec.from_dict(_sum_anomaly_differencing)




        _mean_change_7_days = d.pop("mean_change_7_days", UNSET)
        mean_change_7_days: Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]
        if isinstance(_mean_change_7_days,  Unset):
            mean_change_7_days = UNSET
        else:
            mean_change_7_days = ColumnChangeMeanSince7DaysCheckSpec.from_dict(_mean_change_7_days)




        _mean_change_30_days = d.pop("mean_change_30_days", UNSET)
        mean_change_30_days: Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]
        if isinstance(_mean_change_30_days,  Unset):
            mean_change_30_days = UNSET
        else:
            mean_change_30_days = ColumnChangeMeanSince30DaysCheckSpec.from_dict(_mean_change_30_days)




        _median_change_7_days = d.pop("median_change_7_days", UNSET)
        median_change_7_days: Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]
        if isinstance(_median_change_7_days,  Unset):
            median_change_7_days = UNSET
        else:
            median_change_7_days = ColumnChangeMedianSince7DaysCheckSpec.from_dict(_median_change_7_days)




        _median_change_30_days = d.pop("median_change_30_days", UNSET)
        median_change_30_days: Union[Unset, ColumnChangeMedianSince30DaysCheckSpec]
        if isinstance(_median_change_30_days,  Unset):
            median_change_30_days = UNSET
        else:
            median_change_30_days = ColumnChangeMedianSince30DaysCheckSpec.from_dict(_median_change_30_days)




        _sum_change_7_days = d.pop("sum_change_7_days", UNSET)
        sum_change_7_days: Union[Unset, ColumnChangeSumSince7DaysCheckSpec]
        if isinstance(_sum_change_7_days,  Unset):
            sum_change_7_days = UNSET
        else:
            sum_change_7_days = ColumnChangeSumSince7DaysCheckSpec.from_dict(_sum_change_7_days)




        _sum_change_30_days = d.pop("sum_change_30_days", UNSET)
        sum_change_30_days: Union[Unset, ColumnChangeSumSince30DaysCheckSpec]
        if isinstance(_sum_change_30_days,  Unset):
            sum_change_30_days = UNSET
        else:
            sum_change_30_days = ColumnChangeSumSince30DaysCheckSpec.from_dict(_sum_change_30_days)




        column_anomaly_profiling_checks_spec = cls(
            mean_change=mean_change,
            mean_change_yesterday=mean_change_yesterday,
            median_change=median_change,
            median_change_yesterday=median_change_yesterday,
            sum_change=sum_change,
            sum_change_yesterday=sum_change_yesterday,
            mean_anomaly_stationary_30_days=mean_anomaly_stationary_30_days,
            mean_anomaly_stationary=mean_anomaly_stationary,
            median_anomaly_stationary_30_days=median_anomaly_stationary_30_days,
            median_anomaly_stationary=median_anomaly_stationary,
            sum_anomaly_differencing_30_days=sum_anomaly_differencing_30_days,
            sum_anomaly_differencing=sum_anomaly_differencing,
            mean_change_7_days=mean_change_7_days,
            mean_change_30_days=mean_change_30_days,
            median_change_7_days=median_change_7_days,
            median_change_30_days=median_change_30_days,
            sum_change_7_days=sum_change_7_days,
            sum_change_30_days=sum_change_30_days,
        )

        column_anomaly_profiling_checks_spec.additional_properties = d
        return column_anomaly_profiling_checks_spec

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
