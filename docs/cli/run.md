#run

___
###<b><u>run</u></b>

<b>Description:</b>

Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks.

<b>Synopsis:</b>
<pre><code> run [-h] [-fw] [-hl] [-m&#x3D;&lt;checkRunMode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
     [-s&#x3D;&lt;synchronizationMode&gt;] [-t&#x3D;&lt;timeLimit&gt;]
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
    <td>Check execution reporting mode (silent, summary, info, debug)</td>
    <td>silent<br/>summary<br/>info<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-of`<br/>`--output-format`<br/></td>
    <td>Output format for tabular responses</td>
    <td>TABLE<br/>CSV<br/>JSON<br/></td>
    </tr>
    
    <tr>
    <td>`-s`<br/>`--synchronization-mode`<br/></td>
    <td>Reporting mode for the DQO cloud synchronization (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    <tr>
    <td>`-t`<br/>`--time-limit`<br/></td>
    <td>Optional execution time limit. DQO will run for the given duration and gracefully shut down. Supported values are in the following format: 300s (300 seconds), 10m (10 minutes), 2h (run for up to 2 hours) or just a number that is the time limit in seconds.</td>
    <td></td>
    </tr>
    
    </tbody>
    </table>
