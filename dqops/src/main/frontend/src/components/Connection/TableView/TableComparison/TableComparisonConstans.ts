import {
  QualityCategoryModel,
  TableComparisonGroupingColumnPairModel
} from '../../../../api';
import { CheckTypes } from '../../../../shared/routes';

export type EditProfilingReferenceTableProps = {
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  selectedReference?: string;
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
  categoryCheck?: QualityCategoryModel;
  isCreating?: boolean;
  getNewTableComparison: () => void;
  onChangeSelectedReference: (arg: string) => void;
  listOfExistingReferences: Array<string | undefined>;
  canUserCompareTables?: boolean;
  checksUI: any;
  onUpdateChecks: () => void;
};

export type TSeverityValues = Partial<{
  warning: number;
  error: number;
  fatal: number;
}>;

export const itemsToRender = [
  {
    key: 'min_match',
    prop: 'compare_min'
  },
  {
    key: 'max_match',
    prop: 'compare_max'
  },
  {
    key: 'sum_match',
    prop: 'compare_sum'
  },
  {
    key: 'mean_match',
    prop: 'compare_mean'
  },
  {
    key: 'null_count_match',
    prop: 'compare_null_count'
  },
  {
    key: 'not_null_count_match',
    prop: 'compare_not_null_count'
  }
];

export const checkNames = [
  'Min',
  'Max',
  'Sum',
  'Mean',
  'Nulls count',
  'Not nulls count'
];
