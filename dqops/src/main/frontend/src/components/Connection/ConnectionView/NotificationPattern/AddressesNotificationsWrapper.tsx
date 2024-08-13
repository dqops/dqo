import React from 'react';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Input from '../../../Input';

export default function AddressesNotificationsWrapper({
  pattern,
  onChangePatternTarget
}: {
  pattern: any;
  onChangePatternTarget: any;
}) {
  return (
    <SectionWrapper
      title="Addresses for notifications of an incident state change"
      className="mt-8"
    >
      <Input
        className="mb-4"
        label="A new incident was opened (detected)"
        value={pattern.target?.incident_opened_addresses}
        onChange={(e) =>
          onChangePatternTarget({ incident_opened_addresses: e.target.value })
        }
      />
      <Input
        className="mb-4"
        label="An incident was acknowledged"
        value={pattern.target?.incident_acknowledged_addresses}
        onChange={(e) =>
          onChangePatternTarget({
            incident_acknowledged_addresses: e.target.value
          })
        }
      />
      <Input
        className="mb-4"
        label="An incident was resolved"
        value={pattern.target?.incident_resolved_addresses}
        onChange={(e) =>
          onChangePatternTarget({
            incident_resolved_addresses: e.target.value
          })
        }
      />
      <Input
        className="mb-4"
        label="An incident was muted"
        value={pattern.target?.incident_muted_addresses}
        onChange={(e) =>
          onChangePatternTarget({ incident_muted_addresses: e.target.value })
        }
      />
    </SectionWrapper>
  );
}
