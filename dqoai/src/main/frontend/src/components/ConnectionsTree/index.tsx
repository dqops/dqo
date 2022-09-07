import React from 'react';
import Tree from "rc-tree";
import { TreeNodeProps } from 'rc-tree/lib/TreeNode';
import SvgIcon from '../SvgIcon';
import { useTabs } from '../../contexts/tabContext';
import { DataNode } from 'rc-tree/es/interface';
import "rc-tree/assets/index.css"
import './styles.css';

const treeData = [
  {
    key: "dqo-ai",
    title: "dqo-ai",
    children: [
      {
        key: "dqo-ai.public",
        title: "Public",
        children: [
          { key: "dqo-ai.public.id", title: "Id" },
          { key: "dqo-ai.public.title", title: "Title" },
        ]
      },
      {
        key: "dqo-ai.album",
        title: "Album",
        children: [
          { key: "dqo-ai.album.id", title: "Id" },
          { key: "dqo-ai.album.title", title: "Title" },
          { key: "dqo-ai.album.artistId", title: "Artist Id" },
        ]
      },
      {
        key: "dqo-ai.customer",
        title: "Customer",
        children: [
          { key: "dqo-ai.customer.id", title: "Id" },
          { key: "dqo-ai.customer.title", title: "Title" },
          { key: "dqo-ai.customer.firstName", title: "FirstName" },
          { key: "dqo-ai.customer.lastName", title: "LastName" },
          { key: "dqo-ai.customer.email", title: "email" },
        ]
      },
      {
        key: "dqo-ai.employee",
        title: "Employee",
        children: [
          { key: "dqo-ai.employee.id", title: "Id" },
          { key: "dqo-ai.employee.title", title: "Title" },
          { key: "dqo-ai.employee.firstName", title: "FirstName" },
          { key: "dqo-ai.employee.lastName", title: "LastName" },
          { key: "dqo-ai.employee.email", title: "email" },
        ]
      },
      {
        key: "dqo-ai.invoice",
        title: "Invoice",
        children: [
          { key: "dqo-ai.invoice.id", title: "Id" },
          { key: "dqo-ai.invoice.title", title: "Title" },
          { key: "dqo-ai.invoice.amount", title: "Amount" },
        ]
      },
    ]
  },
  {
    key: "documati",
    title: "documati",
    children: [
    ]
  },
];

const ConnectionsTree = () => {
  const { addTab } = useTabs();

  const onExpand = () => {
  };
  
  const onClick = (event: any, node: DataNode) => {
    addTab(node);
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
        onClick={onClick}
        treeData={treeData}
        icon={(props: TreeNodeProps) => props.data?.children ? <SvgIcon name="grid" className="mr-2 w-4" /> : <SvgIcon name="table" className="mr-2 w-4" />}
        switcherIcon={(props: TreeNodeProps) => props.data?.children ? <SvgIcon name={props.expanded ? "arrow-alt-down" : "arrow-alt-right"} className="w-3 h-3" /> : null}
        rootClassName="connection-tree"
      />
    </div>
  );
};

export default ConnectionsTree;
