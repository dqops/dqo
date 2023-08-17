from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_daily_recurring_checks_spec import (
        ColumnAccuracyDailyRecurringChecksSpec,
    )
    from ..models.column_anomaly_daily_recurring_checks_spec import (
        ColumnAnomalyDailyRecurringChecksSpec,
    )
    from ..models.column_bool_daily_recurring_checks_spec import (
        ColumnBoolDailyRecurringChecksSpec,
    )
    from ..models.column_daily_recurring_check_categories_spec_comparisons import (
        ColumnDailyRecurringCheckCategoriesSpecComparisons,
    )
    from ..models.column_daily_recurring_check_categories_spec_custom import (
        ColumnDailyRecurringCheckCategoriesSpecCustom,
    )
    from ..models.column_datatype_daily_recurring_checks_spec import (
        ColumnDatatypeDailyRecurringChecksSpec,
    )
    from ..models.column_datetime_daily_recurring_checks_spec import (
        ColumnDatetimeDailyRecurringChecksSpec,
    )
    from ..models.column_integrity_daily_recurring_checks_spec import (
        ColumnIntegrityDailyRecurringChecksSpec,
    )
    from ..models.column_nulls_daily_recurring_checks_spec import (
        ColumnNullsDailyRecurringChecksSpec,
    )
    from ..models.column_numeric_daily_recurring_checks_spec import (
        ColumnNumericDailyRecurringChecksSpec,
    )
    from ..models.column_pii_daily_recurring_checks_spec import (
        ColumnPiiDailyRecurringChecksSpec,
    )
    from ..models.column_schema_daily_recurring_checks_spec import (
        ColumnSchemaDailyRecurringChecksSpec,
    )
    from ..models.column_sql_daily_recurring_checks_spec import (
        ColumnSqlDailyRecurringChecksSpec,
    )
    from ..models.column_strings_daily_recurring_checks_spec import (
        ColumnStringsDailyRecurringChecksSpec,
    )
    from ..models.column_uniqueness_daily_recurring_checks_spec import (
        ColumnUniquenessDailyRecurringChecksSpec,
    )


T = TypeVar("T", bound="ColumnDailyRecurringCheckCategoriesSpec")


@attr.s(auto_attribs=True)
class ColumnDailyRecurringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnDailyRecurringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are
            check names.
        nulls (Union[Unset, ColumnNullsDailyRecurringChecksSpec]):
        numeric (Union[Unset, ColumnNumericDailyRecurringChecksSpec]):
        strings (Union[Unset, ColumnStringsDailyRecurringChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessDailyRecurringChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeDailyRecurringChecksSpec]):
        pii (Union[Unset, ColumnPiiDailyRecurringChecksSpec]):
        sql (Union[Unset, ColumnSqlDailyRecurringChecksSpec]):
        bool_ (Union[Unset, ColumnBoolDailyRecurringChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityDailyRecurringChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyDailyRecurringChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeDailyRecurringChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyDailyRecurringChecksSpec]):
        schema (Union[Unset, ColumnSchemaDailyRecurringChecksSpec]):
        comparisons (Union[Unset, ColumnDailyRecurringCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons at a column level. The key that identifies each comparison must match the name of a
            data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnDailyRecurringCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsDailyRecurringChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericDailyRecurringChecksSpec"] = UNSET
    strings: Union[Unset, "ColumnStringsDailyRecurringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessDailyRecurringChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeDailyRecurringChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiDailyRecurringChecksSpec"] = UNSET
    sql: Union[Unset, "ColumnSqlDailyRecurringChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolDailyRecurringChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityDailyRecurringChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyDailyRecurringChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeDailyRecurringChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyDailyRecurringChecksSpec"] = UNSET
    schema: Union[Unset, "ColumnSchemaDailyRecurringChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "ColumnDailyRecurringCheckCategoriesSpecComparisons"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        from ..models.column_accuracy_daily_recurring_checks_spec import (
            ColumnAccuracyDailyRecurringChecksSpec,
        )
        from ..models.column_anomaly_daily_recurring_checks_spec import (
            ColumnAnomalyDailyRecurringChecksSpec,
        )
        from ..models.column_bool_daily_recurring_checks_spec import (
            ColumnBoolDailyRecurringChecksSpec,
        )
        from ..models.column_daily_recurring_check_categories_spec_comparisons import (
            ColumnDailyRecurringCheckCategoriesSpecComparisons,
        )
        from ..models.column_daily_recurring_check_categories_spec_custom import (
            ColumnDailyRecurringCheckCategoriesSpecCustom,
        )
        from ..models.column_datatype_daily_recurring_checks_spec import (
            ColumnDatatypeDailyRecurringChecksSpec,
        )
        from ..models.column_datetime_daily_recurring_checks_spec import (
            ColumnDatetimeDailyRecurringChecksSpec,
        )
        from ..models.column_integrity_daily_recurring_checks_spec import (
            ColumnIntegrityDailyRecurringChecksSpec,
        )
        from ..models.column_nulls_daily_recurring_checks_spec import (
            ColumnNullsDailyRecurringChecksSpec,
        )
        from ..models.column_numeric_daily_recurring_checks_spec import (
            ColumnNumericDailyRecurringChecksSpec,
        )
        from ..models.column_pii_daily_recurring_checks_spec import (
            ColumnPiiDailyRecurringChecksSpec,
        )
        from ..models.column_schema_daily_recurring_checks_spec import (
            ColumnSchemaDailyRecurringChecksSpec,
        )
        from ..models.column_sql_daily_recurring_checks_spec import (
            ColumnSqlDailyRecurringChecksSpec,
        )
        from ..models.column_strings_daily_recurring_checks_spec import (
            ColumnStringsDailyRecurringChecksSpec,
        )
        from ..models.column_uniqueness_daily_recurring_checks_spec import (
            ColumnUniquenessDailyRecurringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnDailyRecurringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnDailyRecurringCheckCategoriesSpecCustom.from_dict(_custom)

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsDailyRecurringChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsDailyRecurringChecksSpec.from_dict(_nulls)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericDailyRecurringChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericDailyRecurringChecksSpec.from_dict(_numeric)

        _strings = d.pop("strings", UNSET)
        strings: Union[Unset, ColumnStringsDailyRecurringChecksSpec]
        if isinstance(_strings, Unset):
            strings = UNSET
        else:
            strings = ColumnStringsDailyRecurringChecksSpec.from_dict(_strings)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessDailyRecurringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessDailyRecurringChecksSpec.from_dict(_uniqueness)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeDailyRecurringChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeDailyRecurringChecksSpec.from_dict(_datetime_)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiDailyRecurringChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiDailyRecurringChecksSpec.from_dict(_pii)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, ColumnSqlDailyRecurringChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = ColumnSqlDailyRecurringChecksSpec.from_dict(_sql)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolDailyRecurringChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolDailyRecurringChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityDailyRecurringChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityDailyRecurringChecksSpec.from_dict(_integrity)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, ColumnAccuracyDailyRecurringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = ColumnAccuracyDailyRecurringChecksSpec.from_dict(_accuracy)

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeDailyRecurringChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeDailyRecurringChecksSpec.from_dict(_datatype)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyDailyRecurringChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyDailyRecurringChecksSpec.from_dict(_anomaly)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, ColumnSchemaDailyRecurringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = ColumnSchemaDailyRecurringChecksSpec.from_dict(_schema)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, ColumnDailyRecurringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = ColumnDailyRecurringCheckCategoriesSpecComparisons.from_dict(
                _comparisons
            )

        column_daily_recurring_check_categories_spec = cls(
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

        column_daily_recurring_check_categories_spec.additional_properties = d
        return column_daily_recurring_check_categories_spec

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
