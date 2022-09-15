import { useDispatch } from 'react-redux';
import { Dispatch } from 'redux';

type ActionDispatch = (dispatch: Dispatch) => Promise<any>;

export const useActionDispatch = () => {
  const dispatch = useDispatch();

  return (dispatchAction: object | ActionDispatch) => {
    if (typeof dispatchAction === 'object') return dispatch(dispatchAction as any);

    return dispatchAction(dispatch);
  };
};
