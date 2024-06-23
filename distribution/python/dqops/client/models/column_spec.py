from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_monitoring_check_categories_spec import (
        ColumnMonitoringCheckCategoriesSpec,
    )
    from ..models.column_partitioned_check_categories_spec import (
        ColumnPartitionedCheckCategoriesSpec,
    )
    from ..models.column_profiling_check_categories_spec import (
        ColumnProfilingCheckCategoriesSpec,
    )
    from ..models.column_statistics_collectors_root_categories_spec import (
        ColumnStatisticsCollectorsRootCategoriesSpec,
    )
    from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
    from ..models.comment_spec import CommentSpec


T = TypeVar("T", bound="ColumnSpec")


@_attrs_define
class ColumnSpec:
    """
    Attributes:
        disabled (Union[Unset, bool]): Disables all data quality checks on the column. Data quality checks will not be
            executed.
        sql_expression (Union[Unset, str]): SQL expression used for calculated fields or when additional column value
            transformation is required before the column can be used for analysis with data quality checks (data type
            conversion, transformation). It should be an SQL expression that uses the SQL language of the analyzed database
            type. Use the replacement tokens {table} to replace the content with the full table name, {alias} to replace the
            content with the table alias of the table under analysis, or {column} to replace the content with the analyzed
            column name. An example of extracting a value from a string column storing JSON in PostgreSQL:
            "{column}::json->'address'->'zip'".
        type_snapshot (Union[Unset, ColumnTypeSnapshotSpec]):
        id (Union[Unset, bool]): True when this column is a part of the primary key or a business key that identifies a
            row. Error sampling captures values of id columns to identify the row where the error sample was found.
        profiling_checks (Union[Unset, ColumnProfilingCheckCategoriesSpec]):
        monitoring_checks (Union[Unset, ColumnMonitoringCheckCategoriesSpec]):
        partitioned_checks (Union[Unset, ColumnPartitionedCheckCategoriesSpec]):
        statistics (Union[Unset, ColumnStatisticsCollectorsRootCategoriesSpec]):
        labels (Union[Unset, List[str]]): Custom labels that were assigned to the column. Labels are used for searching
            for columns when filtered data quality checks are executed.
        comments (Union[Unset, List['CommentSpec']]): Comments for change tracking. Please put comments in this
            collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and
            deserialization will remove non tracked comments).
    """

    disabled: Union[Unset, bool] = UNSET
    sql_expression: Union[Unset, str] = UNSET
    type_snapshot: Union[Unset, "ColumnTypeSnapshotSpec"] = UNSET
    id: Union[Unset, bool] = UNSET
    profiling_checks: Union[Unset, "ColumnProfilingCheckCategoriesSpec"] = UNSET
    monitoring_checks: Union[Unset, "ColumnMonitoringCheckCategoriesSpec"] = UNSET
    partitioned_checks: Union[Unset, "ColumnPartitionedCheckCategoriesSpec"] = UNSET
    statistics: Union[Unset, "ColumnStatisticsCollectorsRootCategoriesSpec"] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    comments: Union[Unset, List["CommentSpec"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        disabled = self.disabled
        sql_expression = self.sql_expression
        type_snapshot: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.type_snapshot, Unset):
            type_snapshot = self.type_snapshot.to_dict()

        id = self.id
        profiling_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profiling_checks, Unset):
            profiling_checks = self.profiling_checks.to_dict()

        monitoring_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monitoring_checks, Unset):
            monitoring_checks = self.monitoring_checks.to_dict()

        partitioned_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.partitioned_checks, Unset):
            partitioned_checks = self.partitioned_checks.to_dict()

        statistics: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.statistics, Unset):
            statistics = self.statistics.to_dict()

        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        comments: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.comments, Unset):
            comments = []
            for comments_item_data in self.comments:
                comments_item = comments_item_data.to_dict()

                comments.append(comments_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if sql_expression is not UNSET:
            field_dict["sql_expression"] = sql_expression
        if type_snapshot is not UNSET:
            field_dict["type_snapshot"] = type_snapshot
        if id is not UNSET:
            field_dict["id"] = id
        if profiling_checks is not UNSET:
            field_dict["profiling_checks"] = profiling_checks
        if monitoring_checks is not UNSET:
            field_dict["monitoring_checks"] = monitoring_checks
        if partitioned_checks is not UNSET:
            field_dict["partitioned_checks"] = partitioned_checks
        if statistics is not UNSET:
            field_dict["statistics"] = statistics
        if labels is not UNSET:
            field_dict["labels"] = labels
        if comments is not UNSET:
            field_dict["comments"] = comments

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_monitoring_check_categories_spec import (
            ColumnMonitoringCheckCategoriesSpec,
        )
        from ..models.column_partitioned_check_categories_spec import (
            ColumnPartitionedCheckCategoriesSpec,
        )
        from ..models.column_profiling_check_categories_spec import (
            ColumnProfilingCheckCategoriesSpec,
        )
        from ..models.column_statistics_collectors_root_categories_spec import (
            ColumnStatisticsCollectorsRootCategoriesSpec,
        )
        from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
        from ..models.comment_spec import CommentSpec

        d = src_dict.copy()
        disabled = d.pop("disabled", UNSET)

        sql_expression = d.pop("sql_expression", UNSET)

        _type_snapshot = d.pop("type_snapshot", UNSET)
        type_snapshot: Union[Unset, ColumnTypeSnapshotSpec]
        if isinstance(_type_snapshot, Unset):
            type_snapshot = UNSET
        else:
            type_snapshot = ColumnTypeSnapshotSpec.from_dict(_type_snapshot)

        id = d.pop("id", UNSET)

        _profiling_checks = d.pop("profiling_checks", UNSET)
        profiling_checks: Union[Unset, ColumnProfilingCheckCategoriesSpec]
        if isinstance(_profiling_checks, Unset):
            profiling_checks = UNSET
        else:
            profiling_checks = ColumnProfilingCheckCategoriesSpec.from_dict(
                _profiling_checks
            )

        _monitoring_checks = d.pop("monitoring_checks", UNSET)
        monitoring_checks: Union[Unset, ColumnMonitoringCheckCategoriesSpec]
        if isinstance(_monitoring_checks, Unset):
            monitoring_checks = UNSET
        else:
            monitoring_checks = ColumnMonitoringCheckCategoriesSpec.from_dict(
                _monitoring_checks
            )

        _partitioned_checks = d.pop("partitioned_checks", UNSET)
        partitioned_checks: Union[Unset, ColumnPartitionedCheckCategoriesSpec]
        if isinstance(_partitioned_checks, Unset):
            partitioned_checks = UNSET
        else:
            partitioned_checks = ColumnPartitionedCheckCategoriesSpec.from_dict(
                _partitioned_checks
            )

        _statistics = d.pop("statistics", UNSET)
        statistics: Union[Unset, ColumnStatisticsCollectorsRootCategoriesSpec]
        if isinstance(_statistics, Unset):
            statistics = UNSET
        else:
            statistics = ColumnStatisticsCollectorsRootCategoriesSpec.from_dict(
                _statistics
            )

        labels = cast(List[str], d.pop("labels", UNSET))

        comments = []
        _comments = d.pop("comments", UNSET)
        for comments_item_data in _comments or []:
            comments_item = CommentSpec.from_dict(comments_item_data)

            comments.append(comments_item)

        column_spec = cls(
            disabled=disabled,
            sql_expression=sql_expression,
            type_snapshot=type_snapshot,
            id=id,
            profiling_checks=profiling_checks,
            monitoring_checks=monitoring_checks,
            partitioned_checks=partitioned_checks,
            statistics=statistics,
            labels=labels,
            comments=comments,
        )

        column_spec.additional_properties = d
        return column_spec

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
