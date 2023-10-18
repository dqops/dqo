from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_daily_partitioned_checks_spec import (
        ColumnAccuracyDailyPartitionedChecksSpec,
    )
    from ..models.column_anomaly_daily_partitioned_checks_spec import (
        ColumnAnomalyDailyPartitionedChecksSpec,
    )
    from ..models.column_bool_daily_partitioned_checks_spec import (
        ColumnBoolDailyPartitionedChecksSpec,
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
    from ..models.column_pii_daily_partitioned_checks_spec import (
        ColumnPiiDailyPartitionedChecksSpec,
    )
    from ..models.column_sql_daily_partitioned_checks_spec import (
        ColumnSqlDailyPartitionedChecksSpec,
    )
    from ..models.column_strings_daily_partitioned_checks_spec import (
        ColumnStringsDailyPartitionedChecksSpec,
    )
    from ..models.column_uniqueness_daily_partitioned_checks_spec import (
        ColumnUniquenessDailyPartitionedChecksSpec,
    )


T = TypeVar("T", bound="ColumnDailyPartitionedCheckCategoriesSpec")


@_attrs_define
class ColumnDailyPartitionedCheckCategoriesSpec:
    """
    Attributes:
        custom (Union[Unset, ColumnDailyPartitionedCheckCategoriesSpecCustom]): Dictionary of custom checks. The keys
            are check names within this category.
        nulls (Union[Unset, ColumnNullsDailyPartitionedChecksSpec]):
        numeric (Union[Unset, ColumnNumericDailyPartitionedChecksSpec]):
        strings (Union[Unset, ColumnStringsDailyPartitionedChecksSpec]):
        uniqueness (Union[Unset, ColumnUniquenessDailyPartitionedChecksSpec]):
        datetime_ (Union[Unset, ColumnDatetimeDailyPartitionedChecksSpec]):
        pii (Union[Unset, ColumnPiiDailyPartitionedChecksSpec]):
        sql (Union[Unset, ColumnSqlDailyPartitionedChecksSpec]):
        bool_ (Union[Unset, ColumnBoolDailyPartitionedChecksSpec]):
        integrity (Union[Unset, ColumnIntegrityDailyPartitionedChecksSpec]):
        accuracy (Union[Unset, ColumnAccuracyDailyPartitionedChecksSpec]):
        datatype (Union[Unset, ColumnDatatypeDailyPartitionedChecksSpec]):
        anomaly (Union[Unset, ColumnAnomalyDailyPartitionedChecksSpec]):
        comparisons (Union[Unset, ColumnDailyPartitionedCheckCategoriesSpecComparisons]): Dictionary of configuration of
            checks for table comparisons at a column level. The key that identifies each comparison must match the name of a
            data comparison that is configured on the parent table.
    """

    custom: Union[Unset, "ColumnDailyPartitionedCheckCategoriesSpecCustom"] = UNSET
    nulls: Union[Unset, "ColumnNullsDailyPartitionedChecksSpec"] = UNSET
    numeric: Union[Unset, "ColumnNumericDailyPartitionedChecksSpec"] = UNSET
    strings: Union[Unset, "ColumnStringsDailyPartitionedChecksSpec"] = UNSET
    uniqueness: Union[Unset, "ColumnUniquenessDailyPartitionedChecksSpec"] = UNSET
    datetime_: Union[Unset, "ColumnDatetimeDailyPartitionedChecksSpec"] = UNSET
    pii: Union[Unset, "ColumnPiiDailyPartitionedChecksSpec"] = UNSET
    sql: Union[Unset, "ColumnSqlDailyPartitionedChecksSpec"] = UNSET
    bool_: Union[Unset, "ColumnBoolDailyPartitionedChecksSpec"] = UNSET
    integrity: Union[Unset, "ColumnIntegrityDailyPartitionedChecksSpec"] = UNSET
    accuracy: Union[Unset, "ColumnAccuracyDailyPartitionedChecksSpec"] = UNSET
    datatype: Union[Unset, "ColumnDatatypeDailyPartitionedChecksSpec"] = UNSET
    anomaly: Union[Unset, "ColumnAnomalyDailyPartitionedChecksSpec"] = UNSET
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
        if comparisons is not UNSET:
            field_dict["comparisons"] = comparisons

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accuracy_daily_partitioned_checks_spec import (
            ColumnAccuracyDailyPartitionedChecksSpec,
        )
        from ..models.column_anomaly_daily_partitioned_checks_spec import (
            ColumnAnomalyDailyPartitionedChecksSpec,
        )
        from ..models.column_bool_daily_partitioned_checks_spec import (
            ColumnBoolDailyPartitionedChecksSpec,
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
        from ..models.column_pii_daily_partitioned_checks_spec import (
            ColumnPiiDailyPartitionedChecksSpec,
        )
        from ..models.column_sql_daily_partitioned_checks_spec import (
            ColumnSqlDailyPartitionedChecksSpec,
        )
        from ..models.column_strings_daily_partitioned_checks_spec import (
            ColumnStringsDailyPartitionedChecksSpec,
        )
        from ..models.column_uniqueness_daily_partitioned_checks_spec import (
            ColumnUniquenessDailyPartitionedChecksSpec,
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

        _numeric = d.pop("numeric", UNSET)
        numeric: Union[Unset, ColumnNumericDailyPartitionedChecksSpec]
        if isinstance(_numeric, Unset):
            numeric = UNSET
        else:
            numeric = ColumnNumericDailyPartitionedChecksSpec.from_dict(_numeric)

        _strings = d.pop("strings", UNSET)
        strings: Union[Unset, ColumnStringsDailyPartitionedChecksSpec]
        if isinstance(_strings, Unset):
            strings = UNSET
        else:
            strings = ColumnStringsDailyPartitionedChecksSpec.from_dict(_strings)

        _uniqueness = d.pop("uniqueness", UNSET)
        uniqueness: Union[Unset, ColumnUniquenessDailyPartitionedChecksSpec]
        if isinstance(_uniqueness, Unset):
            uniqueness = UNSET
        else:
            uniqueness = ColumnUniquenessDailyPartitionedChecksSpec.from_dict(
                _uniqueness
            )

        _datetime_ = d.pop("datetime", UNSET)
        datetime_: Union[Unset, ColumnDatetimeDailyPartitionedChecksSpec]
        if isinstance(_datetime_, Unset):
            datetime_ = UNSET
        else:
            datetime_ = ColumnDatetimeDailyPartitionedChecksSpec.from_dict(_datetime_)

        _pii = d.pop("pii", UNSET)
        pii: Union[Unset, ColumnPiiDailyPartitionedChecksSpec]
        if isinstance(_pii, Unset):
            pii = UNSET
        else:
            pii = ColumnPiiDailyPartitionedChecksSpec.from_dict(_pii)

        _sql = d.pop("sql", UNSET)
        sql: Union[Unset, ColumnSqlDailyPartitionedChecksSpec]
        if isinstance(_sql, Unset):
            sql = UNSET
        else:
            sql = ColumnSqlDailyPartitionedChecksSpec.from_dict(_sql)

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

        _accuracy = d.pop("accuracy", UNSET)
        accuracy: Union[Unset, ColumnAccuracyDailyPartitionedChecksSpec]
        if isinstance(_accuracy, Unset):
            accuracy = UNSET
        else:
            accuracy = ColumnAccuracyDailyPartitionedChecksSpec.from_dict(_accuracy)

        _datatype = d.pop("datatype", UNSET)
        datatype: Union[Unset, ColumnDatatypeDailyPartitionedChecksSpec]
        if isinstance(_datatype, Unset):
            datatype = UNSET
        else:
            datatype = ColumnDatatypeDailyPartitionedChecksSpec.from_dict(_datatype)

        _anomaly = d.pop("anomaly", UNSET)
        anomaly: Union[Unset, ColumnAnomalyDailyPartitionedChecksSpec]
        if isinstance(_anomaly, Unset):
            anomaly = UNSET
        else:
            anomaly = ColumnAnomalyDailyPartitionedChecksSpec.from_dict(_anomaly)

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
