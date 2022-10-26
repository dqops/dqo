import React from 'react';

import Avatar from 'react-avatar';

const Header = () => {
  return (
    <div className="fixed top-0 left-70 right-0 min-h-16 bg-white shadow-header flex items-center justify-end z-10 px-4">
      <Avatar name="John Doe" className="!w-10 !h-10" round />
    </div>
  );
};

export default Header;
