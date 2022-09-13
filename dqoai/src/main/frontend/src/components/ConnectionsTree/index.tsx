import React from 'react';
import Tree from "rc-tree";
import { TreeNodeProps } from 'rc-tree/lib/TreeNode';
import SvgIcon from '../SvgIcon';
import { useTabs } from '../../contexts/tabContext';
import { DataNode } from 'rc-tree/es/interface';
import { useHistory } from 'react-router-dom';
import { TREE_LEVEL } from '../../shared/enums';
import "rc-tree/assets/index.css"
import './styles.css';

const ConnectionsTree = () => {
  const { addTab, treeData } = useTabs();
  const history = useHistory();

  const onExpand = () => {
  };
  
  const onClick = (event: any, node: DataNode) => {
    if (history.location.pathname !== '/test') {
      history.push('/test');
    }
    addTab(node);
  };

  return (
    <div className="px-4 text-gray-100">
      <Tree
        className="myCls"
        showLine
        selectable={false}
        defaultExpandAll
        onExpand={onExpand}
        defaultSelectedKeys={[]}
        defaultCheckedKeys={[]}
        onClick={onClick}
        treeData={treeData}
        icon={(props: any) => {
          if (props.data?.level === TREE_LEVEL.SCHEMA || props.data?.level === TREE_LEVEL.TABLE) {
            return <SvgIcon name="grid" className="mr-2 w-4" />;
          }
          if (props.data?.level === TREE_LEVEL.COLUMN) {
            return <SvgIcon name="table" className="mr-2 w-4" />;
          }
          return null;
        }}
        switcherIcon={(props: TreeNodeProps) => props.data?.children ? <SvgIcon name={props.expanded ? "arrow-alt-down" : "arrow-alt-right"} className="w-3 h-3" /> : null}
        rootClassName="connection-tree"
      />
    </div>
  );
};

export default ConnectionsTree;
