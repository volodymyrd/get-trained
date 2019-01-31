import { Map } from 'immutable'

/**
 * The initial values for the Main redux state.
 */
export const INITIAL_STATE = Map({
  locale: null,
  langCode: null,

  accessIsLoading: false,
  isAuthenticated: false,
})
