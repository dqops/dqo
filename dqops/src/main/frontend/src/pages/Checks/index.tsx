import React from 'react';

import { useHistory } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";
import { useDecodedParams } from '../../utils';

const ChecksPage = () => {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const history = useHistory();

  if (!Object.values(CheckTypes).includes(checkTypes)) {
    history.push("/");

    return null;
  }

  return (
    <>

    </>
  );
};

export default ChecksPage;
