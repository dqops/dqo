import React from 'react';

export default function StaticHomePage() {
  return (
    <div>
      <div className="bg-teal-500 text-white px-4 py-4 text-2xl font-bold mb-4">
        Welcome to DQOps Data Quality Operations Center
      </div>
      <div className="px-8 py-4 w-full">
        <p className="font-bold text-lg mb-5">
          To start monitoring your data, just follow these steps
        </p>

        <div className="w-full grid grid-cols-3 gap-20">
          <div className="col-span-2">
            <div className="mb-6">
              <p className="font-semibold">
                <span className="mr-2">1.</span>Add a connection to your
                database
              </p>
              <ul className="pl-8 list-disc list-outside">
                <li className="mb-2">
                  <div className="inline-flex items-end gap-2">
                    <span>Go to the Data Sources section and click</span>
                    <img
                      src="/images/home/addConnection.png"
                      className="w-30"
                      alt=""
                    />
                    <span>in the upper left corner.</span>
                  </div>
                </li>
                <li className="mb-2">Fill in the connection parameters.</li>
                <li className="mb-2">Import tables for monitoring.</li>
              </ul>
            </div>
            <div className="mb-6">
              <p className="font-semibold mb-4">
                <span className="mr-2">2.</span>Select and run Profiling data
                quality checks
              </p>
              <ul className="pl-8 list-disc list-outside">
                <li className="mb-2">
                  <div className="inline-flex items-end">
                    Go to the Profiling section
                  </div>
                </li>
                <li className="mb-2">
                  <div className="inline-flex items-end">
                    On the tree view on the left, find a table{' '}
                    <img
                      src="/images/home/table.png"
                      alt="table"
                      className="inline"
                    />{' '}
                    or column{' '}
                    <img
                      src="/images/home/column.png"
                      alt="column"
                      className="inline"
                    />{' '}
                    of interest by expanding the added connection.
                  </div>
                </li>
                <li className="mb-2">
                  <div className="inline-flex items-end">
                    In the list of checks on the right, enable the selected data
                    quality checks by clicking the switch{' '}
                    <img
                      src="/images/home/switch-off.png"
                      alt=""
                      className="inline"
                    />{' '}
                    .
                  </div>
                </li>
                <li className="mb-2">
                  <div className="inline-flex items-end">
                    Adjust the threshold levels or leave the default option{' '}
                    <img
                      src="/images/home/min_count.png"
                      alt=""
                      className="w-25 inline ml-2"
                    />
                    .
                  </div>
                </li>
                <li className="mb-2">
                  <div className="inline-flex items-end">
                    Click{' '}
                    <img
                      src="/images/home/save.png"
                      alt=""
                      className="inline w-30"
                    />{' '}
                    in the upper right corner.
                  </div>
                </li>
                <li className="mb-2">
                  <div className="inline-flex items-end">
                    Run data quality check by clicking the Run Check icon{' '}
                    <img
                      src="/images/home/play.png"
                      alt=""
                      className="inline"
                    />
                    .
                  </div>
                </li>
              </ul>
            </div>
            <div className="mb-6">
              <p className="font-semibold mb-4">
                <span className="mr-2">3.</span>Evaluate the results
              </p>
              <ul className="pl-8 list-disc list-outside">
                <li className="mb-2 leading-[2]">
                  The square next to the check name will indicate the results of
                  the check run: Valid, Warning, Error or Fatal{' '}
                  <img
                    src="/images/home/status-bar.png"
                    alt=""
                    className="inline"
                  />
                  .
                  <br />
                  You can check the details by placing the mouse cursor on the
                  square.
                </li>
                <li className="mb-2">
                  Click the Results icon{' '}
                  <img
                    src="/images/home/details.png"
                    alt=""
                    className="inline"
                  />{' '}
                  to view more details.
                </li>
                <li className="mb-2">
                  Go to the Data Quality Dashboards section to review KPIs
                  (percentage of passed data quality checks).
                </li>
              </ul>
            </div>
          </div>
          <div className="flex flex-col gap-4">
            <p>Check the docs for more tutorials</p>
            <a href="https://dqops.com/docs/">
              <img src="/images/home/book.svg" className="w-20 mb-2" alt="" />
            </a>
            <p>
              Download our best practices for effective <br />
              data quality improvement
            </p>
            <a
              href="https://dqops.com/best-practices-for-effective-data-quality-improvement/"
              className="mb-2"
              target="blank"
            >
              <img src="/images/home/download_practice.png" alt="" />
            </a>

            <p>Contact us for more information</p>
            <a
              className="w-40 bg-primary rounded-lg py-2 px-8 text-white mb-2"
              href="https://dqops.com/contact-us/"
            >
              Contact us
            </a>

            <p>Check our progress on GitHub</p>
            <a href="https://github.com/dqops/dqo" className="mb-2">
              <img src="/images/home/github.png" className="w-40" alt="" />
            </a>
            <p>Visit DQOps website</p>
            <a href="https://dqops.com/">
              <img src="/logo.svg" className="w-40" alt="" />
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}
