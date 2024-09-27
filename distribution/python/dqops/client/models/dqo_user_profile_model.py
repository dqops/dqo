from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_user_role import DqoUserRole
from ..types import UNSET, Unset

T = TypeVar("T", bound="DqoUserProfileModel")


@_attrs_define
class DqoUserProfileModel:
    """The model that describes the current user and his access rights.

    Attributes:
        user (Union[Unset, str]): User email.
        tenant (Union[Unset, str]): DQOps Cloud tenant.
        license_type (Union[Unset, str]): DQOps Cloud license type.
        trial_period_expires_at (Union[Unset, str]): The date and time when the trial period of a PERSONAL DQOps license
            expires and the account is downgraded to a FREE license.
        connections_limit (Union[Unset, int]): Limit of the number of connections that can be synchronized to the DQOps
            Cloud data quality warehouse.
        users_limit (Union[Unset, int]): Limit of the number of users that can be added to a DQOps environment.
        months_limit (Union[Unset, int]): Limit of the number of recent months (excluding the current month) that can be
            synchronized to the DQOps Cloud data quality warehouse.
        connection_tables_limit (Union[Unset, int]): Limit of the number of tables inside each connection that can be
            synchronized to the DQOps Cloud data quality warehouse.
        tables_limit (Union[Unset, int]): Limit of the total number of tables that can be synchronized to the DQOps
            Cloud data quality warehouse.
        jobs_limit (Union[Unset, int]): Limit of the number of supported concurrent jobs that DQOps can run in parallel
            on this instance.
        data_domains_limit (Union[Unset, int]): Limit of the number of supported data domains in an ENTERPRISE version
            of DQOps.
        account_role (Union[Unset, DqoUserRole]):
        data_quality_data_warehouse_enabled (Union[Unset, bool]): True when the account has access to the DQOps Cloud's
            data quality data lake and data warehouse, allowing to synchronize files and use the data quality data
            warehouse.
        can_manage_account (Union[Unset, bool]): User is the administrator of the account and can perform security
            related actions, such as managing users.
        can_view_any_object (Union[Unset, bool]): User can view any object and view all results.
        can_manage_scheduler (Union[Unset, bool]): User can start and stop the job scheduler.
        can_cancel_jobs (Union[Unset, bool]): User can cancel running jobs.
        can_run_checks (Union[Unset, bool]): User can run data quality checks.
        can_delete_data (Union[Unset, bool]): User can delete data quality results.
        can_collect_statistics (Union[Unset, bool]): User can collect statistics.
        can_manage_data_sources (Union[Unset, bool]): User can manage data sources: create connections, import tables,
            change the configuration of connections, tables, columns. Change any settings in the Data Sources section.
        can_synchronize (Union[Unset, bool]): User can trigger the synchronization with DQOps Cloud.
        can_edit_comments (Union[Unset, bool]): User can edit comments on connections, tables, columns.
        can_edit_labels (Union[Unset, bool]): User can edit labels on connections, tables, columns.
        can_manage_definitions (Union[Unset, bool]): User can manage definitions of sensors, rules, checks and the
            default data quality check configuration that is applied on imported tables.
        can_compare_tables (Union[Unset, bool]): User can define table comparison configurations and compare tables.
        can_manage_users (Union[Unset, bool]): User can manage other users, add users to a multi-user account, change
            access rights, reset passwords.
        can_manage_and_view_shared_credentials (Union[Unset, bool]): User can manage shared credentials and view (or
            download) already defined shared credentials.
        can_change_own_password (Union[Unset, bool]): User can change his own password in DQOps Cloud, because the DQOps
            Cloud Pairing API Key is valid and synchronization is enabled.
        can_use_data_domains (Union[Unset, bool]): User can use data domains. Support for data domains requires an
            ENTERPRISE license of DQOps.
        can_synchronize_to_data_catalog (Union[Unset, bool]): User can synchronize data to a data catalog. The instance
            must be configured correctly and the user must have at least an EDITOR role.
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
    data_domains_limit: Union[Unset, int] = UNSET
    account_role: Union[Unset, DqoUserRole] = UNSET
    data_quality_data_warehouse_enabled: Union[Unset, bool] = UNSET
    can_manage_account: Union[Unset, bool] = UNSET
    can_view_any_object: Union[Unset, bool] = UNSET
    can_manage_scheduler: Union[Unset, bool] = UNSET
    can_cancel_jobs: Union[Unset, bool] = UNSET
    can_run_checks: Union[Unset, bool] = UNSET
    can_delete_data: Union[Unset, bool] = UNSET
    can_collect_statistics: Union[Unset, bool] = UNSET
    can_manage_data_sources: Union[Unset, bool] = UNSET
    can_synchronize: Union[Unset, bool] = UNSET
    can_edit_comments: Union[Unset, bool] = UNSET
    can_edit_labels: Union[Unset, bool] = UNSET
    can_manage_definitions: Union[Unset, bool] = UNSET
    can_compare_tables: Union[Unset, bool] = UNSET
    can_manage_users: Union[Unset, bool] = UNSET
    can_manage_and_view_shared_credentials: Union[Unset, bool] = UNSET
    can_change_own_password: Union[Unset, bool] = UNSET
    can_use_data_domains: Union[Unset, bool] = UNSET
    can_synchronize_to_data_catalog: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

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
        data_domains_limit = self.data_domains_limit
        account_role: Union[Unset, str] = UNSET
        if not isinstance(self.account_role, Unset):
            account_role = self.account_role.value

        data_quality_data_warehouse_enabled = self.data_quality_data_warehouse_enabled
        can_manage_account = self.can_manage_account
        can_view_any_object = self.can_view_any_object
        can_manage_scheduler = self.can_manage_scheduler
        can_cancel_jobs = self.can_cancel_jobs
        can_run_checks = self.can_run_checks
        can_delete_data = self.can_delete_data
        can_collect_statistics = self.can_collect_statistics
        can_manage_data_sources = self.can_manage_data_sources
        can_synchronize = self.can_synchronize
        can_edit_comments = self.can_edit_comments
        can_edit_labels = self.can_edit_labels
        can_manage_definitions = self.can_manage_definitions
        can_compare_tables = self.can_compare_tables
        can_manage_users = self.can_manage_users
        can_manage_and_view_shared_credentials = (
            self.can_manage_and_view_shared_credentials
        )
        can_change_own_password = self.can_change_own_password
        can_use_data_domains = self.can_use_data_domains
        can_synchronize_to_data_catalog = self.can_synchronize_to_data_catalog

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
        if data_domains_limit is not UNSET:
            field_dict["data_domains_limit"] = data_domains_limit
        if account_role is not UNSET:
            field_dict["account_role"] = account_role
        if data_quality_data_warehouse_enabled is not UNSET:
            field_dict["data_quality_data_warehouse_enabled"] = (
                data_quality_data_warehouse_enabled
            )
        if can_manage_account is not UNSET:
            field_dict["can_manage_account"] = can_manage_account
        if can_view_any_object is not UNSET:
            field_dict["can_view_any_object"] = can_view_any_object
        if can_manage_scheduler is not UNSET:
            field_dict["can_manage_scheduler"] = can_manage_scheduler
        if can_cancel_jobs is not UNSET:
            field_dict["can_cancel_jobs"] = can_cancel_jobs
        if can_run_checks is not UNSET:
            field_dict["can_run_checks"] = can_run_checks
        if can_delete_data is not UNSET:
            field_dict["can_delete_data"] = can_delete_data
        if can_collect_statistics is not UNSET:
            field_dict["can_collect_statistics"] = can_collect_statistics
        if can_manage_data_sources is not UNSET:
            field_dict["can_manage_data_sources"] = can_manage_data_sources
        if can_synchronize is not UNSET:
            field_dict["can_synchronize"] = can_synchronize
        if can_edit_comments is not UNSET:
            field_dict["can_edit_comments"] = can_edit_comments
        if can_edit_labels is not UNSET:
            field_dict["can_edit_labels"] = can_edit_labels
        if can_manage_definitions is not UNSET:
            field_dict["can_manage_definitions"] = can_manage_definitions
        if can_compare_tables is not UNSET:
            field_dict["can_compare_tables"] = can_compare_tables
        if can_manage_users is not UNSET:
            field_dict["can_manage_users"] = can_manage_users
        if can_manage_and_view_shared_credentials is not UNSET:
            field_dict["can_manage_and_view_shared_credentials"] = (
                can_manage_and_view_shared_credentials
            )
        if can_change_own_password is not UNSET:
            field_dict["can_change_own_password"] = can_change_own_password
        if can_use_data_domains is not UNSET:
            field_dict["can_use_data_domains"] = can_use_data_domains
        if can_synchronize_to_data_catalog is not UNSET:
            field_dict["can_synchronize_to_data_catalog"] = (
                can_synchronize_to_data_catalog
            )

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

        data_domains_limit = d.pop("data_domains_limit", UNSET)

        _account_role = d.pop("account_role", UNSET)
        account_role: Union[Unset, DqoUserRole]
        if isinstance(_account_role, Unset):
            account_role = UNSET
        else:
            account_role = DqoUserRole(_account_role)

        data_quality_data_warehouse_enabled = d.pop(
            "data_quality_data_warehouse_enabled", UNSET
        )

        can_manage_account = d.pop("can_manage_account", UNSET)

        can_view_any_object = d.pop("can_view_any_object", UNSET)

        can_manage_scheduler = d.pop("can_manage_scheduler", UNSET)

        can_cancel_jobs = d.pop("can_cancel_jobs", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        can_manage_data_sources = d.pop("can_manage_data_sources", UNSET)

        can_synchronize = d.pop("can_synchronize", UNSET)

        can_edit_comments = d.pop("can_edit_comments", UNSET)

        can_edit_labels = d.pop("can_edit_labels", UNSET)

        can_manage_definitions = d.pop("can_manage_definitions", UNSET)

        can_compare_tables = d.pop("can_compare_tables", UNSET)

        can_manage_users = d.pop("can_manage_users", UNSET)

        can_manage_and_view_shared_credentials = d.pop(
            "can_manage_and_view_shared_credentials", UNSET
        )

        can_change_own_password = d.pop("can_change_own_password", UNSET)

        can_use_data_domains = d.pop("can_use_data_domains", UNSET)

        can_synchronize_to_data_catalog = d.pop(
            "can_synchronize_to_data_catalog", UNSET
        )

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
            data_domains_limit=data_domains_limit,
            account_role=account_role,
            data_quality_data_warehouse_enabled=data_quality_data_warehouse_enabled,
            can_manage_account=can_manage_account,
            can_view_any_object=can_view_any_object,
            can_manage_scheduler=can_manage_scheduler,
            can_cancel_jobs=can_cancel_jobs,
            can_run_checks=can_run_checks,
            can_delete_data=can_delete_data,
            can_collect_statistics=can_collect_statistics,
            can_manage_data_sources=can_manage_data_sources,
            can_synchronize=can_synchronize,
            can_edit_comments=can_edit_comments,
            can_edit_labels=can_edit_labels,
            can_manage_definitions=can_manage_definitions,
            can_compare_tables=can_compare_tables,
            can_manage_users=can_manage_users,
            can_manage_and_view_shared_credentials=can_manage_and_view_shared_credentials,
            can_change_own_password=can_change_own_password,
            can_use_data_domains=can_use_data_domains,
            can_synchronize_to_data_catalog=can_synchronize_to_data_catalog,
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
