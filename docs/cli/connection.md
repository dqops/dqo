#connection

___
###<b><u>connection list</u></b>

<b>Description:</b>

List connections which match filters

<b>Synopsis:</b>
<pre><code> connection list [-h] [-fw] [-hl] [-n&#x3D;&lt;name&gt;] [-of&#x3D;&lt;outputFormat&gt;]
                 [-d&#x3D;&lt;dimensions&gt;]... [-l&#x3D;&lt;labels&gt;]...
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`-d`<br/>`--dimension`<br/></td>
    <td>Dimension filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-l`<br/>`--label`<br/></td>
    <td>Label filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-n`<br/>`--name`<br/></td>
    <td>Connection name filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection add</u></b>

<b>Description:</b>

Add connection with specified details

<b>Synopsis:</b>
<pre><code> connection add [-h] [-fw] [-hl] [--postgresql-ssl]
                [--bigquery-authentication-mode&#x3D;&lt;authenticationMode&gt;]
                [--bigquery-billing-project-id&#x3D;&lt;billingProjectId&gt;]
                [--bigquery-json-key-content&#x3D;&lt;jsonKeyContent&gt;]
                [--bigquery-json-key-path&#x3D;&lt;jsonKeyPath&gt;]
                [--bigquery-quota-project-id&#x3D;&lt;quotaProjectId&gt;]
                [--bigquery-source-project-id&#x3D;&lt;sourceProjectId&gt;] [-n&#x3D;&lt;name&gt;]
                [-of&#x3D;&lt;outputFormat&gt;] [--postgresql-database&#x3D;&lt;database&gt;]
                [--postgresql-host&#x3D;&lt;host&gt;] [--postgresql-options&#x3D;&lt;options&gt;]
                [--postgresql-password&#x3D;&lt;password&gt;] [--postgresql-port&#x3D;&lt;port&gt;]
                [--postgresql-user&#x3D;&lt;user&gt;] [--snowflake-account&#x3D;&lt;account&gt;]
                [--snowflake-database&#x3D;&lt;database&gt;]
                [--snowflake-password&#x3D;&lt;password&gt;] [--snowflake-role&#x3D;&lt;role&gt;]
                [--snowflake-user&#x3D;&lt;user&gt;] [--snowflake-warehouse&#x3D;&lt;warehouse&gt;]
                [-t&#x3D;&lt;providerType&gt;]
                [--postgresql-properties&#x3D;&lt;String&#x3D;String&gt;]...
                [--snowflake-properties&#x3D;&lt;String&#x3D;String&gt;]...
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`--bigquery-authentication-mode`<br/></td>
    <td>Bigquery authentication mode.</td>
    <td>google_application_credentials<br/>json_key_content<br/>json_key_path<br/></td>
    </tr>
    
    <tr>
    <td>`--bigquery-billing-project-id`<br/></td>
    <td>Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-json-key-content`<br/></td>
    <td>Bigquery service account key content as JSON.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-json-key-path`<br/></td>
    <td>Path to a GCP service account key JSON file used to authenticate to Bigquery.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-quota-project-id`<br/></td>
    <td>Bigquery quota GCP project id.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-source-project-id`<br/></td>
    <td>Bigquery source GCP project id. This is the project that has datasets that will be imported.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-n`<br/>`--name`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`--postgresql-database`<br/></td>
    <td>PostgreSQL database name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-host`<br/></td>
    <td>PostgreSQL host name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-options`<br/></td>
    <td>PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-password`<br/></td>
    <td>PostgreSQL database password. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-port`<br/></td>
    <td>PostgreSQL port number</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-properties`<br/></td>
    <td>PostgreSQL additional properties that are added to the JDBC connection string</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-ssl`<br/></td>
    <td>Connect to PostgreSQL using SSL</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-user`<br/></td>
    <td>PostgreSQL user name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--provider`<br/></td>
    <td>Connection provider type</td>
    <td>bigquery<br/>snowflake<br/>postgresql<br/></td>
    </tr>
    
    <tr>
    <td>`--snowflake-account`<br/></td>
    <td>Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-database`<br/></td>
    <td>Snowflake database name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-password`<br/></td>
    <td>Snowflake database password. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-properties`<br/></td>
    <td>Snowflake additional properties that are added to the JDBC connection string</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-role`<br/></td>
    <td>Snowflake role name.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-user`<br/></td>
    <td>Snowflake user name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-warehouse`<br/></td>
    <td>Snowflake warehouse name.</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection remove</u></b>

<b>Description:</b>

Remove connection or connections which match filters

<b>Synopsis:</b>
<pre><code> connection remove [-h] [-fw] [-hl] [-n&#x3D;&lt;name&gt;] [-of&#x3D;&lt;outputFormat&gt;]
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-n`<br/>`--name`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection update</u></b>

<b>Description:</b>

Update connection or connections which match filters

<b>Synopsis:</b>
<pre><code> connection update [-h] [-fw] [-hl] [--postgresql-ssl]
                   [--bigquery-authentication-mode&#x3D;&lt;authenticationMode&gt;]
                   [--bigquery-billing-project-id&#x3D;&lt;billingProjectId&gt;]
                   [--bigquery-json-key-content&#x3D;&lt;jsonKeyContent&gt;]
                   [--bigquery-json-key-path&#x3D;&lt;jsonKeyPath&gt;]
                   [--bigquery-quota-project-id&#x3D;&lt;quotaProjectId&gt;]
                   [--bigquery-source-project-id&#x3D;&lt;sourceProjectId&gt;] [-n&#x3D;&lt;name&gt;]
                   [-of&#x3D;&lt;outputFormat&gt;] [--postgresql-database&#x3D;&lt;database&gt;]
                   [--postgresql-host&#x3D;&lt;host&gt;] [--postgresql-options&#x3D;&lt;options&gt;]
                   [--postgresql-password&#x3D;&lt;password&gt;]
                   [--postgresql-port&#x3D;&lt;port&gt;] [--postgresql-user&#x3D;&lt;user&gt;]
                   [--snowflake-account&#x3D;&lt;account&gt;]
                   [--snowflake-database&#x3D;&lt;database&gt;]
                   [--snowflake-password&#x3D;&lt;password&gt;] [--snowflake-role&#x3D;&lt;role&gt;]
                   [--snowflake-user&#x3D;&lt;user&gt;]
                   [--snowflake-warehouse&#x3D;&lt;warehouse&gt;]
                   [--postgresql-properties&#x3D;&lt;String&#x3D;String&gt;]...
                   [--snowflake-properties&#x3D;&lt;String&#x3D;String&gt;]...
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`--bigquery-authentication-mode`<br/></td>
    <td>Bigquery authentication mode.</td>
    <td>google_application_credentials<br/>json_key_content<br/>json_key_path<br/></td>
    </tr>
    
    <tr>
    <td>`--bigquery-billing-project-id`<br/></td>
    <td>Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-json-key-content`<br/></td>
    <td>Bigquery service account key content as JSON.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-json-key-path`<br/></td>
    <td>Path to a GCP service account key JSON file used to authenticate to Bigquery.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-quota-project-id`<br/></td>
    <td>Bigquery quota GCP project id.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--bigquery-source-project-id`<br/></td>
    <td>Bigquery source GCP project id. This is the project that has datasets that will be imported.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-n`<br/>`--name`<br/></td>
    <td>Connection name, supports wildcards for changing multiple connections at once, i.e. &quot;conn*&quot;</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`--postgresql-database`<br/></td>
    <td>PostgreSQL database name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-host`<br/></td>
    <td>PostgreSQL host name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-options`<br/></td>
    <td>PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-password`<br/></td>
    <td>PostgreSQL database password. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-port`<br/></td>
    <td>PostgreSQL port number</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-properties`<br/></td>
    <td>PostgreSQL additional properties that are added to the JDBC connection string</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-ssl`<br/></td>
    <td>Connect to PostgreSQL using SSL</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--postgresql-user`<br/></td>
    <td>PostgreSQL user name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-account`<br/></td>
    <td>Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-database`<br/></td>
    <td>Snowflake database name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-password`<br/></td>
    <td>Snowflake database password. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-properties`<br/></td>
    <td>Snowflake additional properties that are added to the JDBC connection string</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-role`<br/></td>
    <td>Snowflake role name.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-user`<br/></td>
    <td>Snowflake user name. The value could be in the format null to use dynamic substitution.</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`--snowflake-warehouse`<br/></td>
    <td>Snowflake warehouse name.</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection schema list</u></b>

<b>Description:</b>

List schemas in source connection

<b>Synopsis:</b>
<pre><code> connection schema list [-h] [-fw] [-hl] [-n&#x3D;&lt;name&gt;] [-of&#x3D;&lt;outputFormat&gt;]
                        [-d&#x3D;&lt;dimensions&gt;]... [-l&#x3D;&lt;labels&gt;]...
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`-d`<br/>`--dimension`<br/></td>
    <td>Dimension filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-l`<br/>`--label`<br/></td>
    <td>Label filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-n`<br/>`--name`<br/></td>
    <td>Connection name filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection table list</u></b>

<b>Description:</b>

List tables for connection

<b>Synopsis:</b>
<pre><code> connection table list [-h] [-fw] [-hl] [-c&#x3D;&lt;connection&gt;] [-of&#x3D;&lt;outputFormat&gt;]
                       [-s&#x3D;&lt;schema&gt;] [-t&#x3D;&lt;table&gt;] [-d&#x3D;&lt;dimensions&gt;]...
                       [-l&#x3D;&lt;labels&gt;]...
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-d`<br/>`--dimension`<br/></td>
    <td>Dimension filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-l`<br/>`--label`<br/></td>
    <td>Label filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`-s`<br/>`--schema`<br/></td>
    <td>Schema name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection table show</u></b>

<b>Description:</b>

Show table for connection

<b>Synopsis:</b>
<pre><code> connection table show [-h] [-fw] [-hl] [-c&#x3D;&lt;connection&gt;] [-of&#x3D;&lt;outputFormat&gt;]
                       [-t&#x3D;&lt;table&gt;]
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Full table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>connection edit</u></b>

<b>Description:</b>

Edit connection which match filters

<b>Synopsis:</b>
<pre><code> connection edit [-h] [-fw] [-hl] [-n&#x3D;&lt;connection&gt;] [-of&#x3D;&lt;outputFormat&gt;]
</code></pre>

=== "Options"
    <table>
    <thead>
    <tr>
    <th>Command</th>
    <th>Description</th>
    <th>Accepted values:</th>
    </tr>
    </thead>
    <tbody>
    
    <tr>
    <td>`-n`<br/>`--connection`<br/></td>
    <td>Connection Name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-fw`<br/>`--file-write`<br/></td>
    <td>Write command response to a file</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-hl`<br/>`--headless`<br/></td>
    <td>Run the command in an headless (no user input allowed) mode</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-h`<br/>`--help`<br/></td>
    <td>Show the help for the command and parameters</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>
