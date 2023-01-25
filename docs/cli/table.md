#table

___
###<b><u>table import</u></b>

<b>Description:</b>

Import tables from a specified database

<b>Synopsis:</b>
<pre><code> table import [-h] [-fw] [-hl] [-c&#x3D;&lt;connection&gt;] [-of&#x3D;&lt;outputFormat&gt;]
              [-s&#x3D;&lt;schema&gt;] [-t&#x3D;&lt;table&gt;]
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
    
    <tr>
    <td>`-s`<br/>`--schema`<br/></td>
    <td>Schema Name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table Name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>table edit</u></b>

<b>Description:</b>

Edit table which match filters

<b>Synopsis:</b>
<pre><code> table edit [-h] [-fw] [-hl] [-c&#x3D;&lt;connection&gt;] [-of&#x3D;&lt;outputFormat&gt;] [-t&#x3D;&lt;table&gt;]
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
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Full table name (schema.table)</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>table add</u></b>

<b>Description:</b>

Add table with specified name

<b>Synopsis:</b>
<pre><code> table add [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-of&#x3D;&lt;outputFormat&gt;]
           [-t&#x3D;&lt;fullTableName&gt;]
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
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>table remove</u></b>

<b>Description:</b>

Remove tables which match filters

<b>Synopsis:</b>
<pre><code> table remove [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-of&#x3D;&lt;outputFormat&gt;]
              [-t&#x3D;&lt;fullTableName&gt;]
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
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>table update</u></b>

<b>Description:</b>

Update tables which match filters

<b>Synopsis:</b>
<pre><code> table update [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-n&#x3D;&lt;newTableName&gt;]
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
    <td>`-c`<br/>`--connection`<br/></td>
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
    <td>`-n`<br/>`--newTable`<br/></td>
    <td>New table name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>table list</u></b>

<b>Description:</b>

List tables which match filters

<b>Synopsis:</b>
<pre><code> table list [-h] [-fw] [-hl] [-c&#x3D;&lt;connectionName&gt;] [-of&#x3D;&lt;outputFormat&gt;]
            [-t&#x3D;&lt;tableName&gt;] [-d&#x3D;&lt;dimensions&gt;]... [-l&#x3D;&lt;labels&gt;]...
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
    <td>`-t`<br/>`--table`<br/></td>
    <td>Table name filter</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>
