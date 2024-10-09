from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.comment_spec import CommentSpec
    from ..models.cron_schedules_spec import CronSchedulesSpec
    from ..models.file_format_spec import FileFormatSpec
    from ..models.partition_incremental_time_window_spec import (
        PartitionIncrementalTimeWindowSpec,
    )
    from ..models.table_incident_grouping_spec import TableIncidentGroupingSpec
    from ..models.table_lineage_source_spec import TableLineageSourceSpec
    from ..models.table_monitoring_check_categories_spec import (
        TableMonitoringCheckCategoriesSpec,
    )
    from ..models.table_owner_spec import TableOwnerSpec
    from ..models.table_partitioned_check_categories_spec import (
        TablePartitionedCheckCategoriesSpec,
    )
    from ..models.table_profiling_check_categories_spec import (
        TableProfilingCheckCategoriesSpec,
    )
    from ..models.table_spec_advanced_properties import TableSpecAdvancedProperties
    from ..models.table_spec_columns import TableSpecColumns
    from ..models.table_spec_groupings import TableSpecGroupings
    from ..models.table_spec_table_comparisons import TableSpecTableComparisons
    from ..models.table_statistics_collectors_root_categories_spec import (
        TableStatisticsCollectorsRootCategoriesSpec,
    )
    from ..models.timestamp_columns_spec import TimestampColumnsSpec


T = TypeVar("T", bound="TableSpec")


@_attrs_define
class TableSpec:
    """
    Attributes:
        disabled (Union[Unset, bool]): Disables all data quality checks on the table. Data quality checks will not be
            executed.
        stage (Union[Unset, str]): Stage name.
        priority (Union[Unset, int]): Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The
            table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of
            more and less important tables during a data quality improvement project, when the data quality issues on higher
            priority tables are fixed before data quality issues on less important tables.
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to
            replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed
            table or {column} to replace the content with the analyzed column name.
        do_not_collect_error_samples_in_profiling (Union[Unset, bool]): Disable automatic collection of error samples in
            the profiling section. The profiling checks by default always collect error samples for failed data quality
            checks.
        always_collect_error_samples_in_monitoring (Union[Unset, bool]): Always collect error samples for failed
            monitoring checks. DQOps will not collect error samples automatically when the checks are executed by a
            scheduler or by running checks from the metadata tree. Error samples are always collected only when the checks
            are run from the check editor.
        timestamp_columns (Union[Unset, TimestampColumnsSpec]):
        incremental_time_window (Union[Unset, PartitionIncrementalTimeWindowSpec]):
        default_grouping_name (Union[Unset, str]): The name of the default data grouping configuration that is applied
            on data quality checks. When a default data grouping is selected, all data quality checks run SQL queries with a
            GROUP BY clause, calculating separate data quality checks for each group of data. The data groupings are defined
            in the 'groupings' dictionary (indexed by the data grouping name).
        groupings (Union[Unset, TableSpecGroupings]): Data grouping configurations list. Data grouping configurations
            are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze
            different datasets using separate time series, for example a table contains data from multiple countries and
            there is a 'country' column used for partitioning. (2) a tag is assigned to a table (within a data grouping
            level hierarchy), when the data is segmented at a table level (similar tables store the same information, but
            for different countries, etc.).
        table_comparisons (Union[Unset, TableSpecTableComparisons]): Dictionary of data comparison configurations. Data
            comparison configurations are used for comparisons between data sources to compare this table (called the
            compared table) with other reference tables (the source of truth). The reference table's metadata must be
            imported into DQOps, but the reference table may be located in another data source. DQOps will compare metrics
            calculated for groups of rows (using the GROUP BY clause). For each comparison, the user must specify a name of
            a data grouping. The number of data grouping dimensions in the parent table and the reference table defined in
            the selected data grouping configurations must match. DQOps will run the same data quality sensors on both the
            parent table (table under test) and the reference table (the source of truth), comparing the measures (sensor
            readouts) captured from both tables.
        incident_grouping (Union[Unset, TableIncidentGroupingSpec]):
        owner (Union[Unset, TableOwnerSpec]):
        profiling_checks (Union[Unset, TableProfilingCheckCategoriesSpec]):
        monitoring_checks (Union[Unset, TableMonitoringCheckCategoriesSpec]):
        partitioned_checks (Union[Unset, TablePartitionedCheckCategoriesSpec]):
        statistics (Union[Unset, TableStatisticsCollectorsRootCategoriesSpec]):
        schedules_override (Union[Unset, CronSchedulesSpec]):
        columns (Union[Unset, TableSpecColumns]): Dictionary of columns, indexed by a physical column name. Column
            specification contains the expected column data type and a list of column level data quality checks that are
            enabled for a column.
        labels (Union[Unset, List[str]]): Custom labels that were assigned to the table. Labels are used for searching
            for tables when filtered data quality checks are executed.
        comments (Union[Unset, List['CommentSpec']]): Comments used for change tracking and documenting changes directly
            in the table data quality specification file.
        file_format (Union[Unset, FileFormatSpec]):
        advanced_properties (Union[Unset, TableSpecAdvancedProperties]): A dictionary of advanced properties that can be
            used for e.g. to support mapping data to data catalogs, a key/value dictionary.
        source_tables (Union[Unset, List['TableLineageSourceSpec']]): A list of source tables. This information is used
            to define the data lineage report for the table.
    """

    disabled: Union[Unset, bool] = UNSET
    stage: Union[Unset, str] = UNSET
    priority: Union[Unset, int] = UNSET
    filter_: Union[Unset, str] = UNSET
    do_not_collect_error_samples_in_profiling: Union[Unset, bool] = UNSET
    always_collect_error_samples_in_monitoring: Union[Unset, bool] = UNSET
    timestamp_columns: Union[Unset, "TimestampColumnsSpec"] = UNSET
    incremental_time_window: Union[Unset, "PartitionIncrementalTimeWindowSpec"] = UNSET
    default_grouping_name: Union[Unset, str] = UNSET
    groupings: Union[Unset, "TableSpecGroupings"] = UNSET
    table_comparisons: Union[Unset, "TableSpecTableComparisons"] = UNSET
    incident_grouping: Union[Unset, "TableIncidentGroupingSpec"] = UNSET
    owner: Union[Unset, "TableOwnerSpec"] = UNSET
    profiling_checks: Union[Unset, "TableProfilingCheckCategoriesSpec"] = UNSET
    monitoring_checks: Union[Unset, "TableMonitoringCheckCategoriesSpec"] = UNSET
    partitioned_checks: Union[Unset, "TablePartitionedCheckCategoriesSpec"] = UNSET
    statistics: Union[Unset, "TableStatisticsCollectorsRootCategoriesSpec"] = UNSET
    schedules_override: Union[Unset, "CronSchedulesSpec"] = UNSET
    columns: Union[Unset, "TableSpecColumns"] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    comments: Union[Unset, List["CommentSpec"]] = UNSET
    file_format: Union[Unset, "FileFormatSpec"] = UNSET
    advanced_properties: Union[Unset, "TableSpecAdvancedProperties"] = UNSET
    source_tables: Union[Unset, List["TableLineageSourceSpec"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        disabled = self.disabled
        stage = self.stage
        priority = self.priority
        filter_ = self.filter_
        do_not_collect_error_samples_in_profiling = (
            self.do_not_collect_error_samples_in_profiling
        )
        always_collect_error_samples_in_monitoring = (
            self.always_collect_error_samples_in_monitoring
        )
        timestamp_columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.timestamp_columns, Unset):
            timestamp_columns = self.timestamp_columns.to_dict()

        incremental_time_window: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.incremental_time_window, Unset):
            incremental_time_window = self.incremental_time_window.to_dict()

        default_grouping_name = self.default_grouping_name
        groupings: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.groupings, Unset):
            groupings = self.groupings.to_dict()

        table_comparisons: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table_comparisons, Unset):
            table_comparisons = self.table_comparisons.to_dict()

        incident_grouping: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.incident_grouping, Unset):
            incident_grouping = self.incident_grouping.to_dict()

        owner: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.owner, Unset):
            owner = self.owner.to_dict()

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

        schedules_override: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schedules_override, Unset):
            schedules_override = self.schedules_override.to_dict()

        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        comments: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.comments, Unset):
            comments = []
            for comments_item_data in self.comments:
                comments_item = comments_item_data.to_dict()

                comments.append(comments_item)

        file_format: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.file_format, Unset):
            file_format = self.file_format.to_dict()

        advanced_properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.advanced_properties, Unset):
            advanced_properties = self.advanced_properties.to_dict()

        source_tables: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.source_tables, Unset):
            source_tables = []
            for source_tables_item_data in self.source_tables:
                source_tables_item = source_tables_item_data.to_dict()

                source_tables.append(source_tables_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if stage is not UNSET:
            field_dict["stage"] = stage
        if priority is not UNSET:
            field_dict["priority"] = priority
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if do_not_collect_error_samples_in_profiling is not UNSET:
            field_dict["do_not_collect_error_samples_in_profiling"] = (
                do_not_collect_error_samples_in_profiling
            )
        if always_collect_error_samples_in_monitoring is not UNSET:
            field_dict["always_collect_error_samples_in_monitoring"] = (
                always_collect_error_samples_in_monitoring
            )
        if timestamp_columns is not UNSET:
            field_dict["timestamp_columns"] = timestamp_columns
        if incremental_time_window is not UNSET:
            field_dict["incremental_time_window"] = incremental_time_window
        if default_grouping_name is not UNSET:
            field_dict["default_grouping_name"] = default_grouping_name
        if groupings is not UNSET:
            field_dict["groupings"] = groupings
        if table_comparisons is not UNSET:
            field_dict["table_comparisons"] = table_comparisons
        if incident_grouping is not UNSET:
            field_dict["incident_grouping"] = incident_grouping
        if owner is not UNSET:
            field_dict["owner"] = owner
        if profiling_checks is not UNSET:
            field_dict["profiling_checks"] = profiling_checks
        if monitoring_checks is not UNSET:
            field_dict["monitoring_checks"] = monitoring_checks
        if partitioned_checks is not UNSET:
            field_dict["partitioned_checks"] = partitioned_checks
        if statistics is not UNSET:
            field_dict["statistics"] = statistics
        if schedules_override is not UNSET:
            field_dict["schedules_override"] = schedules_override
        if columns is not UNSET:
            field_dict["columns"] = columns
        if labels is not UNSET:
            field_dict["labels"] = labels
        if comments is not UNSET:
            field_dict["comments"] = comments
        if file_format is not UNSET:
            field_dict["file_format"] = file_format
        if advanced_properties is not UNSET:
            field_dict["advanced_properties"] = advanced_properties
        if source_tables is not UNSET:
            field_dict["source_tables"] = source_tables

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.comment_spec import CommentSpec
        from ..models.cron_schedules_spec import CronSchedulesSpec
        from ..models.file_format_spec import FileFormatSpec
        from ..models.partition_incremental_time_window_spec import (
            PartitionIncrementalTimeWindowSpec,
        )
        from ..models.table_incident_grouping_spec import TableIncidentGroupingSpec
        from ..models.table_lineage_source_spec import TableLineageSourceSpec
        from ..models.table_monitoring_check_categories_spec import (
            TableMonitoringCheckCategoriesSpec,
        )
        from ..models.table_owner_spec import TableOwnerSpec
        from ..models.table_partitioned_check_categories_spec import (
            TablePartitionedCheckCategoriesSpec,
        )
        from ..models.table_profiling_check_categories_spec import (
            TableProfilingCheckCategoriesSpec,
        )
        from ..models.table_spec_advanced_properties import TableSpecAdvancedProperties
        from ..models.table_spec_columns import TableSpecColumns
        from ..models.table_spec_groupings import TableSpecGroupings
        from ..models.table_spec_table_comparisons import TableSpecTableComparisons
        from ..models.table_statistics_collectors_root_categories_spec import (
            TableStatisticsCollectorsRootCategoriesSpec,
        )
        from ..models.timestamp_columns_spec import TimestampColumnsSpec

        d = src_dict.copy()
        disabled = d.pop("disabled", UNSET)

        stage = d.pop("stage", UNSET)

        priority = d.pop("priority", UNSET)

        filter_ = d.pop("filter", UNSET)

        do_not_collect_error_samples_in_profiling = d.pop(
            "do_not_collect_error_samples_in_profiling", UNSET
        )

        always_collect_error_samples_in_monitoring = d.pop(
            "always_collect_error_samples_in_monitoring", UNSET
        )

        _timestamp_columns = d.pop("timestamp_columns", UNSET)
        timestamp_columns: Union[Unset, TimestampColumnsSpec]
        if isinstance(_timestamp_columns, Unset):
            timestamp_columns = UNSET
        else:
            timestamp_columns = TimestampColumnsSpec.from_dict(_timestamp_columns)

        _incremental_time_window = d.pop("incremental_time_window", UNSET)
        incremental_time_window: Union[Unset, PartitionIncrementalTimeWindowSpec]
        if isinstance(_incremental_time_window, Unset):
            incremental_time_window = UNSET
        else:
            incremental_time_window = PartitionIncrementalTimeWindowSpec.from_dict(
                _incremental_time_window
            )

        default_grouping_name = d.pop("default_grouping_name", UNSET)

        _groupings = d.pop("groupings", UNSET)
        groupings: Union[Unset, TableSpecGroupings]
        if isinstance(_groupings, Unset):
            groupings = UNSET
        else:
            groupings = TableSpecGroupings.from_dict(_groupings)

        _table_comparisons = d.pop("table_comparisons", UNSET)
        table_comparisons: Union[Unset, TableSpecTableComparisons]
        if isinstance(_table_comparisons, Unset):
            table_comparisons = UNSET
        else:
            table_comparisons = TableSpecTableComparisons.from_dict(_table_comparisons)

        _incident_grouping = d.pop("incident_grouping", UNSET)
        incident_grouping: Union[Unset, TableIncidentGroupingSpec]
        if isinstance(_incident_grouping, Unset):
            incident_grouping = UNSET
        else:
            incident_grouping = TableIncidentGroupingSpec.from_dict(_incident_grouping)

        _owner = d.pop("owner", UNSET)
        owner: Union[Unset, TableOwnerSpec]
        if isinstance(_owner, Unset):
            owner = UNSET
        else:
            owner = TableOwnerSpec.from_dict(_owner)

        _profiling_checks = d.pop("profiling_checks", UNSET)
        profiling_checks: Union[Unset, TableProfilingCheckCategoriesSpec]
        if isinstance(_profiling_checks, Unset):
            profiling_checks = UNSET
        else:
            profiling_checks = TableProfilingCheckCategoriesSpec.from_dict(
                _profiling_checks
            )

        _monitoring_checks = d.pop("monitoring_checks", UNSET)
        monitoring_checks: Union[Unset, TableMonitoringCheckCategoriesSpec]
        if isinstance(_monitoring_checks, Unset):
            monitoring_checks = UNSET
        else:
            monitoring_checks = TableMonitoringCheckCategoriesSpec.from_dict(
                _monitoring_checks
            )

        _partitioned_checks = d.pop("partitioned_checks", UNSET)
        partitioned_checks: Union[Unset, TablePartitionedCheckCategoriesSpec]
        if isinstance(_partitioned_checks, Unset):
            partitioned_checks = UNSET
        else:
            partitioned_checks = TablePartitionedCheckCategoriesSpec.from_dict(
                _partitioned_checks
            )

        _statistics = d.pop("statistics", UNSET)
        statistics: Union[Unset, TableStatisticsCollectorsRootCategoriesSpec]
        if isinstance(_statistics, Unset):
            statistics = UNSET
        else:
            statistics = TableStatisticsCollectorsRootCategoriesSpec.from_dict(
                _statistics
            )

        _schedules_override = d.pop("schedules_override", UNSET)
        schedules_override: Union[Unset, CronSchedulesSpec]
        if isinstance(_schedules_override, Unset):
            schedules_override = UNSET
        else:
            schedules_override = CronSchedulesSpec.from_dict(_schedules_override)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, TableSpecColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = TableSpecColumns.from_dict(_columns)

        labels = cast(List[str], d.pop("labels", UNSET))

        comments = []
        _comments = d.pop("comments", UNSET)
        for comments_item_data in _comments or []:
            comments_item = CommentSpec.from_dict(comments_item_data)

            comments.append(comments_item)

        _file_format = d.pop("file_format", UNSET)
        file_format: Union[Unset, FileFormatSpec]
        if isinstance(_file_format, Unset):
            file_format = UNSET
        else:
            file_format = FileFormatSpec.from_dict(_file_format)

        _advanced_properties = d.pop("advanced_properties", UNSET)
        advanced_properties: Union[Unset, TableSpecAdvancedProperties]
        if isinstance(_advanced_properties, Unset):
            advanced_properties = UNSET
        else:
            advanced_properties = TableSpecAdvancedProperties.from_dict(
                _advanced_properties
            )

        source_tables = []
        _source_tables = d.pop("source_tables", UNSET)
        for source_tables_item_data in _source_tables or []:
            source_tables_item = TableLineageSourceSpec.from_dict(
                source_tables_item_data
            )

            source_tables.append(source_tables_item)

        table_spec = cls(
            disabled=disabled,
            stage=stage,
            priority=priority,
            filter_=filter_,
            do_not_collect_error_samples_in_profiling=do_not_collect_error_samples_in_profiling,
            always_collect_error_samples_in_monitoring=always_collect_error_samples_in_monitoring,
            timestamp_columns=timestamp_columns,
            incremental_time_window=incremental_time_window,
            default_grouping_name=default_grouping_name,
            groupings=groupings,
            table_comparisons=table_comparisons,
            incident_grouping=incident_grouping,
            owner=owner,
            profiling_checks=profiling_checks,
            monitoring_checks=monitoring_checks,
            partitioned_checks=partitioned_checks,
            statistics=statistics,
            schedules_override=schedules_override,
            columns=columns,
            labels=labels,
            comments=comments,
            file_format=file_format,
            advanced_properties=advanced_properties,
            source_tables=source_tables,
        )

        table_spec.additional_properties = d
        return table_spec

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
