export type TConfigurationItemRowBoolean = {
  label: string;
  value?: boolean;
  onChange: (str: boolean | undefined) => void;
  defaultValue?: boolean;
  className?: string;
};
