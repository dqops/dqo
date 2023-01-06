import React from "react";
import { DataStreamBasicModel } from "../../../api";
import Button from "../../Button";
import SvgIcon from "../../SvgIcon";
import { DataStreamsApi } from "../../../services/apiClient";

interface IDataStreamListViewProps {
  dataStreams: DataStreamBasicModel[];
  getDataStreams: () => void;
  onCreate: () => void;
  onEdit: (stream: DataStreamBasicModel) => void;
}

const DataStreamListView = ({
  dataStreams,
  getDataStreams,
  onCreate,
  onEdit
}: IDataStreamListViewProps) => {
  const setDefaultStream = async (stream: DataStreamBasicModel) => {
    try {
      await DataStreamsApi.setDefaultDataStream(
        stream.connection_name || '',
        stream.schema_name || '',
        stream.table_name || '',
        stream.data_stream_name || ''
      );
      getDataStreams();
    } catch (err) {
      console.error(err);
    }
  };

  const deleteStream = async (stream: DataStreamBasicModel) => {
    try {
      await DataStreamsApi.deleteDataStream(
        stream.connection_name || '',
        stream.schema_name || '',
        stream.table_name || '',
        stream.data_stream_name || ''
      );
      getDataStreams();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="px-8 py-4">
      <table className="mb-4">
        <thead>
          <tr>
            <th className="px-2 py-1">Data stream configuration name</th>
            <th className="px-2 py-1" />
            <th className="px-2 py-1" />
            <th className="px-2 py-1" />
          </tr>
        </thead>
        <tbody>
          {dataStreams.map((stream, index) => (
            <tr key={index}>
              <td className="px-2 py-1 relative">
                {stream.default_data_stream && (
                  <SvgIcon
                    name="star"
                    className="w-4 text-indigo-700 absolute -left-4 top-1/2 transform -translate-y-1/2"
                  />
                )}
                {stream.data_stream_name}
              </td>
              <td className="px-2 py-1">
                <Button
                  label="Edit"
                  color="primary"
                  variant="text"
                  onClick={() => onEdit(stream)}
                />
              </td>
              <td className="px-2 py-1">
                <Button
                  label="Delete"
                  color="primary"
                  variant="text"
                  onClick={() => deleteStream(stream)}
                />
              </td>
              <td className="px-2 py-1">
                {!stream.default_data_stream ? (
                  <Button
                    label="Make Default"
                    color="primary"
                    variant="text"
                    onClick={() => setDefaultStream(stream)}
                  />
                ) : ''}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <Button
        label="New data stream configuration"
        color="primary"
        onClick={onCreate}
      />
    </div>
  );
};

export default DataStreamListView;
