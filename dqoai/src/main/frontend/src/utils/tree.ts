import { TREE_LEVEL } from '../shared/enums';
import { ITreeNode } from '../shared/interfaces';

export const findNode = (
  treeData: ITreeNode,
  key: string
): ITreeNode | undefined => {
  if (treeData.key === key) return treeData;

  if (treeData.children) {
    for (const item of treeData.children) {
      const node = findNode(item, key);
      if (node) return node;
    }
  }
  return undefined;
};

export const generateTreeNodes = (
  nodes: any[],
  keys: string[],
  level: TREE_LEVEL
) => {
  return nodes.map((node) => ({
    key: keys.map((key) => node[key]).join('.'),
    title: node[keys[keys.length - 1]] || '',
    level
  }));
};
