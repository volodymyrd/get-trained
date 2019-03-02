/**
 * Reducers specify how the application's state changes in response to actions sent to the store.
 *
 * @see https://redux.js.org/basics/reducers
 */

import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { HomeTypes } from './Actions'

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

export const fetchIsTrainerLoading = (state) =>
  state.merge({
    fetchingIsTrainer: true,
  })

export const fetchIsTrainerSuccess = (state, { isTrainer }) =>
  state.merge({
    fetchingIsTrainer: false,
    isTrainer,
  })

export const fetchIsTrainerFailure = (state) =>
  state.merge({
    fetchingIsTrainer: false,
  })

export const fetchConnectionsLoading = (state) =>
  state.merge({
    fetchingConnections: true,
  })

export const fetchConnectionsSuccess = (state, { connections }) =>
  state.merge({
    fetchingConnections: false,
    connections,
  })

export const fetchConnectionsFailure = (state) =>
  state.merge({
    fetchingConnections: false,
  })

export const fetchTraineeRequestLoading = (state) =>
  state.merge({
    fetchingTraineeRequest: true,
  })

export const fetchTraineeRequestSuccess = (state, { connections }) =>
  state.merge({
    fetchingTraineeRequest: false,
    connections,
  })

export const fetchTraineeRequestFailure = (state) =>
  state.merge({
    fetchingTraineeRequest: false,
  })

export const fetchDeleteConnectionLoading = (state) =>
  state.merge({
    fetchingDeleteConnection: true,
  })

export const fetchDeleteConnectionSuccess = (state) =>
  state.merge({
    fetchingDeleteConnection: false,
  })

export const fetchDeleteConnectionFailure = (state) =>
  state.merge({
    fetchingDeleteConnection: false,
  })

export const fetchAcceptConnectionLoading = (state) =>
  state.merge({
    fetchingAcceptConnection: true,
  })

export const fetchAcceptConnectionSuccess = (state) =>
  state.merge({
    fetchingAcceptConnection: false,
  })

export const fetchAcceptConnectionFailure = (state) =>
  state.merge({
    fetchingAcceptConnection: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const home = createReducer(INITIAL_STATE, {
  [HomeTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [HomeTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [HomeTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [HomeTypes.FETCH_LIGHT_PROFILE_LOADING]: fetchLightProfileLoading,
  [HomeTypes.FETCH_LIGHT_PROFILE_SUCCESS]: fetchLightProfileSuccess,
  [HomeTypes.FETCH_LIGHT_PROFILE_FAILURE]: fetchLightProfileFailure,

  [HomeTypes.FETCH_IS_TRAINER_LOADING]: fetchIsTrainerLoading,
  [HomeTypes.FETCH_IS_TRAINER_SUCCESS]: fetchIsTrainerSuccess,
  [HomeTypes.FETCH_IS_TRAINER_FAILURE]: fetchIsTrainerFailure,

  [HomeTypes.FETCH_CONNECTIONS_LOADING]: fetchConnectionsLoading,
  [HomeTypes.FETCH_CONNECTIONS_SUCCESS]: fetchConnectionsSuccess,
  [HomeTypes.FETCH_CONNECTIONS_FAILURE]: fetchConnectionsFailure,

  [HomeTypes.FETCH_TRAINEE_REQUEST_LOADING]: fetchTraineeRequestLoading,
  [HomeTypes.FETCH_TRAINEE_REQUEST_SUCCESS]: fetchTraineeRequestSuccess,
  [HomeTypes.FETCH_TRAINEE_REQUEST_FAILURE]: fetchTraineeRequestFailure,

  [HomeTypes.FETCH_DELETE_CONNECTION_LOADING]: fetchDeleteConnectionLoading,
  [HomeTypes.FETCH_DELETE_CONNECTION_SUCCESS]: fetchDeleteConnectionSuccess,
  [HomeTypes.FETCH_DELETE_CONNECTION_FAILURE]: fetchDeleteConnectionFailure,

  [HomeTypes.FETCH_ACCEPT_CONNECTION_LOADING]: fetchAcceptConnectionLoading,
  [HomeTypes.FETCH_ACCEPT_CONNECTION_SUCCESS]: fetchAcceptConnectionSuccess,
  [HomeTypes.FETCH_ACCEPT_CONNECTION_FAILURE]: fetchAcceptConnectionFailure,
})
