import { useState, useEffect } from 'react';

function usePopup(ref: any) {
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    const handleClickOutside = (event: any) => {
      if (ref.current && !ref.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [ref]);

  const openMenu = () => setIsOpen(true);
  const closeMenu = () => setIsOpen(false);

  const toggleMenu = () => setIsOpen((prevIsOpen) => !prevIsOpen);

  return {
    isOpen,
    closeMenu,
    openMenu,
    toggleMenu
  };
}

export default usePopup;
