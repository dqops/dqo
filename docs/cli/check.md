#check

___
###<b><u>check run</u></b>

<b>Description:</b>

Run data quality checks matching specified filters

<b>Synopsis:</b>
<pre><code> check run [-deh] [-fw] [-hl] [-c&#x3D;&lt;connection&gt;] [-cat&#x3D;&lt;checkCategory&gt;]
           [-ch&#x3D;&lt;check&gt;] [-col&#x3D;&lt;column&gt;] [-ct&#x3D;&lt;checkType&gt;] [-f&#x3D;&lt;failAt&gt;]
           [-m&#x3D;&lt;mode&gt;] [-of&#x3D;&lt;outputFormat&gt;] [-s&#x3D;&lt;sensor&gt;] [-t&#x3D;&lt;table&gt;]
           [-ts&#x3D;&lt;timeScale&gt;] [-l&#x3D;&lt;labels&gt;]... [-tag&#x3D;&lt;tags&gt;]...
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
    <td>`-cat`<br/>`--category`<br/></td>
    <td>Check category name (standard, nulls, numeric, etc.)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-ch`<br/>`--check`<br/></td>
    <td>Data quality check name, supports patterns like &#x27;*_id&#x27;</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-ct`<br/>`--check-type`<br/></td>
    <td>Data quality check type (adhoc, checkpoint, partitioned)</td>
    <td>ADHOC<br/>CHECKPOINT<br/>PARTITIONED<br/></td>
    </tr>
    
    <tr>
    <td>`-col`<br/>`--column`<br/></td>
    <td>Column name, supports patterns like &#x27;*_id&#x27;</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name, supports patterns like &#x27;conn*&#x27;</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-tag`<br/>`--data-stream-level-tag`<br/></td>
    <td>Data stream hierarchy level filter (tag)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-d`<br/>`--dummy`<br/></td>
    <td>Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-e`<br/>`--enabled`<br/></td>
    <td>Runs only enabled or only disabled sensors, by default only enabled sensors are executed</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-f`<br/>`--fail-at`<br/></td>
    <td>Lowest data quality issue severity level (warning, error, fatal) that will cause the command to return with an error code. Use &#x27;none&#x27; to return always a success error code.</td>
    <td>warning<br/>error<br/>fatal<br/>none<br/></td>
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
    <td>`-m`<br/>`--mode`<br/></td>
    <td>Reporting mode (silent, summary, info, debug)</td>
    <td>silent<br/>summary<br/>info<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`-s`<br/>`--sensor`<br/></td>
    <td>Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-ts`<br/>`--time-scale`<br/></td>
    <td>Time scale for checkpoint and partitioned checks (daily, monthly, etc.)</td>
    <td>daily<br/>monthly<br/></td>
    </tr>
    
    </tbody>
    </table>
