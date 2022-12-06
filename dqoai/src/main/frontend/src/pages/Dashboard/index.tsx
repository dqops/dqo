import React, { useState } from 'react';

import DatabaseConnection from '../../components/Dashboard/DatabaseConnection';
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import MainLayout from '../../components/MainLayout';
import ImportSchemas from '../../components/ImportSchemas';
import {
  ConnectionBasicModel,
  ConnectionBasicModelProviderTypeEnum
} from '../../api';

const Dashboard = () => {
  const [step, setStep] = useState(0);
  const [database, setDatabase] = useState<ConnectionBasicModel>({});

  const onSelect = (db: ConnectionBasicModelProviderTypeEnum) => {
    setDatabase({
      provider_type: db
    });
    setStep(1);
  };

  const onPrev = () => {
    if (step > 0) {
      setStep(step - 1);
    }
  };

  const onNext = async () => {
    if (!database?.connection_name) {
      return;
    }
    if (step === 1) {
      setStep(step + 1);
      return;
    }

    if (step < 2) {
      setStep(step + 1);
    }
  };

  return (
    <MainLayout>
      {step === 0 && <SelectDatabase onSelect={onSelect} />}
      {step === 1 && (
        <DatabaseConnection
          onPrev={onPrev}
          onNext={onNext}
          database={database}
          onChange={setDatabase}
        />
      )}
      {step === 2 && (
        <ImportSchemas
          connectionName={database?.connection_name ?? ''}
          onPrev={onPrev}
          onNext={onNext}
        />
      )}
    </MainLayout>
  );
};

export default Dashboard;
