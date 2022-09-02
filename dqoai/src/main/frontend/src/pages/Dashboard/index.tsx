import React, {useState} from 'react';
import MainLayout from "../../components/MainLayout";
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import DatabaseDetail from '../../components/Dashboard/DetabaseDetail';
import DatabaseConnectionDialog from '../../components/DatabaseConnectionDialog';

const dbInfos: any = {
  'big-query': {
    title: 'Google BigQuery Connection Settings',
    description: 'Google BigQuery connection settings',
    logo: '/bigQuery.png',
  },
  snowflake: {
    title: 'Snowflake Connection Settings',
    description: 'Snowflake connection settings',
    logo: '/snowflake.png',
  }
}

const Dashboard = () => {
  const [step, setStep] = useState(0);
  const [database, setDatabase] = useState('');
  const [showDialog, setShowDialog] = useState(false);

  const onSelect = (db: string) => {
    setDatabase(db);
    setShowDialog(true);
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
      <DatabaseConnectionDialog
        open={showDialog}
        onClose={() => setShowDialog(false)}
        {...dbInfos[database]}
      />
    </MainLayout>
  );
};

export default Dashboard;
