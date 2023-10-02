import React from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import ScheduleDetail from '../../components/Connection/ConnectionView/ScheduleDetail'

export default function DefaultSchedules() {
  return (
  <DefinitionLayout>
    <ScheduleDetail isDefault={true}/>
  </DefinitionLayout>
  )
}
