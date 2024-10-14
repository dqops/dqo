from flask import Flask, request
from dqops.client.models.table_current_data_quality_status_model import (
    TableCurrentDataQualityStatusModel
)
from incident_notification_message import IncidentNotificationMessage

app = Flask(__name__)

@app.post("/pushnotification")
def sent_incident_notification():
    """
    REST API endpoint that receives notification message of data quality incident. This endpoint can parse the JSON
    object into an IncidentNotificationMessage object and retrieve all relevant information.

    This example shows how to retrieve the table name with additional incident notification data.

    :return: Empty response that is ignored.
    """
    json_body = request.get_json()
    incident_notification_message: IncidentNotificationMessage = IncidentNotificationMessage().from_dict(json_body)

    print("The table's " + incident_notification_message.connection + "." + incident_notification_message.schema + "." + incident_notification_message.table + " incident status is " + incident_notification_message.status + "\n" +
        "First seen: " + incident_notification_message.first_seen + "\n" +
        "Last seen: " + incident_notification_message.last_seen + "\n" +
        "Quality dimension: " + incident_notification_message.quality_dimension + "\n" +
        "Check category: " + incident_notification_message.check_category + "\n" +
        "Highest severity: " + str(incident_notification_message.highest_severity) + "\n" +
        "Total data quality issues: " + str(incident_notification_message.failed_checks_count))

    return ""

if __name__ == '__main__':
    app.run()