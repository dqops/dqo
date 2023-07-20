from enum import Enum


class CheckContainerModelEffectiveScheduleEnabledStatus(str, Enum):
    DISABLED = "disabled"
    ENABLED = "enabled"
    NOT_CONFIGURED = "not_configured"
    OVERRIDDEN_BY_CHECKS = "overridden_by_checks"

    def __str__(self) -> str:
        return str(self.value)
