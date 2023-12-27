import React from 'react';
import { TableListModel } from '../../api';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDispatch } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';

type SchemaTablesProps = {
  tables: TableListModel[];
};

export const SchemaTables = ({ tables }: SchemaTablesProps) => {
  const chechTypes: CheckTypes = useParams();
  const dispatch = useDispatch();
  const history = useHistory();
  //add routing to table and connection
  const goToConnection = (connection: string) => {
    dispatch(
      addFirstLevelTab(chechTypes, {
        url: ROUTES.CONNECTION_DETAIL(chechTypes, connection, 'detail'),
        value: ROUTES.CONNECTION_LEVEL_VALUE(chechTypes, connection),
        state: {},
        label: connection
      })
    );
    history.push(ROUTES.CONNECTION_DETAIL(chechTypes, connection, 'detail'));
    return;
  };

  return (
    <table className="w-full">
      <thead>
        <tr>
          <th className="px-4 text-left">Connection</th>
          <th className="px-4 text-left">Schema</th>
          <th className="px-4 text-left">Table</th>
          <th className="px-4 text-left">Disabled</th>
          <th className="px-4 text-left">Stage</th>
          <th className="px-4 text-left">Filter</th>
        </tr>
      </thead>
      <tbody>
        {tables.map((item, index) => (
          <tr key={index}>
            <td
              className="px-4 text-teal-500 underline"
              onClick={() => goToConnection(item.connection_name ?? '')}
            >
              {item.connection_name}
            </td>
            <td className="px-4">{item.target?.schema_name}</td>
            <td className="px-4">{item.target?.table_name}</td>
            <td className="px-4">{item?.disabled}</td>
            <td className="px-4">{item?.stage}</td>
            <td className="px-4">{item?.filter}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
