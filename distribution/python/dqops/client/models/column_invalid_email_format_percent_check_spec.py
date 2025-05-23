from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_patterns_invalid_email_format_percent_sensor_parameters_spec import (
        ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec,
    )
    from ..models.comment_spec import CommentSpec
    from ..models.cron_schedule_spec import CronScheduleSpec
    from ..models.max_percent_rule_0_error_parameters_spec import (
        MaxPercentRule0ErrorParametersSpec,
    )
    from ..models.max_percent_rule_0_warning_parameters_spec import (
        MaxPercentRule0WarningParametersSpec,
    )
    from ..models.max_percent_rule_5_parameters_spec import (
        MaxPercentRule5ParametersSpec,
    )


T = TypeVar("T", bound="ColumnInvalidEmailFormatPercentCheckSpec")


@_attrs_define
class ColumnInvalidEmailFormatPercentCheckSpec:
    """
    Attributes:
        schedule_override (Union[Unset, CronScheduleSpec]):
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
        always_collect_error_samples (Union[Unset, bool]): Forces collecting error samples for this check whenever it
            fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect
            error samples will impose additional load on the data source.
        do_not_schedule (Union[Unset, bool]): Disables running this check by a DQOps CRON scheduler. When a check is
            disabled from scheduling, it can be only triggered from the user interface or by submitting "run checks" job.
        parameters (Union[Unset, ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec]):
        warning (Union[Unset, MaxPercentRule0WarningParametersSpec]):
        error (Union[Unset, MaxPercentRule0ErrorParametersSpec]):
        fatal (Union[Unset, MaxPercentRule5ParametersSpec]):
    """

    schedule_override: Union[Unset, "CronScheduleSpec"] = UNSET
    comments: Union[Unset, List["CommentSpec"]] = UNSET
    disabled: Union[Unset, bool] = UNSET
    exclude_from_kpi: Union[Unset, bool] = UNSET
    include_in_sla: Union[Unset, bool] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    display_name: Union[Unset, str] = UNSET
    data_grouping: Union[Unset, str] = UNSET
    always_collect_error_samples: Union[Unset, bool] = UNSET
    do_not_schedule: Union[Unset, bool] = UNSET
    parameters: Union[
        Unset, "ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec"
    ] = UNSET
    warning: Union[Unset, "MaxPercentRule0WarningParametersSpec"] = UNSET
    error: Union[Unset, "MaxPercentRule0ErrorParametersSpec"] = UNSET
    fatal: Union[Unset, "MaxPercentRule5ParametersSpec"] = UNSET
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
        always_collect_error_samples = self.always_collect_error_samples
        do_not_schedule = self.do_not_schedule
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
        if always_collect_error_samples is not UNSET:
            field_dict["always_collect_error_samples"] = always_collect_error_samples
        if do_not_schedule is not UNSET:
            field_dict["do_not_schedule"] = do_not_schedule
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
        from ..models.column_patterns_invalid_email_format_percent_sensor_parameters_spec import (
            ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec,
        )
        from ..models.comment_spec import CommentSpec
        from ..models.cron_schedule_spec import CronScheduleSpec
        from ..models.max_percent_rule_0_error_parameters_spec import (
            MaxPercentRule0ErrorParametersSpec,
        )
        from ..models.max_percent_rule_0_warning_parameters_spec import (
            MaxPercentRule0WarningParametersSpec,
        )
        from ..models.max_percent_rule_5_parameters_spec import (
            MaxPercentRule5ParametersSpec,
        )

        d = src_dict.copy()
        _schedule_override = d.pop("schedule_override", UNSET)
        schedule_override: Union[Unset, CronScheduleSpec]
        if isinstance(_schedule_override, Unset):
            schedule_override = UNSET
        else:
            schedule_override = CronScheduleSpec.from_dict(_schedule_override)

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

        always_collect_error_samples = d.pop("always_collect_error_samples", UNSET)

        do_not_schedule = d.pop("do_not_schedule", UNSET)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[
            Unset, ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec
        ]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = (
                ColumnPatternsInvalidEmailFormatPercentSensorParametersSpec.from_dict(
                    _parameters
                )
            )

        _warning = d.pop("warning", UNSET)
        warning: Union[Unset, MaxPercentRule0WarningParametersSpec]
        if isinstance(_warning, Unset):
            warning = UNSET
        else:
            warning = MaxPercentRule0WarningParametersSpec.from_dict(_warning)

        _error = d.pop("error", UNSET)
        error: Union[Unset, MaxPercentRule0ErrorParametersSpec]
        if isinstance(_error, Unset):
            error = UNSET
        else:
            error = MaxPercentRule0ErrorParametersSpec.from_dict(_error)

        _fatal = d.pop("fatal", UNSET)
        fatal: Union[Unset, MaxPercentRule5ParametersSpec]
        if isinstance(_fatal, Unset):
            fatal = UNSET
        else:
            fatal = MaxPercentRule5ParametersSpec.from_dict(_fatal)

        column_invalid_email_format_percent_check_spec = cls(
            schedule_override=schedule_override,
            comments=comments,
            disabled=disabled,
            exclude_from_kpi=exclude_from_kpi,
            include_in_sla=include_in_sla,
            quality_dimension=quality_dimension,
            display_name=display_name,
            data_grouping=data_grouping,
            always_collect_error_samples=always_collect_error_samples,
            do_not_schedule=do_not_schedule,
            parameters=parameters,
            warning=warning,
            error=error,
            fatal=fatal,
        )

        column_invalid_email_format_percent_check_spec.additional_properties = d
        return column_invalid_email_format_percent_check_spec

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
