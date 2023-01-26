#cloud

___
###<b><u>cloud login</u></b>

<b>Description:</b>

Logs in or registers an account at DQO Cloud

<b>Synopsis:</b>
<pre><code> cloud login [-h] [-fw] [-hl] [-of&#x3D;&lt;outputFormat&gt;]
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
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>cloud sync data</u></b>

<b>Description:</b>

Synchronize local &quot;data&quot; folder with sensor readouts and rule results with DQO Cloud

<b>Synopsis:</b>
<pre><code> cloud sync data [-h] [-fw] [-hl] [-m&#x3D;&lt;mode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
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
    <td>`-m`<br/>`--mode`<br/></td>
    <td>Reporting mode (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>cloud sync sources</u></b>

<b>Description:</b>

Synchronize local &quot;sources&quot; connection and table level quality definitions with DQO Cloud

<b>Synopsis:</b>
<pre><code> cloud sync sources [-h] [-fw] [-hl] [-m&#x3D;&lt;mode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
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
    <td>`-m`<br/>`--mode`<br/></td>
    <td>Reporting mode (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>cloud sync sensors</u></b>

<b>Description:</b>

Synchronize local &quot;sensors&quot; folder with custom sensor definitions with DQO Cloud

<b>Synopsis:</b>
<pre><code> cloud sync sensors [-h] [-fw] [-hl] [-m&#x3D;&lt;mode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
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
    <td>`-m`<br/>`--mode`<br/></td>
    <td>Reporting mode (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>cloud sync rules</u></b>

<b>Description:</b>

Synchronize local &quot;rules&quot; folder with custom rule definitions with DQO Cloud

<b>Synopsis:</b>
<pre><code> cloud sync rules [-h] [-fw] [-hl] [-m&#x3D;&lt;mode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
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
    <td>`-m`<br/>`--mode`<br/></td>
    <td>Reporting mode (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>cloud sync all</u></b>

<b>Description:</b>

Synchronize local files with DQO Cloud (sources, table rules, custom rules, custom sensors and data - sensor readouts and rule results)

<b>Synopsis:</b>
<pre><code> cloud sync all [-h] [-fw] [-hl] [-m&#x3D;&lt;mode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
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
    <td>`-m`<br/>`--mode`<br/></td>
    <td>Reporting mode (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    </tbody>
    </table>
