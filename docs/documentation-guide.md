# DQOps documentation guide
This page is the starting point to explore DQOps documentation. It lists all the main topics that you should learn to measure data quality with DQOps.

## DQOps documentation
<div class="grid cards" markdown>

-   :material-home:{ .lg .middle } [__What is DQOps__](index.md)

    ---

    DQOps is a data quality platform for  **data quality teams** and **data engineering teams**
    to make data quality visible to **business sponsors**.

    Learn how DQOps addresses the needs for them.

    [:octicons-arrow-right-24: What is DQOps?](index.md)


-   :material-book-open-page-variant:{ .lg .middle } [__Getting started__](getting-started/index.md)

    ---

    Learn how to start using DQOps, install the platform locally, connect a data source,
    run a data quality check, and review the results on data quality dashboards.

    [:octicons-arrow-right-24: Getting started with DQOps](getting-started/index.md)


-   :material-lightbulb-on-90:{ .lg .middle } [__DQOps concepts__](dqo-concepts/index.md)

    ---

    This section describes all the concepts behind DQOps. You will understand how data quality checks work and how to configure them.

    [:octicons-arrow-right-24: Learn about DQOps concepts](dqo-concepts/index.md)

-   :fontawesome-solid-user-gear:{ .lg .middle } [__Installation guide__](dqops-installation/index.md)

    ---

    DQOps can be installed locally as a package downloaded from PyPI or as a Docker container. Learn how to install the platform.

    [:octicons-arrow-right-24: Download DQOps from PyPI](dqops-installation/install-dqops-using-pip.md) or [Docker Hub](dqops-installation/run-dqops-as-docker-container.md)

-   :material-database:{ .lg .middle } [__Data sources__](data-sources/index.md)

    ---

    Find out what data sources DQOps supports and how to configure the connection parameters in the user interface or YAML files.

    [:octicons-arrow-right-24: List of supported data sources](data-sources/index.md)

-   :material-format-list-checks:{ .lg .middle } [__Categories of data quality checks__](categories-of-data-quality-checks/index.md)

    ---

    Find out what types of common data quality issues are easy to detect with DQOps.

    [:octicons-arrow-right-24: Categories of data quality checks](categories-of-data-quality-checks/index.md)


-   :material-book-education:{ .lg .middle } [__Data quality use cases and examples__](examples/index.md)

    ---

    DQOps use cases and examples are step-by-step guides to solving the most common data quality issues.

    [:octicons-arrow-right-24: Review data quality use cases](examples/index.md)


-   :material-tooltip-question:{ .lg .middle } [__Working with DQOps__](working-with-dqo/index.md)

    ---

    Learn how to work with DQOps, performing regular activities such as reviewing data quality results or managing incidents.

    [:octicons-arrow-right-24: Working with DQOps](working-with-dqo/index.md)


-   :material-power-plug:{ .lg .middle } [__DQOps Integrations__](integrations/index.md)

    ---

    Learn how to integrate DQOps with other data platforms or edit configuration files efficiently in Visual Studio Code.

    [:octicons-arrow-right-24: Integrating DQOps with other tools](integrations/index.md)

</div>

## References
<div class="grid cards" markdown>

-   :octicons-terminal-16:{ .lg .middle } [__DQOps command-line interface__](command-line-interface/index.md)

    ---

    The reference of all commands supported by the DQOps shell or usable from the command prompt.

    [:octicons-arrow-right-24: Command-line interface reference](command-line-interface/index.md)


-   :material-api:{ .lg .middle } [__DQOps REST API Python Client__](client/index.md)

    ---

    Learn how to integrate DQOps directly in Python scripts. Run data quality checks from data pipelines or detect fatal issues with source tables.

    [:octicons-arrow-right-24: REST API Python Client reference](client/index.md)


-   :material-book-open-variant:{ .lg .middle } [__Data quality checks reference__](checks/index.md)

    ---

    The reference of all data quality checks provided in DQOps shows configuration examples in YAML and SQL queries that DQOps uses for each data source.

    [:octicons-arrow-right-24: Data quality checks reference](checks/index.md)


-   :material-table-eye:{ .lg .middle } [__Data quality sensors reference__](reference/sensors/index.md)

    ---

    Data quality sensors are templates of SQL queries. Find out what SQL query DQOps uses for each data source.

    [:octicons-arrow-right-24: Data quality sensors reference](reference/sensors/index.md)


-   :material-ruler:{ .lg .middle } [__Data quality rules reference__](reference/rules/index.md)

    ---

    Data quality rules are Python functions that evaluate data quality measures. Find out what rules are bundled in DQOps.

    [:octicons-arrow-right-24: Data quality rules reference](reference/rules/index.md)


-   :material-file-code:{ .lg .middle } [__DQOps YAML files reference__](reference/yaml/index.md)

    ---

    DQOps stores the configuration of data sources and data quality checks in YAML files. Find the reference of every YAML element used by DQOps.

    [:octicons-arrow-right-24: DQOps YAML file schema reference](reference/yaml/index.md)


-   :material-table-large:{ .lg .middle } [__DQOps parquet tables reference__](reference/parquetfiles/index.md)

    ---

    DQOps stores the data quality results in a Hive-compliant data lake. Find the schema reference of every Parquet table used by DQOps.

    [:octicons-arrow-right-24: Parquet data tables reference](reference/parquetfiles/index.md)

</div>

## What's more
You should start by reading [what is DQOps](index.md) to understand how DQOps can help you.
Then follow the [getting started](getting-started/index.md) guide.
