import datetime
from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.parameter_definition_spec import ParameterDefinitionSpec


T = TypeVar("T", bound="FieldModel")


@_attrs_define
class FieldModel:
    """Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the
    field and the current value.

        Attributes:
            definition (Union[Unset, ParameterDefinitionSpec]): Defines a single field that is a sensor parameter or a rule
                parameter.
            optional (Union[Unset, bool]): Field value is optional and may be null, when false - the field is required and
                must be filled.
            string_value (Union[Unset, str]): Field value for a string field.
            boolean_value (Union[Unset, bool]): Field value for a boolean field.
            integer_value (Union[Unset, int]): Field value for an integer (32-bit) field.
            long_value (Union[Unset, int]): Field value for a long (64-bit) field.
            double_value (Union[Unset, float]): Field value for a double field.
            datetime_value (Union[Unset, datetime.datetime]): Field value for a date time field.
            column_name_value (Union[Unset, str]): Field value for a column name field.
            enum_value (Union[Unset, str]): Field value for an enum (choice) field.
            string_list_value (Union[Unset, List[str]]): Field value for an array (list) of strings.
            integer_list_value (Union[Unset, List[int]]): Field value for an array (list) of integers, using 64 bit
                integers.
            date_value (Union[Unset, datetime.date]): Field value for an date.
    """

    definition: Union[Unset, "ParameterDefinitionSpec"] = UNSET
    optional: Union[Unset, bool] = UNSET
    string_value: Union[Unset, str] = UNSET
    boolean_value: Union[Unset, bool] = UNSET
    integer_value: Union[Unset, int] = UNSET
    long_value: Union[Unset, int] = UNSET
    double_value: Union[Unset, float] = UNSET
    datetime_value: Union[Unset, datetime.datetime] = UNSET
    column_name_value: Union[Unset, str] = UNSET
    enum_value: Union[Unset, str] = UNSET
    string_list_value: Union[Unset, List[str]] = UNSET
    integer_list_value: Union[Unset, List[int]] = UNSET
    date_value: Union[Unset, datetime.date] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        definition: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.definition, Unset):
            definition = self.definition.to_dict()

        optional = self.optional
        string_value = self.string_value
        boolean_value = self.boolean_value
        integer_value = self.integer_value
        long_value = self.long_value
        double_value = self.double_value
        datetime_value: Union[Unset, str] = UNSET
        if not isinstance(self.datetime_value, Unset):
            datetime_value = self.datetime_value.isoformat()

        column_name_value = self.column_name_value
        enum_value = self.enum_value
        string_list_value: Union[Unset, List[str]] = UNSET
        if not isinstance(self.string_list_value, Unset):
            string_list_value = self.string_list_value

        integer_list_value: Union[Unset, List[int]] = UNSET
        if not isinstance(self.integer_list_value, Unset):
            integer_list_value = self.integer_list_value

        date_value: Union[Unset, str] = UNSET
        if not isinstance(self.date_value, Unset):
            date_value = self.date_value.isoformat()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if definition is not UNSET:
            field_dict["definition"] = definition
        if optional is not UNSET:
            field_dict["optional"] = optional
        if string_value is not UNSET:
            field_dict["string_value"] = string_value
        if boolean_value is not UNSET:
            field_dict["boolean_value"] = boolean_value
        if integer_value is not UNSET:
            field_dict["integer_value"] = integer_value
        if long_value is not UNSET:
            field_dict["long_value"] = long_value
        if double_value is not UNSET:
            field_dict["double_value"] = double_value
        if datetime_value is not UNSET:
            field_dict["datetime_value"] = datetime_value
        if column_name_value is not UNSET:
            field_dict["column_name_value"] = column_name_value
        if enum_value is not UNSET:
            field_dict["enum_value"] = enum_value
        if string_list_value is not UNSET:
            field_dict["string_list_value"] = string_list_value
        if integer_list_value is not UNSET:
            field_dict["integer_list_value"] = integer_list_value
        if date_value is not UNSET:
            field_dict["date_value"] = date_value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.parameter_definition_spec import ParameterDefinitionSpec

        d = src_dict.copy()
        _definition = d.pop("definition", UNSET)
        definition: Union[Unset, ParameterDefinitionSpec]
        if isinstance(_definition, Unset):
            definition = UNSET
        else:
            definition = ParameterDefinitionSpec.from_dict(_definition)

        optional = d.pop("optional", UNSET)

        string_value = d.pop("string_value", UNSET)

        boolean_value = d.pop("boolean_value", UNSET)

        integer_value = d.pop("integer_value", UNSET)

        long_value = d.pop("long_value", UNSET)

        double_value = d.pop("double_value", UNSET)

        _datetime_value = d.pop("datetime_value", UNSET)
        datetime_value: Union[Unset, datetime.datetime]
        if isinstance(_datetime_value, Unset):
            datetime_value = UNSET
        else:
            datetime_value = isoparse(_datetime_value)

        column_name_value = d.pop("column_name_value", UNSET)

        enum_value = d.pop("enum_value", UNSET)

        string_list_value = cast(List[str], d.pop("string_list_value", UNSET))

        integer_list_value = cast(List[int], d.pop("integer_list_value", UNSET))

        _date_value = d.pop("date_value", UNSET)
        date_value: Union[Unset, datetime.date]
        if isinstance(_date_value, Unset):
            date_value = UNSET
        else:
            date_value = isoparse(_date_value).date()

        field_model = cls(
            definition=definition,
            optional=optional,
            string_value=string_value,
            boolean_value=boolean_value,
            integer_value=integer_value,
            long_value=long_value,
            double_value=double_value,
            datetime_value=datetime_value,
            column_name_value=column_name_value,
            enum_value=enum_value,
            string_list_value=string_list_value,
            integer_list_value=integer_list_value,
            date_value=date_value,
        )

        field_model.additional_properties = d
        return field_model

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
