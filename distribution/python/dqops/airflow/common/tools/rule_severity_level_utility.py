from dqops.client.models.rule_severity_level import RuleSeverityLevel


def get_severity_value(rule_severity: RuleSeverityLevel) -> int:
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
