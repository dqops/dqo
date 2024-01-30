import React from 'react';

import ConnectionLayout from "../../components/ConnectionLayout";
import { useHistory, useParams } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";

const ChecksPage = () => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
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
