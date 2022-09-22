import React, { useState } from 'react';

import DatabaseConnection from '../../components/Dashboard/DatabaseConnection';
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import MainLayout from '../../components/MainLayout';
import { DATABASE_TYPE } from '../../shared/enums';

const Dashboard = () => {
  const [step, setStep] = useState(0);
  const [database, setDatabase] = useState<DATABASE_TYPE | undefined>();

  const onSelect = (db: DATABASE_TYPE) => {
    setDatabase(db);
    setStep(1);
  };

  const onPrev = () => {
    if (step > 0) {
      setStep(step - 1);
    }
  };

  const onNext = () => {
    if (step < 1) {
      setStep(step + 1);
    }
  };

  return (
    <MainLayout>
      {step === 0 && <SelectDatabase onSelect={onSelect} />}
      {step === 1 && (
        <DatabaseConnection type={database} onPrev={onPrev} onNext={onNext} />
      )}
    </MainLayout>
  );
};

export default Dashboard;
