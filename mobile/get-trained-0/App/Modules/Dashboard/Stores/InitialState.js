import { Map } from 'immutable'

/**
 * The initial values for the Dashboard redux states.
 */
export const WORKSPACE_INITIAL_STATE = Map({
  fetchingMetadata: false,
  metadata: {},
  fetchingSignOut: false,
})
