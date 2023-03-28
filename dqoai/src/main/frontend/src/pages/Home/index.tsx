import React from 'react';
import Header from '../../components/Header';
import Button from '../../components/Button';
import Logo from '../../components/Logo';

const HomePage = () => {
  return (
    <div className="flex min-h-screen overflow-hidden">
      <Header />
      <div className="mt-16">
        <div className="bg-teal-500 text-white px-4 py-4 text-2xl font-bold mb-4">
          Welcome do DQO
        </div>
        <div className="px-8 py-4">
          <p className="font-bold text-xl mb-5">
            To start monitoring your data, just follow these steps
          </p>

          <div className="grid grid-cols-3 gap-8">
            <div className="col-span-2">
              <div className="mb-6">
                <p className="font-semibold"><span className="mr-4">1.</span>Add a connection to your database</p>
                <ul className="pl-8 list-disc list-inside">
                  <li className="mb-2">
                    <div className="inline-flex items-end gap-2">
                      <span>
                        Go to the Data Sources section and click
                      </span>
                      <img src="/images/home/addConnection.png" alt="" />
                      <span>
                        in the upper left corner.
                      </span>
                    </div>
                  </li>
                  <li className="mb-2">
                    Fill in the connection parameters.
                  </li>
                  <li className="mb-2">
                    Import tables for monitoring.
                  </li>
                </ul>
              </div>
              <div className="mb-6">
                <p className="font-semibold mb-4"><span className="mr-4">2.</span>Select and run Profiling data quality checks</p>
                <ul className="pl-8 list-disc list-inside">
                  <li className="mb-2">
                    <div className="inline-flex items-end">
                      Go to the Profiling section
                    </div>
                  </li>
                  <li className="mb-2">
                    <div className="inline-flex items-end">
                      On the tree view on the left, find a table <img src="/images/home/table.png" alt="table" className="inline" />  or column <img src="/images/home/column.png" alt="column" className="inline" /> of interest by expanding the added connection.
                    </div>
                  </li>
                  <li className="mb-2">
                    <div className="inline-flex items-end">
                      In the list of checks on the right,  enable the selected data quality checks by clicking the switch <img src="/images/home/switch-off.png" alt="" className="inline" /> .
                    </div>
                  </li>
                  <li className="mb-2">
                    <div className="inline-flex items-end">
                      Adjust the threshold levels or leave the default option <img src="/images/home/min_count.png" alt="" className="inline" />.
                    </div>
                  </li>
                  <li className="mb-2">
                    <div className="inline-flex items-end">
                      Click <img src="/images/home/save.png" alt="" className="inline" /> in the upper right corner.
                    </div>
                  </li>
                  <li className="mb-2">
                    <div className="inline-flex items-end">
                      Run data quality check by clicking the Run Check icon <img src="/images/home/play.png" alt="" className="inline" />.
                    </div>
                  </li>
                </ul>
              </div>
              <div className="mb-6">
                <p className="font-semibold mb-4"><span className="mr-4">3.</span>Evaluate the results</p>
                <ul className="pl-8 list-disc list-inside">
                  <li className="mb-2">
                    The square next to the check name will indicate the results of the check run: Valid, Warning, Error or Fatal <img src="/images/home/status-bar.png" alt="" className="inline" />. You can check the details by placing the mouse cursor on the square.
                  </li>
                  <li className="mb-2">
                    Click the Check Details icon <img src="/images/home/details.png" alt="" className="inline" />  to view more details.
                  </li>
                  <li className="mb-2">
                    Go to the Data Quality Dashboards section to review KPIs (percentage of passed data quality checks).
                  </li>
                </ul>
              </div>
            </div>
            <div className="flex flex-col gap-4">
              <p>Check the docs for more tutorials</p>
              <a href="https://dqo.ai/contact-us">
                <img src="/images/home/book.png" alt="" />
              </a>
              <p>
                Download our best practices for effective
                data quality improvement
              </p>
              <a href="https://dqo.ai/dqo_ebook_a_step-by-step_guide_to_improve_data_quality-2/">
                <img src="/images/home/download_practice.png" alt="" />
              </a>

              <p>
                Contact us for more information
              </p>
              <img src="/images/home/contact_us.png" className="w-25" alt="" />

              <p>
                Check our progress on GitHub
              </p>
              <a href="https://github.com/dqoai/dqo">
                <img src="/images/home/github.png" className="w-20" alt="" />
              </a>
              <p>
                Back to home page
              </p>
              <a href="https://dqo.ai/">
                <img src="/images/home/dqoai.png" className="w-20" alt="" />
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;