from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.provider_type import ProviderType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.big_query_parameters_spec import BigQueryParametersSpec
    from ..models.comment_spec import CommentSpec
    from ..models.connection_incident_grouping_spec import (
        ConnectionIncidentGroupingSpec,
    )
    from ..models.data_grouping_configuration_spec import DataGroupingConfigurationSpec
    from ..models.databricks_parameters_spec import DatabricksParametersSpec
    from ..models.default_schedules_spec import DefaultSchedulesSpec
    from ..models.duckdb_parameters_spec import DuckdbParametersSpec
    from ..models.mysql_parameters_spec import MysqlParametersSpec
    from ..models.oracle_parameters_spec import OracleParametersSpec
    from ..models.postgresql_parameters_spec import PostgresqlParametersSpec
    from ..models.presto_parameters_spec import PrestoParametersSpec
    from ..models.redshift_parameters_spec import RedshiftParametersSpec
    from ..models.snowflake_parameters_spec import SnowflakeParametersSpec
    from ..models.spark_parameters_spec import SparkParametersSpec
    from ..models.sql_server_parameters_spec import SqlServerParametersSpec
    from ..models.trino_parameters_spec import TrinoParametersSpec


T = TypeVar("T", bound="ConnectionSpec")


@_attrs_define
class ConnectionSpec:
    """
    Attributes:
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
        parallel_jobs_limit (Union[Unset, int]): The concurrency limit for the maximum number of parallel SQL queries
            executed on this connection.
        default_grouping_configuration (Union[Unset, DataGroupingConfigurationSpec]):
        schedules (Union[Unset, DefaultSchedulesSpec]):
        incident_grouping (Union[Unset, ConnectionIncidentGroupingSpec]):
        comments (Union[Unset, List['CommentSpec']]): Comments for change tracking. Please put comments in this
            collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and
            deserialization will remove non tracked comments).
        labels (Union[Unset, List[str]]): Custom labels that were assigned to the connection. Labels are used for
            searching for tables when filtered data quality checks are executed.
    """

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
    parallel_jobs_limit: Union[Unset, int] = UNSET
    default_grouping_configuration: Union[Unset, "DataGroupingConfigurationSpec"] = (
        UNSET
    )
    schedules: Union[Unset, "DefaultSchedulesSpec"] = UNSET
    incident_grouping: Union[Unset, "ConnectionIncidentGroupingSpec"] = UNSET
    comments: Union[Unset, List["CommentSpec"]] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
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

        parallel_jobs_limit = self.parallel_jobs_limit
        default_grouping_configuration: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.default_grouping_configuration, Unset):
            default_grouping_configuration = (
                self.default_grouping_configuration.to_dict()
            )

        schedules: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schedules, Unset):
            schedules = self.schedules.to_dict()

        incident_grouping: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.incident_grouping, Unset):
            incident_grouping = self.incident_grouping.to_dict()

        comments: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.comments, Unset):
            comments = []
            for comments_item_data in self.comments:
                comments_item = comments_item_data.to_dict()

                comments.append(comments_item)

        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
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
        if parallel_jobs_limit is not UNSET:
            field_dict["parallel_jobs_limit"] = parallel_jobs_limit
        if default_grouping_configuration is not UNSET:
            field_dict["default_grouping_configuration"] = (
                default_grouping_configuration
            )
        if schedules is not UNSET:
            field_dict["schedules"] = schedules
        if incident_grouping is not UNSET:
            field_dict["incident_grouping"] = incident_grouping
        if comments is not UNSET:
            field_dict["comments"] = comments
        if labels is not UNSET:
            field_dict["labels"] = labels

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.big_query_parameters_spec import BigQueryParametersSpec
        from ..models.comment_spec import CommentSpec
        from ..models.connection_incident_grouping_spec import (
            ConnectionIncidentGroupingSpec,
        )
        from ..models.data_grouping_configuration_spec import (
            DataGroupingConfigurationSpec,
        )
        from ..models.databricks_parameters_spec import DatabricksParametersSpec
        from ..models.default_schedules_spec import DefaultSchedulesSpec
        from ..models.duckdb_parameters_spec import DuckdbParametersSpec
        from ..models.mysql_parameters_spec import MysqlParametersSpec
        from ..models.oracle_parameters_spec import OracleParametersSpec
        from ..models.postgresql_parameters_spec import PostgresqlParametersSpec
        from ..models.presto_parameters_spec import PrestoParametersSpec
        from ..models.redshift_parameters_spec import RedshiftParametersSpec
        from ..models.snowflake_parameters_spec import SnowflakeParametersSpec
        from ..models.spark_parameters_spec import SparkParametersSpec
        from ..models.sql_server_parameters_spec import SqlServerParametersSpec
        from ..models.trino_parameters_spec import TrinoParametersSpec

        d = src_dict.copy()
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

        parallel_jobs_limit = d.pop("parallel_jobs_limit", UNSET)

        _default_grouping_configuration = d.pop("default_grouping_configuration", UNSET)
        default_grouping_configuration: Union[Unset, DataGroupingConfigurationSpec]
        if isinstance(_default_grouping_configuration, Unset):
            default_grouping_configuration = UNSET
        else:
            default_grouping_configuration = DataGroupingConfigurationSpec.from_dict(
                _default_grouping_configuration
            )

        _schedules = d.pop("schedules", UNSET)
        schedules: Union[Unset, DefaultSchedulesSpec]
        if isinstance(_schedules, Unset):
            schedules = UNSET
        else:
            schedules = DefaultSchedulesSpec.from_dict(_schedules)

        _incident_grouping = d.pop("incident_grouping", UNSET)
        incident_grouping: Union[Unset, ConnectionIncidentGroupingSpec]
        if isinstance(_incident_grouping, Unset):
            incident_grouping = UNSET
        else:
            incident_grouping = ConnectionIncidentGroupingSpec.from_dict(
                _incident_grouping
            )

        comments = []
        _comments = d.pop("comments", UNSET)
        for comments_item_data in _comments or []:
            comments_item = CommentSpec.from_dict(comments_item_data)

            comments.append(comments_item)

        labels = cast(List[str], d.pop("labels", UNSET))

        connection_spec = cls(
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
            parallel_jobs_limit=parallel_jobs_limit,
            default_grouping_configuration=default_grouping_configuration,
            schedules=schedules,
            incident_grouping=incident_grouping,
            comments=comments,
            labels=labels,
        )

        connection_spec.additional_properties = d
        return connection_spec

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
