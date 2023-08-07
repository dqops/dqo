from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="DqoUserProfileModel")


@attr.s(auto_attribs=True)
class DqoUserProfileModel:
    """The model that describes the current user and his access rights.

    Attributes:
        user (Union[Unset, str]): User email.
        tenant (Union[Unset, str]): DQO Cloud tenant.
        license_type (Union[Unset, str]): DQO Cloud license type.
        trial_period_expires_at (Union[Unset, str]): The date and time when the trial period of a PERSONAL DQO license
            expires and the account is downgraded to a FREE license.
        connections_limit (Union[Unset, int]): Limit of the number of connections that could be synchronized to the DQO
            Cloud data quality warehouse.
        users_limit (Union[Unset, int]): Limit of the number of users that could be added to a DQO environment.
        months_limit (Union[Unset, int]): Limit of the number of recent months (excluding the current month) that could
            be synchronized to the DQO Cloud data quality warehouse.
        connection_tables_limit (Union[Unset, int]): Limit of the number of tables inside each connection that could be
            synchronized to the DQO Cloud data quality warehouse.
        tables_limit (Union[Unset, int]): Limit of the total number of tables that could be synchronized to the DQO
            Cloud data quality warehouse.
        jobs_limit (Union[Unset, int]): Limit of the number of supported concurrent jobs that DQO can run in parallel on
            this instance.
    """

    user: Union[Unset, str] = UNSET
    tenant: Union[Unset, str] = UNSET
    license_type: Union[Unset, str] = UNSET
    trial_period_expires_at: Union[Unset, str] = UNSET
    connections_limit: Union[Unset, int] = UNSET
    users_limit: Union[Unset, int] = UNSET
    months_limit: Union[Unset, int] = UNSET
    connection_tables_limit: Union[Unset, int] = UNSET
    tables_limit: Union[Unset, int] = UNSET
    jobs_limit: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        user = self.user
        tenant = self.tenant
        license_type = self.license_type
        trial_period_expires_at = self.trial_period_expires_at
        connections_limit = self.connections_limit
        users_limit = self.users_limit
        months_limit = self.months_limit
        connection_tables_limit = self.connection_tables_limit
        tables_limit = self.tables_limit
        jobs_limit = self.jobs_limit

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if user is not UNSET:
            field_dict["user"] = user
        if tenant is not UNSET:
            field_dict["tenant"] = tenant
        if license_type is not UNSET:
            field_dict["license_type"] = license_type
        if trial_period_expires_at is not UNSET:
            field_dict["trial_period_expires_at"] = trial_period_expires_at
        if connections_limit is not UNSET:
            field_dict["connections_limit"] = connections_limit
        if users_limit is not UNSET:
            field_dict["users_limit"] = users_limit
        if months_limit is not UNSET:
            field_dict["months_limit"] = months_limit
        if connection_tables_limit is not UNSET:
            field_dict["connection_tables_limit"] = connection_tables_limit
        if tables_limit is not UNSET:
            field_dict["tables_limit"] = tables_limit
        if jobs_limit is not UNSET:
            field_dict["jobs_limit"] = jobs_limit

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        user = d.pop("user", UNSET)

        tenant = d.pop("tenant", UNSET)

        license_type = d.pop("license_type", UNSET)

        trial_period_expires_at = d.pop("trial_period_expires_at", UNSET)

        connections_limit = d.pop("connections_limit", UNSET)

        users_limit = d.pop("users_limit", UNSET)

        months_limit = d.pop("months_limit", UNSET)

        connection_tables_limit = d.pop("connection_tables_limit", UNSET)

        tables_limit = d.pop("tables_limit", UNSET)

        jobs_limit = d.pop("jobs_limit", UNSET)

        dqo_user_profile_model = cls(
            user=user,
            tenant=tenant,
            license_type=license_type,
            trial_period_expires_at=trial_period_expires_at,
            connections_limit=connections_limit,
            users_limit=users_limit,
            months_limit=months_limit,
            connection_tables_limit=connection_tables_limit,
            tables_limit=tables_limit,
            jobs_limit=jobs_limit,
        )

        dqo_user_profile_model.additional_properties = d
        return dqo_user_profile_model

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
