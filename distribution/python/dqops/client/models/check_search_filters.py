from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_target import CheckTarget
from ..models.check_time_scale import CheckTimeScale
from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.hierarchy_id_model import HierarchyIdModel


T = TypeVar("T", bound="CheckSearchFilters")


@_attrs_define
class CheckSearchFilters:
    r"""Target data quality checks filter, identifies which checks on which tables and columns should be executed.

    Attributes:
        connection (Union[Unset, str]): The connection (data source) name. Supports search patterns in the format:
            'source\*', '\*_prod', 'prefix\*suffix'.
        full_table_name (Union[Unset, str]): The schema and table name. It is provided as *<schema_name>.<table_name>*,
            for example *public.fact_sales*. The schema and table name accept patterns both in the schema name and table
            name parts. Sample patterns are: 'schema_name.tab_prefix_\*', 'schema_name.*', '*.*', 'schema_name.\*_customer',
            'schema_name.tab_\*_suffix'.
        enabled (Union[Unset, bool]): A boolean flag to target enabled tables, columns or checks. When the value of this
            field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are
            not implicitly disabled.
        tags (Union[Unset, List[str]]): An array of tags assigned to the table. All tags must be present on a table to
            match. The tags can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The tags are assigned to the table
            on the data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a
            tag.
        labels (Union[Unset, List[str]]): An array of labels assigned to the table. All labels must be present on a
            table to match. The labels can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The labels are assigned
            on the labels screen and stored in the *labels* node in the *.dqotable.yaml* file.
        max_results (Union[Unset, int]): Optional limit for the maximum number of results to return.
        column (Union[Unset, str]): The column name. This field accepts search patterns in the format: 'fk_\*', '\*_id',
            'prefix\*suffix'.
        column_data_type (Union[Unset, str]): The column data type that was imported from the data source and is stored
            in the [columns -> column_name -> type_snapshot ->
            column_type](/docs/reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.
        column_nullable (Union[Unset, bool]): Optional filter to find only nullable (when the value is *true*) or not
            nullable (when the value is *false*) columns, based on the value of the [columns -> column_name -> type_snapshot
            -> nullable](/docs/reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.
        check_target (Union[Unset, CheckTarget]):
        check_type (Union[Unset, CheckType]):
        time_scale (Union[Unset, CheckTimeScale]):
        check_category (Union[Unset, str]): The target check category, for example: *nulls*, *volume*, *anomaly*.
        quality_dimension (Union[Unset, str]): The target data quality dimension, for example: *Completeness*,
            *Accuracy*, *Consistency*, *Timeliness*, *Availability*.
        table_comparison_name (Union[Unset, str]): The name of a configured table comparison. When the table comparison
            is provided, DQOps will only perform table comparison checks that compare data between tables.
        check_name (Union[Unset, str]): The target check name to run only this named check. Uses the short check name
            which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as:
            'profiling_\*', '\*_count', 'profiling_\*_percent'.
        sensor_name (Union[Unset, str]): The target sensor name to run only data quality checks that are using this
            sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports
            search patterns such as: 'table/volume/row_\*', '\*_count', 'table/volume/prefix_\*_suffix'.
        check_hierarchy_ids_models (Union[Unset, List['HierarchyIdModel']]):
    """

    connection: Union[Unset, str] = UNSET
    full_table_name: Union[Unset, str] = UNSET
    enabled: Union[Unset, bool] = UNSET
    tags: Union[Unset, List[str]] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    max_results: Union[Unset, int] = UNSET
    column: Union[Unset, str] = UNSET
    column_data_type: Union[Unset, str] = UNSET
    column_nullable: Union[Unset, bool] = UNSET
    check_target: Union[Unset, CheckTarget] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    time_scale: Union[Unset, CheckTimeScale] = UNSET
    check_category: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    table_comparison_name: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    check_hierarchy_ids_models: Union[Unset, List["HierarchyIdModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        full_table_name = self.full_table_name
        enabled = self.enabled
        tags: Union[Unset, List[str]] = UNSET
        if not isinstance(self.tags, Unset):
            tags = self.tags

        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        max_results = self.max_results
        column = self.column
        column_data_type = self.column_data_type
        column_nullable = self.column_nullable
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        time_scale: Union[Unset, str] = UNSET
        if not isinstance(self.time_scale, Unset):
            time_scale = self.time_scale.value

        check_category = self.check_category
        quality_dimension = self.quality_dimension
        table_comparison_name = self.table_comparison_name
        check_name = self.check_name
        sensor_name = self.sensor_name
        check_hierarchy_ids_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.check_hierarchy_ids_models, Unset):
            check_hierarchy_ids_models = []
            for check_hierarchy_ids_models_item_data in self.check_hierarchy_ids_models:
                check_hierarchy_ids_models_item = (
                    check_hierarchy_ids_models_item_data.to_dict()
                )

                check_hierarchy_ids_models.append(check_hierarchy_ids_models_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection is not UNSET:
            field_dict["connection"] = connection
        if full_table_name is not UNSET:
            field_dict["fullTableName"] = full_table_name
        if enabled is not UNSET:
            field_dict["enabled"] = enabled
        if tags is not UNSET:
            field_dict["tags"] = tags
        if labels is not UNSET:
            field_dict["labels"] = labels
        if max_results is not UNSET:
            field_dict["maxResults"] = max_results
        if column is not UNSET:
            field_dict["column"] = column
        if column_data_type is not UNSET:
            field_dict["columnDataType"] = column_data_type
        if column_nullable is not UNSET:
            field_dict["columnNullable"] = column_nullable
        if check_target is not UNSET:
            field_dict["checkTarget"] = check_target
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if time_scale is not UNSET:
            field_dict["timeScale"] = time_scale
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if table_comparison_name is not UNSET:
            field_dict["tableComparisonName"] = table_comparison_name
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if check_hierarchy_ids_models is not UNSET:
            field_dict["checkHierarchyIdsModels"] = check_hierarchy_ids_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.hierarchy_id_model import HierarchyIdModel

        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        full_table_name = d.pop("fullTableName", UNSET)

        enabled = d.pop("enabled", UNSET)

        tags = cast(List[str], d.pop("tags", UNSET))

        labels = cast(List[str], d.pop("labels", UNSET))

        max_results = d.pop("maxResults", UNSET)

        column = d.pop("column", UNSET)

        column_data_type = d.pop("columnDataType", UNSET)

        column_nullable = d.pop("columnNullable", UNSET)

        _check_target = d.pop("checkTarget", UNSET)
        check_target: Union[Unset, CheckTarget]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = CheckTarget(_check_target)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _time_scale = d.pop("timeScale", UNSET)
        time_scale: Union[Unset, CheckTimeScale]
        if isinstance(_time_scale, Unset):
            time_scale = UNSET
        else:
            time_scale = CheckTimeScale(_time_scale)

        check_category = d.pop("checkCategory", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        table_comparison_name = d.pop("tableComparisonName", UNSET)

        check_name = d.pop("checkName", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        check_hierarchy_ids_models = []
        _check_hierarchy_ids_models = d.pop("checkHierarchyIdsModels", UNSET)
        for check_hierarchy_ids_models_item_data in _check_hierarchy_ids_models or []:
            check_hierarchy_ids_models_item = HierarchyIdModel.from_dict(
                check_hierarchy_ids_models_item_data
            )

            check_hierarchy_ids_models.append(check_hierarchy_ids_models_item)

        check_search_filters = cls(
            connection=connection,
            full_table_name=full_table_name,
            enabled=enabled,
            tags=tags,
            labels=labels,
            max_results=max_results,
            column=column,
            column_data_type=column_data_type,
            column_nullable=column_nullable,
            check_target=check_target,
            check_type=check_type,
            time_scale=time_scale,
            check_category=check_category,
            quality_dimension=quality_dimension,
            table_comparison_name=table_comparison_name,
            check_name=check_name,
            sensor_name=sensor_name,
            check_hierarchy_ids_models=check_hierarchy_ids_models,
        )

        check_search_filters.additional_properties = d
        return check_search_filters

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
