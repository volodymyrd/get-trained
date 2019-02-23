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

  [HomeTypes.FETCH_CONNECTIONS_LOADING]: fetchConnectionsLoading,
  [HomeTypes.FETCH_CONNECTIONS_SUCCESS]: fetchConnectionsSuccess,
  [HomeTypes.FETCH_CONNECTIONS_FAILURE]: fetchConnectionsFailure,
})
