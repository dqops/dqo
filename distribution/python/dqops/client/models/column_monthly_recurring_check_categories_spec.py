from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_monthly_recurring_checks_spec import (
        ColumnAccuracyMonthlyRecurringChecksSpec,
    )
    from ..models.column_anomaly_monthly_recurring_checks_spec import (
        ColumnAnomalyMonthlyRecurringChecksSpec,
    )
    from ..models.column_bool_monthly_recurring_checks_spec import (
        ColumnBoolMonthlyRecurringChecksSpec,
    )
    from ..models.column_datatype_monthly_recurring_checks_spec import (
        ColumnDatatypeMonthlyRecurringChecksSpec,
    )
    from ..models.column_datetime_monthly_recurring_checks_spec import (
        ColumnDatetimeMonthlyRecurringChecksSpec,
    )
    from ..models.column_integrity_monthly_recurring_checks_spec import (
        ColumnIntegrityMonthlyRecurringChecksSpec,
    )
    from ..models.column_monthly_recurring_check_categories_spec_comparisons import (
        ColumnMonthlyRecurringCheckCategoriesSpecComparisons,
    )
    from ..models.column_monthly_recurring_check_categories_spec_custom import (
        ColumnMonthlyRecurringCheckCategoriesSpecCustom,
    )
    from ..models.column_nulls_monthly_recurring_checks_spec import (
        ColumnNullsMonthlyRecurringChecksSpec,
    )
    from ..models.column_numeric_monthly_recurring_checks_spec import (
        ColumnNumericMonthlyRecurringChecksSpec,
    )
    from ..models.column_pii_monthly_recurring_checks_spec import (
        ColumnPiiMonthlyRecurringChecksSpec,
    )
    from ..models.column_schema_monthly_recurring_checks_spec import (
        ColumnSchemaMonthlyRecurringChecksSpec,
    )
    from ..models.column_sql_monthly_recurring_checks_spec import (
        ColumnSqlMonthlyRecurringChecksSpec,
    )
    from ..models.column_strings_monthly_recurring_checks_spec import (
        ColumnStringsMonthlyRecurringChecksSpec,
    )
    from ..models.column_uniqueness_monthly_recurring_checks_spec import (
        ColumnUniquenessMonthlyRecurringChecksSpec,
    )


T = TypeVar("T", bound="ColumnMonthlyRecurringCheckCategoriesSpec")


@attr.s(auto_attribs=True)
class ColumnMonthlyRecurringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnMonthlyRecurringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names.
        nulls (Union[Unset, ColumnNullsMonthlyRecurringChecksSpec]):
        numeric (Union[Unset, ColumnNumericMonthlyRecurringChecksSpec]):
        strings (Union[Unset, ColumnStringsMonthlyRecurringChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessMonthlyRecurringChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeMonthlyRecurringChecksSpec]):
        pii (Union[Unset, ColumnPiiMonthlyRecurringChecksSpec]):
        sql (Union[Unset, ColumnSqlMonthlyRecurringChecksSpec]):
        bool_ (Union[Unset, ColumnBoolMonthlyRecurringChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityMonthlyRecurringChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyMonthlyRecurringChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeMonthlyRecurringChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyMonthlyRecurringChecksSpec]):
        schema (Union[Unset, ColumnSchemaMonthlyRecurringChecksSpec]):
        comparisons (Union[Unset, ColumnMonthlyRecurringCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons at a column level. The key that identifies each comparison must match the name of a
            data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnMonthlyRecurringCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsMonthlyRecurringChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericMonthlyRecurringChecksSpec"] = UNSET
    strings: Union[Unset, "ColumnStringsMonthlyRecurringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessMonthlyRecurringChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeMonthlyRecurringChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiMonthlyRecurringChecksSpec"] = UNSET
    sql: Union[Unset, "ColumnSqlMonthlyRecurringChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolMonthlyRecurringChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityMonthlyRecurringChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyMonthlyRecurringChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeMonthlyRecurringChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyMonthlyRecurringChecksSpec"] = UNSET
    schema: Union[Unset, "ColumnSchemaMonthlyRecurringChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "ColumnMonthlyRecurringCheckCategoriesSpecComparisons"
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
        from ..models.column_accuracy_monthly_recurring_checks_spec import (
            ColumnAccuracyMonthlyRecurringChecksSpec,
        )
        from ..models.column_anomaly_monthly_recurring_checks_spec import (
            ColumnAnomalyMonthlyRecurringChecksSpec,
        )
        from ..models.column_bool_monthly_recurring_checks_spec import (
            ColumnBoolMonthlyRecurringChecksSpec,
        )
        from ..models.column_datatype_monthly_recurring_checks_spec import (
            ColumnDatatypeMonthlyRecurringChecksSpec,
        )
        from ..models.column_datetime_monthly_recurring_checks_spec import (
            ColumnDatetimeMonthlyRecurringChecksSpec,
        )
        from ..models.column_integrity_monthly_recurring_checks_spec import (
            ColumnIntegrityMonthlyRecurringChecksSpec,
        )
        from ..models.column_monthly_recurring_check_categories_spec_comparisons import (
            ColumnMonthlyRecurringCheckCategoriesSpecComparisons,
        )
        from ..models.column_monthly_recurring_check_categories_spec_custom import (
            ColumnMonthlyRecurringCheckCategoriesSpecCustom,
        )
        from ..models.column_nulls_monthly_recurring_checks_spec import (
            ColumnNullsMonthlyRecurringChecksSpec,
        )
        from ..models.column_numeric_monthly_recurring_checks_spec import (
            ColumnNumericMonthlyRecurringChecksSpec,
        )
        from ..models.column_pii_monthly_recurring_checks_spec import (
            ColumnPiiMonthlyRecurringChecksSpec,
        )
        from ..models.column_schema_monthly_recurring_checks_spec import (
            ColumnSchemaMonthlyRecurringChecksSpec,
        )
        from ..models.column_sql_monthly_recurring_checks_spec import (
            ColumnSqlMonthlyRecurringChecksSpec,
        )
        from ..models.column_strings_monthly_recurring_checks_spec import (
            ColumnStringsMonthlyRecurringChecksSpec,
        )
        from ..models.column_uniqueness_monthly_recurring_checks_spec import (
            ColumnUniquenessMonthlyRecurringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnMonthlyRecurringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnMonthlyRecurringCheckCategoriesSpecCustom.from_dict(_custom)

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsMonthlyRecurringChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsMonthlyRecurringChecksSpec.from_dict(_nulls)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericMonthlyRecurringChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericMonthlyRecurringChecksSpec.from_dict(_numeric)

        _strings = d.pop("strings", UNSET)
        strings: Union[Unset, ColumnStringsMonthlyRecurringChecksSpec]
        if isinstance(_strings, Unset):
            strings = UNSET
        else:
            strings = ColumnStringsMonthlyRecurringChecksSpec.from_dict(_strings)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessMonthlyRecurringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessMonthlyRecurringChecksSpec.from_dict(
                _uniqueness
            )

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeMonthlyRecurringChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeMonthlyRecurringChecksSpec.from_dict(_datetime_)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiMonthlyRecurringChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiMonthlyRecurringChecksSpec.from_dict(_pii)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, ColumnSqlMonthlyRecurringChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = ColumnSqlMonthlyRecurringChecksSpec.from_dict(_sql)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolMonthlyRecurringChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolMonthlyRecurringChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityMonthlyRecurringChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityMonthlyRecurringChecksSpec.from_dict(_integrity)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, ColumnAccuracyMonthlyRecurringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = ColumnAccuracyMonthlyRecurringChecksSpec.from_dict(_accuracy)

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeMonthlyRecurringChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeMonthlyRecurringChecksSpec.from_dict(_datatype)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyMonthlyRecurringChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyMonthlyRecurringChecksSpec.from_dict(_anomaly)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, ColumnSchemaMonthlyRecurringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = ColumnSchemaMonthlyRecurringChecksSpec.from_dict(_schema)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, ColumnMonthlyRecurringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = (
                ColumnMonthlyRecurringCheckCategoriesSpecComparisons.from_dict(
                    _comparisons
                )
            )

        column_monthly_recurring_check_categories_spec = cls(
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

        column_monthly_recurring_check_categories_spec.additional_properties = d
        return column_monthly_recurring_check_categories_spec

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
