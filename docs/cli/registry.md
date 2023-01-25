#registry

___
###<b><u>registry clean</u></b>

<b>Description:</b>

Delete stored registry data matching specified filters

<b>Synopsis:</b>
<pre><code> registry clean [-h] [-day] [-er] [-fw] [-hl] [-pr] [-rr] [-sr] -b&#x3D;&lt;begin&gt;
                -c&#x3D;&lt;connection&gt; [-cat&#x3D;&lt;checkCategory&gt;] [-ch&#x3D;&lt;check&gt;]
                [-col&#x3D;&lt;column&gt;] [-ct&#x3D;&lt;checkType&gt;] [-ds&#x3D;&lt;dataStream&gt;] -e&#x3D;&lt;end&gt;
                [-of&#x3D;&lt;outputFormat&gt;] [-p&#x3D;&lt;profiler&gt;] [-pcat&#x3D;&lt;profilerCategory&gt;]
                [-pt&#x3D;&lt;profilerType&gt;] [-qd&#x3D;&lt;qualityDimension&gt;] [-s&#x3D;&lt;sensor&gt;]
                -t&#x3D;&lt;table&gt; [-tg&#x3D;&lt;timeGradient&gt;]
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
    <td>`-b`<br/>`--begin`<br/></td>
    <td>Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-cat`<br/>`--category`<br/></td>
    <td>Check category name (standard, nulls, numeric, etc.)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-ch`<br/>`--check`<br/></td>
    <td>Data quality check name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-ct`<br/>`--check-type`<br/></td>
    <td>Data quality check type (adhoc, checkpoint, partitioned)</td>
    <td>ADHOC<br/>CHECKPOINT<br/>PARTITIONED<br/></td>
    </tr>
    
    <tr>
    <td>`-col`<br/>`--column`<br/></td>
    <td>Column name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-c`<br/>`--connection`<br/></td>
    <td>Connection name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-day`<br/>`--daily-detailed`<br/></td>
    <td>Should the period consider days of the time period [begin, end]</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-ds`<br/>`--data-stream`<br/></td>
    <td>Data stream hierarchy level filter (tag)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-e`<br/>`--end`<br/></td>
    <td>End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-er`<br/>`--errors`<br/></td>
    <td>Delete the errors</td>
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
    <td>`-p`<br/>`--profiler`<br/></td>
    <td>Data quality profiler name</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-pcat`<br/>`--profiler-category`<br/></td>
    <td>Profiler category name (standard, nulls, numeric, etc.)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-pt`<br/>`--profiler-type`<br/></td>
    <td>Data quality profiler target type (table, column)</td>
    <td>table<br/>column<br/></td>
    </tr>
    
    <tr>
    <td>`-pr`<br/>`--profiling-results`<br/></td>
    <td>Delete the profiling results</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-qd`<br/>`--quality-dimension`<br/></td>
    <td>Data quality dimension</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-rr`<br/>`--rule-results`<br/></td>
    <td>Delete the rule results</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-s`<br/>`--sensor`<br/></td>
    <td>Data quality sensor name (sensor definition or sensor name)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-sr`<br/>`--sensor-readouts`<br/></td>
    <td>Delete the sensor readouts</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--table`<br/></td>
    <td>Full table name (schema.table)</td>
    <td></td>
    </tr>
    
    <tr>
    <td>`-tg`<br/>`--time-gradient`<br/></td>
    <td>Time gradient of the sensor</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>
