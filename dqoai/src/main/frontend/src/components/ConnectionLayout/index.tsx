import React, { useEffect } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useTree } from "../../contexts/treeContext";
import { findTreeNode } from "../../utils/tree";
import { useParams, useRouteMatch } from "react-router-dom";
import { ROUTES } from "../../shared/routes";

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { tabs, setActiveTab, activeTab, onAddTab, closeTab, treeData, refreshNode, changeActiveTab } =
    useTree();

  const { connection, schema, table, column, category, timePartitioned, checkName } = useParams() as any;
  const match = useRouteMatch();

  useEffect(() => {
    if (activeTab) return;

    (async () => {
      if (connection) {
        const connectionNode = findTreeNode(treeData, connection);
        if (match.path === ROUTES.PATTERNS.CONNECTION) {
          changeActiveTab(connectionNode);
        }
        if (!connectionNode?.open && schema) {
          await refreshNode(connectionNode);
        } else if (schema) {
          const schemaNode = findTreeNode(treeData, `${connection}.${schema}`);

          if (match.path === ROUTES.PATTERNS.SCHEMA) {
            changeActiveTab(schemaNode);
          }
          if (!schemaNode?.open && table) {
            await refreshNode(schemaNode);
          } else if (table) {
            const tableNode = findTreeNode(treeData, `${connection}.${schema}.${table}`);
            if (match.path === ROUTES.PATTERNS.TABLE) {
              changeActiveTab(tableNode);
            } else if (!tableNode?.open) {
              await refreshNode(tableNode);
            } else {
              if (
                match.path === ROUTES.PATTERNS.TABLE_COLUMNS ||
                match.path === ROUTES.PATTERNS.COLUMN ||
                match.path === ROUTES.PATTERNS.COLUMN_AD_HOCS ||
                match.path === ROUTES.PATTERNS.COLUMN_AD_HOCS_FILTER ||
                match.path === ROUTES.PATTERNS.COLUMN_CHECKPOINTS_DAILY ||
                match.path === ROUTES.PATTERNS.COLUMN_CHECKPOINTS_MONTHLY ||
                match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY ||
                match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY ||
                match.path === ROUTES.PATTERNS.COLUMN_CHECKPOINTS_FILTER ||
                match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER
              ) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns`);
                if (match.path === ROUTES.PATTERNS.TABLE_COLUMNS) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (column) {
                  const columnNode = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}`);
                  if (match.path === ROUTES.PATTERNS.COLUMN) {
                    changeActiveTab(columnNode);
                  } else if (!columnNode?.open) {
                    await refreshNode(columnNode);
                  } else {
                    if (match.path === ROUTES.PATTERNS.COLUMN_AD_HOCS) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.checks`);
                      changeActiveTab(node);
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_CHECKPOINTS_DAILY) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.dailyCheck`);
                      changeActiveTab(node);
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_CHECKPOINTS_MONTHLY) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.monthlyCheck`);
                      changeActiveTab(node);
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.dailyPartitionedChecks`);
                      changeActiveTab(node);
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.monthlyPartitionedChecks`);
                      changeActiveTab(node);
                    }
                  }
                }
              }
              if (match.path === ROUTES.PATTERNS.TABLE_AD_HOCS || match.path === ROUTES.PATTERNS.TABLE_AD_HOCS_FILTER) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.checks`);
                if (match.path === ROUTES.PATTERNS.TABLE_AD_HOCS) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_AD_HOCS_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.checks.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }
              if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_DAILY || (timePartitioned === 'daily' && match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyCheck`);
                if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_DAILY) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyCheck.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }

              if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_MONTHLY || (timePartitioned === 'monthly' && match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.monthlyCheck`);
                if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_DAILY) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.monthlyCheck.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }

              if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY || (timePartitioned === 'daily' && match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyPartitionedChecks`);
                if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_DAILY) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyPartitionedChecks.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }

              if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY || (timePartitioned === 'monthly' && match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.monthlyPartitionedChecks`);
                if (match.path === ROUTES.PATTERNS.TABLE_CHECKPOINTS_DAILY) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.monthlyPartitionedChecks.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }
            }
          }
        }
      }
    })();
  }, [treeData]);

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={setActiveTab}
          onRemoveTab={closeTab}
          onAddTab={onAddTab}
        />
        <div className="flex-1 bg-white border border-gray-300 flex-auto">
          {children}
        </div>
      </div>
    </MainLayout>
  );
};

export default ConnectionLayout;
