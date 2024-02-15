export type TConfigurationItemRow = {
  label: string;
  value?: string | number;
  onChange: (str: string | number) => void;
  defaultValue: string | number;
  isEnum?: boolean;
  options?: any,
};