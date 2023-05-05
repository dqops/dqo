import React, { useEffect } from "react";
import Button from "../Button";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getConnections, addFirstLevelTab } from "../../redux/actions/incidents.actions";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import SvgIcon from "../SvgIcon";
import clsx from "clsx";
import { ConnectionBasicModel, IncidentsPerConnectionModel } from "../../api";
import { ROUTES } from "../../shared/routes";

const IncidentsTree = () => {
  const dispatch = useActionDispatch();
  const { connections } = useSelector((state: IRootState) => state.incidents);

  useEffect(() => {
    dispatch(getConnections());
  }, []);

  const openFirstLevelTab = (connection: IncidentsPerConnectionModel) => {
    dispatch(addFirstLevelTab({
      url: ROUTES.INCIDENT_DETAIL(connection?.connection ?? ""),
      value: ROUTES.INCIDENT_DETAIL_VALUE(connection?.connection ?? ""),
      state: {
      },
      label: connection?.connection ?? ""
    }))
  };

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      <Button
        className="w-full mb-6"
        label="Refresh"
        variant="outlined"
        color="primary"
      />

      <div>
        {connections.map((connection, index) => (
          <div
            key={index}
            className="flex space-x-2 py-1 flex-1 w-full text-[13px] cursor-pointer"
            onClick={() => openFirstLevelTab(connection)}
          >
            <SvgIcon
              name="database"
              className={clsx('w-4 shrink-0 min-w-4')}
            />
            <div className="flex-1 truncate mr-7">
              {connection.connection}
            </div>
            {connection.openIncidents ? (
              <div>
                ({connection.openIncidents})
              </div>
            ) : null}
          </div>
        ))}
      </div>
    </div>
  );
};

export default IncidentsTree;
