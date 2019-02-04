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

export const toggleAuthStep = (state, { authStep }) =>
  state.merge({
    authStep,
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

export const fetchSignUpLoading = (state) =>
  state.merge({
    fetchingSignUp: true,
  })

export const fetchSignUpSuccess = (state) =>
  state.merge({
    fetchingSignUp: false,
  })

export const fetchSignUpFailure = (state) =>
  state.merge({
    fetchingSignUp: false,
  })

export const fetchRestorePasswordLoading = (state) =>
  state.merge({
    fetchingRestorePassword: true,
  })

export const fetchRestorePasswordSuccess = (state) =>
  state.merge({
    fetchingRestorePassword: false,
  })

export const fetchRestorePasswordFailure = (state) =>
  state.merge({
    fetchingRestorePassword: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const auth = createReducer(INITIAL_STATE, {
  [AuthTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [AuthTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [AuthTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [AuthTypes.TOGGLE_AUTH_STEP]: toggleAuthStep,

  [AuthTypes.FETCH_AUTHENTICATION_LOADING]: fetchAuthenticationLoading,
  [AuthTypes.FETCH_AUTHENTICATION_SUCCESS]: fetchAuthenticationSuccess,
  [AuthTypes.FETCH_AUTHENTICATION_FAILURE]: fetchAuthenticationFailure,

  [AuthTypes.FETCH_SIGN_UP_LOADING]: fetchSignUpLoading,
  [AuthTypes.FETCH_SIGN_UP_SUCCESS]: fetchSignUpSuccess,
  [AuthTypes.FETCH_SIGN_UP_FAILURE]: fetchSignUpFailure,

  [AuthTypes.FETCH_RESTORE_PASSWORD_LOADING]: fetchRestorePasswordLoading,
  [AuthTypes.FETCH_RESTORE_PASSWORD_SUCCESS]: fetchRestorePasswordSuccess,
  [AuthTypes.FETCH_RESTORE_PASSWORD_FAILURE]: fetchRestorePasswordFailure,
})
