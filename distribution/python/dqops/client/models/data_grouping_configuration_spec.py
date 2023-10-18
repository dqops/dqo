from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.data_grouping_dimension_spec import DataGroupingDimensionSpec


T = TypeVar("T", bound="DataGroupingConfigurationSpec")


@_attrs_define
class DataGroupingConfigurationSpec:
    """
    Attributes:
        level_1 (Union[Unset, DataGroupingDimensionSpec]):
        level_2 (Union[Unset, DataGroupingDimensionSpec]):
        level_3 (Union[Unset, DataGroupingDimensionSpec]):
        level_4 (Union[Unset, DataGroupingDimensionSpec]):
        level_5 (Union[Unset, DataGroupingDimensionSpec]):
        level_6 (Union[Unset, DataGroupingDimensionSpec]):
        level_7 (Union[Unset, DataGroupingDimensionSpec]):
        level_8 (Union[Unset, DataGroupingDimensionSpec]):
        level_9 (Union[Unset, DataGroupingDimensionSpec]):
    """

    level_1: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_2: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_3: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_4: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_5: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_6: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_7: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_8: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    level_9: Union[Unset, "DataGroupingDimensionSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        level_1: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_1, Unset):
            level_1 = self.level_1.to_dict()

        level_2: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_2, Unset):
            level_2 = self.level_2.to_dict()

        level_3: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_3, Unset):
            level_3 = self.level_3.to_dict()

        level_4: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_4, Unset):
            level_4 = self.level_4.to_dict()

        level_5: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_5, Unset):
            level_5 = self.level_5.to_dict()

        level_6: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_6, Unset):
            level_6 = self.level_6.to_dict()

        level_7: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_7, Unset):
            level_7 = self.level_7.to_dict()

        level_8: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_8, Unset):
            level_8 = self.level_8.to_dict()

        level_9: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.level_9, Unset):
            level_9 = self.level_9.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if level_1 is not UNSET:
            field_dict["level_1"] = level_1
        if level_2 is not UNSET:
            field_dict["level_2"] = level_2
        if level_3 is not UNSET:
            field_dict["level_3"] = level_3
        if level_4 is not UNSET:
            field_dict["level_4"] = level_4
        if level_5 is not UNSET:
            field_dict["level_5"] = level_5
        if level_6 is not UNSET:
            field_dict["level_6"] = level_6
        if level_7 is not UNSET:
            field_dict["level_7"] = level_7
        if level_8 is not UNSET:
            field_dict["level_8"] = level_8
        if level_9 is not UNSET:
            field_dict["level_9"] = level_9

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.data_grouping_dimension_spec import DataGroupingDimensionSpec

        d = src_dict.copy()
        _level_1 = d.pop("level_1", UNSET)
        level_1: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_1, Unset):
            level_1 = UNSET
        else:
            level_1 = DataGroupingDimensionSpec.from_dict(_level_1)

        _level_2 = d.pop("level_2", UNSET)
        level_2: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_2, Unset):
            level_2 = UNSET
        else:
            level_2 = DataGroupingDimensionSpec.from_dict(_level_2)

        _level_3 = d.pop("level_3", UNSET)
        level_3: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_3, Unset):
            level_3 = UNSET
        else:
            level_3 = DataGroupingDimensionSpec.from_dict(_level_3)

        _level_4 = d.pop("level_4", UNSET)
        level_4: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_4, Unset):
            level_4 = UNSET
        else:
            level_4 = DataGroupingDimensionSpec.from_dict(_level_4)

        _level_5 = d.pop("level_5", UNSET)
        level_5: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_5, Unset):
            level_5 = UNSET
        else:
            level_5 = DataGroupingDimensionSpec.from_dict(_level_5)

        _level_6 = d.pop("level_6", UNSET)
        level_6: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_6, Unset):
            level_6 = UNSET
        else:
            level_6 = DataGroupingDimensionSpec.from_dict(_level_6)

        _level_7 = d.pop("level_7", UNSET)
        level_7: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_7, Unset):
            level_7 = UNSET
        else:
            level_7 = DataGroupingDimensionSpec.from_dict(_level_7)

        _level_8 = d.pop("level_8", UNSET)
        level_8: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_8, Unset):
            level_8 = UNSET
        else:
            level_8 = DataGroupingDimensionSpec.from_dict(_level_8)

        _level_9 = d.pop("level_9", UNSET)
        level_9: Union[Unset, DataGroupingDimensionSpec]
        if isinstance(_level_9, Unset):
            level_9 = UNSET
        else:
            level_9 = DataGroupingDimensionSpec.from_dict(_level_9)

        data_grouping_configuration_spec = cls(
            level_1=level_1,
            level_2=level_2,
            level_3=level_3,
            level_4=level_4,
            level_5=level_5,
            level_6=level_6,
            level_7=level_7,
            level_8=level_8,
            level_9=level_9,
        )

        data_grouping_configuration_spec.additional_properties = d
        return data_grouping_configuration_spec

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
