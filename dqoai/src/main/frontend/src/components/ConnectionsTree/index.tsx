import React from 'react';
import Tree from "rc-tree";
import "rc-tree/assets/index.css"
import {TreeNodeProps} from 'rc-tree/lib/TreeNode';
import SvgIcon from '../SvgIcon';
import './styles.css';

const treeData = [
  {
    key: "0-0",
    title: "My DB Connection 1",
    children: [
      {
        key: "0-0-1",
        title: "Tables",
        children: [
          { key: "0-0-1-0", title: "Users" },
          { key: "0-0-1-1", title: "Products" },
          { key: "0-0-1-2", title: "Books" },
          { key: "0-0-1-3", title: "Tests" },
        ]
      }
    ]
  },
  {
    key: "con2",
    title: "My DB Connection 2",
    children: [
      {
        key: "1-0-1",
        title: "Tables",
        children: [
          { key: "1-0-1-0", title: "Users" },
          { key: "1-0-1-1", title: "Products" },
          { key: "1-0-1-2", title: "Books" },
          { key: "1-0-1-3", title: "Tests" },
          { key: "1-0-1-4", title: "Times" },
          { key: "1-0-1-5", title: "News" },
          { key: "1-0-1-6", title: "Organizations" },
          { key: "1-0-1-7", title: "Repositories" },
          { key: "1-0-1-8", title: "Comments" },
          { key: "1-0-1-9", title: "Tags" },
          { key: "1-0-2-0", title: "Slashes" },
        ]
      }
    ]
  },
  {
    key: "con3",
    title: "My DB Connection 3",
    children: [
      {
        key: "2-0-1",
        title: "Tables",
        children: [
          { key: "2-0-1-0", title: "Users" },
          { key: "2-0-1-1", title: "Products" },
          { key: "2-0-1-2", title: "Books" },
          { key: "2-0-1-3", title: "Tests" },
          { key: "2-0-1-4", title: "Times" },
          { key: "2-0-1-5", title: "News" },
        ]
      }
    ]
  }

];

const ConnectionsTree = () => {
  const onExpand = () => {
  };
  
  const onSelect = () => {
  };

  return (
    <div className="px-4 py-16 text-gray-100">
      <Tree
        className="myCls"
        showLine
        selectable={false}
        defaultExpandAll
        onExpand={onExpand}
        defaultSelectedKeys={[]}
        defaultCheckedKeys={[]}
        onSelect={onSelect}
        treeData={treeData}
        icon={(props: TreeNodeProps) => props.data?.children ? <SvgIcon name="grid" className="mr-2 w-4" /> : <SvgIcon name="table" className="mr-2 w-4" />}
        switcherIcon={(props: TreeNodeProps) => props.data?.children ? <SvgIcon name={props.expanded ? "arrow-alt-down" : "arrow-alt-right"} className="w-3 h-3" /> : null}
        rootClassName="connection-tree"
      />
    </div>
  );
};

export default ConnectionsTree;
