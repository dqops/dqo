///
/// Copyright © 2024 DQOps (support@dqops.com)
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

import { TreeNodeId } from '@naisutech/react-tree/types/Tree';
import { TREE_LEVEL } from '../shared/enums';
import { CustomTreeNode, ITreeNode } from '../shared/interfaces';

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

export const findTreeNode = (
  treeData: CustomTreeNode[],
  id: TreeNodeId
): CustomTreeNode | undefined => {
  let node = treeData.find((item) => item.id === id);
  if (node) return node;

  for (const item of treeData) {
    if (item.items) {
      node = findTreeNode(item.items, id);

      if (node) {
        return node;
      }
    }
  }

  return undefined;
};

export const sleep = (duration: number) =>
  new Promise((resolve) => setTimeout(resolve, duration));
