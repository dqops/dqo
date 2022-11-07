import { TreeNode } from '@naisutech/react-tree/types/Tree';
import { TREE_LEVEL } from '../enums';

export interface CustomTreeNode extends TreeNode {
  level: TREE_LEVEL;
  label: string;
  items: CustomTreeNode[];
  tooltip?: string;
}
