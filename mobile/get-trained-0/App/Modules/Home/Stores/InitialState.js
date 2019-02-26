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
  fetchingIsTrainer: false,
  isTrainer: false,
  fetchingConnections: false,
  fetchingTraineeRequest: false,
})
