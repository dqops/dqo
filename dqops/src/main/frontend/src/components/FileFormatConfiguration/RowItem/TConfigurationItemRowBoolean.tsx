export type TConfigurationItemRowBoolean = {
  label: string;
  value?: boolean;
  onChange: (str: boolean) => void;
  defaultValue: boolean;
};