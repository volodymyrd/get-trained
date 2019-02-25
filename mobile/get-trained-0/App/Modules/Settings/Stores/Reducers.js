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

export const fetchAddTrainerLoading = (state) =>
  state.merge({
    fetchingAddTrainer: true,
  })

export const fetchAddTrainerSuccess = (state, { isTrainer }) =>
  state.merge({
    fetchingAddTrainer: false,
    isTrainer,
  })

export const fetchAddTrainerFailure = (state) =>
  state.merge({
    fetchingAddTrainer: false,
  })

export const fetchRemoveTrainerLoading = (state) =>
  state.merge({
    fetchingRemoveTrainer: true,
  })

export const fetchRemoveTrainerSuccess = (state, { isTrainer }) =>
  state.merge({
    fetchingRemoveTrainer: false,
    isTrainer,
  })

export const fetchRemoveTrainerFailure = (state) =>
  state.merge({
    fetchingRemoveTrainer: false,
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

  [SettingsTypes.FETCH_IS_TRAINER_LOADING]: fetchIsTrainerLoading,
  [SettingsTypes.FETCH_IS_TRAINER_SUCCESS]: fetchIsTrainerSuccess,
  [SettingsTypes.FETCH_IS_TRAINER_FAILURE]: fetchIsTrainerFailure,

  [SettingsTypes.FETCH_ADD_TRAINER_LOADING]: fetchAddTrainerLoading,
  [SettingsTypes.FETCH_ADD_TRAINER_SUCCESS]: fetchAddTrainerSuccess,
  [SettingsTypes.FETCH_ADD_TRAINER_FAILURE]: fetchAddTrainerFailure,

  [SettingsTypes.FETCH_REMOVE_TRAINER_LOADING]: fetchRemoveTrainerLoading,
  [SettingsTypes.FETCH_REMOVE_TRAINER_SUCCESS]: fetchRemoveTrainerSuccess,
  [SettingsTypes.FETCH_REMOVE_TRAINER_FAILURE]: fetchRemoveTrainerFailure,
})
