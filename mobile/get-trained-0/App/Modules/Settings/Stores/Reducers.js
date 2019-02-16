/**
 * Reducers specify how the application's state changes in response to actions sent to the store.
 *
 * @see https://redux.js.org/basics/reducers
 */

import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { SettingsTypes } from './Actions'

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

export const fetchChangePasswordLoading = (state) =>
  state.merge({
    fetchingChangePassword: true,
    passwordChanged: false,
  })

export const fetchChangePasswordSuccess = (state) =>
  state.merge({
    fetchingChangePassword: false,
    passwordChanged: true,
  })

export const fetchChangePasswordFailure = (state) =>
  state.merge({
    fetchingChangePassword: false,
    passwordChanged: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const settings = createReducer(INITIAL_STATE, {
  [SettingsTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [SettingsTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [SettingsTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [SettingsTypes.FETCH_CHANGE_PASSWORD_LOADING]: fetchChangePasswordLoading,
  [SettingsTypes.FETCH_CHANGE_PASSWORD_SUCCESS]: fetchChangePasswordSuccess,
  [SettingsTypes.FETCH_CHANGE_PASSWORD_FAILURE]: fetchChangePasswordFailure,
})
