from enum import Enum


class SingleStoreDbLoadBalancingMode(str, Enum):
    LOADBALANCE = "loadbalance"
    NONE = "none"
    SEQUENTIAL = "sequential"

    def __str__(self) -> str:
        return str(self.value)
