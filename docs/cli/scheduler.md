#scheduler

___
###<b><u>scheduler start</u></b>

<b>Description:</b>

Starts a background job scheduler. This operation should be called only from the shell mode. When the dqo is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately.

<b>Synopsis:</b>
<pre><code> scheduler start [-h] [-fw] [-hl] [-crm&#x3D;&lt;checkRunMode&gt;] [-of&#x3D;&lt;outputFormat&gt;]
                 [-sm&#x3D;&lt;synchronizationMode&gt;]
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
    <td>`-crm`<br/>`--check-run-mode`<br/></td>
    <td>Check execution reporting mode (silent, summary, info, debug)</td>
    <td>silent<br/>summary<br/>info<br/>debug<br/></td>
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
    <td>`-sm`<br/>`--synchronization-mode`<br/></td>
    <td>Reporting mode for the DQO cloud synchronization (silent, summary, debug)</td>
    <td>silent<br/>summary<br/>debug<br/></td>
    </tr>
    
    </tbody>
    </table>

___
###<b><u>scheduler stop</u></b>

<b>Description:</b>

Stops a background job scheduler. This operation should be called only from the shell mode after the scheduler was started.

<b>Synopsis:</b>
<pre><code> scheduler stop [-h] [-fw] [-hl] [-of&#x3D;&lt;outputFormat&gt;]
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
