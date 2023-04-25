# Foreign key match percent

In this example we will check the data of `bigquery-public-data.census_utility.fips_codes_all` using `foreign_key_match_percent` check.
Our goal is to set up an integrity check on `state_fips_code` column in order to check how many percent of data in this column
matches the values in the indicated by user column in foreign table in this case this is `bigquery-public-data.census_utility.fips_codes_states` column `state_fips_code`.

