from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_monthly_monitoring_checks_spec import (
        ColumnAcceptedValuesMonthlyMonitoringChecksSpec,
    )
    from ..models.column_accuracy_monthly_monitoring_checks_spec import (
        ColumnAccuracyMonthlyMonitoringChecksSpec,
    )
    from ..models.column_bool_monthly_monitoring_checks_spec import (
        ColumnBoolMonthlyMonitoringChecksSpec,
    )
    from ..models.column_conversions_monthly_monitoring_checks_spec import (
        ColumnConversionsMonthlyMonitoringChecksSpec,
    )
    from ..models.column_custom_sql_monthly_monitoring_checks_spec import (
        ColumnCustomSqlMonthlyMonitoringChecksSpec,
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
    from ..models.column_patterns_monthly_monitoring_checks_spec import (
        ColumnPatternsMonthlyMonitoringChecksSpec,
    )
    from ..models.column_pii_monthly_monitoring_checks_spec import (
        ColumnPiiMonthlyMonitoringChecksSpec,
    )
    from ..models.column_schema_monthly_monitoring_checks_spec import (
        ColumnSchemaMonthlyMonitoringChecksSpec,
    )
    from ..models.column_text_monthly_monitoring_checks_spec import (
        ColumnTextMonthlyMonitoringChecksSpec,
    )
    from ..models.column_uniqueness_monthly_monitoring_checks_spec import (
        ColumnUniquenessMonthlyMonitoringChecksSpec,
    )
    from ..models.column_whitespace_monthly_monitoring_checks_spec import (
        ColumnWhitespaceMonthlyMonitoringChecksSpec,
    )


T = TypeVar("T", bound="ColumnMonthlyMonitoringCheckCategoriesSpec")


@_attrs_define
class ColumnMonthlyMonitoringCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        nulls (Union[Unset, ColumnNullsMonthlyMonitoringChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessMonthlyMonitoringChecksSpec]):
        accepted_values (Union[Unset, ColumnAcceptedValuesMonthlyMonitoringChecksSpec]):
        text (Union[Unset, ColumnTextMonthlyMonitoringChecksSpec]):
        whitespace (Union[Unset, ColumnWhitespaceMonthlyMonitoringChecksSpec]):
        conversions (Union[Unset, ColumnConversionsMonthlyMonitoringChecksSpec]):
        patterns (Union[Unset, ColumnPatternsMonthlyMonitoringChecksSpec]):
        pii (Union[Unset, ColumnPiiMonthlyMonitoringChecksSpec]):
        numeric (Union[Unset, ColumnNumericMonthlyMonitoringChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeMonthlyMonitoringChecksSpec]):
        bool_ (Union[Unset, ColumnBoolMonthlyMonitoringChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityMonthlyMonitoringChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyMonthlyMonitoringChecksSpec]):
        custom_sql (Union[Unset, ColumnCustomSqlMonthlyMonitoringChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeMonthlyMonitoringChecksSpec]):
        schema (Union[Unset, ColumnSchemaMonthlyMonitoringChecksSpec]):
        comparisons (Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpecComparisons]): Dictionary of configuration
            of checks for table comparisons at a column level. The key that identifies each comparison must match the name
            of a data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnMonthlyMonitoringCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsMonthlyMonitoringChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessMonthlyMonitoringChecksSpec"] = UNSET
    accepted_values: Union[Unset, "ColumnAcceptedValuesMonthlyMonitoringChecksSpec"] = (
        UNSET
    )
    text: Union[Unset, "ColumnTextMonthlyMonitoringChecksSpec"] = UNSET
    whitespace: Union[Unset, "ColumnWhitespaceMonthlyMonitoringChecksSpec"] = UNSET
    conversions: Union[Unset, "ColumnConversionsMonthlyMonitoringChecksSpec"] = UNSET
    patterns: Union[Unset, "ColumnPatternsMonthlyMonitoringChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiMonthlyMonitoringChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericMonthlyMonitoringChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeMonthlyMonitoringChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolMonthlyMonitoringChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityMonthlyMonitoringChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyMonthlyMonitoringChecksSpec"] = UNSET
    custom_sql: Union[Unset, "ColumnCustomSqlMonthlyMonitoringChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeMonthlyMonitoringChecksSpec"] = UNSET
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
        from ..models.column_accepted_values_monthly_monitoring_checks_spec import (
            ColumnAcceptedValuesMonthlyMonitoringChecksSpec,
        )
        from ..models.column_accuracy_monthly_monitoring_checks_spec import (
            ColumnAccuracyMonthlyMonitoringChecksSpec,
        )
        from ..models.column_bool_monthly_monitoring_checks_spec import (
            ColumnBoolMonthlyMonitoringChecksSpec,
        )
        from ..models.column_conversions_monthly_monitoring_checks_spec import (
            ColumnConversionsMonthlyMonitoringChecksSpec,
        )
        from ..models.column_custom_sql_monthly_monitoring_checks_spec import (
            ColumnCustomSqlMonthlyMonitoringChecksSpec,
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
        from ..models.column_patterns_monthly_monitoring_checks_spec import (
            ColumnPatternsMonthlyMonitoringChecksSpec,
        )
        from ..models.column_pii_monthly_monitoring_checks_spec import (
            ColumnPiiMonthlyMonitoringChecksSpec,
        )
        from ..models.column_schema_monthly_monitoring_checks_spec import (
            ColumnSchemaMonthlyMonitoringChecksSpec,
        )
        from ..models.column_text_monthly_monitoring_checks_spec import (
            ColumnTextMonthlyMonitoringChecksSpec,
        )
        from ..models.column_uniqueness_monthly_monitoring_checks_spec import (
            ColumnUniquenessMonthlyMonitoringChecksSpec,
        )
        from ..models.column_whitespace_monthly_monitoring_checks_spec import (
            ColumnWhitespaceMonthlyMonitoringChecksSpec,
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

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessMonthlyMonitoringChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessMonthlyMonitoringChecksSpec.from_dict(
                _uniqueness
            )

        _accepted_values = d.pop("accepted_values", UNSET)
        accepted_values: Union[Unset, ColumnAcceptedValuesMonthlyMonitoringChecksSpec]
        if isinstance(_accepted_values, Unset):
            accepted_values = UNSET
        else:
            accepted_values = ColumnAcceptedValuesMonthlyMonitoringChecksSpec.from_dict(
                _accepted_values
            )

        _text = d.pop("text", UNSET)
        text: Union[Unset, ColumnTextMonthlyMonitoringChecksSpec]
        if isinstance(_text, Unset):
            text = UNSET
        else:
            text = ColumnTextMonthlyMonitoringChecksSpec.from_dict(_text)

        _whitespace = d.pop("whitespace", UNSET)
        whitespace: Union[Unset, ColumnWhitespaceMonthlyMonitoringChecksSpec]
        if isinstance(_whitespace, Unset):
            whitespace = UNSET
        else:
            whitespace = ColumnWhitespaceMonthlyMonitoringChecksSpec.from_dict(
                _whitespace
            )

        _conversions = d.pop("conversions", UNSET)
        conversions: Union[Unset, ColumnConversionsMonthlyMonitoringChecksSpec]
        if isinstance(_conversions, Unset):
            conversions = UNSET
        else:
            conversions = ColumnConversionsMonthlyMonitoringChecksSpec.from_dict(
                _conversions
            )

        _patterns = d.pop("patterns", UNSET)
        patterns: Union[Unset, ColumnPatternsMonthlyMonitoringChecksSpec]
        if isinstance(_patterns, Unset):
            patterns = UNSET
        else:
            patterns = ColumnPatternsMonthlyMonitoringChecksSpec.from_dict(_patterns)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiMonthlyMonitoringChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiMonthlyMonitoringChecksSpec.from_dict(_pii)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericMonthlyMonitoringChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericMonthlyMonitoringChecksSpec.from_dict(_numeric)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeMonthlyMonitoringChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeMonthlyMonitoringChecksSpec.from_dict(_datetime_)

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

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, ColumnCustomSqlMonthlyMonitoringChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = ColumnCustomSqlMonthlyMonitoringChecksSpec.from_dict(
                _custom_sql
            )

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeMonthlyMonitoringChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeMonthlyMonitoringChecksSpec.from_dict(_datatype)

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
            uniqueness=uniqueness,
            accepted_values=accepted_values,
            text=text,
            whitespace=whitespace,
            conversions=conversions,
            patterns=patterns,
            pii=pii,
            numeric=numeric,
            datetime_=datetime_,
            bool_=bool_,
            integrity=integrity,
            accuracy=accuracy,
            custom_sql=custom_sql,
            datatype=datatype,
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
