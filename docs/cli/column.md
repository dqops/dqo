#column

___
###<b><u>column add</u></b>

<b>Description:</b>

Add a column with specified details

<b>Synopsis:</b>
<pre><code> column add [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
            [-d&#x3D;&lt;dataType&gt;] [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;]
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-d`<br/>`--dataType`<br/></td>
    <td>Data type</td>
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
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>column remove</u></b>

<b>Description:</b>

Remove column or columns which match filters

<b>Synopsis:</b>
<pre><code> column remove [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
               [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;]
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
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
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>column update</u></b>

<b>Description:</b>

Update column or columns which match filters

<b>Synopsis:</b>
<pre><code> column update [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
               [-d&#x3D;&lt;dataType&gt;] [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;]
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-d`<br/>`--dataType`<br/></td>
    <td>Data type</td>
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
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>column list</u></b>

<b>Description:</b>

List columns which match filters

<b>Synopsis:</b>
<pre><code> column list [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
             [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;] [-l&#x3D;&lt;labels&gt;]...
             [-tg&#x3D;&lt;tags&gt;]...
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Connection name filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name filter</td>
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
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table name filter</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-tg`<br/>`--tags`<br/></td>
    <td>Data stream tag filter</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>column enable</u></b>

<b>Description:</b>

Enable column or columns which match filters

<b>Synopsis:</b>
<pre><code> column enable [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
               [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;]
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
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
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>column disable</u></b>

<b>Description:</b>

Disable column or columns which match filters

<b>Synopsis:</b>
<pre><code> column disable [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
                [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;]
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
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
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>column rename</u></b>

<b>Description:</b>

Rename column which match filters

<b>Synopsis:</b>
<pre><code> column rename [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-C&#x3D;&lt;columnName&gt;]
               [-n&#x3D;&lt;newColumnName&gt;] [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;fullTableName&gt;]
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
    <td>`-C`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
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
    <td>`-n`<br/>`--newColumn`<br/></td>
    <td>New column name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>
