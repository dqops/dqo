from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_daily_monitoring_checks_spec import (
        ColumnAcceptedValuesDailyMonitoringChecksSpec,
    )
    from ..models.column_accuracy_daily_monitoring_checks_spec import (
        ColumnAccuracyDailyMonitoringChecksSpec,
    )
    from ..models.column_anomaly_daily_monitoring_checks_spec import (
        ColumnAnomalyDailyMonitoringChecksSpec,
    )
    from ..models.column_bool_daily_monitoring_checks_spec import (
        ColumnBoolDailyMonitoringChecksSpec,
    )
    from ..models.column_conversions_daily_monitoring_checks_spec import (
        ColumnConversionsDailyMonitoringChecksSpec,
    )
    from ..models.column_custom_sql_daily_monitoring_checks_spec import (
        ColumnCustomSqlDailyMonitoringChecksSpec,
    )
    from ..models.column_daily_monitoring_check_categories_spec_comparisons import (
        ColumnDailyMonitoringCheckCategoriesSpecComparisons,
    )
    from ..models.column_daily_monitoring_check_categories_spec_custom import (
        ColumnDailyMonitoringCheckCategoriesSpecCustom,
    )
    from ..models.column_datatype_daily_monitoring_checks_spec import (
        ColumnDatatypeDailyMonitoringChecksSpec,
    )
    from ..models.column_datetime_daily_monitoring_checks_spec import (
        ColumnDatetimeDailyMonitoringChecksSpec,
    )
    from ..models.column_integrity_daily_monitoring_checks_spec import (
        ColumnIntegrityDailyMonitoringChecksSpec,
    )
    from ..models.column_nulls_daily_monitoring_checks_spec import (
        ColumnNullsDailyMonitoringChecksSpec,
    )
    from ..models.column_numeric_daily_monitoring_checks_spec import (
        ColumnNumericDailyMonitoringChecksSpec,
    )
    from ..models.column_patterns_daily_monitoring_checks_spec import (
        ColumnPatternsDailyMonitoringChecksSpec,
    )
    from ..models.column_pii_daily_monitoring_checks_spec import (
        ColumnPiiDailyMonitoringChecksSpec,
    )
    from ..models.column_schema_daily_monitoring_checks_spec import (
        ColumnSchemaDailyMonitoringChecksSpec,
    )
    from ..models.column_text_daily_monitoring_checks_spec import (
        ColumnTextDailyMonitoringChecksSpec,
    )
    from ..models.column_uniqueness_daily_monitoring_checks_spec import (
        ColumnUniquenessDailyMonitoringChecksSpec,
    )
    from ..models.column_whitespace_daily_monitoring_checks_spec import (
        ColumnWhitespaceDailyMonitoringChecksSpec,
    )


T = TypeVar("T", bound="ColumnDailyMonitoringCheckCategoriesSpec")


@_attrs_define
class ColumnDailyMonitoringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnDailyMonitoringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are
            check names within this category.
        nulls (Union[Unset, ColumnNullsDailyMonitoringChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessDailyMonitoringChecksSpec]):
        accepted_values (Union[Unset, ColumnAcceptedValuesDailyMonitoringChecksSpec]):
        text (Union[Unset, ColumnTextDailyMonitoringChecksSpec]):
        whitespace (Union[Unset, ColumnWhitespaceDailyMonitoringChecksSpec]):
        conversions (Union[Unset, ColumnConversionsDailyMonitoringChecksSpec]):
        patterns (Union[Unset, ColumnPatternsDailyMonitoringChecksSpec]):
        pii (Union[Unset, ColumnPiiDailyMonitoringChecksSpec]):
        numeric (Union[Unset, ColumnNumericDailyMonitoringChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyDailyMonitoringChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeDailyMonitoringChecksSpec]):
        bool_ (Union[Unset, ColumnBoolDailyMonitoringChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityDailyMonitoringChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyDailyMonitoringChecksSpec]):
        custom_sql (Union[Unset, ColumnCustomSqlDailyMonitoringChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeDailyMonitoringChecksSpec]):
        schema (Union[Unset, ColumnSchemaDailyMonitoringChecksSpec]):
        comparisons (Union[Unset, ColumnDailyMonitoringCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons at a column level. The key that identifies each comparison must match the name of a
            data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnDailyMonitoringCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsDailyMonitoringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessDailyMonitoringChecksSpec"] = UNSET
    accepted_values: Union[Unset, "ColumnAcceptedValuesDailyMonitoringChecksSpec"] = (
        UNSET
    )
    text: Union[Unset, "ColumnTextDailyMonitoringChecksSpec"] = UNSET
    whitespace: Union[Unset, "ColumnWhitespaceDailyMonitoringChecksSpec"] = UNSET
    conversions: Union[Unset, "ColumnConversionsDailyMonitoringChecksSpec"] = UNSET
    patterns: Union[Unset, "ColumnPatternsDailyMonitoringChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiDailyMonitoringChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericDailyMonitoringChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyDailyMonitoringChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeDailyMonitoringChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolDailyMonitoringChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityDailyMonitoringChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyDailyMonitoringChecksSpec"] = UNSET
    custom_sql: Union[Unset, "ColumnCustomSqlDailyMonitoringChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeDailyMonitoringChecksSpec"] = UNSET
    schema: Union[Unset, "ColumnSchemaDailyMonitoringChecksSpec"] = UNSET
    comparisons: Union[Unset, "ColumnDailyMonitoringCheckCategoriesSpecComparisons"] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom, Unset):
            custom = self.custom.to_dict()

        nulls: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls, Unset):
            nulls = self.nulls.to_dict()

        uniqueness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.uniqueness, Unset):
            uniqueness = self.uniqueness.to_dict()

        accepted_values: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.accepted_values, Unset):
            accepted_values = self.accepted_values.to_dict()

        text: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.text, Unset):
            text = self.text.to_dict()

        whitespace: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.whitespace, Unset):
            whitespace = self.whitespace.to_dict()

        conversions: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.conversions, Unset):
            conversions = self.conversions.to_dict()

        patterns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.patterns, Unset):
            patterns = self.patterns.to_dict()

        pii: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.pii, Unset):
            pii = self.pii.to_dict()

        numeric: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.numeric, Unset):
            numeric = self.numeric.to_dict()

        anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.anomaly, Unset):
            anomaly = self.anomaly.to_dict()

        datetime_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.datetime_, Unset):
            datetime_ = self.datetime_.to_dict()

        bool_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.bool_, Unset):
            bool_ = self.bool_.to_dict()

        integrity: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.integrity, Unset):
            integrity = self.integrity.to_dict()

        accuracy: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.accuracy, Unset):
            accuracy = self.accuracy.to_dict()

        custom_sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_sql, Unset):
            custom_sql = self.custom_sql.to_dict()

        datatype: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.datatype, Unset):
            datatype = self.datatype.to_dict()

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
        if uniqueness is not UNSET:
            field_dict["uniqueness"] = uniqueness
        if accepted_values is not UNSET:
            field_dict["accepted_values"] = accepted_values
        if text is not UNSET:
            field_dict["text"] = text
        if whitespace is not UNSET:
            field_dict["whitespace"] = whitespace
        if conversions is not UNSET:
            field_dict["conversions"] = conversions
        if patterns is not UNSET:
            field_dict["patterns"] = patterns
        if pii is not UNSET:
            field_dict["pii"] = pii
        if numeric is not UNSET:
            field_dict["numeric"] = numeric
        if anomaly is not UNSET:
            field_dict["anomaly"] = anomaly
        if datetime_ is not UNSET:
            field_dict["datetime"] = datetime_
        if bool_ is not UNSET:
            field_dict["bool"] = bool_
        if integrity is not UNSET:
            field_dict["integrity"] = integrity
        if accuracy is not UNSET:
            field_dict["accuracy"] = accuracy
        if custom_sql is not UNSET:
            field_dict["custom_sql"] = custom_sql
        if datatype is not UNSET:
            field_dict["datatype"] = datatype
        if schema is not UNSET:
            field_dict["schema"] = schema
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accepted_values_daily_monitoring_checks_spec import (
            ColumnAcceptedValuesDailyMonitoringChecksSpec,
        )
        from ..models.column_accuracy_daily_monitoring_checks_spec import (
            ColumnAccuracyDailyMonitoringChecksSpec,
        )
        from ..models.column_anomaly_daily_monitoring_checks_spec import (
            ColumnAnomalyDailyMonitoringChecksSpec,
        )
        from ..models.column_bool_daily_monitoring_checks_spec import (
            ColumnBoolDailyMonitoringChecksSpec,
        )
        from ..models.column_conversions_daily_monitoring_checks_spec import (
            ColumnConversionsDailyMonitoringChecksSpec,
        )
        from ..models.column_custom_sql_daily_monitoring_checks_spec import (
            ColumnCustomSqlDailyMonitoringChecksSpec,
        )
        from ..models.column_daily_monitoring_check_categories_spec_comparisons import (
            ColumnDailyMonitoringCheckCategoriesSpecComparisons,
        )
        from ..models.column_daily_monitoring_check_categories_spec_custom import (
            ColumnDailyMonitoringCheckCategoriesSpecCustom,
        )
        from ..models.column_datatype_daily_monitoring_checks_spec import (
            ColumnDatatypeDailyMonitoringChecksSpec,
        )
        from ..models.column_datetime_daily_monitoring_checks_spec import (
            ColumnDatetimeDailyMonitoringChecksSpec,
        )
        from ..models.column_integrity_daily_monitoring_checks_spec import (
            ColumnIntegrityDailyMonitoringChecksSpec,
        )
        from ..models.column_nulls_daily_monitoring_checks_spec import (
            ColumnNullsDailyMonitoringChecksSpec,
        )
        from ..models.column_numeric_daily_monitoring_checks_spec import (
            ColumnNumericDailyMonitoringChecksSpec,
        )
        from ..models.column_patterns_daily_monitoring_checks_spec import (
            ColumnPatternsDailyMonitoringChecksSpec,
        )
        from ..models.column_pii_daily_monitoring_checks_spec import (
            ColumnPiiDailyMonitoringChecksSpec,
        )
        from ..models.column_schema_daily_monitoring_checks_spec import (
            ColumnSchemaDailyMonitoringChecksSpec,
        )
        from ..models.column_text_daily_monitoring_checks_spec import (
            ColumnTextDailyMonitoringChecksSpec,
        )
        from ..models.column_uniqueness_daily_monitoring_checks_spec import (
            ColumnUniquenessDailyMonitoringChecksSpec,
        )
        from ..models.column_whitespace_daily_monitoring_checks_spec import (
            ColumnWhitespaceDailyMonitoringChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnDailyMonitoringCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnDailyMonitoringCheckCategoriesSpecCustom.from_dict(_custom)

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsDailyMonitoringChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsDailyMonitoringChecksSpec.from_dict(_nulls)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessDailyMonitoringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessDailyMonitoringChecksSpec.from_dict(
                _uniqueness
            )

        _accepted_values = d.pop("accepted_values", UNSET)
        accepted_values: Union[Unset, ColumnAcceptedValuesDailyMonitoringChecksSpec]
        if isinstance(_accepted_values, Unset):
            accepted_values = UNSET
        else:
            accepted_values = ColumnAcceptedValuesDailyMonitoringChecksSpec.from_dict(
                _accepted_values
            )

        _text = d.pop("text", UNSET)
        text: Union[Unset, ColumnTextDailyMonitoringChecksSpec]
        if isinstance(_text, Unset):
            text = UNSET
        else:
            text = ColumnTextDailyMonitoringChecksSpec.from_dict(_text)

        _whitespace = d.pop("whitespace", UNSET)
        whitespace: Union[Unset, ColumnWhitespaceDailyMonitoringChecksSpec]
        if isinstance(_whitespace, Unset):
            whitespace = UNSET
        else:
            whitespace = ColumnWhitespaceDailyMonitoringChecksSpec.from_dict(
                _whitespace
            )

        _conversions = d.pop("conversions", UNSET)
        conversions: Union[Unset, ColumnConversionsDailyMonitoringChecksSpec]
        if isinstance(_conversions, Unset):
            conversions = UNSET
        else:
            conversions = ColumnConversionsDailyMonitoringChecksSpec.from_dict(
                _conversions
            )

        _patterns = d.pop("patterns", UNSET)
        patterns: Union[Unset, ColumnPatternsDailyMonitoringChecksSpec]
        if isinstance(_patterns, Unset):
            patterns = UNSET
        else:
            patterns = ColumnPatternsDailyMonitoringChecksSpec.from_dict(_patterns)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiDailyMonitoringChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiDailyMonitoringChecksSpec.from_dict(_pii)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericDailyMonitoringChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericDailyMonitoringChecksSpec.from_dict(_numeric)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyDailyMonitoringChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyDailyMonitoringChecksSpec.from_dict(_anomaly)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeDailyMonitoringChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeDailyMonitoringChecksSpec.from_dict(_datetime_)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolDailyMonitoringChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolDailyMonitoringChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityDailyMonitoringChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityDailyMonitoringChecksSpec.from_dict(_integrity)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, ColumnAccuracyDailyMonitoringChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = ColumnAccuracyDailyMonitoringChecksSpec.from_dict(_accuracy)

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, ColumnCustomSqlDailyMonitoringChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = ColumnCustomSqlDailyMonitoringChecksSpec.from_dict(_custom_sql)

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeDailyMonitoringChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeDailyMonitoringChecksSpec.from_dict(_datatype)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, ColumnSchemaDailyMonitoringChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = ColumnSchemaDailyMonitoringChecksSpec.from_dict(_schema)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, ColumnDailyMonitoringCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = ColumnDailyMonitoringCheckCategoriesSpecComparisons.from_dict(
                _comparisons
            )

        column_daily_monitoring_check_categories_spec = cls(
            custom=custom,
            nulls=nulls,
            uniqueness=uniqueness,
            accepted_values=accepted_values,
            text=text,
            whitespace=whitespace,
            conversions=conversions,
            patterns=patterns,
            pii=pii,
            numeric=numeric,
            anomaly=anomaly,
            datetime_=datetime_,
            bool_=bool_,
            integrity=integrity,
            accuracy=accuracy,
            custom_sql=custom_sql,
            datatype=datatype,
            schema=schema,
            comparisons=comparisons,
        )

        column_daily_monitoring_check_categories_spec.additional_properties = d
        return column_daily_monitoring_check_categories_spec

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
