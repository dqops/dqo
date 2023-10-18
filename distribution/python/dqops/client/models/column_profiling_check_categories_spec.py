from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_profiling_checks_spec import (
        ColumnAccuracyProfilingChecksSpec,
    )
    from ..models.column_anomaly_profiling_checks_spec import (
        ColumnAnomalyProfilingChecksSpec,
    )
    from ..models.column_bool_profiling_checks_spec import ColumnBoolProfilingChecksSpec
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
    from ..models.column_sql_profiling_checks_spec import ColumnSqlProfilingChecksSpec
    from ..models.column_strings_profiling_checks_spec import (
        ColumnStringsProfilingChecksSpec,
    )
    from ..models.column_uniqueness_profiling_checks_spec import (
        ColumnUniquenessProfilingChecksSpec,
    )


T = TypeVar("T", bound="ColumnProfilingCheckCategoriesSpec")


@_attrs_define
class ColumnProfilingCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnProfilingCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys are check
            names within this category.
        nulls (Union[Unset, ColumnNullsProfilingChecksSpec]):
        numeric (Union[Unset, ColumnNumericProfilingChecksSpec]):
        strings (Union[Unset, ColumnStringsProfilingChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessProfilingChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeProfilingChecksSpec]):
        pii (Union[Unset, ColumnPiiProfilingChecksSpec]):
        sql (Union[Unset, ColumnSqlProfilingChecksSpec]):
        bool_ (Union[Unset, ColumnBoolProfilingChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityProfilingChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyProfilingChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeProfilingChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyProfilingChecksSpec]):
        schema (Union[Unset, ColumnSchemaProfilingChecksSpec]):
        comparisons (Union[Unset, ColumnProfilingCheckCategoriesSpecComparisons]): Dictionary of configuration of checks
            for table comparisons at a column level. The key that identifies each comparison must match the name of a data
            comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnProfilingCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsProfilingChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericProfilingChecksSpec"] = UNSET
    strings: Union[Unset, "ColumnStringsProfilingChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessProfilingChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeProfilingChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiProfilingChecksSpec"] = UNSET
    sql: Union[Unset, "ColumnSqlProfilingChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolProfilingChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityProfilingChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyProfilingChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeProfilingChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyProfilingChecksSpec"] = UNSET
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
        from ..models.column_accuracy_profiling_checks_spec import (
            ColumnAccuracyProfilingChecksSpec,
        )
        from ..models.column_anomaly_profiling_checks_spec import (
            ColumnAnomalyProfilingChecksSpec,
        )
        from ..models.column_bool_profiling_checks_spec import (
            ColumnBoolProfilingChecksSpec,
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
        from ..models.column_sql_profiling_checks_spec import (
            ColumnSqlProfilingChecksSpec,
        )
        from ..models.column_strings_profiling_checks_spec import (
            ColumnStringsProfilingChecksSpec,
        )
        from ..models.column_uniqueness_profiling_checks_spec import (
            ColumnUniquenessProfilingChecksSpec,
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

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericProfilingChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericProfilingChecksSpec.from_dict(_numeric)

        _strings = d.pop("strings", UNSET)
        strings: Union[Unset, ColumnStringsProfilingChecksSpec]
        if isinstance(_strings, Unset):
            strings = UNSET
        else:
            strings = ColumnStringsProfilingChecksSpec.from_dict(_strings)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessProfilingChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessProfilingChecksSpec.from_dict(_uniqueness)

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeProfilingChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeProfilingChecksSpec.from_dict(_datetime_)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiProfilingChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiProfilingChecksSpec.from_dict(_pii)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, ColumnSqlProfilingChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = ColumnSqlProfilingChecksSpec.from_dict(_sql)

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

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeProfilingChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeProfilingChecksSpec.from_dict(_datatype)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyProfilingChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyProfilingChecksSpec.from_dict(_anomaly)

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
