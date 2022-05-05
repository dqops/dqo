resource "google_storage_bucket" "tfstate_bucket" {
   name = "${var.google_project_id}-credentials-tfstate"
   location = var.google_region
   force_destroy = false
  
   versioning {
      enabled = "true"
   }
}
