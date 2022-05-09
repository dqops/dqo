# Dimensions

Data observability splits into dimensions, depending on the business needs.
Here, we describe each dimension.

A list of available checks categorized into types and dimensions is available [here](../check_reference/list_of_checks/list_of_checks.md).

## Validity

Validity checks verify that data meets certain requirements for a specific application. Depending on the data type
there are many examples 

Validity tests aspects such as syntax, data type, format, maximum length, value range, time range.
We usually measure validity as a number or percent of valid records.

## Timeliness

Timeliness checks answer the following questions: is the data is up-to-date,
is there a delay in loading files into the databases, how much time does it take,
what is the delay, can the data be used for real-time reporting?

A measure of this dimension is the time between two events or the number (or percent) of time gaps 
below a certain threshold. 

## Completeness

Completeness checks refer to the wholeness of the data.
Their role is to count records that are useful in a business matter.

A measure of completeness is the total amount of rows
reduced by the quantity of rows containing missing information, or the ratio between the first one and the latter 
expressed in percent.


## Relevance

Relevance is a level of unity between the data and user's areas of interest.

## Uniqueness

Uniqueness indicates an occurrence of duplicates and overlaps. Uniqueness check are defined on columns
containing data where each record has to be different in order to identify 

Uniqueness checks are usually defined on columns with records whose goal is to identify certain values, e.g.
singular transaction or a person.  

## Accuracy
Describes the degree to which data reflects 'real world' information.

To measure accuracy we need two data sources: the tested one and one to refer to.

## Consistency

Consistency are designed to detect anomalies of the data.

Consistency check are focused around observing statistical values such as standard deviation or mean actual value 
over time.