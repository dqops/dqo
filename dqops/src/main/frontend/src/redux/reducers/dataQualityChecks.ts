import { CheckContainerModel, CheckSpecFolderBasicModel, QualityCategoryModel } from "../../api";
import { DATA_QUALITY_CHECKS_ACTION } from "../types/dataQualityChecks.types";

export interface IdataQualityChecks{
    checksFolderTree ?: CheckSpecFolderBasicModel
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
            checksFolderTree: action.data,
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
            dataQualityChecksState: {
              ...state.dataQualityChecksState,
              [action.key]: !state.dataQualityChecksState[action.key]
            }
          };
        case DATA_QUALITY_CHECKS_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE:
          return {
            ...state,
            checksFolderTree: action.data,
          }
        default:
          return state;
      }
    };


  

  export default dataQualityChecksReducer