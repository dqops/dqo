from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.big_query_authentication_mode import BigQueryAuthenticationMode
from ..models.big_query_jobs_create_project import BigQueryJobsCreateProject
from ..types import UNSET, Unset

T = TypeVar("T", bound="BigQueryParametersSpec")


@_attrs_define
class BigQueryParametersSpec:
    """
    Attributes:
        source_project_id (Union[Unset, str]): Source GCP project ID. This is the project that has datasets that will be
            imported.
        jobs_create_project (Union[Unset, BigQueryJobsCreateProject]):
        billing_project_id (Union[Unset, str]): Billing GCP project ID. This is the project used as the default GCP
            project. The calling user must have a bigquery.jobs.create permission in this project.
        authentication_mode (Union[Unset, BigQueryAuthenticationMode]):
        json_key_content (Union[Unset, str]): JSON key content. Use an environment variable that contains the content of
            the key as ${KEY_ENV} or a name of a secret in the GCP Secret Manager: ${sm://key-secret-name}. Requires the
            authentication-mode: json_key_content.
        json_key_path (Union[Unset, str]): A path to the JSON key file. Requires the authentication-mode: json_key_path.
        quota_project_id (Union[Unset, str]): Quota GCP project ID.
    """

    source_project_id: Union[Unset, str] = UNSET
    jobs_create_project: Union[Unset, BigQueryJobsCreateProject] = UNSET
    billing_project_id: Union[Unset, str] = UNSET
    authentication_mode: Union[Unset, BigQueryAuthenticationMode] = UNSET
    json_key_content: Union[Unset, str] = UNSET
    json_key_path: Union[Unset, str] = UNSET
    quota_project_id: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        source_project_id = self.source_project_id
        jobs_create_project: Union[Unset, str] = UNSET
        if not isinstance(self.jobs_create_project, Unset):
            jobs_create_project = self.jobs_create_project.value

        billing_project_id = self.billing_project_id
        authentication_mode: Union[Unset, str] = UNSET
        if not isinstance(self.authentication_mode, Unset):
            authentication_mode = self.authentication_mode.value

        json_key_content = self.json_key_content
        json_key_path = self.json_key_path
        quota_project_id = self.quota_project_id

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if source_project_id is not UNSET:
            field_dict["source_project_id"] = source_project_id
        if jobs_create_project is not UNSET:
            field_dict["jobs_create_project"] = jobs_create_project
        if billing_project_id is not UNSET:
            field_dict["billing_project_id"] = billing_project_id
        if authentication_mode is not UNSET:
            field_dict["authentication_mode"] = authentication_mode
        if json_key_content is not UNSET:
            field_dict["json_key_content"] = json_key_content
        if json_key_path is not UNSET:
            field_dict["json_key_path"] = json_key_path
        if quota_project_id is not UNSET:
            field_dict["quota_project_id"] = quota_project_id

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        source_project_id = d.pop("source_project_id", UNSET)

        _jobs_create_project = d.pop("jobs_create_project", UNSET)
        jobs_create_project: Union[Unset, BigQueryJobsCreateProject]
        if isinstance(_jobs_create_project, Unset):
            jobs_create_project = UNSET
        else:
            jobs_create_project = BigQueryJobsCreateProject(_jobs_create_project)

        billing_project_id = d.pop("billing_project_id", UNSET)

        _authentication_mode = d.pop("authentication_mode", UNSET)
        authentication_mode: Union[Unset, BigQueryAuthenticationMode]
        if isinstance(_authentication_mode, Unset):
            authentication_mode = UNSET
        else:
            authentication_mode = BigQueryAuthenticationMode(_authentication_mode)

        json_key_content = d.pop("json_key_content", UNSET)

        json_key_path = d.pop("json_key_path", UNSET)

        quota_project_id = d.pop("quota_project_id", UNSET)

        big_query_parameters_spec = cls(
            source_project_id=source_project_id,
            jobs_create_project=jobs_create_project,
            billing_project_id=billing_project_id,
            authentication_mode=authentication_mode,
            json_key_content=json_key_content,
            json_key_path=json_key_path,
            quota_project_id=quota_project_id,
        )

        big_query_parameters_spec.additional_properties = d
        return big_query_parameters_spec

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
