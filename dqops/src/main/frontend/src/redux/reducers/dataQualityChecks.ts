import { QualityCategoryModel } from "../../api";
import { DATA_QUALITY_CHECKS_ACTION } from "../types/dataQualityChecks";

export interface IdataQualityChecks{
    arrayOfChecks ?: Array<QualityCategoryModel>
    loading: boolean
    error: any
    dataQualityChecksState: Record<string, boolean>
}

const initialState: IdataQualityChecks= {
    loading: false,
    error: null,
    dataQualityChecksState: {}
  };

  const dataQualityChecksReducer = (state = initialState, action: any) => {
    switch (action.type) {
        case DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE:
          return {
            ...state,
            loading: true
          };
        case DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_SUCCESS:
          return {
            ...state,
            loading: false,
            arrayOfChecks: action.data,
            error: null
          };
        case DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_ERROR:
          return {
            ...state,
            loading: false,
            error: action.error
          };
        case DATA_QUALITY_CHECKS_ACTION.TOGGLE_DATA_QUALITY_CHECKS_FOLDER:
          return {
            ...state,
            DATA_QUALITY_CHECKSState: {
              ...state.dataQualityChecksState,
              [action.key]: !state.dataQualityChecksState[action.key]
            }
          };
        case DATA_QUALITY_CHECKS_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE:
          return {
            ...state,
            arrayOfChecks: action.data,
          }
        default:
          return state;
      }
    };


  

  export default dataQualityChecksReducer