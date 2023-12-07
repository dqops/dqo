import { QualityCategoryModel } from '../../../../api';
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

export type TParameters = {
  name?: string;
  refConnection?: string;
  refSchema?: string;
  refTable?: string;
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
