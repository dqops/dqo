import { useEffect, useState } from 'react';

import { createPortal } from 'react-dom';

interface IPortalProps {
  children: any;
}

const Portal = ({ children }: IPortalProps) => {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);

    return () => setMounted(false);
  }, []);

  return mounted ? createPortal(children, document.querySelector('#modal-root') as any) : null;
};

export default Portal;
