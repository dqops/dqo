import React, { useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import Tabs from '../../components/Tabs';
import SvgIcon from '../../components/SvgIcon';

const tabs = [
    {
      label: 'Table',
      value: 'table'
    },
    {
    label: "Column",
    value: "column"
    }
  ];



const checkDefaultDetail = () =>  {
    const [activeTab, setActiveTab] = useState('table');

  return (
    <DefinitionLayout>
         <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Default check editor: {"123"}
              </div>
            </div>
          </div>
           <div className="border-b border-gray-300 relative">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
    </DefinitionLayout>
  )
}
export default checkDefaultDetail
