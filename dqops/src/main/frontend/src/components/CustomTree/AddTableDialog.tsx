import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  ConnectionModel,
  ConnectionSpecProviderTypeEnum,
  DuckdbParametersSpecFilesFormatTypeEnum,
  FileFormatSpec
} from '../../api';
import { TConfiguration } from '../../components/FileFormatConfiguration/TConfiguration';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setAdvisorJobId } from '../../redux/actions/job.actions';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import {
  ConnectionApiClient,
  JobApiClient,
  TableApiClient
} from '../../services/apiClient';
import { CustomTreeNode } from '../../shared/interfaces';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { urlencodeEncoder, useDecodedParams } from '../../utils';
import Button from '../Button';
import FileFormatConfiguration from '../FileFormatConfiguration/FileFormatConfiguration';
import FilePath from '../FileFormatConfiguration/FilePath';
import Input from '../Input';

interface AddTableDialogProps {
  open: boolean;
  onClose: () => void;
  node?: CustomTreeNode;
}

const AddTableDialog = ({ open, onClose, node }: AddTableDialogProps) => {
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(false);
  const [connectionModel, setConnectionModel] = useState<ConnectionModel>({});
  const { refreshNode } = useTree();
  const [paths, setPaths] = useState<Array<string>>(['']);
  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecFilesFormatTypeEnum>(
      DuckdbParametersSpecFilesFormatTypeEnum.csv
    );
  const [configuration, setConfiguration] = useState<TConfiguration>({});
  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
  };
  const cleanConfiguration = () => {
    setConfiguration({});
  };

  const { connection, schema }: { connection: string; schema: string } =
    useDecodedParams();
  const dispatch = useActionDispatch();
  const history = useHistory();

  const args = node ? node.id.toString().split('.') : [];

  const handleSubmit = async () => {
    try {
      setLoading(true);
      if (node) {
        await TableApiClient.createTable(
          urlencodeEncoder(args[0]),
          urlencodeEncoder(args[1]),
          urlencodeEncoder(name),
          {
            file_format:
              connectionModel.provider_type ===
              ConnectionSpecProviderTypeEnum.duckdb
                ? {
                    [fileFormatType as keyof FileFormatSpec]: configuration,
                    file_paths: paths.filter((x) => x)
                  }
                : undefined
          }
        ).then(() =>
          JobApiClient.importTables(undefined, false, undefined, {
            connectionName: urlencodeEncoder(args[0]),
            schemaName: urlencodeEncoder(args[1]),
            tableNames: [urlencodeEncoder(name)]
          }).then((res) => 
          dispatch(setAdvisorJobId(res.data?.jobId?.jobId ?? 0))
        )
        );
        refreshNode(node);
        dispatch(
          addFirstLevelTab(CheckTypes.SOURCES, {
            url: ROUTES.TABLE_LEVEL_PAGE(
              CheckTypes.SOURCES,
              urlencodeEncoder(args[0]),
              urlencodeEncoder(args[1]),
              urlencodeEncoder(name),
              'detail'
            ),
            value: ROUTES.TABLE_LEVEL_VALUE(
              CheckTypes.SOURCES,
              urlencodeEncoder(args[0]),
              urlencodeEncoder(args[1]),
              urlencodeEncoder(name),
            ),
            state: {},
            label: name
          })
        );
        history.push(
          ROUTES.TABLE_LEVEL_PAGE(
            CheckTypes.SOURCES,
            urlencodeEncoder(args[0]),
            urlencodeEncoder(args[1]),
            urlencodeEncoder(name),
            'detail'
          )
        );
      } else {
        await TableApiClient.createTable(
          urlencodeEncoder(connection),
          urlencodeEncoder(schema),
          urlencodeEncoder(name),
          {
            file_format:
              connectionModel.provider_type ===
              ConnectionSpecProviderTypeEnum.duckdb
                ? {
                    [fileFormatType as keyof FileFormatSpec]: configuration,
                    file_paths: paths.filter((x) => x)
                  }
                : undefined
          }
        ).then(() =>
          JobApiClient.importTables(undefined, false, undefined, {
            connectionName: urlencodeEncoder(connection),
            schemaName: urlencodeEncoder(schema),
            tableNames: [urlencodeEncoder(name)]
          }).then((res) => 
            dispatch(setAdvisorJobId(res.data?.jobId?.jobId ?? 0))
          )
        );
        dispatch(
          addFirstLevelTab(CheckTypes.SOURCES, {
            url: ROUTES.TABLE_LEVEL_PAGE(
              CheckTypes.SOURCES,
              connection,
              schema,
              name,
              'detail'
            ),
            value: ROUTES.TABLE_LEVEL_VALUE(
              CheckTypes.SOURCES,
              connection,
              schema,
              name
            ),
            state: {},
            label: name
          })
        );
        history.push(
          ROUTES.TABLE_LEVEL_PAGE(
            CheckTypes.SOURCES,
            connection,
            schema,
            name,
            'detail'
          )
        );
      }
      cleanState();
      onClose();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const getConnectionBasic = async () => {
      await ConnectionApiClient.getConnectionBasic(
        urlencodeEncoder(args[0])
      ).then((res) => setConnectionModel(res.data));
    };
    if (node) {
      getConnectionBasic();
    }
  }, [open]);

  const onAddPath = () => setPaths((prev) => [...prev, '']);
  const onChangePath = (value: string) => {
    const copiedPaths = [...paths];
    copiedPaths[paths.length - 1] = value;
    setPaths(copiedPaths);
  };
  const onDeletePath = (index: number) =>
    setPaths((prev) => prev.filter((x, i) => i !== index));

  const onChangeFile = (val: DuckdbParametersSpecFilesFormatTypeEnum) =>
    setFileFormatType(val);

  const cleanState = () => {
    setConfiguration({});
    setName('');
    setPaths(['']);
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-4 pb-2 px-8">
        <div className="flex flex-col">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">Add table</h1>
          <div>
            <Input
              label="Table Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
        </div>
        {connectionModel.provider_type ===
          ConnectionSpecProviderTypeEnum.duckdb && (
          <FileFormatConfiguration
            fileFormatType={fileFormatType}
            onChangeFile={onChangeFile}
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
            cleanConfiguration={cleanConfiguration}
            freezeFileType={true}
          >
            <FilePath
              paths={paths}
              onAddPath={onAddPath}
              onChangePath={onChangePath}
              onDeletePath={onDeletePath}
            />
          </FileFormatConfiguration>
        )}
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={() => {
            onClose();
            cleanState();
          }}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label="Save"
          loading={loading}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default AddTableDialog;
