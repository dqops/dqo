import React from 'react';
import { ReactTree, ReactTreeTheme, TreeRenderFn } from '@naisutech/react-tree';
import { useTree } from '../../contexts/treeContext';
import SvgIcon from '../SvgIcon';
import { TREE_LEVEL } from '../../shared/enums';
import clsx from 'clsx';
import { TreeNodeId } from '@naisutech/react-tree/types/Tree';
import { Tooltip } from '@material-tailwind/react';

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

  const renderNode: TreeRenderFn = (props) => {
    const { node }: { node: any } = props;

    return (
      <div
        className="flex space-x-2 py-1 flex-1 max-w-full"
        onClick={() => changeActiveTab(node)}
      >
        <SvgIcon
          name={getIcon(node.level)}
          className={clsx('w-4 shrink-0 min-w-4')}
        />
        <Tooltip
          content={node.tooltip}
          className="max-w-120 py-4 px-4 bg-gray-800"
          placement="top-start"
        >
          <div className="text-black truncate flex-1">{node.label}</div>
        </Tooltip>
      </div>
    );
  };

  const onToggleSelectedNodes = (nodes: TreeNodeId[]) => {};
  const onToggleOpenNodes = (nodes: TreeNodeId[]) => {};

  return (
    <div className="px-4 text-gray-100">
      <div className="w-60">
        <ReactTree
          nodes={treeData}
          RenderIcon={renderIcon}
          RenderNode={renderNode}
          onToggleSelectedNodes={onToggleSelectedNodes}
          onToggleOpenNodes={onToggleOpenNodes}
          openNodes={openNodes}
          truncateLongText={true}
          theme="custom"
          themes={{
            custom: theme
          }}
        />
      </div>
    </div>
  );
};

export default CustomTree;
