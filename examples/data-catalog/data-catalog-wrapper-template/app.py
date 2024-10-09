from flask import Flask, request
from dqops.client.models.table_current_data_quality_status_model import (
    TableCurrentDataQualityStatusModel
)

app = Flask(__name__)

@app.post("/pushtocatalog")
def sent_to_data_catalog():
    """
    REST API endpoint that receives updates of data quality status of tables. This endpoint can parse the JSON
    object into a TableCurrentDataQualityStatusModel object and retrieve all relevant information.

    This example shows how to retrieve the table name, its data quality KPI and the KPIs of all data quality dimensions.
    These values can be pushed to a data catalog using the vendor's data quality import API.

    :return: Empty response that is ignored.
    """
    json_body = request.get_json()
    current_table_status = TableCurrentDataQualityStatusModel().from_dict(json_body)

    print("Data quality KPI of table " + current_table_status.connection_name + "." + current_table_status.schema_name + \
          "." + current_table_status.table_name + " is " + str(current_table_status.data_quality_kpi) + "%")

    for dimension_model in current_table_status.dimensions.additional_properties.values():
        print("Data quality dimension: " + dimension_model.dimension + ", current status: " + str(dimension_model.current_severity) + ", KPI: " + str(dimension_model.data_quality_kpi))

    return ""


if __name__ == '__main__':
    app.run()