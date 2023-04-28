# String match date regex percent

In this example we will check the data of `bigquery-public-data.america_health_rankings.ahr` using `string_match_date_regex_percent` check.
Our goal is to set up a validity check on `source_date` column in order to check how many percent of values 
matches the indicated by the user date format, in this case this is `YYYY-MM-DD`.

