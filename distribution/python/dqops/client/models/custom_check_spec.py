from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.comment_spec import CommentSpec
    from ..models.custom_rule_parameters_spec import CustomRuleParametersSpec
    from ..models.custom_sensor_parameters_spec import CustomSensorParametersSpec
    from ..models.monitoring_schedule_spec import MonitoringScheduleSpec


T = TypeVar("T", bound="CustomCheckSpec")


@_attrs_define
class CustomCheckSpec:
    """
    Attributes:
        schedule_override (Union[Unset, MonitoringScheduleSpec]):
        comments (Union[Unset, List['CommentSpec']]): Comments for change tracking. Please put comments in this
            collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and
            deserialization will remove non tracked comments).
        disabled (Union[Unset, bool]): Disables the data quality check. Only enabled data quality checks and monitorings
            are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules
            should be preserved in the configuration.
        exclude_from_kpi (Union[Unset, bool]): Data quality check results (alerts) are included in the data quality KPI
            calculation by default. Set this field to true in order to exclude this data quality check from the data quality
            KPI calculation.
        include_in_sla (Union[Unset, bool]): Marks the data quality check as part of a data quality SLA (Data Contract).
            The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data
            Contract for the dataset.
        quality_dimension (Union[Unset, str]): Configures a custom data quality dimension name that is different than
            the built-in dimensions (Timeliness, Validity, etc.).
        display_name (Union[Unset, str]): Data quality check display name that can be assigned to the check, otherwise
            the check_display_name stored in the parquet result files is the check_name.
        data_grouping (Union[Unset, str]): Data grouping configuration name that should be applied to this data quality
            check. The data grouping is used to group the check's result by a GROUP BY clause in SQL, evaluating the data
            quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent
            table.
        sensor_name (Union[Unset, str]): Optional custom sensor name. It is a folder name inside the user's home
            'sensors' folder or the DQOps Home (DQOps distribution) home/sensors folder. Sample sensor name:
            table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named
            check definition.
        rule_name (Union[Unset, str]): Optional custom rule name. It is a path to a custom rule python module that
            starts at the user's home 'rules' folder. The path should not end with the .py file extension. Sample rule:
            myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named
            check definition.
        parameters (Union[Unset, CustomSensorParametersSpec]):
        warning (Union[Unset, CustomRuleParametersSpec]):
        error (Union[Unset, CustomRuleParametersSpec]):
        fatal (Union[Unset, CustomRuleParametersSpec]):
    """

    schedule_override: Union[Unset, "MonitoringScheduleSpec"] = UNSET
    comments: Union[Unset, List["CommentSpec"]] = UNSET
    disabled: Union[Unset, bool] = UNSET
    exclude_from_kpi: Union[Unset, bool] = UNSET
    include_in_sla: Union[Unset, bool] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    display_name: Union[Unset, str] = UNSET
    data_grouping: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    rule_name: Union[Unset, str] = UNSET
    parameters: Union[Unset, "CustomSensorParametersSpec"] = UNSET
    warning: Union[Unset, "CustomRuleParametersSpec"] = UNSET
    error: Union[Unset, "CustomRuleParametersSpec"] = UNSET
    fatal: Union[Unset, "CustomRuleParametersSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        schedule_override: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schedule_override, Unset):
            schedule_override = self.schedule_override.to_dict()

        comments: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.comments, Unset):
            comments = []
            for comments_item_data in self.comments:
                comments_item = comments_item_data.to_dict()

                comments.append(comments_item)

        disabled = self.disabled
        exclude_from_kpi = self.exclude_from_kpi
        include_in_sla = self.include_in_sla
        quality_dimension = self.quality_dimension
        display_name = self.display_name
        data_grouping = self.data_grouping
        sensor_name = self.sensor_name
        rule_name = self.rule_name
        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        warning: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.warning, Unset):
            warning = self.warning.to_dict()

        error: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.error, Unset):
            error = self.error.to_dict()

        fatal: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.fatal, Unset):
            fatal = self.fatal.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if schedule_override is not UNSET:
            field_dict["schedule_override"] = schedule_override
        if comments is not UNSET:
            field_dict["comments"] = comments
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if exclude_from_kpi is not UNSET:
            field_dict["exclude_from_kpi"] = exclude_from_kpi
        if include_in_sla is not UNSET:
            field_dict["include_in_sla"] = include_in_sla
        if quality_dimension is not UNSET:
            field_dict["quality_dimension"] = quality_dimension
        if display_name is not UNSET:
            field_dict["display_name"] = display_name
        if data_grouping is not UNSET:
            field_dict["data_grouping"] = data_grouping
        if sensor_name is not UNSET:
            field_dict["sensor_name"] = sensor_name
        if rule_name is not UNSET:
            field_dict["rule_name"] = rule_name
        if parameters is not UNSET:
            field_dict["parameters"] = parameters
        if warning is not UNSET:
            field_dict["warning"] = warning
        if error is not UNSET:
            field_dict["error"] = error
        if fatal is not UNSET:
            field_dict["fatal"] = fatal

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.comment_spec import CommentSpec
        from ..models.custom_rule_parameters_spec import CustomRuleParametersSpec
        from ..models.custom_sensor_parameters_spec import CustomSensorParametersSpec
        from ..models.monitoring_schedule_spec import MonitoringScheduleSpec

        d = src_dict.copy()
        _schedule_override = d.pop("schedule_override", UNSET)
        schedule_override: Union[Unset, MonitoringScheduleSpec]
        if isinstance(_schedule_override, Unset):
            schedule_override = UNSET
        else:
            schedule_override = MonitoringScheduleSpec.from_dict(_schedule_override)

        comments = []
        _comments = d.pop("comments", UNSET)
        for comments_item_data in _comments or []:
            comments_item = CommentSpec.from_dict(comments_item_data)

            comments.append(comments_item)

        disabled = d.pop("disabled", UNSET)

        exclude_from_kpi = d.pop("exclude_from_kpi", UNSET)

        include_in_sla = d.pop("include_in_sla", UNSET)

        quality_dimension = d.pop("quality_dimension", UNSET)

        display_name = d.pop("display_name", UNSET)

        data_grouping = d.pop("data_grouping", UNSET)

        sensor_name = d.pop("sensor_name", UNSET)

        rule_name = d.pop("rule_name", UNSET)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, CustomSensorParametersSpec]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = CustomSensorParametersSpec.from_dict(_parameters)

        _warning = d.pop("warning", UNSET)
        warning: Union[Unset, CustomRuleParametersSpec]
        if isinstance(_warning, Unset):
            warning = UNSET
        else:
            warning = CustomRuleParametersSpec.from_dict(_warning)

        _error = d.pop("error", UNSET)
        error: Union[Unset, CustomRuleParametersSpec]
        if isinstance(_error, Unset):
            error = UNSET
        else:
            error = CustomRuleParametersSpec.from_dict(_error)

        _fatal = d.pop("fatal", UNSET)
        fatal: Union[Unset, CustomRuleParametersSpec]
        if isinstance(_fatal, Unset):
            fatal = UNSET
        else:
            fatal = CustomRuleParametersSpec.from_dict(_fatal)

        custom_check_spec = cls(
            schedule_override=schedule_override,
            comments=comments,
            disabled=disabled,
            exclude_from_kpi=exclude_from_kpi,
            include_in_sla=include_in_sla,
            quality_dimension=quality_dimension,
            display_name=display_name,
            data_grouping=data_grouping,
            sensor_name=sensor_name,
            rule_name=rule_name,
            parameters=parameters,
            warning=warning,
            error=error,
            fatal=fatal,
        )

        custom_check_spec.additional_properties = d
        return custom_check_spec

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
