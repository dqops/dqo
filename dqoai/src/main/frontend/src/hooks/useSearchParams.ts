import { useLocation } from "react-router-dom";
import { useMemo } from "react";

const useSearchParams = () => {
  const { search } = useLocation();

  return useMemo(() => new URLSearchParams(search), [search]);
}

export default useSearchParams;