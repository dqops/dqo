from enum import Enum


class BigQueryJobsCreateProject(str, Enum):
    CREATE_JOBS_IN_DEFAULT_PROJECT_FROM_CREDENTIALS = (
        "create_jobs_in_default_project_from_credentials"
    )
    CREATE_JOBS_IN_SELECTED_BILLING_PROJECT_ID = (
        "create_jobs_in_selected_billing_project_id"
    )
    CREATE_JOBS_IN_SOURCE_PROJECT = "create_jobs_in_source_project"

    def __str__(self) -> str:
        return str(self.value)
