from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_daily_partitioned_checks_spec import (
        ColumnAcceptedValuesDailyPartitionedChecksSpec,
    )
    from ..models.column_anomaly_daily_partitioned_checks_spec import (
        ColumnAnomalyDailyPartitionedChecksSpec,
    )
    from ..models.column_bool_daily_partitioned_checks_spec import (
        ColumnBoolDailyPartitionedChecksSpec,
    )
    from ..models.column_conversions_daily_partitioned_checks_spec import (
        ColumnConversionsDailyPartitionedChecksSpec,
    )
    from ..models.column_custom_sql_daily_partitioned_checks_spec import (
        ColumnCustomSqlDailyPartitionedChecksSpec,
    )
    from ..models.column_daily_partitioned_check_categories_spec_comparisons import (
        ColumnDailyPartitionedCheckCategoriesSpecComparisons,
    )
    from ..models.column_daily_partitioned_check_categories_spec_custom import (
        ColumnDailyPartitionedCheckCategoriesSpecCustom,
    )
    from ..models.column_datatype_daily_partitioned_checks_spec import (
        ColumnDatatypeDailyPartitionedChecksSpec,
    )
    from ..models.column_datetime_daily_partitioned_checks_spec import (
        ColumnDatetimeDailyPartitionedChecksSpec,
    )
    from ..models.column_integrity_daily_partitioned_checks_spec import (
        ColumnIntegrityDailyPartitionedChecksSpec,
    )
    from ..models.column_nulls_daily_partitioned_checks_spec import (
        ColumnNullsDailyPartitionedChecksSpec,
    )
    from ..models.column_numeric_daily_partitioned_checks_spec import (
        ColumnNumericDailyPartitionedChecksSpec,
    )
    from ..models.column_patterns_daily_partitioned_checks_spec import (
        ColumnPatternsDailyPartitionedChecksSpec,
    )
    from ..models.column_pii_daily_partitioned_checks_spec import (
        ColumnPiiDailyPartitionedChecksSpec,
    )
    from ..models.column_text_daily_partitioned_checks_spec import (
        ColumnTextDailyPartitionedChecksSpec,
    )
    from ..models.column_uniqueness_daily_partitioned_checks_spec import (
        ColumnUniquenessDailyPartitionedChecksSpec,
    )
    from ..models.column_whitespace_daily_partitioned_checks_spec import (
        ColumnWhitespaceDailyPartitionedChecksSpec,
    )


T = TypeVar("T", bound="ColumnDailyPartitionedCheckCategoriesSpec")


@_attrs_define
class ColumnDailyPartitionedCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnDailyPartitionedCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        nulls (Union[Unset, ColumnNullsDailyPartitionedChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessDailyPartitionedChecksSpec]):
        accepted_values (Union[Unset, ColumnAcceptedValuesDailyPartitionedChecksSpec]):
        text (Union[Unset, ColumnTextDailyPartitionedChecksSpec]):
        whitespace (Union[Unset, ColumnWhitespaceDailyPartitionedChecksSpec]):
        conversions (Union[Unset, ColumnConversionsDailyPartitionedChecksSpec]):
        patterns (Union[Unset, ColumnPatternsDailyPartitionedChecksSpec]):
        pii (Union[Unset, ColumnPiiDailyPartitionedChecksSpec]):
        numeric (Union[Unset, ColumnNumericDailyPartitionedChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyDailyPartitionedChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeDailyPartitionedChecksSpec]):
        bool_ (Union[Unset, ColumnBoolDailyPartitionedChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityDailyPartitionedChecksSpec]):
        custom_sql (Union[Unset, ColumnCustomSqlDailyPartitionedChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeDailyPartitionedChecksSpec]):
        comparisons (Union[Unset, ColumnDailyPartitionedCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons at a column level. The key that identifies each comparison must match the name of a
            data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnDailyPartitionedCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsDailyPartitionedChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessDailyPartitionedChecksSpec"] = UNSET
    accepted_values: Union[Unset, "ColumnAcceptedValuesDailyPartitionedChecksSpec"] = (
        UNSET
    )
    text: Union[Unset, "ColumnTextDailyPartitionedChecksSpec"] = UNSET
    whitespace: Union[Unset, "ColumnWhitespaceDailyPartitionedChecksSpec"] = UNSET
    conversions: Union[Unset, "ColumnConversionsDailyPartitionedChecksSpec"] = UNSET
    patterns: Union[Unset, "ColumnPatternsDailyPartitionedChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiDailyPartitionedChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericDailyPartitionedChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyDailyPartitionedChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeDailyPartitionedChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolDailyPartitionedChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityDailyPartitionedChecksSpec"] = UNSET
    custom_sql: Union[Unset, "ColumnCustomSqlDailyPartitionedChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeDailyPartitionedChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "ColumnDailyPartitionedCheckCategoriesSpecComparisons"
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

        custom_sql: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_sql, Unset):
            custom_sql = self.custom_sql.to_dict()

        datatype: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.datatype, Unset):
            datatype = self.datatype.to_dict()

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
        if custom_sql is not UNSET:
            field_dict["custom_sql"] = custom_sql
        if datatype is not UNSET:
            field_dict["datatype"] = datatype
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accepted_values_daily_partitioned_checks_spec import (
            ColumnAcceptedValuesDailyPartitionedChecksSpec,
        )
        from ..models.column_anomaly_daily_partitioned_checks_spec import (
            ColumnAnomalyDailyPartitionedChecksSpec,
        )
        from ..models.column_bool_daily_partitioned_checks_spec import (
            ColumnBoolDailyPartitionedChecksSpec,
        )
        from ..models.column_conversions_daily_partitioned_checks_spec import (
            ColumnConversionsDailyPartitionedChecksSpec,
        )
        from ..models.column_custom_sql_daily_partitioned_checks_spec import (
            ColumnCustomSqlDailyPartitionedChecksSpec,
        )
        from ..models.column_daily_partitioned_check_categories_spec_comparisons import (
            ColumnDailyPartitionedCheckCategoriesSpecComparisons,
        )
        from ..models.column_daily_partitioned_check_categories_spec_custom import (
            ColumnDailyPartitionedCheckCategoriesSpecCustom,
        )
        from ..models.column_datatype_daily_partitioned_checks_spec import (
            ColumnDatatypeDailyPartitionedChecksSpec,
        )
        from ..models.column_datetime_daily_partitioned_checks_spec import (
            ColumnDatetimeDailyPartitionedChecksSpec,
        )
        from ..models.column_integrity_daily_partitioned_checks_spec import (
            ColumnIntegrityDailyPartitionedChecksSpec,
        )
        from ..models.column_nulls_daily_partitioned_checks_spec import (
            ColumnNullsDailyPartitionedChecksSpec,
        )
        from ..models.column_numeric_daily_partitioned_checks_spec import (
            ColumnNumericDailyPartitionedChecksSpec,
        )
        from ..models.column_patterns_daily_partitioned_checks_spec import (
            ColumnPatternsDailyPartitionedChecksSpec,
        )
        from ..models.column_pii_daily_partitioned_checks_spec import (
            ColumnPiiDailyPartitionedChecksSpec,
        )
        from ..models.column_text_daily_partitioned_checks_spec import (
            ColumnTextDailyPartitionedChecksSpec,
        )
        from ..models.column_uniqueness_daily_partitioned_checks_spec import (
            ColumnUniquenessDailyPartitionedChecksSpec,
        )
        from ..models.column_whitespace_daily_partitioned_checks_spec import (
            ColumnWhitespaceDailyPartitionedChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnDailyPartitionedCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnDailyPartitionedCheckCategoriesSpecCustom.from_dict(_custom)

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsDailyPartitionedChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsDailyPartitionedChecksSpec.from_dict(_nulls)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessDailyPartitionedChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessDailyPartitionedChecksSpec.from_dict(
                _uniqueness
            )

        _accepted_values = d.pop("accepted_values", UNSET)
        accepted_values: Union[Unset, ColumnAcceptedValuesDailyPartitionedChecksSpec]
        if isinstance(_accepted_values, Unset):
            accepted_values = UNSET
        else:
            accepted_values = ColumnAcceptedValuesDailyPartitionedChecksSpec.from_dict(
                _accepted_values
            )

        _text = d.pop("text", UNSET)
        text: Union[Unset, ColumnTextDailyPartitionedChecksSpec]
        if isinstance(_text, Unset):
            text = UNSET
        else:
            text = ColumnTextDailyPartitionedChecksSpec.from_dict(_text)

        _whitespace = d.pop("whitespace", UNSET)
        whitespace: Union[Unset, ColumnWhitespaceDailyPartitionedChecksSpec]
        if isinstance(_whitespace, Unset):
            whitespace = UNSET
        else:
            whitespace = ColumnWhitespaceDailyPartitionedChecksSpec.from_dict(
                _whitespace
            )

        _conversions = d.pop("conversions", UNSET)
        conversions: Union[Unset, ColumnConversionsDailyPartitionedChecksSpec]
        if isinstance(_conversions, Unset):
            conversions = UNSET
        else:
            conversions = ColumnConversionsDailyPartitionedChecksSpec.from_dict(
                _conversions
            )

        _patterns = d.pop("patterns", UNSET)
        patterns: Union[Unset, ColumnPatternsDailyPartitionedChecksSpec]
        if isinstance(_patterns, Unset):
            patterns = UNSET
        else:
            patterns = ColumnPatternsDailyPartitionedChecksSpec.from_dict(_patterns)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiDailyPartitionedChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiDailyPartitionedChecksSpec.from_dict(_pii)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericDailyPartitionedChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericDailyPartitionedChecksSpec.from_dict(_numeric)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyDailyPartitionedChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyDailyPartitionedChecksSpec.from_dict(_anomaly)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeDailyPartitionedChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeDailyPartitionedChecksSpec.from_dict(_datetime_)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolDailyPartitionedChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolDailyPartitionedChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityDailyPartitionedChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityDailyPartitionedChecksSpec.from_dict(_integrity)

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, ColumnCustomSqlDailyPartitionedChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = ColumnCustomSqlDailyPartitionedChecksSpec.from_dict(
                _custom_sql
            )

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeDailyPartitionedChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeDailyPartitionedChecksSpec.from_dict(_datatype)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[Unset, ColumnDailyPartitionedCheckCategoriesSpecComparisons]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = (
                ColumnDailyPartitionedCheckCategoriesSpecComparisons.from_dict(
                    _comparisons
                )
            )

        column_daily_partitioned_check_categories_spec = cls(
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
            custom_sql=custom_sql,
            datatype=datatype,
            comparisons=comparisons,
        )

        column_daily_partitioned_check_categories_spec.additional_properties = d
        return column_daily_partitioned_check_categories_spec

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
