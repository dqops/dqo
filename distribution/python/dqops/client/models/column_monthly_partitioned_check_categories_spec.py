from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accepted_values_monthly_partitioned_checks_spec import (
        ColumnAcceptedValuesMonthlyPartitionedChecksSpec,
    )
    from ..models.column_bool_monthly_partitioned_checks_spec import (
        ColumnBoolMonthlyPartitionedChecksSpec,
    )
    from ..models.column_conversions_monthly_partitioned_checks_spec import (
        ColumnConversionsMonthlyPartitionedChecksSpec,
    )
    from ..models.column_custom_sql_monthly_partitioned_checks_spec import (
        ColumnCustomSqlMonthlyPartitionedChecksSpec,
    )
    from ..models.column_datatype_monthly_partitioned_checks_spec import (
        ColumnDatatypeMonthlyPartitionedChecksSpec,
    )
    from ..models.column_datetime_monthly_partitioned_checks_spec import (
        ColumnDatetimeMonthlyPartitionedChecksSpec,
    )
    from ..models.column_integrity_monthly_partitioned_checks_spec import (
        ColumnIntegrityMonthlyPartitionedChecksSpec,
    )
    from ..models.column_monthly_partitioned_check_categories_spec_comparisons import (
        ColumnMonthlyPartitionedCheckCategoriesSpecComparisons,
    )
    from ..models.column_monthly_partitioned_check_categories_spec_custom import (
        ColumnMonthlyPartitionedCheckCategoriesSpecCustom,
    )
    from ..models.column_nulls_monthly_partitioned_checks_spec import (
        ColumnNullsMonthlyPartitionedChecksSpec,
    )
    from ..models.column_numeric_monthly_partitioned_checks_spec import (
        ColumnNumericMonthlyPartitionedChecksSpec,
    )
    from ..models.column_patterns_monthly_partitioned_checks_spec import (
        ColumnPatternsMonthlyPartitionedChecksSpec,
    )
    from ..models.column_pii_monthly_partitioned_checks_spec import (
        ColumnPiiMonthlyPartitionedChecksSpec,
    )
    from ..models.column_text_monthly_partitioned_checks_spec import (
        ColumnTextMonthlyPartitionedChecksSpec,
    )
    from ..models.column_uniqueness_monthly_partitioned_checks_spec import (
        ColumnUniquenessMonthlyPartitionedChecksSpec,
    )
    from ..models.column_whitespace_monthly_partitioned_checks_spec import (
        ColumnWhitespaceMonthlyPartitionedChecksSpec,
    )


T = TypeVar("T", bound="ColumnMonthlyPartitionedCheckCategoriesSpec")


@_attrs_define
class ColumnMonthlyPartitionedCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnMonthlyPartitionedCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        nulls (Union[Unset, ColumnNullsMonthlyPartitionedChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessMonthlyPartitionedChecksSpec]):
        accepted_values (Union[Unset, ColumnAcceptedValuesMonthlyPartitionedChecksSpec]):
        text (Union[Unset, ColumnTextMonthlyPartitionedChecksSpec]):
        whitespace (Union[Unset, ColumnWhitespaceMonthlyPartitionedChecksSpec]):
        conversions (Union[Unset, ColumnConversionsMonthlyPartitionedChecksSpec]):
        patterns (Union[Unset, ColumnPatternsMonthlyPartitionedChecksSpec]):
        pii (Union[Unset, ColumnPiiMonthlyPartitionedChecksSpec]):
        numeric (Union[Unset, ColumnNumericMonthlyPartitionedChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeMonthlyPartitionedChecksSpec]):
        bool_ (Union[Unset, ColumnBoolMonthlyPartitionedChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityMonthlyPartitionedChecksSpec]):
        custom_sql (Union[Unset, ColumnCustomSqlMonthlyPartitionedChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeMonthlyPartitionedChecksSpec]):
        comparisons (Union[Unset, ColumnMonthlyPartitionedCheckCategoriesSpecComparisons]): Dictionary of configuration
            of checks for table comparisons at a column level. The key that identifies each comparison must match the name
            of a data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnMonthlyPartitionedCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsMonthlyPartitionedChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessMonthlyPartitionedChecksSpec"] = UNSET
    accepted_values: Union[
        Unset, "ColumnAcceptedValuesMonthlyPartitionedChecksSpec"
    ] = UNSET
    text: Union[Unset, "ColumnTextMonthlyPartitionedChecksSpec"] = UNSET
    whitespace: Union[Unset, "ColumnWhitespaceMonthlyPartitionedChecksSpec"] = UNSET
    conversions: Union[Unset, "ColumnConversionsMonthlyPartitionedChecksSpec"] = UNSET
    patterns: Union[Unset, "ColumnPatternsMonthlyPartitionedChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiMonthlyPartitionedChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericMonthlyPartitionedChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeMonthlyPartitionedChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolMonthlyPartitionedChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityMonthlyPartitionedChecksSpec"] = UNSET
    custom_sql: Union[Unset, "ColumnCustomSqlMonthlyPartitionedChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeMonthlyPartitionedChecksSpec"] = UNSET
    comparisons: Union[
        Unset, "ColumnMonthlyPartitionedCheckCategoriesSpecComparisons"
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
        from ..models.column_accepted_values_monthly_partitioned_checks_spec import (
            ColumnAcceptedValuesMonthlyPartitionedChecksSpec,
        )
        from ..models.column_bool_monthly_partitioned_checks_spec import (
            ColumnBoolMonthlyPartitionedChecksSpec,
        )
        from ..models.column_conversions_monthly_partitioned_checks_spec import (
            ColumnConversionsMonthlyPartitionedChecksSpec,
        )
        from ..models.column_custom_sql_monthly_partitioned_checks_spec import (
            ColumnCustomSqlMonthlyPartitionedChecksSpec,
        )
        from ..models.column_datatype_monthly_partitioned_checks_spec import (
            ColumnDatatypeMonthlyPartitionedChecksSpec,
        )
        from ..models.column_datetime_monthly_partitioned_checks_spec import (
            ColumnDatetimeMonthlyPartitionedChecksSpec,
        )
        from ..models.column_integrity_monthly_partitioned_checks_spec import (
            ColumnIntegrityMonthlyPartitionedChecksSpec,
        )
        from ..models.column_monthly_partitioned_check_categories_spec_comparisons import (
            ColumnMonthlyPartitionedCheckCategoriesSpecComparisons,
        )
        from ..models.column_monthly_partitioned_check_categories_spec_custom import (
            ColumnMonthlyPartitionedCheckCategoriesSpecCustom,
        )
        from ..models.column_nulls_monthly_partitioned_checks_spec import (
            ColumnNullsMonthlyPartitionedChecksSpec,
        )
        from ..models.column_numeric_monthly_partitioned_checks_spec import (
            ColumnNumericMonthlyPartitionedChecksSpec,
        )
        from ..models.column_patterns_monthly_partitioned_checks_spec import (
            ColumnPatternsMonthlyPartitionedChecksSpec,
        )
        from ..models.column_pii_monthly_partitioned_checks_spec import (
            ColumnPiiMonthlyPartitionedChecksSpec,
        )
        from ..models.column_text_monthly_partitioned_checks_spec import (
            ColumnTextMonthlyPartitionedChecksSpec,
        )
        from ..models.column_uniqueness_monthly_partitioned_checks_spec import (
            ColumnUniquenessMonthlyPartitionedChecksSpec,
        )
        from ..models.column_whitespace_monthly_partitioned_checks_spec import (
            ColumnWhitespaceMonthlyPartitionedChecksSpec,
        )

        d = src_dict.copy()
        _custom = d.pop("custom", UNSET)
        custom: Union[Unset, ColumnMonthlyPartitionedCheckCategoriesSpecCustom]
        if isinstance(_custom, Unset):
            custom = UNSET
        else:
            custom = ColumnMonthlyPartitionedCheckCategoriesSpecCustom.from_dict(
                _custom
            )

        _nulls = d.pop("nulls", UNSET)
        nulls: Union[Unset, ColumnNullsMonthlyPartitionedChecksSpec]
        if isinstance(_nulls, Unset):
            nulls = UNSET
        else:
            nulls = ColumnNullsMonthlyPartitionedChecksSpec.from_dict(_nulls)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessMonthlyPartitionedChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessMonthlyPartitionedChecksSpec.from_dict(
                _uniqueness
            )

        _accepted_values = d.pop("accepted_values", UNSET)
        accepted_values: Union[Unset, ColumnAcceptedValuesMonthlyPartitionedChecksSpec]
        if isinstance(_accepted_values, Unset):
            accepted_values = UNSET
        else:
            accepted_values = (
                ColumnAcceptedValuesMonthlyPartitionedChecksSpec.from_dict(
                    _accepted_values
                )
            )

        _text = d.pop("text", UNSET)
        text: Union[Unset, ColumnTextMonthlyPartitionedChecksSpec]
        if isinstance(_text, Unset):
            text = UNSET
        else:
            text = ColumnTextMonthlyPartitionedChecksSpec.from_dict(_text)

        _whitespace = d.pop("whitespace", UNSET)
        whitespace: Union[Unset, ColumnWhitespaceMonthlyPartitionedChecksSpec]
        if isinstance(_whitespace, Unset):
            whitespace = UNSET
        else:
            whitespace = ColumnWhitespaceMonthlyPartitionedChecksSpec.from_dict(
                _whitespace
            )

        _conversions = d.pop("conversions", UNSET)
        conversions: Union[Unset, ColumnConversionsMonthlyPartitionedChecksSpec]
        if isinstance(_conversions, Unset):
            conversions = UNSET
        else:
            conversions = ColumnConversionsMonthlyPartitionedChecksSpec.from_dict(
                _conversions
            )

        _patterns = d.pop("patterns", UNSET)
        patterns: Union[Unset, ColumnPatternsMonthlyPartitionedChecksSpec]
        if isinstance(_patterns, Unset):
            patterns = UNSET
        else:
            patterns = ColumnPatternsMonthlyPartitionedChecksSpec.from_dict(_patterns)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiMonthlyPartitionedChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiMonthlyPartitionedChecksSpec.from_dict(_pii)

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericMonthlyPartitionedChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericMonthlyPartitionedChecksSpec.from_dict(_numeric)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeMonthlyPartitionedChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeMonthlyPartitionedChecksSpec.from_dict(_datetime_)

        _bool_ = d.pop("bool", UNSET)
        bool_: Union[Unset, ColumnBoolMonthlyPartitionedChecksSpec]
        if isinstance(_bool_, Unset):
            bool_ = UNSET
        else:
            bool_ = ColumnBoolMonthlyPartitionedChecksSpec.from_dict(_bool_)

        _integrity = d.pop("integrity", UNSET)
        integrity: Union[Unset, ColumnIntegrityMonthlyPartitionedChecksSpec]
        if isinstance(_integrity, Unset):
            integrity = UNSET
        else:
            integrity = ColumnIntegrityMonthlyPartitionedChecksSpec.from_dict(
                _integrity
            )

        _custom_sql = d.pop("custom_sql", UNSET)
        custom_sql: Union[Unset, ColumnCustomSqlMonthlyPartitionedChecksSpec]
        if isinstance(_custom_sql, Unset):
            custom_sql = UNSET
        else:
            custom_sql = ColumnCustomSqlMonthlyPartitionedChecksSpec.from_dict(
                _custom_sql
            )

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeMonthlyPartitionedChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeMonthlyPartitionedChecksSpec.from_dict(_datatype)

        _comparisons = d.pop("comparisons", UNSET)
        comparisons: Union[
            Unset, ColumnMonthlyPartitionedCheckCategoriesSpecComparisons
        ]
        if isinstance(_comparisons, Unset):
            comparisons = UNSET
        else:
            comparisons = (
                ColumnMonthlyPartitionedCheckCategoriesSpecComparisons.from_dict(
                    _comparisons
                )
            )

        column_monthly_partitioned_check_categories_spec = cls(
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
            custom_sql=custom_sql,
            datatype=datatype,
            comparisons=comparisons,
        )

        column_monthly_partitioned_check_categories_spec.additional_properties = d
        return column_monthly_partitioned_check_categories_spec

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
