import React, { useCallback, useEffect } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useTree } from "../../contexts/treeContext";
import { findTreeNode } from "../../utils/tree";
import { useParams, useRouteMatch } from "react-router-dom";
import { ROUTES } from "../../shared/routes";
import { CustomTreeNode } from "../../shared/interfaces";

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { tabs, setActiveTab, activeTab, closeTab, treeData, refreshNode, changeActiveTab, switchTab, activeNode, sourceRoute } =
    useTree();

  const { connection, schema, table, column, category, timePartitioned, checkName } = useParams() as any;
  const match = useRouteMatch();

  const handleChangeTab = useCallback((node?: CustomTreeNode) => {
    if (!node) return;

    if (!activeTab) {
      changeActiveTab(node, true);
    } else {
      console.log('>>', node.id, activeTab)
      if ((node.id !== activeTab && activeNode?.id === node.id) || (node.id === activeTab && activeNode?.id !== node.id)) {
        changeActiveTab(node, true);
      }
    }
  }, [changeActiveTab, activeTab, activeNode])

  useEffect(() => {
    (async () => {
      if (connection) {
        const connectionNode = findTreeNode(treeData, connection);
        if (!connectionNode) {
          return;
        }
        if (match.path === ROUTES.PATTERNS.CONNECTION) {
          handleChangeTab(connectionNode);
        }
        if (!connectionNode?.open && schema) {
          await refreshNode(connectionNode);
        } else if (schema) {
          const schemaNode = findTreeNode(treeData, `${connection}.${schema}`);

          if (match.path === ROUTES.PATTERNS.SCHEMA) {
            handleChangeTab(schemaNode);
          }
          if (!schemaNode?.open && table) {
            await refreshNode(schemaNode);
          } else if (table) {
            const tableNode = findTreeNode(treeData, `${connection}.${schema}.${table}`);
            if (match.path === ROUTES.PATTERNS.TABLE) {
              handleChangeTab(tableNode);
            } else if (!tableNode?.open) {
              await refreshNode(tableNode);
            } else {
              if (
                match.path === ROUTES.PATTERNS.TABLE_COLUMNS ||
                match.path === ROUTES.PATTERNS.COLUMN ||
                match.path === ROUTES.PATTERNS.COLUMN_PROFILINGS ||
                match.path === ROUTES.PATTERNS.COLUMN_PROFILINGS_FILTER ||
                match.path === ROUTES.PATTERNS.COLUMN_RECURRING_DAILY ||
                match.path === ROUTES.PATTERNS.COLUMN_RECURRING_MONTHLY ||
                match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY ||
                match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY ||
                match.path === ROUTES.PATTERNS.COLUMN_RECURRING_FILTER ||
                match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER
              ) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns`);
                if (match.path === ROUTES.PATTERNS.TABLE_COLUMNS) {
                  handleChangeTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (column) {
                  const columnNode = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}`);
                  if (match.path === ROUTES.PATTERNS.COLUMN) {
                    handleChangeTab(columnNode);
                  } else if (!columnNode?.open) {
                    await refreshNode(columnNode);
                  } else {
                    if (match.path === ROUTES.PATTERNS.COLUMN_PROFILINGS || match.path === ROUTES.PATTERNS.COLUMN_PROFILINGS_FILTER) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.checks`);

                      if (match.path === ROUTES.PATTERNS.COLUMN_PROFILINGS) {
                        handleChangeTab(node);
                      } else if (!node?.open) {
                        await refreshNode(node);
                      } else if (match.path === ROUTES.PATTERNS.COLUMN_PROFILINGS_FILTER) {
                        const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.checks.${category}_${checkName}`);
                        changeActiveTab(node);
                      }
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_RECURRING_DAILY || (timePartitioned === 'daily' && match.path === ROUTES.PATTERNS.COLUMN_RECURRING_FILTER)) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.dailyCheck`);

                      if (match.path === ROUTES.PATTERNS.COLUMN_RECURRING_DAILY) {
                        changeActiveTab(node);
                      } else if (!node?.open) {
                        await refreshNode(node);
                      } else if (match.path === ROUTES.PATTERNS.COLUMN_RECURRING_FILTER) {
                        const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.dailyCheck.${category}_${checkName}`);
                        changeActiveTab(node);
                      }
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_RECURRING_MONTHLY || (timePartitioned === 'monthly' && match.path === ROUTES.PATTERNS.COLUMN_RECURRING_FILTER)) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.monthlyCheck`);

                      if (match.path === ROUTES.PATTERNS.COLUMN_RECURRING_MONTHLY) {
                        changeActiveTab(node);
                      } else if (!node?.open) {
                        await refreshNode(node);
                      } else if (match.path === ROUTES.PATTERNS.COLUMN_RECURRING_FILTER) {
                        const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.monthlyCheck.${category}_${checkName}`);
                        changeActiveTab(node);
                      }
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY || (timePartitioned === 'daily' && match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER)) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.dailyPartitionedChecks`);

                      if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY) {
                        changeActiveTab(node);
                      } else if (!node?.open) {
                        await refreshNode(node);
                      } else if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER) {
                        const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.dailyPartitionedChecks.${category}_${checkName}`);
                        changeActiveTab(node);
                      }
                    }
                    if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY || (timePartitioned === 'monthly' && match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER)) {
                      const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.monthlyPartitionedChecks`);
                      if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY) {
                        changeActiveTab(node);
                      } else if (!node?.open) {
                        await refreshNode(node);
                      } else if (match.path === ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER) {
                        const node = findTreeNode(treeData, `${connection}.${schema}.${table}.columns.${column}.monthlyPartitionedChecks.${category}_${checkName}`);
                        changeActiveTab(node);
                      }
                    }
                  }
                }
              }
              if (match.path === ROUTES.PATTERNS.TABLE_PROFILINGS || match.path === ROUTES.PATTERNS.TABLE_PROFILINGS_FILTER) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.checks`);
                if (match.path === ROUTES.PATTERNS.TABLE_PROFILINGS) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_PROFILINGS_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.checks.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }
              if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_DAILY || (timePartitioned === 'daily' && match.path === ROUTES.PATTERNS.TABLE_RECURRING_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyCheck`);
                if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_DAILY) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyCheck.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }

              if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_MONTHLY || (timePartitioned === 'monthly' && match.path === ROUTES.PATTERNS.TABLE_RECURRING_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.monthlyCheck`);
                if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_DAILY) {
                  changeActiveTab(node);
                } else if (!node?.open) {
                  await refreshNode(node);
                } else if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_FILTER) {
                  const node = findTreeNode(treeData, `${connection}.${schema}.${table}.monthlyCheck.${category}_${checkName}`);
                  changeActiveTab(node);
                }
              }

              if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY || (timePartitioned === 'daily' && match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER)) {
                const node = findTreeNode(treeData, `${connection}.${schema}.${table}.dailyPartitionedChecks`);
                if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_DAILY) {
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
                if (match.path === ROUTES.PATTERNS.TABLE_RECURRING_DAILY) {
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
  }, [connection, schema, table, column, category, timePartitioned, checkName, treeData, handleChangeTab]);

  const handleChange = (value: string) => {
    const node = findTreeNode(treeData, value);
    switchTab(node, sourceRoute);
    setActiveTab(value);
  }

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={handleChange}
          onRemoveTab={closeTab}
          limit={10}
        />
        <div
          className="flex-1 bg-white border border-gray-300 flex-auto min-h-0 overflow-auto"
          style={{ maxHeight: "calc(100vh - 80px)" }}
        >
          {!tabs[activeTab] && (
            <div>
              {children}
            </div>
          )}
        </div>
      </div>
    </MainLayout>
  );
};

export default ConnectionLayout;
