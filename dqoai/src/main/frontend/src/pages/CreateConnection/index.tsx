import React, { useState } from 'react';

import DatabaseConnection from '../../components/Dashboard/DatabaseConnection';
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import MainLayout from '../../components/MainLayout';
import ImportSchemas from '../../components/ImportSchemas';
import {
  ConnectionBasicModel,
  ConnectionBasicModelProviderTypeEnum
} from '../../api';

const CreateConnection = () => {
  const [step, setStep] = useState(0);
  const [database, setDatabase] = useState<ConnectionBasicModel>({});
  const [nameofDB, setNameofDB] = useState<string>('');

  const onSelect = (
    db: ConnectionBasicModelProviderTypeEnum,
    nameOfdatabase?: string
  ) => {
    setDatabase({
      provider_type: db
    });
    setNameofDB(nameOfdatabase ? nameOfdatabase : '');
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
          onNext={onNext}
          database={database}
          onChange={setDatabase}
          nameOfdatabase={nameofDB.length !== 0 ? nameofDB : ''}
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

export default CreateConnection;
