import { QualityCategoryModel } from '../../../../api';
import { TParameters } from '../../../../shared/constants';
import { CheckTypes } from '../../../../shared/routes';
import { Option } from '../../../Select';

export type EditProfilingReferenceTableProps = {
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  selectedReference?: string;
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
  categoryCheck?: QualityCategoryModel;
  getNewTableComparison: () => void;
  onChangeSelectedReference: (arg: string) => void;
  listOfExistingReferences: Array<string | undefined>;
  canUserCompareTables?: boolean;
  checksUI: any;
  onUpdateChecks: () => void;
};

export type TEditReferenceTable = {
  selectedReference?: string;
  timePartitioned?: 'daily' | 'monthly';
  existingTableComparisonConfigurations: (string | undefined)[];
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  columnOptions: {
    comparedColumnsOptions: Option[];
    referencedColumnsOptions: Option[];
  };
  editConfigurationParameters: TParameters;
  onChangeParameters: (obj: Partial<TParameters>) => void;
  onUpdateChecks: () => void;
  setConfigurationToEditing: (name: string) => void;
  onChangeIsUpdated: (isUpdated: boolean) => void;
  isUpdated: boolean;
  compareTables: () => Promise<void>;
  deleteData: (params: { [key: string]: string | boolean }) => Promise<void>;
  disabled: boolean | undefined;
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
