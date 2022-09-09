import { DataNode } from 'rc-tree/es/interface';
import { TREE_LEVEL } from '../enums';

export interface ITab {
  label: string;
  value: string;
}

export interface TDataNode extends DataNode {
  level: TREE_LEVEL;
  children?: TDataNode[];
  activeNode?: string;
}