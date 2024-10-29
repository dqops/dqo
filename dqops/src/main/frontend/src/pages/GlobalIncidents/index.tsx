import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { TopIncidentsModel } from '../../api';
import Button from '../../components/Button';
import Loader from '../../components/Loader';
import RadioButton from '../../components/RadioButton';
import SvgIcon from '../../components/SvgIcon';
import { IncidentsApi } from '../../services/apiClient';
import GlobalIncidentsDashboard from './GlobalIncidentDashboard/GlobalIncidentsDashboard';
import SummaryTable from './Summary/SummaryTable';

export default function Index() {
  const [incidents, setIncidents] = useState<TopIncidentsModel>({});
  const [groupingField, setGroupingField] = useState<'category' | 'dimension'>(
    'category'
  );
  const [loading, setLoading] = useState<boolean>(false);

  const getIncidents = async () => {
    setLoading(true);
    await IncidentsApi.findTopIncidentsGrouped(undefined, groupingField)
      .then((response) => {
        const data = response.data;

        // Sort data before updating the state
        const sortedData = Object.entries(data.topIncidents || {})
          .filter(([, value]) => value.length !== 0)
          .sort(([, valueA], [, valueB]) => {
            if (
              groupingField === 'category' &&
              valueA[0]?.checkCategory &&
              valueB[0]?.checkCategory
            ) {
              return valueA[0]?.checkCategory.localeCompare(
                valueB[0]?.checkCategory
              );
            }
            if (
              groupingField === 'dimension' &&
              valueA[0]?.qualityDimension &&
              valueB[0]?.qualityDimension
            ) {
              return valueA[0]?.qualityDimension.localeCompare(
                valueB[0]?.qualityDimension
              );
            }
            return 0;
          });

        // Convert back to an object and set in state
        setIncidents({
          ...response.data,
          topIncidents: Object.fromEntries(sortedData)
        });
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    getIncidents();
  }, [groupingField]);

  if (loading && Object.keys(incidents).length === 0) {
    return (
      <div className="flex justify-center h-50">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <div>
      <div className="flex items-center justify-between">
        <div className="flex pb-2 pt-6 pl-4 gap-x-5 items-center">
          <div className="text-sm pl-1">Group incidents by: </div>
          <RadioButton
            checked={groupingField === 'category'}
            label="category"
            fontClassName="text-sm"
            onClick={() => setGroupingField('category')}
          />
          <RadioButton
            checked={groupingField === 'dimension'}
            label="quality dimension"
            fontClassName="text-sm"
            onClick={() => setGroupingField('dimension')}
          />
        </div>
        <Button
          color="primary"
          leftIcon={
            <SvgIcon
              name="sync"
              className={clsx('w-4 h-4 mr-3', loading ? 'animate-spin' : '')}
            />
          }
          className="mr-8 pr-0 pl-3 mt-3"
          onClick={() => getIncidents()}
        />
      </div>
      <SummaryTable incidents={incidents} />
      {loading ? (
        <div className="flex justify-center h-50">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      ) : (
        <GlobalIncidentsDashboard
          incidents={incidents}
          groupBy={groupingField}
        />
      )}
    </div>
  );
}
