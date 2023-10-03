
## ParameterDefinitionsListSpec  
List of parameter definitions - the parameters for custom sensors or custom rules.  
  








___  

## RuleTimeWindowSettingsSpec  
Rule historic data configuration. Specifies the number of past values for rules that are analyzing historic data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|prediction_time_window|Number of historic time periods to look back for results. Returns results from previous time periods before the sensor readout timestamp to be used in a rule. Time periods are used in rules that need historic data to calculate an average to detect anomalies. e.g. when the sensor is configured to use a &#x27;day&#x27; time period, the rule will receive results from the time_periods number of days before the time period in the sensor readout. The default is 14 (days).|integer| | | |
|min_periods_with_readouts|Minimum number of past time periods with a sensor readout that must be present in the data in order to call the rule. The rule is not called and the sensor readout is discarded as not analyzable (not enough historic data to perform prediction) when the number of past sensor readouts is not met. The default is 7.|integer| | | |
|historic_data_point_grouping|Time period grouping for collecting previous data quality sensor results for the data quality rules that use historic data for prediction. For example, when the default time period grouping &#x27;day&#x27; is used, DQO will find the most recent data quality sensor readout for each day and pass an array of most recent days per day in an array of historic sensor readout data points to a data quality rule for prediction.|enum|week<br/>month<br/>hour<br/>year<br/>last_n_readouts<br/>day<br/>quarter<br/>| | |









___  

## ParameterDefinitionSpec  
Defines a single field that is a sensor parameter or a rule parameter.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|field_name|Field name that matches the field name (snake_case) used in the YAML specification.|string| | | |
|display_name|Field display name that should be shown as a label for the control.|string| | | |
|help_text|Help text (full description) that will be shown to the user as a hint when the cursor is moved over the control.|string| | | |
|data_type|Parameter data type.|enum|date<br/>string<br/>enum<br/>string_list<br/>object<br/>datetime<br/>column_name<br/>boolean<br/>integer<br/>double<br/>integer_list<br/>long<br/>| | |
|display_hint|UI control display hint.|enum|textarea<br/>| | |
|required|True when the value for the parameter must be provided.|boolean| | | |
|allowed_values|List of allowed values for a field that is of an enum type.|string_list| | | |
|sample_values|List of sample values. The sample values are used in the documentation or help messages.|string_list| | | |









___  

## RuleDefinitionSpec  
Custom data quality rule specification. Provides the custom rule configuration. For example, rules that require a range of historic values will have this configuration.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|type|Rule runner type|enum|python<br/>java_class<br/>| | |
|java_class_name|Java class name for a rule runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string| | | |
|mode|Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. The number of time windows is configured in the time_window setting.|enum|previous_readouts<br/>current_value<br/>| | |
|[time_window](#ruletimewindowsettingsspec)|Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.|[RuleTimeWindowSettingsSpec](#ruletimewindowsettingsspec)| | | |
|[fields](#parameterdefinitionslistspec)|List of fields that are parameters of a custom rule. Those fields are used by the DQO UI to display the data quality check editing screens with proper UI controls for all required fields.|[ParameterDefinitionsListSpec](#parameterdefinitionslistspec)| | | |









___  

## RuleDefinitionYaml  
Custom rule specification that describes the configuration of a python module with the rule code (additional parameters).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#ruledefinitionspec)||[RuleDefinitionSpec](#ruledefinitionspec)| | | |









___  

