from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_monthly_monitoring_checks_spec import (
        ColumnAccuracyMonthlyMonitoringChecksSpec,
    )
    from ..models.column_anomaly_monthly_monitoring_checks_spec import (
        ColumnAnomalyMonthlyMonitoringChecksSpec,
    )
    from ..models.column_bool_monthly_monitoring_checks_spec import (
        ColumnBoolMonthlyMonitoringChecksSpec,
    )
    from ..models.column_datatype_monthly_monitoring_checks_spec import (
        ColumnDatatypeMonthlyMonitoringChecksSpec,
    )
    from ..models.column_datetime_monthly_monitoring_checks_spec import (
        ColumnDatetimeMonthlyMonitoringChecksSpec,
    )
    from ..models.column_integrity_monthly_monitoring_checks_spec import (
        ColumnIntegrityMonthlyMonitoringChecksSpec,
    )
    from ..models.column_monthly_monitoring_check_categories_spec_comparisons import (
        ColumnMonthlyMonitoringCheckCategoriesSpecComparisons,
    )
    from ..models.column_monthly_monitoring_check_categories_spec_custom import (
        ColumnMonthlyMonitoringCheckCategoriesSpecCustom,
    )
    from ..models.column_nulls_monthly_monitoring_checks_spec import (
        ColumnNullsMonthlyMonitoringChecksSpec,
    )
    from ..models.column_numeric_monthly_monitoring_checks_spec import (
        ColumnNumericMonthlyMonitoringChecksSpec,
    )
    from ..models.column_pii_monthly_monitoring_checks_spec import (
        ColumnPiiMonthlyMonitoringChecksSpec,
    )
    from ..models.column_schema_monthly_monitoring_checks_spec import (
        ColumnSchemaMonthlyMonitoringChecksSpec,
    )
    from ..models.column_sql_monthly_monitoring_checks_spec import (
        ColumnSqlMonthlyMonitoringChecksSpec,
    )
    from ..models.column_strings_monthly_monitoring_checks_spec import (
        ColumnStringsMonthlyMonitoringChecksSpec,
    )
    from ..models.column_uniqueness_monthly_monitoring_checks_spec import (
        ColumnUniquenessMonthlyMonitoringChecksSpec,
    )


T = TypeVar("T", bound="ColumnMonthlyMonitoringCheckCategoriesSpec")


@_attrs_define
class ColumnMonthlyMonitoringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        nulls (Union[Unset, ColumnNullsMonthlyMonitoringChecksSpec]):
        numeric (Union[Unset, ColumnNumericMonthlyMonitoringChecksSpec]):
        strings (Union[Unset, ColumnStringsMonthlyMonitoringChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessMonthlyMonitoringChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeMonthlyMonitoringChecksSpec]):
        pii (Union[Unset, ColumnPiiMonthlyMonitoringChecksSpec]):
        sql (Union[Unset, ColumnSqlMonthlyMonitoringChecksSpec]):
        bool_ (Union[Unset, ColumnBoolMonthlyMonitoringChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityMonthlyMonitoringChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyMonthlyMonitoringChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeMonthlyMonitoringChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyMonthlyMonitoringChecksSpec]):
        schema (Union[Unset, ColumnSchemaMonthlyMonitoringChecksSpec]):
        comparisons (Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpecComparisons]): Dictionary of configuration
            of checks for table comparisons at a column level. The key that identifies each comparison must match the name
            of a data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnMonthlyMonitoringCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsMonthlyMonitoringChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericMonthlyMonitoringChecksSpec"] = UNSET
    strings: Union[Unset, "ColumnStringsMonthlyMonitoringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessMonthlyMonitoringChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeMonthlyMonitoringChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiMonthlyMonitoringChecksSpec"] = UNSET
    sql: Union[Unset, "ColumnSqlMonthlyMonitoringChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolMonthlyMonitoringChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityMonthlyMonitoringChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyMonthlyMonitoringChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeMonthlyMonitoringChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyMonthlyMonitoringChecksSpec"] = UNSET
    schema: Union[Unset, "ColumnSchemaMonthlyMonitoringChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "ColumnMonthlyMonitoringCheckCategoriesSpecComparisons"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom, Unset):
            custom = self.custom.to_dict()

        nulls: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls, Unset):
            nulls = self.nulls.to_dict()

        numeric: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.numeric, Unset):
            numeric = self.numeric.to_dict()

        strings: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.strings, Unset):
            strings = self.strings.to_dict()

        uniqueness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.uniqueness, Unset):
            uniqueness = self.uniqueness.to_dict()

        datetime_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.datetime_, Unset):
            datetime_ = self.datetime_.to_dict()

        pii: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.pii, Unset):
            pii = self.pii.to_dict()

        sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql, Unset):
            sql = self.sql.to_dict()

        bool_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.bool_, Unset):
            bool_ = self.bool_.to_dict()

        integrity: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.integrity, Unset):
            integrity = self.integrity.to_dict()

        accuracy: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.accuracy, Unset):
            accuracy = self.accuracy.to_dict()

        datatype: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.datatype, Unset):
            datatype = self.datatype.to_dict()

        anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.anomaly, Unset):
            anomaly = self.anomaly.to_dict()

        schema: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schema, Unset):
            schema = self.schema.to_dict()

        comparisons: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.comparisons, Unset):
            comparisons = self.comparisons.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom is not UNSET:
            field_dict["custom"] = custom
        if nulls is not UNSET:
            field_dict["nulls"] = nulls
        if numeric is not UNSET:
            field_dict["numeric"] = numeric
        if strings is not UNSET:
            field_dict["strings"] = strings
        if uniqueness is not UNSET:
            field_dict["uniqueness"] = uniqueness
        if datetime_ is not UNSET:
            field_dict["datetime"] = datetime_
        if pii is not UNSET:
            field_dict["pii"] = pii
        if sql is not UNSET:
            field_dict["sql"] = sql
        if bool_ is not UNSET:
            field_dict["bool"] = bool_
        if integrity is not UNSET:
            field_dict["integrity"] = integrity
        if accuracy is not UNSET:
            field_dict["accuracy"] = accuracy
        if datatype is not UNSET:
            field_dict["datatype"] = datatype
        if anomaly is not UNSET:
            field_dict["anomaly"] = anomaly
        if schema is not UNSET:
            field_dict["schema"] = schema
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accuracy_monthly_monitoring_checks_spec import (
            ColumnAccuracyMonthlyMonitoringChecksSpec,
        )
        from ..models.column_anomaly_monthly_monitoring_checks_spec import (
            ColumnAnomalyMonthlyMonitoringChecksSpec,
        )
        from ..models.column_bool_monthly_monitoring_checks_spec import (
            ColumnBoolMonthlyMonitoringChecksSpec,
        )
        from ..models.column_datatype_monthly_monitoring_checks_spec import (
            ColumnDatatypeMonthlyMonitoringChecksSpec,
        )
        from ..models.column_datetime_monthly_monitoring_checks_spec import (
            ColumnDatetimeMonthlyMonitoringChecksSpec,
        )
        from ..models.column_integrity_monthly_monitoring_checks_spec import (
            ColumnIntegrityMonthlyMonitoringChecksSpec,
        )
        from ..models.column_monthly_monitoring_check_categories_spec_comparisons import (
            ColumnMonthlyMonitoringCheckCategoriesSpecComparisons,
        )
        from ..models.column_monthly_monitoring_check_categories_spec_custom import (
            ColumnMonthlyMonitoringCheckCategoriesSpecCustom,
        )
        from ..models.column_nulls_monthly_monitoring_checks_spec import (
            ColumnNullsMonthlyMonitoringChecksSpec,
        )
        from ..models.column_numeric_monthly_monitoring_checks_spec import (
            ColumnNumericMonthlyMonitoringChecksSpec,
        )
        from ..models.column_pii_monthly_monitoring_checks_spec import (
            ColumnPiiMonthlyMonitoringChecksSpec,
        )
        from ..models.column_schema_monthly_monitoring_checks_spec import (
            ColumnSchemaMonthlyMonitoringChecksSpec,
        )
        from ..models.column_sql_monthly_monitoring_checks_spec import (
            ColumnSqlMonthlyMonitoringChecksSpec,
        )
        from ..models.column_strings_monthly_monitoring_checks_spec import (
            ColumnStringsMonthlyMonitoringChecksSpec,
        )
        from ..models.column_uniqueness_monthly_monitoring_checks_spec import (
            ColumnUniquenessMonthlyMonitoringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnMonthlyMonitoringCheckCategoriesSpecCustom.from_dict(_custom)

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsMonthlyMonitoringChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsMonthlyMonitoringChecksSpec.from_dict(_nulls)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericMonthlyMonitoringChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericMonthlyMonitoringChecksSpec.from_dict(_numeric)

        _strings = d.pop("strings", UNSET)
        strings: Union[Unset, ColumnStringsMonthlyMonitoringChecksSpec]
        if isinstance(_strings, Unset):
            strings = UNSET
        else:
            strings = ColumnStringsMonthlyMonitoringChecksSpec.from_dict(_strings)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessMonthlyMonitoringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessMonthlyMonitoringChecksSpec.from_dict(
                _uniqueness
            )

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeMonthlyMonitoringChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeMonthlyMonitoringChecksSpec.from_dict(_datetime_)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiMonthlyMonitoringChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiMonthlyMonitoringChecksSpec.from_dict(_pii)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, ColumnSqlMonthlyMonitoringChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = ColumnSqlMonthlyMonitoringChecksSpec.from_dict(_sql)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolMonthlyMonitoringChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolMonthlyMonitoringChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityMonthlyMonitoringChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityMonthlyMonitoringChecksSpec.from_dict(_integrity)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, ColumnAccuracyMonthlyMonitoringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = ColumnAccuracyMonthlyMonitoringChecksSpec.from_dict(_accuracy)

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeMonthlyMonitoringChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeMonthlyMonitoringChecksSpec.from_dict(_datatype)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyMonthlyMonitoringChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyMonthlyMonitoringChecksSpec.from_dict(_anomaly)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, ColumnSchemaMonthlyMonitoringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = ColumnSchemaMonthlyMonitoringChecksSpec.from_dict(_schema)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = (
                ColumnMonthlyMonitoringCheckCategoriesSpecComparisons.from_dict(
                    _comparisons
                )
            )

        column_monthly_monitoring_check_categories_spec = cls(
            custom=custom,
            nulls=nulls,
            numeric=numeric,
            strings=strings,
            uniqueness=uniqueness,
            datetime_=datetime_,
            pii=pii,
            sql=sql,
            bool_=bool_,
            integrity=integrity,
            accuracy=accuracy,
            datatype=datatype,
            anomaly=anomaly,
            schema=schema,
            comparisons=comparisons,
        )

        column_monthly_monitoring_check_categories_spec.additional_properties = d
        return column_monthly_monitoring_check_categories_spec

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
