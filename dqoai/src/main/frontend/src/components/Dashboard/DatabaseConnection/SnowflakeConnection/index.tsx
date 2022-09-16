import React from 'react';

import Input from '../../../Input';
import SectionWrapper from '../../SectionWrapper';

const SnowflakeConnection = () => {
  return (
    <SectionWrapper title='Snowkflake connection parameters' className='mb-4'>
      <Input label='Account' className='mb-4' name='account' />
      <Input label='Warehouse' className='mb-4' name='warehouse' />
      <Input label='Role' name='role' />
    </SectionWrapper>
  );
};

export default SnowflakeConnection;
