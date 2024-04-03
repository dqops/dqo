from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_profiling_checks_spec import (
        ColumnAcceptedValuesProfilingChecksSpec,
    )
    from ..models.column_accuracy_profiling_checks_spec import (
        ColumnAccuracyProfilingChecksSpec,
    )
    from ..models.column_anomaly_profiling_checks_spec import (
        ColumnAnomalyProfilingChecksSpec,
    )
    from ..models.column_bool_profiling_checks_spec import ColumnBoolProfilingChecksSpec
    from ..models.column_conversions_profiling_checks_spec import (
        ColumnConversionsProfilingChecksSpec,
    )
    from ..models.column_custom_sql_profiling_checks_spec import (
        ColumnCustomSqlProfilingChecksSpec,
    )
    from ..models.column_datatype_profiling_checks_spec import (
        ColumnDatatypeProfilingChecksSpec,
    )
    from ..models.column_datetime_profiling_checks_spec import (
        ColumnDatetimeProfilingChecksSpec,
    )
    from ..models.column_integrity_profiling_checks_spec import (
        ColumnIntegrityProfilingChecksSpec,
    )
    from ..models.column_nulls_profiling_checks_spec import (
        ColumnNullsProfilingChecksSpec,
    )
    from ..models.column_numeric_profiling_checks_spec import (
        ColumnNumericProfilingChecksSpec,
    )
    from ..models.column_patterns_profiling_checks_spec import (
        ColumnPatternsProfilingChecksSpec,
    )
    from ..models.column_pii_profiling_checks_spec import ColumnPiiProfilingChecksSpec
    from ..models.column_profiling_check_categories_spec_comparisons import (
        ColumnProfilingCheckCategoriesSpecComparisons,
    )
    from ..models.column_profiling_check_categories_spec_custom import (
        ColumnProfilingCheckCategoriesSpecCustom,
    )
    from ..models.column_schema_profiling_checks_spec import (
        ColumnSchemaProfilingChecksSpec,
    )
    from ..models.column_text_profiling_checks_spec import ColumnTextProfilingChecksSpec
    from ..models.column_uniqueness_profiling_checks_spec import (
        ColumnUniquenessProfilingChecksSpec,
    )
    from ..models.column_whitespace_profiling_checks_spec import (
        ColumnWhitespaceProfilingChecksSpec,
    )


T = TypeVar("T", bound="ColumnProfilingCheckCategoriesSpec")


@_attrs_define
class ColumnProfilingCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnProfilingCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are check
            names within this category.
        nulls (Union[Unset, ColumnNullsProfilingChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessProfilingChecksSpec]):
        accepted_values (Union[Unset, ColumnAcceptedValuesProfilingChecksSpec]):
        text (Union[Unset, ColumnTextProfilingChecksSpec]):
        whitespace (Union[Unset, ColumnWhitespaceProfilingChecksSpec]):
        conversions (Union[Unset, ColumnConversionsProfilingChecksSpec]):
        patterns (Union[Unset, ColumnPatternsProfilingChecksSpec]):
        pii (Union[Unset, ColumnPiiProfilingChecksSpec]):
        numeric (Union[Unset, ColumnNumericProfilingChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyProfilingChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeProfilingChecksSpec]):
        bool_ (Union[Unset, ColumnBoolProfilingChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityProfilingChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyProfilingChecksSpec]):
        custom_sql (Union[Unset, ColumnCustomSqlProfilingChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeProfilingChecksSpec]):
        schema (Union[Unset, ColumnSchemaProfilingChecksSpec]):
        comparisons (Union[Unset, ColumnProfilingCheckCategoriesSpecComparisons]): Dictionary of configuration of checks
            for table comparisons at a column level. The key that identifies each comparison must match the name of a data
            comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnProfilingCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsProfilingChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessProfilingChecksSpec"] = UNSET
    accepted_values: Union[Unset, "ColumnAcceptedValuesProfilingChecksSpec"] = UNSET
    text: Union[Unset, "ColumnTextProfilingChecksSpec"] = UNSET
    whitespace: Union[Unset, "ColumnWhitespaceProfilingChecksSpec"] = UNSET
    conversions: Union[Unset, "ColumnConversionsProfilingChecksSpec"] = UNSET
    patterns: Union[Unset, "ColumnPatternsProfilingChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiProfilingChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericProfilingChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyProfilingChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeProfilingChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolProfilingChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityProfilingChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyProfilingChecksSpec"] = UNSET
    custom_sql: Union[Unset, "ColumnCustomSqlProfilingChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeProfilingChecksSpec"] = UNSET
    schema: Union[Unset, "ColumnSchemaProfilingChecksSpec"] = UNSET
    comparisons: Union[Unset, "ColumnProfilingCheckCategoriesSpecComparisons"] = UNSET
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
        from ..models.column_accepted_values_profiling_checks_spec import (
            ColumnAcceptedValuesProfilingChecksSpec,
        )
        from ..models.column_accuracy_profiling_checks_spec import (
            ColumnAccuracyProfilingChecksSpec,
        )
        from ..models.column_anomaly_profiling_checks_spec import (
            ColumnAnomalyProfilingChecksSpec,
        )
        from ..models.column_bool_profiling_checks_spec import (
            ColumnBoolProfilingChecksSpec,
        )
        from ..models.column_conversions_profiling_checks_spec import (
            ColumnConversionsProfilingChecksSpec,
        )
        from ..models.column_custom_sql_profiling_checks_spec import (
            ColumnCustomSqlProfilingChecksSpec,
        )
        from ..models.column_datatype_profiling_checks_spec import (
            ColumnDatatypeProfilingChecksSpec,
        )
        from ..models.column_datetime_profiling_checks_spec import (
            ColumnDatetimeProfilingChecksSpec,
        )
        from ..models.column_integrity_profiling_checks_spec import (
            ColumnIntegrityProfilingChecksSpec,
        )
        from ..models.column_nulls_profiling_checks_spec import (
            ColumnNullsProfilingChecksSpec,
        )
        from ..models.column_numeric_profiling_checks_spec import (
            ColumnNumericProfilingChecksSpec,
        )
        from ..models.column_patterns_profiling_checks_spec import (
            ColumnPatternsProfilingChecksSpec,
        )
        from ..models.column_pii_profiling_checks_spec import (
            ColumnPiiProfilingChecksSpec,
        )
        from ..models.column_profiling_check_categories_spec_comparisons import (
            ColumnProfilingCheckCategoriesSpecComparisons,
        )
        from ..models.column_profiling_check_categories_spec_custom import (
            ColumnProfilingCheckCategoriesSpecCustom,
        )
        from ..models.column_schema_profiling_checks_spec import (
            ColumnSchemaProfilingChecksSpec,
        )
        from ..models.column_text_profiling_checks_spec import (
            ColumnTextProfilingChecksSpec,
        )
        from ..models.column_uniqueness_profiling_checks_spec import (
            ColumnUniquenessProfilingChecksSpec,
        )
        from ..models.column_whitespace_profiling_checks_spec import (
            ColumnWhitespaceProfilingChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnProfilingCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnProfilingCheckCategoriesSpecCustom.from_dict(_custom)

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsProfilingChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsProfilingChecksSpec.from_dict(_nulls)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessProfilingChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessProfilingChecksSpec.from_dict(_uniqueness)

        _accepted_values = d.pop("accepted_values", UNSET)
        accepted_values: Union[Unset, ColumnAcceptedValuesProfilingChecksSpec]
        if isinstance(_accepted_values, Unset):
            accepted_values = UNSET
        else:
            accepted_values = ColumnAcceptedValuesProfilingChecksSpec.from_dict(
                _accepted_values
            )

        _text = d.pop("text", UNSET)
        text: Union[Unset, ColumnTextProfilingChecksSpec]
        if isinstance(_text, Unset):
            text = UNSET
        else:
            text = ColumnTextProfilingChecksSpec.from_dict(_text)

        _whitespace = d.pop("whitespace", UNSET)
        whitespace: Union[Unset, ColumnWhitespaceProfilingChecksSpec]
        if isinstance(_whitespace, Unset):
            whitespace = UNSET
        else:
            whitespace = ColumnWhitespaceProfilingChecksSpec.from_dict(_whitespace)

        _conversions = d.pop("conversions", UNSET)
        conversions: Union[Unset, ColumnConversionsProfilingChecksSpec]
        if isinstance(_conversions, Unset):
            conversions = UNSET
        else:
            conversions = ColumnConversionsProfilingChecksSpec.from_dict(_conversions)

        _patterns = d.pop("patterns", UNSET)
        patterns: Union[Unset, ColumnPatternsProfilingChecksSpec]
        if isinstance(_patterns, Unset):
            patterns = UNSET
        else:
            patterns = ColumnPatternsProfilingChecksSpec.from_dict(_patterns)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiProfilingChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiProfilingChecksSpec.from_dict(_pii)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericProfilingChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericProfilingChecksSpec.from_dict(_numeric)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyProfilingChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyProfilingChecksSpec.from_dict(_anomaly)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeProfilingChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeProfilingChecksSpec.from_dict(_datetime_)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolProfilingChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolProfilingChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityProfilingChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityProfilingChecksSpec.from_dict(_integrity)

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, ColumnAccuracyProfilingChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = ColumnAccuracyProfilingChecksSpec.from_dict(_accuracy)

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, ColumnCustomSqlProfilingChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = ColumnCustomSqlProfilingChecksSpec.from_dict(_custom_sql)

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeProfilingChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeProfilingChecksSpec.from_dict(_datatype)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, ColumnSchemaProfilingChecksSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = ColumnSchemaProfilingChecksSpec.from_dict(_schema)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, ColumnProfilingCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = ColumnProfilingCheckCategoriesSpecComparisons.from_dict(
                _comparisons
            )

        column_profiling_check_categories_spec = cls(
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

        column_profiling_check_categories_spec.additional_properties = d
        return column_profiling_check_categories_spec

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
