import React from 'react';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Input from '../../../Input';

export default function AddressesNotificationsWrapper({
  target,
  onChangePatternTarget
}: {
  target: any;
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
        placeholder="user@domain.com,other.user@domain.com,https://hooks/slack/services/1234567890"
        value={target?.incident_opened_addresses}
        onChange={(e) =>
          onChangePatternTarget({ incident_opened_addresses: e.target.value })
        }
      />
      <Input
        className="mb-4"
        label="An incident was acknowledged"
        placeholder="user@domain.com,other.user@domain.com,https://hooks/slack/services/1234567890"
        value={target?.incident_acknowledged_addresses}
        onChange={(e) =>
          onChangePatternTarget({
            incident_acknowledged_addresses: e.target.value
          })
        }
      />
      <Input
        className="mb-4"
        label="An incident was resolved"
        placeholder="user@domain.com,other.user@domain.com,https://hooks/slack/services/1234567890"
        value={target?.incident_resolved_addresses}
        onChange={(e) =>
          onChangePatternTarget({
            incident_resolved_addresses: e.target.value
          })
        }
      />
      <Input
        className="mb-4"
        label="An incident was muted"
        placeholder="user@domain.com,other.user@domain.com,https://hooks/slack/services/1234567890"
        value={target?.incident_muted_addresses}
        onChange={(e) =>
          onChangePatternTarget({ incident_muted_addresses: e.target.value })
        }
      />
    </SectionWrapper>
  );
}
