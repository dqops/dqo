import React from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import ScheduleDetail from '../../components/Connection/ConnectionView/ScheduleDetail'

export default function DefaultSchedules() {
  return (
  <DefinitionLayout>
    <div className="relative h-full min-h-full flex flex-col">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0 pr-[340px]">
          <div className="flex items-center space-x-2 max-w-full">
            <div className="text-xl font-semibold truncate">Default schedules configuration</div>
          </div>
        </div>
        <ScheduleDetail isDefault={true}/>
      </div>
  </DefinitionLayout>
  )
}
