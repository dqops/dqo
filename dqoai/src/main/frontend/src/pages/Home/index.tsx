import React from 'react';
import Header from '../../components/Header';
import Button from '../../components/Button';
import Logo from '../../components/Logo';
import SvgIcon from '../../components/SvgIcon';
import { useHistory } from 'react-router-dom';

const HomePage = () => {
  const history = useHistory();
  
  return (
    <div className="flex min-h-screen overflow-hidden">
      <Header sidebarWidth={0} isHome />
      <div className="flex w-full">
        <div className="w-10 bg-gray-600 h-full mt-16 flex flex-col items-center">
          <div
            className="w-full py-3 bg-gray-250 flex items-center justify-center mt-1.5 mb-1.5 cursor-pointer"
            onClick={() => history.push('/home')}
          >
            <SvgIcon name="house" className="w-6 text-white" />
          </div>
          <div
            className="w-full py-3 bg-gray-250 flex items-center justify-center cursor-pointer"
            onClick={() => history.push('/dashboard')}
          >
            <img src="/images/home/dashboard.png" className="w-6" alt="" />
          </div>
        </div>
        <div className="p-12 mt-16 flex-1">
          <h1 className="text-3xl italic mb-4">Welcome to your DQO.ai account</h1>
          <div className="mb-3">
            In the menu on the left you can find some propositions of checks results data visualizations.
          </div>
          <div className="mb-3">
            If you are a new user there is the instruction on how to <b>install and run</b> the DQO.ai application:
          </div>
          <ol className="list-decimal pl-4">
            <li>
              <div className="flex space-x-1 items-center">
                <span>Make sure you have pip installed, use the</span>
                <img src="/images/home/1.png" className="mb-2" alt="" />
                <span>command</span>
              </div>
              <div>
                {`If you don't have it use on Windows and MacOS system:`}
              </div>
              <img src="/images/home/2.png" className="mb-2" alt="" />
              <div>
                or
              </div>
              <img src="/images/home/3.png" className="mb-2" alt="" />
              <div>on Linux</div>
            </li>
            <li>
              <div>Install the DQO app</div>
              <img src="/images/home/4.png" className="mb-2" alt="" />
            </li>
            <li>
              <div>Now you can run the application</div>
              <img src="/images/home/5.png" className="mb-2" alt="" />
            </li>
            <li>
              <div>Log into cloud dqo ai</div>
              <img src="/images/home/6.png" className="mb-2" alt="" />
            </li>
          </ol>
    
          <div className="mt-12 grid grid-cols-2 gap-8">
            <div>
              <div className="text-xl mb-5">
                Please contact us for more information
              </div>
              <Button label="Contact Us" color="success" variant="contained" className="bg-green-600" />
            </div>
      
            <div>
              <div className="text-xl mb-3">
                Or check out the progress of our work on
              </div>
              <img src="images/home/8.png" alt="" className="w-40" />
            </div>
      
            <div>
              <div className="text-xl mb-5">
                Download and read the guide
              </div>
              <img src="images/home/7.png" alt="" className="w-40" />
            </div>
      
            <div>
              <div className="text-xl mb-3">
                Back to main page
              </div>
              <Logo className="w-30" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;