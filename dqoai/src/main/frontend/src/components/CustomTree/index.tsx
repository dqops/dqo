import React, { useState } from 'react';
import { ReactTree, ReactTreeTheme, TreeRenderFn } from '@naisutech/react-tree';
import { useTree } from '../../contexts/treeContext';
import SvgIcon from '../SvgIcon';
import { TREE_LEVEL } from '../../shared/enums';
import clsx from 'clsx';
import { TreeNodeId } from '@naisutech/react-tree/types/Tree';
import { Tooltip } from '@material-tailwind/react';
import ContextMenu from './ContextMenu';
import ConfirmDialog from './ConfirmDialog';
import { CustomTreeNode } from '../../shared/interfaces';
import { useHistory, useLocation } from 'react-router-dom';

const theme: ReactTreeTheme = {
  text: {
    fontFamily: 'sans-serif'
  },
  nodes: {
    separator: {
      border: 'none'
    },
    folder: {
      hoverBgColor: '#E1E5E9'
    },
    icons: {
      leafColor: '#2D3748',
      folderColor: '#2D3748'
    }
  }
};

const CustomTree = () => {
  const { changeActiveTab, treeData, openNodes, toggleOpenNode } = useTree();
  const [isOpen, setIsOpen] = useState(false);
  const { removeNode } = useTree();
  const [selectedNode, setSelectedNode] = useState<CustomTreeNode>();
  const history = useHistory();
  const location = useLocation();

  const handleNodeClick = (node: CustomTreeNode) => {
    if (location.pathname !== '/') {
      history.push('/');
    }
    changeActiveTab(node);
  };

  const renderIcon: TreeRenderFn = (props: any) => {
    if (props.node.level === TREE_LEVEL.CHECK) {
      return <div className="w-4 h-0" />;
    }
    return (
      <SvgIcon
        className="w-4 min-w-4 text-black cursor-pointer"
        name={!props.open ? 'arrow-alt-right' : 'arrow-alt-down'}
        onClick={() => {
          toggleOpenNode(props.node.id);
        }}
      />
    );
  };

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

  const renderNode: TreeRenderFn = (props) => {
    const { node }: { node: any } = props;

    return (
      <div
        className="flex space-x-2 py-1 flex-1"
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
    );
  };

  const onToggleSelectedNodes = (nodes: TreeNodeId[]) => {};
  const onToggleOpenNodes = (nodes: TreeNodeId[]) => {};

  return (
    <div className="text-gray-100">
      <div className="">
        <ReactTree
          nodes={treeData}
          RenderIcon={renderIcon}
          RenderNode={renderNode}
          onToggleSelectedNodes={onToggleSelectedNodes}
          onToggleOpenNodes={onToggleOpenNodes}
          openNodes={openNodes}
          theme="custom"
          themes={{
            custom: theme
          }}
          containerStyles={{
            paddingRight: 0
          }}
        />
      </div>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        message="Are you sure want to remove this?"
        onConfirm={() => removeNode(selectedNode)}
      />
    </div>
  );
};

export default CustomTree;
