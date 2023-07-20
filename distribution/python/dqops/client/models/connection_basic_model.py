from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.connection_basic_model_provider_type import (
    ConnectionBasicModelProviderType,
)
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.big_query_parameters_spec import BigQueryParametersSpec
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.mysql_parameters_spec import MysqlParametersSpec
    from ..models.oracle_parameters_spec import OracleParametersSpec
    from ..models.postgresql_parameters_spec import PostgresqlParametersSpec
    from ..models.redshift_parameters_spec import RedshiftParametersSpec
    from ..models.snowflake_parameters_spec import SnowflakeParametersSpec
    from ..models.sql_server_parameters_spec import SqlServerParametersSpec
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="ConnectionBasicModel")


@attr.s(auto_attribs=True)
class ConnectionBasicModel:
    """Basic connection model with a subset of parameters, excluding all nested objects.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        connection_hash (Union[Unset, int]): Connection hash that identifies the connection using a unique hash code.
        parallel_runs_limit (Union[Unset, int]): The concurrency limit for the maximum number of parallel SQL queries
            executed on this connection.
        provider_type (Union[Unset, ConnectionBasicModelProviderType]): Database provider type (required). Accepts:
            bigquery, snowflake.
        bigquery (Union[Unset, BigQueryParametersSpec]):
        snowflake (Union[Unset, SnowflakeParametersSpec]):
        postgresql (Union[Unset, PostgresqlParametersSpec]):
        redshift (Union[Unset, RedshiftParametersSpec]):
        sqlserver (Union[Unset, SqlServerParametersSpec]):
        mysql (Union[Unset, MysqlParametersSpec]):
        oracle (Union[Unset, OracleParametersSpec]):
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        run_profiling_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        run_recurring_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        run_partition_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        collect_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
    """

    connection_name: Union[Unset, str] = UNSET
    connection_hash: Union[Unset, int] = UNSET
    parallel_runs_limit: Union[Unset, int] = UNSET
    provider_type: Union[Unset, ConnectionBasicModelProviderType] = UNSET
    bigquery: Union[Unset, "BigQueryParametersSpec"] = UNSET
    snowflake: Union[Unset, "SnowflakeParametersSpec"] = UNSET
    postgresql: Union[Unset, "PostgresqlParametersSpec"] = UNSET
    redshift: Union[Unset, "RedshiftParametersSpec"] = UNSET
    sqlserver: Union[Unset, "SqlServerParametersSpec"] = UNSET
    mysql: Union[Unset, "MysqlParametersSpec"] = UNSET
    oracle: Union[Unset, "OracleParametersSpec"] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_profiling_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_recurring_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_partition_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    collect_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        connection_hash = self.connection_hash
        parallel_runs_limit = self.parallel_runs_limit
        provider_type: Union[Unset, str] = UNSET
        if not isinstance(self.provider_type, Unset):
            provider_type = self.provider_type.value

        bigquery: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.bigquery, Unset):
            bigquery = self.bigquery.to_dict()

        snowflake: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.snowflake, Unset):
            snowflake = self.snowflake.to_dict()

        postgresql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.postgresql, Unset):
            postgresql = self.postgresql.to_dict()

        redshift: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.redshift, Unset):
            redshift = self.redshift.to_dict()

        sqlserver: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sqlserver, Unset):
            sqlserver = self.sqlserver.to_dict()

        mysql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mysql, Unset):
            mysql = self.mysql.to_dict()

        oracle: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.oracle, Unset):
            oracle = self.oracle.to_dict()

        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        run_profiling_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_profiling_checks_job_template, Unset):
            run_profiling_checks_job_template = (
                self.run_profiling_checks_job_template.to_dict()
            )

        run_recurring_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_recurring_checks_job_template, Unset):
            run_recurring_checks_job_template = (
                self.run_recurring_checks_job_template.to_dict()
            )

        run_partition_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_partition_checks_job_template, Unset):
            run_partition_checks_job_template = (
                self.run_partition_checks_job_template.to_dict()
            )

        collect_statistics_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_statistics_job_template, Unset):
            collect_statistics_job_template = (
                self.collect_statistics_job_template.to_dict()
            )

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if connection_hash is not UNSET:
            field_dict["connection_hash"] = connection_hash
        if parallel_runs_limit is not UNSET:
            field_dict["parallel_runs_limit"] = parallel_runs_limit
        if provider_type is not UNSET:
            field_dict["provider_type"] = provider_type
        if bigquery is not UNSET:
            field_dict["bigquery"] = bigquery
        if snowflake is not UNSET:
            field_dict["snowflake"] = snowflake
        if postgresql is not UNSET:
            field_dict["postgresql"] = postgresql
        if redshift is not UNSET:
            field_dict["redshift"] = redshift
        if sqlserver is not UNSET:
            field_dict["sqlserver"] = sqlserver
        if mysql is not UNSET:
            field_dict["mysql"] = mysql
        if oracle is not UNSET:
            field_dict["oracle"] = oracle
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if run_profiling_checks_job_template is not UNSET:
            field_dict[
                "run_profiling_checks_job_template"
            ] = run_profiling_checks_job_template
        if run_recurring_checks_job_template is not UNSET:
            field_dict[
                "run_recurring_checks_job_template"
            ] = run_recurring_checks_job_template
        if run_partition_checks_job_template is not UNSET:
            field_dict[
                "run_partition_checks_job_template"
            ] = run_partition_checks_job_template
        if collect_statistics_job_template is not UNSET:
            field_dict[
                "collect_statistics_job_template"
            ] = collect_statistics_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.big_query_parameters_spec import BigQueryParametersSpec
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.mysql_parameters_spec import MysqlParametersSpec
        from ..models.oracle_parameters_spec import OracleParametersSpec
        from ..models.postgresql_parameters_spec import PostgresqlParametersSpec
        from ..models.redshift_parameters_spec import RedshiftParametersSpec
        from ..models.snowflake_parameters_spec import SnowflakeParametersSpec
        from ..models.sql_server_parameters_spec import SqlServerParametersSpec
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        connection_hash = d.pop("connection_hash", UNSET)

        parallel_runs_limit = d.pop("parallel_runs_limit", UNSET)

        _provider_type = d.pop("provider_type", UNSET)
        provider_type: Union[Unset, ConnectionBasicModelProviderType]
        if isinstance(_provider_type, Unset):
            provider_type = UNSET
        else:
            provider_type = ConnectionBasicModelProviderType(_provider_type)

        _bigquery = d.pop("bigquery", UNSET)
        bigquery: Union[Unset, BigQueryParametersSpec]
        if isinstance(_bigquery, Unset):
            bigquery = UNSET
        else:
            bigquery = BigQueryParametersSpec.from_dict(_bigquery)

        _snowflake = d.pop("snowflake", UNSET)
        snowflake: Union[Unset, SnowflakeParametersSpec]
        if isinstance(_snowflake, Unset):
            snowflake = UNSET
        else:
            snowflake = SnowflakeParametersSpec.from_dict(_snowflake)

        _postgresql = d.pop("postgresql", UNSET)
        postgresql: Union[Unset, PostgresqlParametersSpec]
        if isinstance(_postgresql, Unset):
            postgresql = UNSET
        else:
            postgresql = PostgresqlParametersSpec.from_dict(_postgresql)

        _redshift = d.pop("redshift", UNSET)
        redshift: Union[Unset, RedshiftParametersSpec]
        if isinstance(_redshift, Unset):
            redshift = UNSET
        else:
            redshift = RedshiftParametersSpec.from_dict(_redshift)

        _sqlserver = d.pop("sqlserver", UNSET)
        sqlserver: Union[Unset, SqlServerParametersSpec]
        if isinstance(_sqlserver, Unset):
            sqlserver = UNSET
        else:
            sqlserver = SqlServerParametersSpec.from_dict(_sqlserver)

        _mysql = d.pop("mysql", UNSET)
        mysql: Union[Unset, MysqlParametersSpec]
        if isinstance(_mysql, Unset):
            mysql = UNSET
        else:
            mysql = MysqlParametersSpec.from_dict(_mysql)

        _oracle = d.pop("oracle", UNSET)
        oracle: Union[Unset, OracleParametersSpec]
        if isinstance(_oracle, Unset):
            oracle = UNSET
        else:
            oracle = OracleParametersSpec.from_dict(_oracle)

        _run_checks_job_template = d.pop("run_checks_job_template", UNSET)
        run_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_checks_job_template, Unset):
            run_checks_job_template = UNSET
        else:
            run_checks_job_template = CheckSearchFilters.from_dict(
                _run_checks_job_template
            )

        _run_profiling_checks_job_template = d.pop(
            "run_profiling_checks_job_template", UNSET
        )
        run_profiling_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_profiling_checks_job_template, Unset):
            run_profiling_checks_job_template = UNSET
        else:
            run_profiling_checks_job_template = CheckSearchFilters.from_dict(
                _run_profiling_checks_job_template
            )

        _run_recurring_checks_job_template = d.pop(
            "run_recurring_checks_job_template", UNSET
        )
        run_recurring_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_recurring_checks_job_template, Unset):
            run_recurring_checks_job_template = UNSET
        else:
            run_recurring_checks_job_template = CheckSearchFilters.from_dict(
                _run_recurring_checks_job_template
            )

        _run_partition_checks_job_template = d.pop(
            "run_partition_checks_job_template", UNSET
        )
        run_partition_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_partition_checks_job_template, Unset):
            run_partition_checks_job_template = UNSET
        else:
            run_partition_checks_job_template = CheckSearchFilters.from_dict(
                _run_partition_checks_job_template
            )

        _collect_statistics_job_template = d.pop(
            "collect_statistics_job_template", UNSET
        )
        collect_statistics_job_template: Union[Unset, StatisticsCollectorSearchFilters]
        if isinstance(_collect_statistics_job_template, Unset):
            collect_statistics_job_template = UNSET
        else:
            collect_statistics_job_template = (
                StatisticsCollectorSearchFilters.from_dict(
                    _collect_statistics_job_template
                )
            )

        _data_clean_job_template = d.pop("data_clean_job_template", UNSET)
        data_clean_job_template: Union[Unset, DeleteStoredDataQueueJobParameters]
        if isinstance(_data_clean_job_template, Unset):
            data_clean_job_template = UNSET
        else:
            data_clean_job_template = DeleteStoredDataQueueJobParameters.from_dict(
                _data_clean_job_template
            )

        connection_basic_model = cls(
            connection_name=connection_name,
            connection_hash=connection_hash,
            parallel_runs_limit=parallel_runs_limit,
            provider_type=provider_type,
            bigquery=bigquery,
            snowflake=snowflake,
            postgresql=postgresql,
            redshift=redshift,
            sqlserver=sqlserver,
            mysql=mysql,
            oracle=oracle,
            run_checks_job_template=run_checks_job_template,
            run_profiling_checks_job_template=run_profiling_checks_job_template,
            run_recurring_checks_job_template=run_recurring_checks_job_template,
            run_partition_checks_job_template=run_partition_checks_job_template,
            collect_statistics_job_template=collect_statistics_job_template,
            data_clean_job_template=data_clean_job_template,
        )

        connection_basic_model.additional_properties = d
        return connection_basic_model

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
