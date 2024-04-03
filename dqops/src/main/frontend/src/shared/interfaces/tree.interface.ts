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

import { TreeNode } from '@naisutech/react-tree/types/Tree';
import { TREE_LEVEL } from '../enums';
import { CheckSearchFilters, DeleteStoredDataQueueJobParameters, StatisticsCollectorSearchFilters } from '../../api';

export interface CustomTreeNode extends TreeNode {
  level: TREE_LEVEL;
  label: string;
  items: CustomTreeNode[];
  tooltip?: string;
  hasCheck?: boolean;
  run_checks_job_template?: CheckSearchFilters;
  collect_statistics_job_template?: StatisticsCollectorSearchFilters;
  data_clean_job_template?: DeleteStoredDataQueueJobParameters;
  open?: boolean;
  category?: string;
  error_message?: string;

  configured?: boolean;
  parsingYamlError?: string; 
}
