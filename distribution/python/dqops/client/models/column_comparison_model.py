from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.compare_thresholds_model import CompareThresholdsModel


T = TypeVar("T", bound="ColumnComparisonModel")


@_attrs_define
class ColumnComparisonModel:
    """The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count)
    are compared for this column between the compared (tested) column and the reference column from the reference table.

        Attributes:
            compared_column_name (Union[Unset, str]): The name of the compared column in the compared table (the tested
                table). The REST API returns all columns defined in the metadata.
            reference_column_name (Union[Unset, str]): The name of the reference column in the reference table (the source
                of truth). Set the name of the reference column to enable comparison between the compared and the reference
                columns.
            compare_min (Union[Unset, CompareThresholdsModel]): Model with the compare threshold levels for raising data
                quality issues at different severity levels when the difference between the compared (tested) table and the
                reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual
                value and the expected value from the reference table.
            compare_max (Union[Unset, CompareThresholdsModel]): Model with the compare threshold levels for raising data
                quality issues at different severity levels when the difference between the compared (tested) table and the
                reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual
                value and the expected value from the reference table.
            compare_sum (Union[Unset, CompareThresholdsModel]): Model with the compare threshold levels for raising data
                quality issues at different severity levels when the difference between the compared (tested) table and the
                reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual
                value and the expected value from the reference table.
            compare_mean (Union[Unset, CompareThresholdsModel]): Model with the compare threshold levels for raising data
                quality issues at different severity levels when the difference between the compared (tested) table and the
                reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual
                value and the expected value from the reference table.
            compare_null_count (Union[Unset, CompareThresholdsModel]): Model with the compare threshold levels for raising
                data quality issues at different severity levels when the difference between the compared (tested) table and the
                reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual
                value and the expected value from the reference table.
            compare_not_null_count (Union[Unset, CompareThresholdsModel]): Model with the compare threshold levels for
                raising data quality issues at different severity levels when the difference between the compared (tested) table
                and the reference table (the source of truth) exceed given thresholds as a percentage of difference between the
                actual value and the expected value from the reference table.
    """

    compared_column_name: Union[Unset, str] = UNSET
    reference_column_name: Union[Unset, str] = UNSET
    compare_min: Union[Unset, "CompareThresholdsModel"] = UNSET
    compare_max: Union[Unset, "CompareThresholdsModel"] = UNSET
    compare_sum: Union[Unset, "CompareThresholdsModel"] = UNSET
    compare_mean: Union[Unset, "CompareThresholdsModel"] = UNSET
    compare_null_count: Union[Unset, "CompareThresholdsModel"] = UNSET
    compare_not_null_count: Union[Unset, "CompareThresholdsModel"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        compared_column_name = self.compared_column_name
        reference_column_name = self.reference_column_name
        compare_min: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compare_min, Unset):
            compare_min = self.compare_min.to_dict()

        compare_max: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compare_max, Unset):
            compare_max = self.compare_max.to_dict()

        compare_sum: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compare_sum, Unset):
            compare_sum = self.compare_sum.to_dict()

        compare_mean: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compare_mean, Unset):
            compare_mean = self.compare_mean.to_dict()

        compare_null_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compare_null_count, Unset):
            compare_null_count = self.compare_null_count.to_dict()

        compare_not_null_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.compare_not_null_count, Unset):
            compare_not_null_count = self.compare_not_null_count.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if compared_column_name is not UNSET:
            field_dict["compared_column_name"] = compared_column_name
        if reference_column_name is not UNSET:
            field_dict["reference_column_name"] = reference_column_name
        if compare_min is not UNSET:
            field_dict["compare_min"] = compare_min
        if compare_max is not UNSET:
            field_dict["compare_max"] = compare_max
        if compare_sum is not UNSET:
            field_dict["compare_sum"] = compare_sum
        if compare_mean is not UNSET:
            field_dict["compare_mean"] = compare_mean
        if compare_null_count is not UNSET:
            field_dict["compare_null_count"] = compare_null_count
        if compare_not_null_count is not UNSET:
            field_dict["compare_not_null_count"] = compare_not_null_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.compare_thresholds_model import CompareThresholdsModel

        d = src_dict.copy()
        compared_column_name = d.pop("compared_column_name", UNSET)

        reference_column_name = d.pop("reference_column_name", UNSET)

        _compare_min = d.pop("compare_min", UNSET)
        compare_min: Union[Unset, CompareThresholdsModel]
        if isinstance(_compare_min, Unset):
            compare_min = UNSET
        else:
            compare_min = CompareThresholdsModel.from_dict(_compare_min)

        _compare_max = d.pop("compare_max", UNSET)
        compare_max: Union[Unset, CompareThresholdsModel]
        if isinstance(_compare_max, Unset):
            compare_max = UNSET
        else:
            compare_max = CompareThresholdsModel.from_dict(_compare_max)

        _compare_sum = d.pop("compare_sum", UNSET)
        compare_sum: Union[Unset, CompareThresholdsModel]
        if isinstance(_compare_sum, Unset):
            compare_sum = UNSET
        else:
            compare_sum = CompareThresholdsModel.from_dict(_compare_sum)

        _compare_mean = d.pop("compare_mean", UNSET)
        compare_mean: Union[Unset, CompareThresholdsModel]
        if isinstance(_compare_mean, Unset):
            compare_mean = UNSET
        else:
            compare_mean = CompareThresholdsModel.from_dict(_compare_mean)

        _compare_null_count = d.pop("compare_null_count", UNSET)
        compare_null_count: Union[Unset, CompareThresholdsModel]
        if isinstance(_compare_null_count, Unset):
            compare_null_count = UNSET
        else:
            compare_null_count = CompareThresholdsModel.from_dict(_compare_null_count)

        _compare_not_null_count = d.pop("compare_not_null_count", UNSET)
        compare_not_null_count: Union[Unset, CompareThresholdsModel]
        if isinstance(_compare_not_null_count, Unset):
            compare_not_null_count = UNSET
        else:
            compare_not_null_count = CompareThresholdsModel.from_dict(
                _compare_not_null_count
            )

        column_comparison_model = cls(
            compared_column_name=compared_column_name,
            reference_column_name=reference_column_name,
            compare_min=compare_min,
            compare_max=compare_max,
            compare_sum=compare_sum,
            compare_mean=compare_mean,
            compare_null_count=compare_null_count,
            compare_not_null_count=compare_not_null_count,
        )

        column_comparison_model.additional_properties = d
        return column_comparison_model

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
