/**
 * Reducers specify how the application's state changes in response to actions sent to the store.
 *
 * @see https://redux.js.org/basics/reducers
 */

import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { TraineeProfileTypes } from './Actions'

export const fetchMetadataLoading = (state) =>
  state.merge({
    fetchingMetadata: true,
  })

export const fetchMetadataSuccess = (state, { metadata }) =>
  state.merge({
    fetchingMetadata: false,
    failedRetrievingMetadata: false,
    metadata,
  })

export const fetchMetadataFailure = (state) =>
  state.merge({
    fetchingMetadata: false,
    failedRetrievingMetadata: true,
  })

export const fetchLightProfileLoading = (state) =>
  state.merge({
    fetchingLightProfile: true,
  })

export const fetchLightProfileSuccess = (state, { lightProfile }) =>
  state.merge({
    fetchingLightProfile: false,
    lightProfile,
  })

export const fetchLightProfileFailure = (state) =>
  state.merge({
    fetchingLightProfile: false,
    lightProfile: undefined,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const profile = createReducer(INITIAL_STATE, {
  [TraineeProfileTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [TraineeProfileTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [TraineeProfileTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [TraineeProfileTypes.FETCH_LIGHT_PROFILE_LOADING]: fetchLightProfileLoading,
  [TraineeProfileTypes.FETCH_LIGHT_PROFILE_SUCCESS]: fetchLightProfileSuccess,
  [TraineeProfileTypes.FETCH_LIGHT_PROFILE_FAILURE]: fetchLightProfileFailure,
})
