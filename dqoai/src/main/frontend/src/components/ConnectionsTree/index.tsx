import React from 'react';
import Tree from "rc-tree";
import "rc-tree/assets/index.css"
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

  const onCheck = () => {
  };
  
  return (
    <div className="px-4 py-16 text-gray-100">
      <Tree
        className="myCls"
        showLine
        checkable
        selectable={false}
        defaultExpandAll
        onExpand={onExpand}
        defaultSelectedKeys={[]}
        defaultCheckedKeys={[]}
        onSelect={onSelect}
        onCheck={onCheck}
        treeData={treeData}
      />
    </div>
  );
};

export default ConnectionsTree;
