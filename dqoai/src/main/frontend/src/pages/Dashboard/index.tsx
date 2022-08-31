import React, {useState} from 'react';
import MainLayout from "../../components/MainLayout";
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import DatabaseDetail from '../../components/Dashboard/DetabaseDetail';

const Dashboard = () => {
  const [step, setStep] = useState(0);
  const [database, setDatabase] = useState('');

  const onSelect = (db: string) => {
    setDatabase(db);
    setStep(1);
  }

  return (
    <MainLayout>
      {
        step === 0 && (
          <SelectDatabase onSelect={onSelect} />
        )
      }
      {
        step === 1 && (
          <DatabaseDetail />
        )
      }
    </MainLayout>
  );
};

export default Dashboard;
