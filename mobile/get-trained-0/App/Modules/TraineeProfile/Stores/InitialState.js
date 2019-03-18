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
  fetchingTraineeProfile: false,
  fetchingUpdateTraineeProfile: false,
  fetchingTraineeFitnessProfiles: false,
  fetchingTraineeFitnessProfile: false,
})
