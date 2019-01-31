/**
 * Reducers specify how the application's state changes in response to actions sent to the store.
 *
 * @see https://redux.js.org/basics/reducers
 */

import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { AuthTypes } from './Actions'

export const fetchMetadataLoading = (state) =>
  state.merge({
    fetchingMetadata: true,
  })

export const fetchMetadataSuccess = (state, { metadata }) =>
  state.merge({
    fetchingMetadata: false,
    metadata,
  })

export const fetchMetadataFailure = (state) =>
  state.merge({
    fetchingMetadata: false,
  })

export const toggleAuthType = (state, { authType }) =>
  state.merge({
    authType,
  })

export const fetchAuthenticationLoading = (state) =>
  state.merge({
    fetchingAuthenticating: true,
  })

export const fetchAuthenticationSuccess = (state) =>
  state.merge({
    fetchingAuthenticating: false,
    authenticated: true,
  })

export const fetchAuthenticationFailure = (state) =>
  state.merge({
    fetchingAuthenticating: false,
    authenticated: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const auth = createReducer(INITIAL_STATE, {
  [AuthTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [AuthTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [AuthTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [AuthTypes.TOGGLE_AUTH_TYPE]: toggleAuthType,

  [AuthTypes.FETCH_AUTHENTICATION_LOADING]: fetchAuthenticationLoading,
  [AuthTypes.FETCH_AUTHENTICATION_SUCCESS]: fetchAuthenticationSuccess,
  [AuthTypes.FETCH_AUTHENTICATION_FAILURE]: fetchAuthenticationFailure,
})
