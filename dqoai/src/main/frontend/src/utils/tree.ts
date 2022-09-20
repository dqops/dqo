import { TREE_LEVEL } from '../shared/enums';
import { TDataNode } from '../shared/interfaces';

export const findNode = (treeData: TDataNode[], key: string): TDataNode | undefined => {
  for (const item of treeData) {
    if (item.key === key) {
      return item;
    }
    if (item.children && item.children.length) {
      const result = findNode(item.children, key);
      if (result) {
        return result;
      }
    }
  }
  return undefined;
};

export const generateTreeNodes = (nodes: any[], keys: string[], level: TREE_LEVEL) => {
  return nodes.map((node) => ({
    key: keys.map((key) => node[key]).join('.'),
    title: node[keys[keys.length - 1]] || '',
    level,
  }));
};
