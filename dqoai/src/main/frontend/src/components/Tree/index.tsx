import React, { useMemo, useState } from 'react';
import { useTree } from '../../contexts/treeContext';
import { groupBy } from 'lodash';
import { TREE_LEVEL } from '../../shared/enums';
import SvgIcon from '../SvgIcon';
import { CustomTreeNode } from '../../shared/interfaces';
import clsx from 'clsx';
import { Tooltip } from '@material-tailwind/react';
import ContextMenu from '../CustomTree/ContextMenu';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { CheckTypes } from "../../shared/routes";

const Tree = () => {
  const { changeActiveTab, treeData, toggleOpenNode, activeTab, switchTab, sourceRoute } = useTree();
  const [isOpen, setIsOpen] = useState(false);
  const { removeNode, loadingNodes } = useTree();
  const [selectedNode, setSelectedNode] = useState<CustomTreeNode>();

  const handleNodeClick = (node: CustomTreeNode) => {
    switchTab(node, sourceRoute);
    changeActiveTab(node, true);
  };

  const groupedData = groupBy(treeData, 'parentId');

  const getIcon = (level: TREE_LEVEL) => {
    if (level === TREE_LEVEL.DATABASE) return 'database';
    if (level === TREE_LEVEL.SCHEMA) return 'schema';
    if (level === TREE_LEVEL.TABLE) return 'table';
    if (level === TREE_LEVEL.CHECK) return 'search';
    if (level === TREE_LEVEL.COLUMNS ||  level === TREE_LEVEL.COLUMN) return 'column';
    if (level === TREE_LEVEL.COLUMN_CHECKS
      || level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS
      || level === TREE_LEVEL.COLUMN_DAILY_CHECKS
      || level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS
      || level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
    ) {
      return 'column-check';
    }
    if (level === TREE_LEVEL.TABLE_CHECKS
      || level === TREE_LEVEL.TABLE_DAILY_CHECKS
      || level === TREE_LEVEL.TABLE_MONTHLY_CHECKS
      || level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS
      || level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
    ) {
      return 'table-check';
    }

    return 'column';
  };

  const openConfirm = (node: CustomTreeNode) => {
    setSelectedNode(node);
    setIsOpen(true);
  };

  const renderIcon = (node: CustomTreeNode) => {
    if (node.level === TREE_LEVEL.CHECK || (node.level === TREE_LEVEL.COLUMN && sourceRoute === CheckTypes.SOURCES)) {
      return <div className="w-0 shrink-0" />;
    }
    if (loadingNodes[node.id]) {
      return (
        <SvgIcon
          className="w-4 min-w-4 cursor-pointer shrink-0 animate-spin"
          name="spinner"
        />
      )
    }
    return (
      <SvgIcon
        className="w-4 min-w-4 cursor-pointer shrink-0"
        name={!node.open ? 'arrow-alt-right' : 'arrow-alt-down'}
        onClick={() => {
          toggleOpenNode(node.id);
        }}
      />
    );
  };

  const renderTreeNode = (node: CustomTreeNode, deep: number) => {
    return (
      <div style={{ paddingLeft: deep ? 16 : 0 }}>
        <div
          className={clsx(
            'px-2 cursor-pointer flex space-x-1 hover:bg-gray-100 mb-0.5',
            activeTab === node.id ? 'bg-gray-100' : ''
          )}
        >
          {renderIcon(node)}
          <div
            className="flex space-x-2 py-1 flex-1 w-full text-[13px]"
            onClick={() => handleNodeClick(node)}
          >
            <SvgIcon
              name={getIcon(node.level)}
              className={clsx('w-4 shrink-0 min-w-4')}
            />
            <Tooltip
              content={node.tooltip}
              className="max-w-120 py-4 px-4 bg-gray-800 delay-300"
              placement="top-start"
            >
              <div className="flex flex-1 justify-between items-center">
                <div
                  className={clsx(
                    `flex-1 truncate mr-7`,
                    node.hasCheck ? 'font-bold' : ''
                  )}
                >
                  {node.label}
                </div>
                <ContextMenu node={node} openConfirm={openConfirm} />
              </div>
            </Tooltip>
          </div>
        </div>
        {node.open && <div>{renderTree(node.id.toString(), deep + 1)}</div>}
      </div>
    );
  };

  const renderTree = (parentId: string, deep: number) => {
    if (!groupedData[parentId]) return;
    return (
      <div>
        {groupedData[parentId].map((item) => (
          <div key={item.id}>{renderTreeNode(item, deep)}</div>
        ))}
      </div>
    );
  };

  const message = useMemo(() => {
    if (selectedNode?.level === TREE_LEVEL.DATABASE) {
      return `Are you sure want to remove connection ${selectedNode?.label}?`;
    }

    if (selectedNode?.level === TREE_LEVEL.SCHEMA) {
      return `Are you sure want to remove schema ${selectedNode?.label}?`;
    }

    if (selectedNode?.level === TREE_LEVEL.TABLE) {
      return `Are you sure want to remove table ${selectedNode?.label}?`;
    }

    if (selectedNode?.level === TREE_LEVEL.COLUMN) {
      return `Are you sure want to remove column ${selectedNode?.label}?`;
    }

    return '';
  }, [selectedNode]);

  return (
    <div className={clsx("pl-2", sourceRoute === 'sources' ? 'mt-4' : 'mt-0')}>
      <div>{renderTree('null', 0)}</div>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        message={message}
        onConfirm={() => removeNode(selectedNode)}
      />
    </div>
  );
};

export default Tree;
