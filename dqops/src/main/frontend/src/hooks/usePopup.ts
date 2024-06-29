import { useEffect, useState } from 'react';

function usePopup(ref: any) {
  const [isOpen, setIsOpen] = useState(false);

  const openMenu = () => setIsOpen(true);
  const closeMenu = () => setIsOpen(false);

  const toggleMenu = () => setIsOpen((prevIsOpen) => !prevIsOpen);

  useEffect(() => {
    const handleClickOutside = (event: any) => {
      if (ref.current && !ref.current.contains(event.target)) {
        closeMenu();
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [ref]);

  return {
    isOpen,
    closeMenu,
    openMenu,
    toggleMenu
  };
}

export default usePopup;
