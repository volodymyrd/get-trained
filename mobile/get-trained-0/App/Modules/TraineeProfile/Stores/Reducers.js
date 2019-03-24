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

export const fetchGendersLoading = (state) =>
  state.merge({
    fetchingGenders: true,
  })

export const fetchGendersSuccess = (state, { genders }) =>
  state.merge({
    fetchingGenders: false,
    genders,
  })

export const fetchGendersFailure = (state) =>
  state.merge({
    fetchingGenders: false,
    traineeProfile: undefined,
  })

export const fetchTraineeProfileLoading = (state) =>
  state.merge({
    fetchingTraineeProfile: true,
  })

export const fetchTraineeProfileSuccess = (state, { traineeProfile }) =>
  state.merge({
    fetchingTraineeProfile: false,
    traineeProfile,
  })

export const fetchTraineeProfileFailure = (state) =>
  state.merge({
    fetchingTraineeProfile: false,
    traineeProfile: undefined,
  })

export const fetchUpdateTraineeProfileLoading = (state) =>
  state.merge({
    fetchingUpdateTraineeProfile: true,
  })

export const fetchUpdateTraineeProfileSuccess = (state, { traineeProfile }) =>
  state.merge({
    fetchingUpdateTraineeProfile: false,
    traineeProfile,
  })

export const fetchUpdateTraineeProfileFailure = (state) =>
  state.merge({
    fetchingUpdateTraineeProfile: false,
  })

export const fetchTraineeFitnessProfilesLoading = (state) =>
  state.merge({
    fetchingTraineeFitnessProfiles: true,
  })

export const fetchTraineeFitnessProfilesSuccess = (state, { traineeFitnessProfiles }) =>
  state.merge({
    fetchingTraineeFitnessProfiles: false,
    traineeFitnessProfiles,
  })

export const fetchTraineeFitnessProfilesFailure = (state) =>
  state.merge({
    fetchingTraineeFitnessProfiles: false,
  })

export const newTraineeFitnessProfile = (state) =>
  state.merge({
    traineeFitnessProfile: {},
  })

export const fetchUpdateTraineeFitnessProfileLoading = (state) =>
  state.merge({
    fetchingUpdateTraineeFitnessProfile: true,
  })

export const fetchUpdateTraineeFitnessProfileSuccess = (state, { traineeFitnessProfile }) =>
  state.merge({
    fetchingUpdateTraineeFitnessProfile: false,
    traineeFitnessProfile,
  })

export const fetchUpdateTraineeFitnessProfileFailure = (state) =>
  state.merge({
    fetchingUpdateTraineeFitnessProfile: false,
  })

export const fetchTraineeFitnessProfileLoading = (state, { traineeProfileId }) =>
  state.merge({
    fetchingTraineeFitnessProfile: traineeProfileId,
  })

export const fetchTraineeFitnessProfileSuccess = (state, { traineeFitnessProfile }) =>
  state.merge({
    fetchingTraineeFitnessProfile: -1,
    traineeFitnessProfile,
  })

export const fetchTraineeFitnessProfileFailure = (state) =>
  state.merge({
    fetchingTraineeFitnessProfile: -1,
  })

export const fetchDeleteTraineeFitnessProfileLoading = (state, { traineeProfileId }) =>
  state.merge({
    fetchingDeleteTraineeFitnessProfile: traineeProfileId,
  })

export const fetchDeleteTraineeFitnessProfileSuccess = (state) =>
  state.merge({
    fetchingDeleteTraineeFitnessProfile: -1,
  })

export const fetchDeleteTraineeFitnessProfileFailure = (state) =>
  state.merge({
    fetchingDeleteTraineeFitnessProfile: -1,
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

  [TraineeProfileTypes.FETCH_GENDERS_LOADING]: fetchGendersLoading,
  [TraineeProfileTypes.FETCH_GENDERS_SUCCESS]: fetchGendersSuccess,
  [TraineeProfileTypes.FETCH_GENDERS_FAILURE]: fetchGendersFailure,

  [TraineeProfileTypes.FETCH_TRAINEE_PROFILE_LOADING]: fetchTraineeProfileLoading,
  [TraineeProfileTypes.FETCH_TRAINEE_PROFILE_SUCCESS]: fetchTraineeProfileSuccess,
  [TraineeProfileTypes.FETCH_TRAINEE_PROFILE_FAILURE]: fetchTraineeProfileFailure,

  [TraineeProfileTypes.FETCH_UPDATE_TRAINEE_PROFILE_LOADING]: fetchUpdateTraineeProfileLoading,
  [TraineeProfileTypes.FETCH_UPDATE_TRAINEE_PROFILE_SUCCESS]: fetchUpdateTraineeProfileSuccess,
  [TraineeProfileTypes.FETCH_UPDATE_TRAINEE_PROFILE_FAILURE]: fetchUpdateTraineeProfileFailure,

  [TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILES_LOADING]: fetchTraineeFitnessProfilesLoading,
  [TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILES_SUCCESS]: fetchTraineeFitnessProfilesSuccess,
  [TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILES_FAILURE]: fetchTraineeFitnessProfilesFailure,

  [TraineeProfileTypes.NEW_TRAINEE_FITNESS_PROFILE]: newTraineeFitnessProfile,
  [TraineeProfileTypes.FETCH_UPDATE_TRAINEE_FITNESS_PROFILE_LOADING]: fetchUpdateTraineeFitnessProfileLoading,
  [TraineeProfileTypes.FETCH_UPDATE_TRAINEE_FITNESS_PROFILE_SUCCESS]: fetchUpdateTraineeFitnessProfileSuccess,
  [TraineeProfileTypes.FETCH_UPDATE_TRAINEE_FITNESS_PROFILE_FAILURE]: fetchUpdateTraineeFitnessProfileFailure,

  [TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILE_LOADING]: fetchTraineeFitnessProfileLoading,
  [TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILE_SUCCESS]: fetchTraineeFitnessProfileSuccess,
  [TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILE_FAILURE]: fetchTraineeFitnessProfileFailure,

  [TraineeProfileTypes.FETCH_DELETE_TRAINEE_FITNESS_PROFILE_LOADING]: fetchDeleteTraineeFitnessProfileLoading,
  [TraineeProfileTypes.FETCH_DELETE_TRAINEE_FITNESS_PROFILE_SUCCESS]: fetchDeleteTraineeFitnessProfileSuccess,
  [TraineeProfileTypes.FETCH_DELETE_TRAINEE_FITNESS_PROFILE_FAILURE]: fetchDeleteTraineeFitnessProfileFailure,
})
