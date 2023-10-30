# Data quality check execution flow
DQOps is designed to support multiple deployment and distribution options. The platform could be started as a standalone Java Spring Boot
application, started as a Python package downloaded from PyPi or run inside a Docker container.

## Targeting connections and tables


``` { .asc .annotate hl_lines="5 6" }
$DQO_USER_HOME
├───.data                                                                   
│   └────...                                                  
└───sources                                                                
    └───sales-dwh (1)
        └─public.fact_sales.dqotable.yaml (2)
```

1. The target data source
2. The target table



``` { .asc .annotate hl_lines="6 7" }
$DQO_USER_HOME
├───.data                                                                   
│   └────...                                                  
└───sources                                                                
    └───sales-dwh (1)
        ├─public.dim_product.dqotable.yaml (2)
        ├─public.dim_date.dqotable.yaml (3)
        └─public.fact_sales.dqotable.yaml
```

1. The target data source
2. The first table that matches the *public.dim_\** pattern
3. The second table that matches the *public.dim_\** pattern


![DQOps simple data flow](https://dqops.com/docs/images/architecture/DQOPs-simple-data-flow-diagram-min.png)

![DQOps data quality checks and incidents workflow](https://dqops.com/docs/images/architecture/DQOps-data-quality-incident-flow-diagram-min.png)



![DQOps engine components](https://dqops.com/docs/images/architecture/DQOps-engine-components-min.png)
