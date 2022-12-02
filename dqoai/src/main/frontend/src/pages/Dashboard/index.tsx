import React, { useState } from 'react';

import DatabaseConnection from '../../components/Dashboard/DatabaseConnection';
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import MainLayout from '../../components/MainLayout';
import { DATABASE_TYPE } from '../../shared/enums';
import { IDatabase } from '../../shared/interfaces';
import ImportSchemas from '../../components/ImportSchemas';

const initialDatabase = {
  connectionName: ''
};

const Dashboard = () => {
  const [step, setStep] = useState(0);
  const [dbType, setDBType] = useState<DATABASE_TYPE | undefined>();
  const [database, setDatabase] = useState<IDatabase>(initialDatabase);

  const onSelect = (db: DATABASE_TYPE) => {
    setDBType(db);
    setStep(1);
  };

  const onPrev = () => {
    if (step > 0) {
      setStep(step - 1);
    }
  };

  const onNext = () => {
    if (step < 2) {
      setStep(step + 1);
    }
  };

  return (
    <MainLayout>
      {step === 0 && <SelectDatabase onSelect={onSelect} />}
      {step === 1 && (
        <DatabaseConnection
          type={dbType}
          onPrev={onPrev}
          onNext={onNext}
          database={database}
          onChange={setDatabase}
        />
      )}
      {step === 2 && (
        <ImportSchemas
          connectionName={database.connectionName}
          onPrev={onPrev}
          onNext={onNext}
        />
      )}
    </MainLayout>
  );
};

export default Dashboard;
