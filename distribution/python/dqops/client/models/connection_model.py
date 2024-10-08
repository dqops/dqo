from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.provider_type import ProviderType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.big_query_parameters_spec import BigQueryParametersSpec
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.connection_model_advanced_properties import (
        ConnectionModelAdvancedProperties,
    )
    from ..models.databricks_parameters_spec import DatabricksParametersSpec
    from ..models.db_2_parameters_spec import Db2ParametersSpec
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.duckdb_parameters_spec import DuckdbParametersSpec
    from ..models.hana_parameters_spec import HanaParametersSpec
    from ..models.mysql_parameters_spec import MysqlParametersSpec
    from ..models.oracle_parameters_spec import OracleParametersSpec
    from ..models.postgresql_parameters_spec import PostgresqlParametersSpec
    from ..models.presto_parameters_spec import PrestoParametersSpec
    from ..models.redshift_parameters_spec import RedshiftParametersSpec
    from ..models.snowflake_parameters_spec import SnowflakeParametersSpec
    from ..models.spark_parameters_spec import SparkParametersSpec
    from ..models.sql_server_parameters_spec import SqlServerParametersSpec
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )
    from ..models.trino_parameters_spec import TrinoParametersSpec


T = TypeVar("T", bound="ConnectionModel")


@_attrs_define
class ConnectionModel:
    """Connection model with a subset of parameters, excluding all nested objects.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        connection_hash (Union[Unset, int]): Connection hash that identifies the connection using a unique hash code.
        parallel_jobs_limit (Union[Unset, int]): The concurrency limit for the maximum number of parallel SQL queries
            executed on this connection.
        schedule_on_instance (Union[Unset, str]): Limits running scheduled checks (started by a CRON job scheduler) to
            run only on a named DQOps instance. When this field is empty, data quality checks are run on all DQOps
            instances. Set a DQOps instance name to run checks on a named instance only. The default name of the DQOps Cloud
            SaaS instance is "cloud".
        provider_type (Union[Unset, ProviderType]):
        bigquery (Union[Unset, BigQueryParametersSpec]):
        snowflake (Union[Unset, SnowflakeParametersSpec]):
        postgresql (Union[Unset, PostgresqlParametersSpec]):
        duckdb (Union[Unset, DuckdbParametersSpec]):
        redshift (Union[Unset, RedshiftParametersSpec]):
        sqlserver (Union[Unset, SqlServerParametersSpec]):
        presto (Union[Unset, PrestoParametersSpec]):
        trino (Union[Unset, TrinoParametersSpec]):
        mysql (Union[Unset, MysqlParametersSpec]):
        oracle (Union[Unset, OracleParametersSpec]):
        spark (Union[Unset, SparkParametersSpec]):
        databricks (Union[Unset, DatabricksParametersSpec]):
        hana (Union[Unset, HanaParametersSpec]):
        db2 (Union[Unset, Db2ParametersSpec]):
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        run_profiling_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        run_monitoring_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        run_partition_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        collect_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
        advanced_properties (Union[Unset, ConnectionModelAdvancedProperties]): A dictionary of advanced properties that
            can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete the connection
            to the data source.
        can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
            statistics.
        can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
        can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    connection_name: Union[Unset, str] = UNSET
    connection_hash: Union[Unset, int] = UNSET
    parallel_jobs_limit: Union[Unset, int] = UNSET
    schedule_on_instance: Union[Unset, str] = UNSET
    provider_type: Union[Unset, ProviderType] = UNSET
    bigquery: Union[Unset, "BigQueryParametersSpec"] = UNSET
    snowflake: Union[Unset, "SnowflakeParametersSpec"] = UNSET
    postgresql: Union[Unset, "PostgresqlParametersSpec"] = UNSET
    duckdb: Union[Unset, "DuckdbParametersSpec"] = UNSET
    redshift: Union[Unset, "RedshiftParametersSpec"] = UNSET
    sqlserver: Union[Unset, "SqlServerParametersSpec"] = UNSET
    presto: Union[Unset, "PrestoParametersSpec"] = UNSET
    trino: Union[Unset, "TrinoParametersSpec"] = UNSET
    mysql: Union[Unset, "MysqlParametersSpec"] = UNSET
    oracle: Union[Unset, "OracleParametersSpec"] = UNSET
    spark: Union[Unset, "SparkParametersSpec"] = UNSET
    databricks: Union[Unset, "DatabricksParametersSpec"] = UNSET
    hana: Union[Unset, "HanaParametersSpec"] = UNSET
    db2: Union[Unset, "Db2ParametersSpec"] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_profiling_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_monitoring_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_partition_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    collect_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    advanced_properties: Union[Unset, "ConnectionModelAdvancedProperties"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_collect_statistics: Union[Unset, bool] = UNSET
    can_run_checks: Union[Unset, bool] = UNSET
    can_delete_data: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        connection_hash = self.connection_hash
        parallel_jobs_limit = self.parallel_jobs_limit
        schedule_on_instance = self.schedule_on_instance
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

        duckdb: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duckdb, Unset):
            duckdb = self.duckdb.to_dict()

        redshift: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.redshift, Unset):
            redshift = self.redshift.to_dict()

        sqlserver: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sqlserver, Unset):
            sqlserver = self.sqlserver.to_dict()

        presto: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.presto, Unset):
            presto = self.presto.to_dict()

        trino: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.trino, Unset):
            trino = self.trino.to_dict()

        mysql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mysql, Unset):
            mysql = self.mysql.to_dict()

        oracle: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.oracle, Unset):
            oracle = self.oracle.to_dict()

        spark: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spark, Unset):
            spark = self.spark.to_dict()

        databricks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.databricks, Unset):
            databricks = self.databricks.to_dict()

        hana: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.hana, Unset):
            hana = self.hana.to_dict()

        db2: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.db2, Unset):
            db2 = self.db2.to_dict()

        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        run_profiling_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_profiling_checks_job_template, Unset):
            run_profiling_checks_job_template = (
                self.run_profiling_checks_job_template.to_dict()
            )

        run_monitoring_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_monitoring_checks_job_template, Unset):
            run_monitoring_checks_job_template = (
                self.run_monitoring_checks_job_template.to_dict()
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

        advanced_properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.advanced_properties, Unset):
            advanced_properties = self.advanced_properties.to_dict()

        can_edit = self.can_edit
        can_collect_statistics = self.can_collect_statistics
        can_run_checks = self.can_run_checks
        can_delete_data = self.can_delete_data
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if connection_hash is not UNSET:
            field_dict["connection_hash"] = connection_hash
        if parallel_jobs_limit is not UNSET:
            field_dict["parallel_jobs_limit"] = parallel_jobs_limit
        if schedule_on_instance is not UNSET:
            field_dict["schedule_on_instance"] = schedule_on_instance
        if provider_type is not UNSET:
            field_dict["provider_type"] = provider_type
        if bigquery is not UNSET:
            field_dict["bigquery"] = bigquery
        if snowflake is not UNSET:
            field_dict["snowflake"] = snowflake
        if postgresql is not UNSET:
            field_dict["postgresql"] = postgresql
        if duckdb is not UNSET:
            field_dict["duckdb"] = duckdb
        if redshift is not UNSET:
            field_dict["redshift"] = redshift
        if sqlserver is not UNSET:
            field_dict["sqlserver"] = sqlserver
        if presto is not UNSET:
            field_dict["presto"] = presto
        if trino is not UNSET:
            field_dict["trino"] = trino
        if mysql is not UNSET:
            field_dict["mysql"] = mysql
        if oracle is not UNSET:
            field_dict["oracle"] = oracle
        if spark is not UNSET:
            field_dict["spark"] = spark
        if databricks is not UNSET:
            field_dict["databricks"] = databricks
        if hana is not UNSET:
            field_dict["hana"] = hana
        if db2 is not UNSET:
            field_dict["db2"] = db2
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if run_profiling_checks_job_template is not UNSET:
            field_dict["run_profiling_checks_job_template"] = (
                run_profiling_checks_job_template
            )
        if run_monitoring_checks_job_template is not UNSET:
            field_dict["run_monitoring_checks_job_template"] = (
                run_monitoring_checks_job_template
            )
        if run_partition_checks_job_template is not UNSET:
            field_dict["run_partition_checks_job_template"] = (
                run_partition_checks_job_template
            )
        if collect_statistics_job_template is not UNSET:
            field_dict["collect_statistics_job_template"] = (
                collect_statistics_job_template
            )
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if advanced_properties is not UNSET:
            field_dict["advanced_properties"] = advanced_properties
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_collect_statistics is not UNSET:
            field_dict["can_collect_statistics"] = can_collect_statistics
        if can_run_checks is not UNSET:
            field_dict["can_run_checks"] = can_run_checks
        if can_delete_data is not UNSET:
            field_dict["can_delete_data"] = can_delete_data
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.big_query_parameters_spec import BigQueryParametersSpec
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.connection_model_advanced_properties import (
            ConnectionModelAdvancedProperties,
        )
        from ..models.databricks_parameters_spec import DatabricksParametersSpec
        from ..models.db_2_parameters_spec import Db2ParametersSpec
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.duckdb_parameters_spec import DuckdbParametersSpec
        from ..models.hana_parameters_spec import HanaParametersSpec
        from ..models.mysql_parameters_spec import MysqlParametersSpec
        from ..models.oracle_parameters_spec import OracleParametersSpec
        from ..models.postgresql_parameters_spec import PostgresqlParametersSpec
        from ..models.presto_parameters_spec import PrestoParametersSpec
        from ..models.redshift_parameters_spec import RedshiftParametersSpec
        from ..models.snowflake_parameters_spec import SnowflakeParametersSpec
        from ..models.spark_parameters_spec import SparkParametersSpec
        from ..models.sql_server_parameters_spec import SqlServerParametersSpec
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )
        from ..models.trino_parameters_spec import TrinoParametersSpec

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        connection_hash = d.pop("connection_hash", UNSET)

        parallel_jobs_limit = d.pop("parallel_jobs_limit", UNSET)

        schedule_on_instance = d.pop("schedule_on_instance", UNSET)

        _provider_type = d.pop("provider_type", UNSET)
        provider_type: Union[Unset, ProviderType]
        if isinstance(_provider_type, Unset):
            provider_type = UNSET
        else:
            provider_type = ProviderType(_provider_type)

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

        _duckdb = d.pop("duckdb", UNSET)
        duckdb: Union[Unset, DuckdbParametersSpec]
        if isinstance(_duckdb, Unset):
            duckdb = UNSET
        else:
            duckdb = DuckdbParametersSpec.from_dict(_duckdb)

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

        _presto = d.pop("presto", UNSET)
        presto: Union[Unset, PrestoParametersSpec]
        if isinstance(_presto, Unset):
            presto = UNSET
        else:
            presto = PrestoParametersSpec.from_dict(_presto)

        _trino = d.pop("trino", UNSET)
        trino: Union[Unset, TrinoParametersSpec]
        if isinstance(_trino, Unset):
            trino = UNSET
        else:
            trino = TrinoParametersSpec.from_dict(_trino)

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

        _spark = d.pop("spark", UNSET)
        spark: Union[Unset, SparkParametersSpec]
        if isinstance(_spark, Unset):
            spark = UNSET
        else:
            spark = SparkParametersSpec.from_dict(_spark)

        _databricks = d.pop("databricks", UNSET)
        databricks: Union[Unset, DatabricksParametersSpec]
        if isinstance(_databricks, Unset):
            databricks = UNSET
        else:
            databricks = DatabricksParametersSpec.from_dict(_databricks)

        _hana = d.pop("hana", UNSET)
        hana: Union[Unset, HanaParametersSpec]
        if isinstance(_hana, Unset):
            hana = UNSET
        else:
            hana = HanaParametersSpec.from_dict(_hana)

        _db2 = d.pop("db2", UNSET)
        db2: Union[Unset, Db2ParametersSpec]
        if isinstance(_db2, Unset):
            db2 = UNSET
        else:
            db2 = Db2ParametersSpec.from_dict(_db2)

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

        _run_monitoring_checks_job_template = d.pop(
            "run_monitoring_checks_job_template", UNSET
        )
        run_monitoring_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_monitoring_checks_job_template, Unset):
            run_monitoring_checks_job_template = UNSET
        else:
            run_monitoring_checks_job_template = CheckSearchFilters.from_dict(
                _run_monitoring_checks_job_template
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

        _advanced_properties = d.pop("advanced_properties", UNSET)
        advanced_properties: Union[Unset, ConnectionModelAdvancedProperties]
        if isinstance(_advanced_properties, Unset):
            advanced_properties = UNSET
        else:
            advanced_properties = ConnectionModelAdvancedProperties.from_dict(
                _advanced_properties
            )

        can_edit = d.pop("can_edit", UNSET)

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        connection_model = cls(
            connection_name=connection_name,
            connection_hash=connection_hash,
            parallel_jobs_limit=parallel_jobs_limit,
            schedule_on_instance=schedule_on_instance,
            provider_type=provider_type,
            bigquery=bigquery,
            snowflake=snowflake,
            postgresql=postgresql,
            duckdb=duckdb,
            redshift=redshift,
            sqlserver=sqlserver,
            presto=presto,
            trino=trino,
            mysql=mysql,
            oracle=oracle,
            spark=spark,
            databricks=databricks,
            hana=hana,
            db2=db2,
            run_checks_job_template=run_checks_job_template,
            run_profiling_checks_job_template=run_profiling_checks_job_template,
            run_monitoring_checks_job_template=run_monitoring_checks_job_template,
            run_partition_checks_job_template=run_partition_checks_job_template,
            collect_statistics_job_template=collect_statistics_job_template,
            data_clean_job_template=data_clean_job_template,
            advanced_properties=advanced_properties,
            can_edit=can_edit,
            can_collect_statistics=can_collect_statistics,
            can_run_checks=can_run_checks,
            can_delete_data=can_delete_data,
            yaml_parsing_error=yaml_parsing_error,
        )

        connection_model.additional_properties = d
        return connection_model

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
