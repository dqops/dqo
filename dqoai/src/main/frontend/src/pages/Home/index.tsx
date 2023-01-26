import React from 'react';
import Header from '../../components/Header';
import Button from '../../components/Button';
import Logo from '../../components/Logo';

const HomePage = () => {
  return (
    <div className="flex min-h-screen overflow-hidden">
      <Header />
      <div className="flex w-full">
        <div className="p-12 mt-16 flex-1">
          <h1 className="text-3xl italic mb-4">Welcome to your DQO account</h1>
          <div className="mb-3">
            The navigation menu at the top of the page provides access the Data Quality Checks and Data Quality Dashboards sections.
          </div>
          <div className="mb-3">
            In the Data Quality Dashboard section you can find examples of visualization dashboards showing the results of check execution.
          </div>
          <div className="mb-3">
            If you are a new user, follow the instruction below on how to install and run the DQO application:
          </div>
          <ol className="list-decimal pl-4">
            <li>
              <div className="flex space-x-1 items-center">
                <span>{"DQO requires Python version >= 3.6 and can be installed using pip. You can check your version of pip with"}</span>
                <img src="/images/home/1.png" className="mb-2" alt="" />
              </div>
              <div>
                {"You can install pip version with this command for Windows or MacOS:"}
              </div>
              <img src="/images/home/2.png" className="mb-2" alt="" />
              <div>
                or the following command for Linux
              </div>
              <img src="/images/home/3.png" className="mb-2" alt="" />
            </li>
            <li>
              <div>Install the DQO app</div>
              <img src="/images/home/4.png" className="mb-2" alt="" />
            </li>
            <li>
              <div>Start the application</div>
              <img src="/images/home/5.png" className="mb-2" alt="" />
            </li>
            <li>
              <div>Log into dqo cloud</div>
              <img src="/images/home/6.png" className="mb-2" alt="" />
            </li>
          </ol>
    
          <div className="mt-12 grid grid-cols-2 gap-8">
            <div>
              <div className="text-xl mb-5">
                Contact us for more information
              </div>
               <a href="https://dqo.ai/contact-us"><Button label="Contact Us" color="success" variant="contained" className="bg-green-600" /></a>
            </div>
      
            <div>
              <div className="text-xl mb-3">
                Check our progress on GitHub
              </div>
              <a href="https://github.com/dqoai/dqo"><img src="images/home/8.png" alt="" className="w-40" /></a>
            </div>
      
            <div>
              <div className="text-xl mb-5">
                Download our best practices for effective data quality improvement
              </div>
              <a href="https://dqo.ai/dqo_ebook_a_step-by-step_guide_to_improve_data_quality-2/"><img src="images/home/7.png" alt="" className="w-40" /></a>
            </div>
      
            <div>
              <div className="text-xl mb-3">
                Back to home page
              </div>
              <a href="https://dqo.ai/"><Logo className="w-30" /></a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;