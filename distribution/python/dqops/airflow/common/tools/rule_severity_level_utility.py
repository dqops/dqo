from dqops.client.models.check_result_status import CheckResultStatus
from dqops.client.models.rule_severity_level import RuleSeverityLevel


def get_severity_value_from_rule_severity(rule_severity: RuleSeverityLevel) -> int:
    """
    Maps the rule severity level to the corresponding int value.

    Parameters
    ----------
    rule_severity: RuleSeverityLevel
        The rule severity.
    """

    if rule_severity == RuleSeverityLevel.VALID:
        return 0
    if rule_severity == RuleSeverityLevel.WARNING:
        return 1
    if rule_severity == RuleSeverityLevel.ERROR:
        return 2
    if rule_severity == RuleSeverityLevel.FATAL:
        return 3
    raise Exception("Unrecognized rule severity level : " + str(rule_severity))


def get_severity_value_from_check_result(check_result_status: CheckResultStatus) -> int:
    """
    Maps the check result to the corresponding int value.

    Parameters
    ----------
    check_result_status: CheckResultStatus
        The check result.
    """

    if check_result_status is None:
        return 0
    if check_result_status == CheckResultStatus.VALID:
        return 0
    if check_result_status == CheckResultStatus.WARNING:
        return 1
    if check_result_status == CheckResultStatus.ERROR:
        return 2
    if check_result_status == CheckResultStatus.FATAL:
        return 3
    if check_result_status == CheckResultStatus.EXECUTION_ERROR:
        return 4
    raise Exception("Unrecognized check result status : " + str(check_result_status))
