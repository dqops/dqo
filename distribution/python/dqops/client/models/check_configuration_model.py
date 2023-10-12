from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_target import CheckTarget
from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.field_model import FieldModel
    from ..models.rule_parameters_model import RuleParametersModel


T = TypeVar("T", bound="CheckConfigurationModel")


@_attrs_define
class CheckConfigurationModel:
    """Model containing fundamental configuration of a single data quality check.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        schema_name (Union[Unset, str]): Schema name.
        table_name (Union[Unset, str]): Table name.
        column_name (Union[Unset, str]): Column name, if the check is set up on a column.
        check_target (Union[Unset, CheckTarget]):
        check_type (Union[Unset, CheckType]):
        check_time_scale (Union[Unset, CheckTimeScale]):
        category_name (Union[Unset, str]): Category to which this check belongs.
        check_name (Union[Unset, str]): Check name that is used in YAML file.
        sensor_parameters (Union[Unset, List['FieldModel']]): List of fields for editing the sensor parameters.
        table_level_filter (Union[Unset, str]): SQL WHERE clause added to the sensor query for every check on this
            table.
        sensor_level_filter (Union[Unset, str]): SQL WHERE clause added to the sensor query for this check.
        warning (Union[Unset, RuleParametersModel]): Model that returns the form definition and the form data to edit
            parameters (thresholds) for a rule at a single severity level (low, medium, high).
        error (Union[Unset, RuleParametersModel]): Model that returns the form definition and the form data to edit
            parameters (thresholds) for a rule at a single severity level (low, medium, high).
        fatal (Union[Unset, RuleParametersModel]): Model that returns the form definition and the form data to edit
            parameters (thresholds) for a rule at a single severity level (low, medium, high).
        disabled (Union[Unset, bool]): Whether the check has been disabled.
        configured (Union[Unset, bool]): Whether the check is configured (not null).
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    column_name: Union[Unset, str] = UNSET
    check_target: Union[Unset, CheckTarget] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    check_time_scale: Union[Unset, CheckTimeScale] = UNSET
    category_name: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    sensor_parameters: Union[Unset, List["FieldModel"]] = UNSET
    table_level_filter: Union[Unset, str] = UNSET
    sensor_level_filter: Union[Unset, str] = UNSET
    warning: Union[Unset, "RuleParametersModel"] = UNSET
    error: Union[Unset, "RuleParametersModel"] = UNSET
    fatal: Union[Unset, "RuleParametersModel"] = UNSET
    disabled: Union[Unset, bool] = UNSET
    configured: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        column_name = self.column_name
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        check_time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.check_time_scale, Unset):
            check_time_scale = self.check_time_scale.value

        category_name = self.category_name
        check_name = self.check_name
        sensor_parameters: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.sensor_parameters, Unset):
            sensor_parameters = []
            for sensor_parameters_item_data in self.sensor_parameters:
                sensor_parameters_item = sensor_parameters_item_data.to_dict()

                sensor_parameters.append(sensor_parameters_item)

        table_level_filter = self.table_level_filter
        sensor_level_filter = self.sensor_level_filter
        warning: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.warning, Unset):
            warning = self.warning.to_dict()

        error: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.error, Unset):
            error = self.error.to_dict()

        fatal: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.fatal, Unset):
            fatal = self.fatal.to_dict()

        disabled = self.disabled
        configured = self.configured

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if column_name is not UNSET:
            field_dict["column_name"] = column_name
        if check_target is not UNSET:
            field_dict["check_target"] = check_target
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if check_time_scale is not UNSET:
            field_dict["check_time_scale"] = check_time_scale
        if category_name is not UNSET:
            field_dict["category_name"] = category_name
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if sensor_parameters is not UNSET:
            field_dict["sensor_parameters"] = sensor_parameters
        if table_level_filter is not UNSET:
            field_dict["table_level_filter"] = table_level_filter
        if sensor_level_filter is not UNSET:
            field_dict["sensor_level_filter"] = sensor_level_filter
        if warning is not UNSET:
            field_dict["warning"] = warning
        if error is not UNSET:
            field_dict["error"] = error
        if fatal is not UNSET:
            field_dict["fatal"] = fatal
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if configured is not UNSET:
            field_dict["configured"] = configured

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.field_model import FieldModel
        from ..models.rule_parameters_model import RuleParametersModel

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        column_name = d.pop("column_name", UNSET)

        _check_target = d.pop("check_target", UNSET)
        check_target: Union[Unset, CheckTarget]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = CheckTarget(_check_target)

        _check_type = d.pop("check_type", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _check_time_scale = d.pop("check_time_scale", UNSET)
        check_time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_check_time_scale, Unset):
            check_time_scale = UNSET
        else:
            check_time_scale = CheckTimeScale(_check_time_scale)

        category_name = d.pop("category_name", UNSET)

        check_name = d.pop("check_name", UNSET)

        sensor_parameters = []
        _sensor_parameters = d.pop("sensor_parameters", UNSET)
        for sensor_parameters_item_data in _sensor_parameters or []:
            sensor_parameters_item = FieldModel.from_dict(sensor_parameters_item_data)

            sensor_parameters.append(sensor_parameters_item)

        table_level_filter = d.pop("table_level_filter", UNSET)

        sensor_level_filter = d.pop("sensor_level_filter", UNSET)

        _warning = d.pop("warning", UNSET)
        warning: Union[Unset, RuleParametersModel]
        if isinstance(_warning, Unset):
            warning = UNSET
        else:
            warning = RuleParametersModel.from_dict(_warning)

        _error = d.pop("error", UNSET)
        error: Union[Unset, RuleParametersModel]
        if isinstance(_error, Unset):
            error = UNSET
        else:
            error = RuleParametersModel.from_dict(_error)

        _fatal = d.pop("fatal", UNSET)
        fatal: Union[Unset, RuleParametersModel]
        if isinstance(_fatal, Unset):
            fatal = UNSET
        else:
            fatal = RuleParametersModel.from_dict(_fatal)

        disabled = d.pop("disabled", UNSET)

        configured = d.pop("configured", UNSET)

        check_configuration_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            column_name=column_name,
            check_target=check_target,
            check_type=check_type,
            check_time_scale=check_time_scale,
            category_name=category_name,
            check_name=check_name,
            sensor_parameters=sensor_parameters,
            table_level_filter=table_level_filter,
            sensor_level_filter=sensor_level_filter,
            warning=warning,
            error=error,
            fatal=fatal,
            disabled=disabled,
            configured=configured,
        )

        check_configuration_model.additional_properties = d
        return check_configuration_model

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
