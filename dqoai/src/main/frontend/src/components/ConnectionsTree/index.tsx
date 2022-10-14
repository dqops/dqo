import React from 'react';

import Tree from 'react-ui-tree';
import { useHistory } from 'react-router-dom';

import { useTabs } from '../../contexts/tabContext';
import SvgIcon from '../SvgIcon';
import { ITreeNode } from '../../shared/interfaces';
import 'react-ui-tree/dist/react-ui-tree.css';
import './styles.css';
import clsx from 'clsx';
import { TREE_LEVEL } from '../../shared/enums';

const ConnectionsTree = () => {
  const { changeActiveTab, treeData, setTreeData } = useTabs();
  const history = useHistory();

  const onClick = (node: ITreeNode) => {
    if (history.location.pathname !== '/connection') {
      history.push('/connection');
    }
    changeActiveTab(node);
  };

  const getIcon = (level: TREE_LEVEL) => {
    if (level === TREE_LEVEL.DATABASE) return 'database';
    if (level === TREE_LEVEL.SCHEMA) return 'schema';
    if (level === TREE_LEVEL.TABLE) return 'table';

    return 'column';
  };

  const renderNode = (node: ITreeNode) => {
    if (node.key === 'root') {
      return <div />;
    }

    return (
      <div onClick={() => onClick(node)}>
        <div className="flex text-black space-x-2 items-center mb-2">
          <div className="w-4 shrink-0">
            {node.level !== TREE_LEVEL.COLUMN && (
              <SvgIcon
                className="w-4"
                name={node.collapsed ? 'arrow-alt-right' : 'arrow-alt-down'}
              />
            )}
          </div>
          <SvgIcon
            name={getIcon(node.level)}
            className={clsx(
              'w-4 shrink-0',
              node.level !== TREE_LEVEL.COLUMN ? '!ml-1' : ''
            )}
          />
          <div className="truncate">{node.module}</div>
        </div>
      </div>
    );
  };

  return (
    <div className="px-4 text-gray-100">
      <div className="-ml-5">
        <Tree
          paddingLeft={20}
          tree={treeData}
          onChange={setTreeData}
          renderNode={renderNode}
        />
      </div>
    </div>
  );
};

export default ConnectionsTree;
