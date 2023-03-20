import React, { useState } from "react";
import { DataStreamBasicModel } from "../../../api";
import Button from "../../Button";
import { DataStreamsApi } from "../../../services/apiClient";
import ConfirmDialog from "../../CustomTree/ConfirmDialog";
import { Checkbox } from "@material-tailwind/react";
import SvgIcon from "../../SvgIcon";

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
  const [open, setOpen] = useState(false);
  const [selectedStream, setSelectedStream] = useState<DataStreamBasicModel>();

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

  const deleteStream = async (stream?: DataStreamBasicModel) => {
    if (!stream) {
      return;
    }

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

  const openConfirmDeleteModal = (stream: DataStreamBasicModel) => {
    setOpen(true);
    setSelectedStream(stream);
  };

  return (
    <div className="px-8 py-4">
      <table className="mb-4">
        <thead>
          <tr>
            <th className="pr-2 py-1">Data stream configuration name</th>
            <th className="px-2 py-1" />
            <th className="px-2 py-1" />
            <th className="px-2 py-1" />
          </tr>
        </thead>
        <tbody>
          {dataStreams.map((stream, index) => (
            <tr key={index}>
              <td className="pr-2 py-1 relative flex items-center gap-2">
                {stream.default_data_stream && (
                  <div className="w-5 h-5 bg-primary rounded flex items-center justify-center">
                    <SvgIcon name="check" className="text-white" />
                  </div>
                )}
                <span>{stream.data_stream_name}</span>
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
                  onClick={() => openConfirmDeleteModal(stream)}
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
      <ConfirmDialog
        open={open}
        onClose={() => setOpen(false)}
        message={`Are you sure to delete data stream ${selectedStream?.data_stream_name}?`}
        onConfirm={() => deleteStream(selectedStream)}
      />
    </div>
  );
};

export default DataStreamListView;
