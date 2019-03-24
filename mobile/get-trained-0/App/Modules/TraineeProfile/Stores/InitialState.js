import { Map } from 'immutable'

/**
 * The initial values for the Auth redux state.
 */
export const INITIAL_STATE = Map({
  fetchingMetadata: false,
  metadata: {},
  failedRetrievingMetadata: false,
  fetchingLightProfile: false,
  lightProfile: undefined,
  fetchingGenders: false,
  fetchingTraineeProfile: false,
  fetchingUpdateTraineeProfile: false,
  fetchingTraineeFitnessProfiles: false,
  fetchingTraineeFitnessProfile: -1,
  fetchingUpdateTraineeFitnessProfile: false,
})
