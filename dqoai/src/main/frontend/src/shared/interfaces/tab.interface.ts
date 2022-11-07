import { DataNode } from 'rc-tree/es/interface';

import { TREE_LEVEL } from '../enums';

export interface ITab {
  label: string;
  value: string;
  type?: string;
  tooltip?: string;
}

export interface TDataNode extends DataNode {
  level: TREE_LEVEL;
  children?: TDataNode[];
  activeNode?: string;
  collapsed: boolean;
}

export interface ITreeNode {
  key: string;
  module: string;
  children?: ITreeNode[];
  level: TREE_LEVEL;
  collapsed: boolean;
  parent: string;
}
