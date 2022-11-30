import React from 'react';

import { ReactComponent as AddSvg } from './svg/add.svg';
import { ReactComponent as ArrowAltDownSvg } from './svg/arrow-alt-down.svg';
import { ReactComponent as ArrowAltRightSvg } from './svg/arrow-alt-right.svg';
import { ReactComponent as BellSvg } from './svg/bell.svg';
import { ReactComponent as BigQueryBigSvg } from './svg/bigquery-big.svg';
import { ReactComponent as BigQuerySvg } from './svg/bigquery.svg';
import { ReactComponent as ChartBarSvg } from './svg/chart-bar.svg';
import { ReactComponent as CheckSvg } from './svg/check.svg';
import { ReactComponent as ChevronDownSvg } from './svg/chevron-down.svg';
import { ReactComponent as ChevronUpSvg } from './svg/chevron-up.svg';
import { ReactComponent as ClockSvg } from './svg/clock.svg';
import { ReactComponent as ClockOffSvg } from './svg/clock-off.svg';
import { ReactComponent as CloseSvg } from './svg/close.svg';
import { ReactComponent as CogSvg } from './svg/cog.svg';
import { ReactComponent as DownloadSvg } from './svg/download.svg';
import { ReactComponent as FacebookSvg } from './svg/facebook.svg';
import { ReactComponent as GoogleSvg } from './svg/google.svg';
import { ReactComponent as GridSvg } from './svg/grid.svg';
import { ReactComponent as InfoSvg } from './svg/info.svg';
import { ReactComponent as LockSvg } from './svg/lock.svg';
import { ReactComponent as MenuSvg } from './svg/menu.svg';
import { ReactComponent as SearchSvg } from './svg/search.svg';
import { ReactComponent as SelectorSvg } from './svg/selector.svg';
import { ReactComponent as ShoppingBagSvg } from './svg/shopping-bag.svg';
import { ReactComponent as SnowflakeBigSvg } from './svg/snowflake-big.svg';
import { ReactComponent as SnowflakeSvg } from './svg/snowflake.svg';
import { ReactComponent as TableSvg } from './svg/table.svg';
import { ReactComponent as UploadSvg } from './svg/upload.svg';
import { ReactComponent as UserAddSvg } from './svg/user-add.svg';
import { ReactComponent as UserCircleSvg } from './svg/user-circle.svg';
import { ReactComponent as UserSvg } from './svg/user.svg';
import { ReactComponent as UsersSvg } from './svg/users.svg';
import { ReactComponent as XCircleSvg } from './svg/x-circle.svg';
import { ReactComponent as DatabaseSvg } from './svg/database.svg';
import { ReactComponent as SchemaSvg } from './svg/schema.svg';
import { ReactComponent as ColumnSvg } from './svg/column.svg';
import { ReactComponent as EditSvg } from './svg/edit.svg';
import { ReactComponent as DeleteSvg } from './svg/delete.svg';
import { ReactComponent as SaveSvg } from './svg/save.svg';
import { ReactComponent as StopSvg } from './svg/stop.svg';
import { ReactComponent as DisableSvg } from './svg/disable.svg';
import { ReactComponent as PlaySvg } from './svg/play.svg';
import { ReactComponent as OptionsSvg } from './svg/options.svg';

const iconsMap: any = {
  bell: BellSvg,
  'chart-bar': ChartBarSvg,
  clock: ClockSvg,
  cog: CogSvg,
  download: DownloadSvg,
  facebook: FacebookSvg,
  google: GoogleSvg,
  lock: LockSvg,
  menu: MenuSvg,
  search: SearchSvg,
  selector: SelectorSvg,
  'shopping-bag': ShoppingBagSvg,
  upload: UploadSvg,
  user: UserSvg,
  'user-add': UserAddSvg,
  'user-circle': UserCircleSvg,
  users: UsersSvg,
  'x-circle': XCircleSvg,
  'big-query': BigQuerySvg,
  'bigquery-big': BigQueryBigSvg,
  snowflake: SnowflakeSvg,
  info: InfoSvg,
  check: CheckSvg,
  'chevron-down': ChevronDownSvg,
  'chevron-up': ChevronUpSvg,
  'snowflake-big': SnowflakeBigSvg,
  close: CloseSvg,
  table: TableSvg,
  grid: GridSvg,
  'arrow-alt-down': ArrowAltDownSvg,
  'arrow-alt-right': ArrowAltRightSvg,
  add: AddSvg,
  database: DatabaseSvg,
  schema: SchemaSvg,
  column: ColumnSvg,
  edit: EditSvg,
  delete: DeleteSvg,
  save: SaveSvg,
  stop: StopSvg,
  disable: DisableSvg,
  'clock-off': ClockOffSvg,
  play: PlaySvg,
  options: OptionsSvg
};

interface SvgIconProps {
  className?: string;
  name: string;
  onClick?: (e: any) => void;
  strokeWidth?: number;
}

const SvgIcon = ({ className, name, ...others }: SvgIconProps) => {
  const Component = iconsMap[name];

  if (!Component) {
    return <div />;
  }

  return React.cloneElement(<Component data-testid="svg-icon" />, {
    className,
    ...others
  });
};

export default SvgIcon;
