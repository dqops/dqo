import {TDataNode} from '../shared/interfaces';

export const findNode = (treeData: TDataNode[], key: string): (TDataNode | undefined) => {
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
}