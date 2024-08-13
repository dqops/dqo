from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.physical_table_name import PhysicalTableName


T = TypeVar("T", bound="TableProfilingSetupStatusModel")


@_attrs_define
class TableProfilingSetupStatusModel:
    """Table status model that identifies which type of information is already collected, such as data quality checks are
    configured, or statistics collected.

        Attributes:
            connection_name (Union[Unset, str]): Connection name.
            table_hash (Union[Unset, int]): Table hash that identifies the table using a unique hash code.
            target (Union[Unset, PhysicalTableName]):
            basic_statistics_collected (Union[Unset, bool]): The basic statistics were collected for this table. If this
                field returns false, the statistics were not collected and the user should collect basic statistics again.
            profiling_checks_configured (Union[Unset, bool]): Returns true if the table has any profiling checks configured
                on the table, or any of its column. Returns false when the user should first generate a configuration of the
                profiling checks using the rule miner.
            monitoring_checks_configured (Union[Unset, bool]): Returns true if the table has any monitoring checks
                configured on the table, or any of its column. Returns false when the user should first generate a configuration
                of the monitoring checks using the rule miner.
            partition_checks_configured (Union[Unset, bool]): Returns true if the table has any partition checks configured
                on the table, or any of its column. The value is true also when the table is not configured to support
                partitioned checks, so asking the user to configure partition checks is useless. Returns false when the user
                should first generate a configuration of the partition checks using the rule miner.
            check_results_present (Union[Unset, bool]): Returns true if the table has any recent data quality check results.
                Returns false when the user should run any checks to get any results.
    """

    connection_name: Union[Unset, str] = UNSET
    table_hash: Union[Unset, int] = UNSET
    target: Union[Unset, "PhysicalTableName"] = UNSET
    basic_statistics_collected: Union[Unset, bool] = UNSET
    profiling_checks_configured: Union[Unset, bool] = UNSET
    monitoring_checks_configured: Union[Unset, bool] = UNSET
    partition_checks_configured: Union[Unset, bool] = UNSET
    check_results_present: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table_hash = self.table_hash
        target: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.to_dict()

        basic_statistics_collected = self.basic_statistics_collected
        profiling_checks_configured = self.profiling_checks_configured
        monitoring_checks_configured = self.monitoring_checks_configured
        partition_checks_configured = self.partition_checks_configured
        check_results_present = self.check_results_present

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table_hash is not UNSET:
            field_dict["table_hash"] = table_hash
        if target is not UNSET:
            field_dict["target"] = target
        if basic_statistics_collected is not UNSET:
            field_dict["basic_statistics_collected"] = basic_statistics_collected
        if profiling_checks_configured is not UNSET:
            field_dict["profiling_checks_configured"] = profiling_checks_configured
        if monitoring_checks_configured is not UNSET:
            field_dict["monitoring_checks_configured"] = monitoring_checks_configured
        if partition_checks_configured is not UNSET:
            field_dict["partition_checks_configured"] = partition_checks_configured
        if check_results_present is not UNSET:
            field_dict["check_results_present"] = check_results_present

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.physical_table_name import PhysicalTableName

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        table_hash = d.pop("table_hash", UNSET)

        _target = d.pop("target", UNSET)
        target: Union[Unset, PhysicalTableName]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = PhysicalTableName.from_dict(_target)

        basic_statistics_collected = d.pop("basic_statistics_collected", UNSET)

        profiling_checks_configured = d.pop("profiling_checks_configured", UNSET)

        monitoring_checks_configured = d.pop("monitoring_checks_configured", UNSET)

        partition_checks_configured = d.pop("partition_checks_configured", UNSET)

        check_results_present = d.pop("check_results_present", UNSET)

        table_profiling_setup_status_model = cls(
            connection_name=connection_name,
            table_hash=table_hash,
            target=target,
            basic_statistics_collected=basic_statistics_collected,
            profiling_checks_configured=profiling_checks_configured,
            monitoring_checks_configured=monitoring_checks_configured,
            partition_checks_configured=partition_checks_configured,
            check_results_present=check_results_present,
        )

        table_profiling_setup_status_model.additional_properties = d
        return table_profiling_setup_status_model

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
