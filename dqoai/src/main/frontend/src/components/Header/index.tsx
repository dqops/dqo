import React from 'react';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';
import clsx from 'clsx';
import { useHistory, useLocation } from 'react-router-dom';
import useSearchParams from "../../hooks/useSearchParams";
import { CheckTypes } from "../../shared/routes";
import { useTree } from "../../contexts/treeContext";

const Header = () => {
  const { getConnections } = useTree();
  const history = useHistory();
  const location = useLocation();
  const query = useSearchParams();
  const isDataQualityChecksActive = location.pathname.startsWith('/checks') && !query.get("type"); // will deprecate
  const isAdHocChecksActive = location.pathname.startsWith('/checks') && query.get("type") === CheckTypes.ADHOC;
  const isWholeTableChecksActive = location.pathname.startsWith('/checks') && query.get("type") === CheckTypes.CHECKPOINT;
  const isTimePeriodChecksActive = location.pathname.startsWith('/checks') && query.get("type") === CheckTypes.PARTITION;

  const handleRedirectToChecks = (checkType?: CheckTypes) => () => {
    const isChecksPage = location.pathname.startsWith('/checks');
    const currentCheckType = query.get("type");

    if (!isChecksPage || currentCheckType !== checkType) {
      query.set("type", checkType as string);
      getConnections();
      history.push(`/checks${checkType ? `?${query.toString()}` : ''}`);
    }
  }

  return (
    <div
      className="fixed top-0 left-0 right-0 min-h-16 max-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300 px-4"
    >
      <div className="flex space-x-2">
        <div onClick={() => history.push('/')}>
          <Logo className="w-30 cursor-pointer" />
        </div>
        <div className="flex items-center">
          {/* will deprecate */}
          <div
            className={clsx("px-4 cursor-pointer", isDataQualityChecksActive ? 'font-bold' : '' )}
            onClick={handleRedirectToChecks()}
          >
            Source
          </div>
          <div
            className={clsx("px-4 cursor-pointer", isAdHocChecksActive ? 'font-bold' : '' )}
            onClick={handleRedirectToChecks(CheckTypes.ADHOC)}
          >
            Profiling
          </div>
          <div
            className={clsx("px-4 cursor-pointer", isWholeTableChecksActive ? 'font-bold' : '' )}
            onClick={handleRedirectToChecks(CheckTypes.CHECKPOINT)}
          >
            Whole table checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", isTimePeriodChecksActive ? 'font-bold' : '' )}
            onClick={handleRedirectToChecks(CheckTypes.PARTITION)}
          >
            Time period checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname === '/dashboards' ? 'font-bold' : '' )}
            onClick={() => history.push('/dashboards')}
          >
            Data Quality Dashboards
          </div>
        </div>
      </div>
      <NotificationMenu />
    </div>
  );
};

export default Header;
