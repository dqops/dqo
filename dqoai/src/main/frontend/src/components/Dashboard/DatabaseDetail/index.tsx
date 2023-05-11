import React from 'react';

import Button from '../../Button';
import Input from '../../Input';
import TextArea from '../../TextArea';

const DatabaseDetail = () => {
  return (
    <div>
      <div className="text-2xl font-semibold mb-4">Database detail</div>

      <div className="bg-white rounded-lg px-4 py-6 border border-gray-100">
        <Input label="Server" className="mb-4" />
        <Input label="Database Name" className="mb-4" />
        <Input label="Username" className="mb-4" />
        <Input label="Password" className="mb-4" />
        <Input label="Port" className="mb-4" />
        <TextArea label="Description" className="mb-4" />

        <div className="flex space-x-4 justify-end mt-6">
          <Button
            color="primary"
            variant="outlined"
            label="Prev"
            className="w-40"
          />
          <Button
            color="primary"
            variant="contained"
            label="Next"
            className="w-40"
          />
        </div>
      </div>
    </div>
  );
};

export default DatabaseDetail;
