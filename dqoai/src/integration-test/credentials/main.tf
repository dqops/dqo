resource "google_project_service" "enable_secret_manager" {
   project = var.google_project_id
   service = "secretmanager.googleapis.com"
}

resource "google_bigquery_dataset" "dataset" {
   project        = var.google_project_id
   dataset_id     = var.google_bigquery_dataset
   friendly_name  = var.google_bigquery_dataset
   description    = "Testable data for dqo.ai integration tests"
   location       = var.google_region
}


resource "google_secret_manager_secret" "bigquery-project" {
   project   = var.google_project_id
   secret_id = "bigquery-project"

   labels = {
      label = "bigquery-project"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "bigquery-project" {
   secret = google_secret_manager_secret.bigquery-project.id
   secret_data = var.google_project_id
}


resource "google_secret_manager_secret" "bigquery-dataset" {
   project   = var.google_project_id
   secret_id = "bigquery-dataset"

   labels = {
      label = "bigquery-dataset"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "bigquery-dataset" {
   secret = google_secret_manager_secret.bigquery-dataset.id
   secret_data = var.google_bigquery_dataset
}


resource "google_secret_manager_secret" "snowflake-account" {
   project   = var.google_project_id
   secret_id = "snowflake-account"

   labels = {
      label = "snowflake-account"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "snowflake-account" {
   secret = google_secret_manager_secret.snowflake-account.id
   secret_data = var.snowflake_account
}


resource "google_secret_manager_secret" "snowflake-warehouse" {
   project   = var.google_project_id
   secret_id = "snowflake-warehouse"

   labels = {
      label = "snowflake-warehouse"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "snowflake-warehouse" {
   secret = google_secret_manager_secret.snowflake-warehouse.id
   secret_data = var.snowflake_warehouse
}


resource "google_secret_manager_secret" "snowflake-database" {
   project   = var.google_project_id
   secret_id = "snowflake-database"

   labels = {
      label = "snowflake-database"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "snowflake-database" {
   secret = google_secret_manager_secret.snowflake-database.id
   secret_data = var.snowflake_database
}


resource "google_secret_manager_secret" "snowflake-user" {
   project   = var.google_project_id
   secret_id = "snowflake-user"

   labels = {
      label = "snowflake-user"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "snowflake-user" {
   secret = google_secret_manager_secret.snowflake-user.id
   secret_data = var.snowflake_user
}


resource "google_secret_manager_secret" "snowflake-password" {
   project   = var.google_project_id
   secret_id = "snowflake-password"

   labels = {
      label = "snowflake-password"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "snowflake-password" {
   secret = google_secret_manager_secret.snowflake-password.id
   secret_data = var.snowflake_password
}

resource "google_secret_manager_secret" "testable-api-key" {
   project   = var.google_project_id
   secret_id = "testable-api-key"

   labels = {
      label = "testable-api-key"
   }

   replication {
      automatic = true
   }
}

resource "google_secret_manager_secret_version" "testable-api-key" {
   secret = google_secret_manager_secret.testable-api-key.id
   secret_data = var.testable_api_key
}
