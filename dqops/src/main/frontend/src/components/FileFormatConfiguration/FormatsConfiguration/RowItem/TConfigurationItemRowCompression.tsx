export type TConfigurationItemRowCompression = {
  label: string;
  value?: string | number;
  onChange: (str: string | number, noExt: boolean) => void;
  defaultValue?: string | number;
  options?: any;
  className?: string;
};
