from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_target_model import CheckTargetModel
from ..models.default_rule_severity_level import DefaultRuleSeverityLevel
from ..models.schedule_enabled_status_model import ScheduleEnabledStatusModel
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.comment_spec import CommentSpec
    from ..models.cron_schedule_spec import CronScheduleSpec
    from ..models.data_grouping_configuration_spec import DataGroupingConfigurationSpec
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.effective_schedule_model import EffectiveScheduleModel
    from ..models.field_model import FieldModel
    from ..models.rule_thresholds_model import RuleThresholdsModel
    from ..models.similar_check_model import SimilarCheckModel


T = TypeVar("T", bound="CheckModel")


@_attrs_define
class CheckModel:
    """Model that returns the form definition and the form data to edit a single data quality check.

    Attributes:
        check_name (Union[Unset, str]): Data quality check name that is used in YAML.
        help_text (Union[Unset, str]): Help text that describes the data quality check.
        display_name (Union[Unset, str]): User assigned display name that is shown instead of the original data quality
            check name.
        friendly_name (Union[Unset, str]): An alternative check's name that is shown on the check editor as a hint.
        sensor_parameters (Union[Unset, List['FieldModel']]): List of fields for editing the sensor parameters.
        sensor_name (Union[Unset, str]): Full sensor name. This field is for information purposes and can be used to
            create additional custom checks that reuse the same data quality sensor.
        quality_dimension (Union[Unset, str]): Data quality dimension used for tagging the results of this data quality
            checks.
        rule (Union[Unset, RuleThresholdsModel]): Model that returns the form definition and the form data to edit a
            single rule with all three threshold levels (low, medium, high).
        supports_error_sampling (Union[Unset, bool]): The data quality check supports capturing error samples, because
            an error sampling template is defined.
        supports_grouping (Union[Unset, bool]): The data quality check supports a custom data grouping configuration.
        standard (Union[Unset, bool]): This is a standard data quality check that is always shown on the data quality
            checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are
            shown when the user decides to expand the list of checks.
        default_check (Union[Unset, bool]): This is a check that was applied on-the-fly, because it is configured as a
            default data observability check and can be run, but it is not configured in the table YAML.
        default_severity (Union[Unset, DefaultRuleSeverityLevel]):
        data_grouping_override (Union[Unset, DataGroupingConfigurationSpec]):
        schedule_override (Union[Unset, CronScheduleSpec]):
        effective_schedule (Union[Unset, EffectiveScheduleModel]): Model of a configured schedule (enabled on connection
            or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution,
            as well as the duration until this time.
        schedule_enabled_status (Union[Unset, ScheduleEnabledStatusModel]):
        comments (Union[Unset, List['CommentSpec']]): Comments for change tracking. Please put comments in this
            collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and
            deserialization will remove non tracked comments).
        disabled (Union[Unset, bool]): Disables the data quality check. Only enabled checks are executed. The sensor
            should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in
            the configuration.
        exclude_from_kpi (Union[Unset, bool]): Data quality check results (alerts) are included in the data quality KPI
            calculation by default. Set this field to true in order to exclude this data quality check from the data quality
            KPI calculation.
        include_in_sla (Union[Unset, bool]): Marks the data quality check as part of a data quality SLA (Data Contract).
            The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data
            Contract for the dataset.
        configured (Union[Unset, bool]): True if the data quality check is configured (not null). When saving the data
            quality check configuration, set the flag to true for storing the check.
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        data_grouping_configuration (Union[Unset, str]): The name of a data grouping configuration defined at a table
            that should be used for this check.
        always_collect_error_samples (Union[Unset, bool]): Forces collecting error samples for this check whenever it
            fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect
            error samples will impose additional load on the data source.
        do_not_schedule (Union[Unset, bool]): Disables running this check by a DQOps CRON scheduler. When a check is
            disabled from scheduling, it can be only triggered from the user interface or by submitting "run checks" job.
        check_target (Union[Unset, CheckTargetModel]):
        configuration_requirements_errors (Union[Unset, List[str]]): List of configuration errors that must be fixed
            before the data quality check can be executed.
        similar_checks (Union[Unset, List['SimilarCheckModel']]): List of similar checks in other check types or in
            other time scales.
        check_hash (Union[Unset, int]): The check hash code that identifies the check instance.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can edit the check.
        can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
        can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
        similar_profiling_check (Union[Unset, SimilarCheckModel]): Model that identifies a similar check in another
            category or another type of check (monitoring, partition).
    """

    check_name: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    display_name: Union[Unset, str] = UNSET
    friendly_name: Union[Unset, str] = UNSET
    sensor_parameters: Union[Unset, List["FieldModel"]] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    rule: Union[Unset, "RuleThresholdsModel"] = UNSET
    supports_error_sampling: Union[Unset, bool] = UNSET
    supports_grouping: Union[Unset, bool] = UNSET
    standard: Union[Unset, bool] = UNSET
    default_check: Union[Unset, bool] = UNSET
    default_severity: Union[Unset, DefaultRuleSeverityLevel] = UNSET
    data_grouping_override: Union[Unset, "DataGroupingConfigurationSpec"] = UNSET
    schedule_override: Union[Unset, "CronScheduleSpec"] = UNSET
    effective_schedule: Union[Unset, "EffectiveScheduleModel"] = UNSET
    schedule_enabled_status: Union[Unset, ScheduleEnabledStatusModel] = UNSET
    comments: Union[Unset, List["CommentSpec"]] = UNSET
    disabled: Union[Unset, bool] = UNSET
    exclude_from_kpi: Union[Unset, bool] = UNSET
    include_in_sla: Union[Unset, bool] = UNSET
    configured: Union[Unset, bool] = UNSET
    filter_: Union[Unset, str] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    data_grouping_configuration: Union[Unset, str] = UNSET
    always_collect_error_samples: Union[Unset, bool] = UNSET
    do_not_schedule: Union[Unset, bool] = UNSET
    check_target: Union[Unset, CheckTargetModel] = UNSET
    configuration_requirements_errors: Union[Unset, List[str]] = UNSET
    similar_checks: Union[Unset, List["SimilarCheckModel"]] = UNSET
    check_hash: Union[Unset, int] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_run_checks: Union[Unset, bool] = UNSET
    can_delete_data: Union[Unset, bool] = UNSET
    similar_profiling_check: Union[Unset, "SimilarCheckModel"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        help_text = self.help_text
        display_name = self.display_name
        friendly_name = self.friendly_name
        sensor_parameters: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.sensor_parameters, Unset):
            sensor_parameters = []
            for sensor_parameters_item_data in self.sensor_parameters:
                sensor_parameters_item = sensor_parameters_item_data.to_dict()

                sensor_parameters.append(sensor_parameters_item)

        sensor_name = self.sensor_name
        quality_dimension = self.quality_dimension
        rule: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.rule, Unset):
            rule = self.rule.to_dict()

        supports_error_sampling = self.supports_error_sampling
        supports_grouping = self.supports_grouping
        standard = self.standard
        default_check = self.default_check
        default_severity: Union[Unset, str] = UNSET
        if not isinstance(self.default_severity, Unset):
            default_severity = self.default_severity.value

        data_grouping_override: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_grouping_override, Unset):
            data_grouping_override = self.data_grouping_override.to_dict()

        schedule_override: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schedule_override, Unset):
            schedule_override = self.schedule_override.to_dict()

        effective_schedule: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.effective_schedule, Unset):
            effective_schedule = self.effective_schedule.to_dict()

        schedule_enabled_status: Union[Unset, str] = UNSET
        if not isinstance(self.schedule_enabled_status, Unset):
            schedule_enabled_status = self.schedule_enabled_status.value

        comments: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.comments, Unset):
            comments = []
            for comments_item_data in self.comments:
                comments_item = comments_item_data.to_dict()

                comments.append(comments_item)

        disabled = self.disabled
        exclude_from_kpi = self.exclude_from_kpi
        include_in_sla = self.include_in_sla
        configured = self.configured
        filter_ = self.filter_
        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        data_grouping_configuration = self.data_grouping_configuration
        always_collect_error_samples = self.always_collect_error_samples
        do_not_schedule = self.do_not_schedule
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        configuration_requirements_errors: Union[Unset, List[str]] = UNSET
        if not isinstance(self.configuration_requirements_errors, Unset):
            configuration_requirements_errors = self.configuration_requirements_errors

        similar_checks: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.similar_checks, Unset):
            similar_checks = []
            for similar_checks_item_data in self.similar_checks:
                similar_checks_item = similar_checks_item_data.to_dict()

                similar_checks.append(similar_checks_item)

        check_hash = self.check_hash
        can_edit = self.can_edit
        can_run_checks = self.can_run_checks
        can_delete_data = self.can_delete_data
        similar_profiling_check: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.similar_profiling_check, Unset):
            similar_profiling_check = self.similar_profiling_check.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if help_text is not UNSET:
            field_dict["help_text"] = help_text
        if display_name is not UNSET:
            field_dict["display_name"] = display_name
        if friendly_name is not UNSET:
            field_dict["friendly_name"] = friendly_name
        if sensor_parameters is not UNSET:
            field_dict["sensor_parameters"] = sensor_parameters
        if sensor_name is not UNSET:
            field_dict["sensor_name"] = sensor_name
        if quality_dimension is not UNSET:
            field_dict["quality_dimension"] = quality_dimension
        if rule is not UNSET:
            field_dict["rule"] = rule
        if supports_error_sampling is not UNSET:
            field_dict["supports_error_sampling"] = supports_error_sampling
        if supports_grouping is not UNSET:
            field_dict["supports_grouping"] = supports_grouping
        if standard is not UNSET:
            field_dict["standard"] = standard
        if default_check is not UNSET:
            field_dict["default_check"] = default_check
        if default_severity is not UNSET:
            field_dict["default_severity"] = default_severity
        if data_grouping_override is not UNSET:
            field_dict["data_grouping_override"] = data_grouping_override
        if schedule_override is not UNSET:
            field_dict["schedule_override"] = schedule_override
        if effective_schedule is not UNSET:
            field_dict["effective_schedule"] = effective_schedule
        if schedule_enabled_status is not UNSET:
            field_dict["schedule_enabled_status"] = schedule_enabled_status
        if comments is not UNSET:
            field_dict["comments"] = comments
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if exclude_from_kpi is not UNSET:
            field_dict["exclude_from_kpi"] = exclude_from_kpi
        if include_in_sla is not UNSET:
            field_dict["include_in_sla"] = include_in_sla
        if configured is not UNSET:
            field_dict["configured"] = configured
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if data_grouping_configuration is not UNSET:
            field_dict["data_grouping_configuration"] = data_grouping_configuration
        if always_collect_error_samples is not UNSET:
            field_dict["always_collect_error_samples"] = always_collect_error_samples
        if do_not_schedule is not UNSET:
            field_dict["do_not_schedule"] = do_not_schedule
        if check_target is not UNSET:
            field_dict["check_target"] = check_target
        if configuration_requirements_errors is not UNSET:
            field_dict["configuration_requirements_errors"] = (
                configuration_requirements_errors
            )
        if similar_checks is not UNSET:
            field_dict["similar_checks"] = similar_checks
        if check_hash is not UNSET:
            field_dict["check_hash"] = check_hash
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_run_checks is not UNSET:
            field_dict["can_run_checks"] = can_run_checks
        if can_delete_data is not UNSET:
            field_dict["can_delete_data"] = can_delete_data
        if similar_profiling_check is not UNSET:
            field_dict["similar_profiling_check"] = similar_profiling_check

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.comment_spec import CommentSpec
        from ..models.cron_schedule_spec import CronScheduleSpec
        from ..models.data_grouping_configuration_spec import (
            DataGroupingConfigurationSpec,
        )
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.effective_schedule_model import EffectiveScheduleModel
        from ..models.field_model import FieldModel
        from ..models.rule_thresholds_model import RuleThresholdsModel
        from ..models.similar_check_model import SimilarCheckModel

        d = src_dict.copy()
        check_name = d.pop("check_name", UNSET)

        help_text = d.pop("help_text", UNSET)

        display_name = d.pop("display_name", UNSET)

        friendly_name = d.pop("friendly_name", UNSET)

        sensor_parameters = []
        _sensor_parameters = d.pop("sensor_parameters", UNSET)
        for sensor_parameters_item_data in _sensor_parameters or []:
            sensor_parameters_item = FieldModel.from_dict(sensor_parameters_item_data)

            sensor_parameters.append(sensor_parameters_item)

        sensor_name = d.pop("sensor_name", UNSET)

        quality_dimension = d.pop("quality_dimension", UNSET)

        _rule = d.pop("rule", UNSET)
        rule: Union[Unset, RuleThresholdsModel]
        if isinstance(_rule, Unset):
            rule = UNSET
        else:
            rule = RuleThresholdsModel.from_dict(_rule)

        supports_error_sampling = d.pop("supports_error_sampling", UNSET)

        supports_grouping = d.pop("supports_grouping", UNSET)

        standard = d.pop("standard", UNSET)

        default_check = d.pop("default_check", UNSET)

        _default_severity = d.pop("default_severity", UNSET)
        default_severity: Union[Unset, DefaultRuleSeverityLevel]
        if isinstance(_default_severity, Unset):
            default_severity = UNSET
        else:
            default_severity = DefaultRuleSeverityLevel(_default_severity)

        _data_grouping_override = d.pop("data_grouping_override", UNSET)
        data_grouping_override: Union[Unset, DataGroupingConfigurationSpec]
        if isinstance(_data_grouping_override, Unset):
            data_grouping_override = UNSET
        else:
            data_grouping_override = DataGroupingConfigurationSpec.from_dict(
                _data_grouping_override
            )

        _schedule_override = d.pop("schedule_override", UNSET)
        schedule_override: Union[Unset, CronScheduleSpec]
        if isinstance(_schedule_override, Unset):
            schedule_override = UNSET
        else:
            schedule_override = CronScheduleSpec.from_dict(_schedule_override)

        _effective_schedule = d.pop("effective_schedule", UNSET)
        effective_schedule: Union[Unset, EffectiveScheduleModel]
        if isinstance(_effective_schedule, Unset):
            effective_schedule = UNSET
        else:
            effective_schedule = EffectiveScheduleModel.from_dict(_effective_schedule)

        _schedule_enabled_status = d.pop("schedule_enabled_status", UNSET)
        schedule_enabled_status: Union[Unset, ScheduleEnabledStatusModel]
        if isinstance(_schedule_enabled_status, Unset):
            schedule_enabled_status = UNSET
        else:
            schedule_enabled_status = ScheduleEnabledStatusModel(
                _schedule_enabled_status
            )

        comments = []
        _comments = d.pop("comments", UNSET)
        for comments_item_data in _comments or []:
            comments_item = CommentSpec.from_dict(comments_item_data)

            comments.append(comments_item)

        disabled = d.pop("disabled", UNSET)

        exclude_from_kpi = d.pop("exclude_from_kpi", UNSET)

        include_in_sla = d.pop("include_in_sla", UNSET)

        configured = d.pop("configured", UNSET)

        filter_ = d.pop("filter", UNSET)

        _run_checks_job_template = d.pop("run_checks_job_template", UNSET)
        run_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_checks_job_template, Unset):
            run_checks_job_template = UNSET
        else:
            run_checks_job_template = CheckSearchFilters.from_dict(
                _run_checks_job_template
            )

        _data_clean_job_template = d.pop("data_clean_job_template", UNSET)
        data_clean_job_template: Union[Unset, DeleteStoredDataQueueJobParameters]
        if isinstance(_data_clean_job_template, Unset):
            data_clean_job_template = UNSET
        else:
            data_clean_job_template = DeleteStoredDataQueueJobParameters.from_dict(
                _data_clean_job_template
            )

        data_grouping_configuration = d.pop("data_grouping_configuration", UNSET)

        always_collect_error_samples = d.pop("always_collect_error_samples", UNSET)

        do_not_schedule = d.pop("do_not_schedule", UNSET)

        _check_target = d.pop("check_target", UNSET)
        check_target: Union[Unset, CheckTargetModel]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = CheckTargetModel(_check_target)

        configuration_requirements_errors = cast(
            List[str], d.pop("configuration_requirements_errors", UNSET)
        )

        similar_checks = []
        _similar_checks = d.pop("similar_checks", UNSET)
        for similar_checks_item_data in _similar_checks or []:
            similar_checks_item = SimilarCheckModel.from_dict(similar_checks_item_data)

            similar_checks.append(similar_checks_item)

        check_hash = d.pop("check_hash", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        _similar_profiling_check = d.pop("similar_profiling_check", UNSET)
        similar_profiling_check: Union[Unset, SimilarCheckModel]
        if isinstance(_similar_profiling_check, Unset):
            similar_profiling_check = UNSET
        else:
            similar_profiling_check = SimilarCheckModel.from_dict(
                _similar_profiling_check
            )

        check_model = cls(
            check_name=check_name,
            help_text=help_text,
            display_name=display_name,
            friendly_name=friendly_name,
            sensor_parameters=sensor_parameters,
            sensor_name=sensor_name,
            quality_dimension=quality_dimension,
            rule=rule,
            supports_error_sampling=supports_error_sampling,
            supports_grouping=supports_grouping,
            standard=standard,
            default_check=default_check,
            default_severity=default_severity,
            data_grouping_override=data_grouping_override,
            schedule_override=schedule_override,
            effective_schedule=effective_schedule,
            schedule_enabled_status=schedule_enabled_status,
            comments=comments,
            disabled=disabled,
            exclude_from_kpi=exclude_from_kpi,
            include_in_sla=include_in_sla,
            configured=configured,
            filter_=filter_,
            run_checks_job_template=run_checks_job_template,
            data_clean_job_template=data_clean_job_template,
            data_grouping_configuration=data_grouping_configuration,
            always_collect_error_samples=always_collect_error_samples,
            do_not_schedule=do_not_schedule,
            check_target=check_target,
            configuration_requirements_errors=configuration_requirements_errors,
            similar_checks=similar_checks,
            check_hash=check_hash,
            can_edit=can_edit,
            can_run_checks=can_run_checks,
            can_delete_data=can_delete_data,
            similar_profiling_check=similar_profiling_check,
        )

        check_model.additional_properties = d
        return check_model

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
