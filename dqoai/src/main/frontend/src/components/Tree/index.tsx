import React, { useState } from 'react';
import { useTree } from '../../contexts/treeContext';
import { groupBy } from 'lodash';
import { TREE_LEVEL } from '../../shared/enums';
import SvgIcon from '../SvgIcon';
import { CustomTreeNode } from '../../shared/interfaces';
import clsx from 'clsx';
import { Tooltip } from '@material-tailwind/react';
import ContextMenu from '../CustomTree/ContextMenu';
import { useHistory } from 'react-router-dom';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { ROUTES } from "../../shared/routes";
import { findTreeNode } from "../../utils/tree";

const Tree = () => {
  const { changeActiveTab, treeData, toggleOpenNode, activeTab, tabMap } = useTree();
  const [isOpen, setIsOpen] = useState(false);
  const { removeNode } = useTree();
  const [selectedNode, setSelectedNode] = useState<CustomTreeNode>();
  const history = useHistory();

  const handleNodeClick = (node: CustomTreeNode) => {
    if (node.level === TREE_LEVEL.DATABASE) {
      history.push(ROUTES.CONNECTION_DETAIL(node.label, tabMap[node.label] || 'detail'));
    } else if (node.level === TREE_LEVEL.SCHEMA) {
      const connectionNode = findTreeNode(treeData, node.parentId ?? '');

      history.push(ROUTES.SCHEMA_LEVEL_PAGE(connectionNode?.label ?? '', node.label, 'tables'));
    } else if (node.level === TREE_LEVEL.TABLE) {
      const schemaNode = findTreeNode(treeData, node?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      history.push(ROUTES.TABLE_LEVEL_PAGE(connectionNode?.label ?? '', schemaNode?.label ?? '', node.label, 'detail'));
    } else if (node.level === TREE_LEVEL.TABLE_CHECKS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      history.push(ROUTES.TABLE_AD_HOCS(connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? ''));
    } else if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      history.push(ROUTES.TABLE_CHECKPOINTS(connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'daily'));
    } else if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      history.push(ROUTES.TABLE_CHECKPOINTS(connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'monthly'));
    } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      history.push(ROUTES.TABLE_PARTITIONED(connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'daily'));
    } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      history.push(ROUTES.TABLE_PARTITIONED(connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'monthly'));
    } else if (node.level === TREE_LEVEL.CHECK) {
      const parentNode = findTreeNode(treeData, node.parentId ?? '');
      const tableNode = findTreeNode(treeData, parentNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      history.push(ROUTES.TABLE_AD_HOCS_UI_FILTER(connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', node.category ?? '', node.label));
    } else {
      history.push('/checks');
    }
    changeActiveTab(node);
  };

  const groupedData = groupBy(treeData, 'parentId');

  const getIcon = (level: TREE_LEVEL) => {
    if (level === TREE_LEVEL.DATABASE) return 'database';
    if (level === TREE_LEVEL.SCHEMA) return 'schema';
    if (level === TREE_LEVEL.TABLE) return 'table';

    return 'column';
  };

  const openConfirm = (node: CustomTreeNode) => {
    setSelectedNode(node);
    setIsOpen(true);
  };

  const renderIcon = (node: CustomTreeNode) => {
    if (node.level === TREE_LEVEL.CHECK) {
      return <div className="w-6" />;
    }
    return (
      <SvgIcon
        className="w-4 min-w-4 text-black cursor-pointer"
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
            'px-2 cursor-pointer flex space-x-1 hover:bg-gray-100 mb-1',
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
                    `text-black flex-1 truncate mr-7`,
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

  return (
    <div className="pl-2 mt-4">
      <div>{renderTree('null', 0)}</div>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        message="Are you sure want to remove this?"
        onConfirm={() => removeNode(selectedNode)}
      />
    </div>
  );
};

export default Tree;
