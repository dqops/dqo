#sensor

___
###<b><u>sensor edit</u></b>

<b>Description:</b>

Edit sensor that match filters

<b>Synopsis:</b>
<pre><code> sensor edit [-h] [-fw] [-hl] [-f&#x3D;&lt;ext&gt;] [-of&#x3D;&lt;outputFormat&gt;] [-p&#x3D;&lt;provider&gt;]
             [-s&#x3D;&lt;name&gt;]
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
    <td>`-f`<br/>`--ext`<br/></td>
    <td>File type</td>
    <td>JINJA2<br/>YAML<br/></td>
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
    <td>`-p`<br/>`--provider`<br/></td>
    <td>Provider type</td>
    <td>bigquery<br/>snowflake<br/>postgresql<br/></td>
    </tr>
    
    <tr>
    <td>`-s`<br/>`--sensor`<br/></td>
    <td>Sensor name</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>
