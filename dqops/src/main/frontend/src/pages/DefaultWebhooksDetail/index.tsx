import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IncidentWebhookNotificationsSpec } from '../../api';
import Button from '../../components/Button';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import Input from '../../components/Input';
import { IRootState } from '../../redux/reducers';
import { SettingsApi } from '../../services/apiClient';

export default function DefaultWebhooksDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const [defaultWebhooksConfiguration, setDefaultWebhooksConfiguration] =
    useState<IncidentWebhookNotificationsSpec>();
  const [isUpdated, setIsUpdated] = useState(false);

  const getDefaultWebhooksConfiguration = async () => {
    await SettingsApi.getDefaultWebhooks().then((res) =>
      setDefaultWebhooksConfiguration(res.data)
    );
  };

  const onChangeWebhooks = (obj: Partial<IncidentWebhookNotificationsSpec>) => {
    setDefaultWebhooksConfiguration((prevState) => ({
      ...prevState,
      ...obj
    }));
    setIsUpdated(true);
  };

  const updateDefaultWebhooksConfiguration = async () => {
    await SettingsApi.updateDefaultWebhooks(defaultWebhooksConfiguration).then(
      () => setIsUpdated(false)
    );
  };

  useEffect(() => {
    getDefaultWebhooksConfiguration();
  }, []);

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0">
        <div className="flex items-center justify-between w-full">
          <div className="text-lg font-semibold truncate">
            Default webhooks configuration
          </div>
        </div>
        <Button
          label="Save"
          color="primary"
          className="w-45"
          onClick={updateDefaultWebhooksConfiguration}
          disabled={!(isUpdated && userProfile.can_manage_definitions === true)}
        />
      </div>
      <SectionWrapper
        title="Webhooks for notifications of an incident state change"
        className="mt-8 mx-4"
      >
        <Input
          className="mb-4"
          label="A new incident was opened (detected):"
          value={defaultWebhooksConfiguration?.incident_opened_webhook_url}
          onChange={(e) =>
            onChangeWebhooks({ incident_opened_webhook_url: e.target.value })
          }
          disabled={userProfile.can_manage_definitions !== true}
        />
        <Input
          className="mb-4"
          label="An incident was acknowledged:"
          value={
            defaultWebhooksConfiguration?.incident_acknowledged_webhook_url
          }
          onChange={(e) =>
            onChangeWebhooks({
              incident_acknowledged_webhook_url: e.target.value
            })
          }
          disabled={userProfile.can_manage_definitions !== true}
        />
        <Input
          className="mb-4"
          label="An incident was resolved:"
          value={defaultWebhooksConfiguration?.incident_resolved_webhook_url}
          onChange={(e) =>
            onChangeWebhooks({ incident_resolved_webhook_url: e.target.value })
          }
          disabled={userProfile.can_manage_definitions !== true}
        />
        <Input
          className="mb-4"
          label="An incident was muted:"
          value={defaultWebhooksConfiguration?.incident_muted_webhook_url}
          onChange={(e) =>
            onChangeWebhooks({ incident_muted_webhook_url: e.target.value })
          }
          disabled={userProfile.can_manage_definitions !== true}
        />
      </SectionWrapper>
    </>
  );
}
