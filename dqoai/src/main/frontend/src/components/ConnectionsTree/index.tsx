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

const treeData = [
  {
    key: "dqo-ai",
    title: "dqo-ai",
    level: TREE_LEVEL.DATABASE,
    children: [
      {
        key: "dqo-ai.public",
        title: "Public",
        level: TREE_LEVEL.SCHEMA,
        children: [
          { key: "dqo-ai.public.id", title: "Id", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.public.title", title: "Title", level: TREE_LEVEL.COLUMN },
        ]
      },
      {
        key: "dqo-ai.album",
        title: "Album",
        level: TREE_LEVEL.SCHEMA,
        children: [
          { key: "dqo-ai.album.id", title: "Id", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.album.title", title: "Title", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.album.artistId", title: "Artist Id", level: TREE_LEVEL.COLUMN },
        ]
      },
      {
        key: "dqo-ai.customer",
        title: "Customer",
        level: TREE_LEVEL.SCHEMA,
        children: [
          { key: "dqo-ai.customer.id", title: "Id", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.customer.title", title: "Title", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.customer.firstName", title: "FirstName", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.customer.lastName", title: "LastName", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.customer.email", title: "email", level: TREE_LEVEL.COLUMN },
        ]
      },
      {
        key: "dqo-ai.employee",
        title: "Employee",
        level: TREE_LEVEL.SCHEMA,
        children: [
          { key: "dqo-ai.employee.id", title: "Id", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.employee.title", title: "Title", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.employee.firstName", title: "FirstName", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.employee.lastName", title: "LastName", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.employee.email", title: "email", level: TREE_LEVEL.COLUMN },
        ]
      },
      {
        key: "dqo-ai.invoice",
        title: "Invoice",
        level: TREE_LEVEL.SCHEMA,
        children: [
          { key: "dqo-ai.invoice.id", title: "Id", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.invoice.title", title: "Title", level: TREE_LEVEL.COLUMN },
          { key: "dqo-ai.invoice.amount", title: "Amount", level: TREE_LEVEL.COLUMN },
        ]
      },
    ]
  },
  {
    key: "documati",
    title: "documati",
    level: TREE_LEVEL.DATABASE,
    children: [
      {
        key: "documenti.public",
        title: 'Public'
      }
    ]
  },
];

const ConnectionsTree = () => {
  const { addTab } = useTabs();
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
          if (props.data?.level === TREE_LEVEL.SCHEMA) {
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
