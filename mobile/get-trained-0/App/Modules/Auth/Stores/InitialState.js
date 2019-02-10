import { Map } from 'immutable'

/**
 * The initial values for the Auth redux state.
 */
export const AuthStep = {
  SIGN_IN: 'signIn',
  SIGN_UP: 'signUp',
  RESTORE_PASSWORD: 'restorePassword',
}

export const INITIAL_STATE = Map({
  fetchingMetadata: false,
  metadata: {},
  failedRetrievingMetadata: false,
  authStep: 'signIn', // AuthStep
  fetchingAuthenticating: false,
  authenticated: false,
  fetchingSignUp: false,
  fetchingRestorePassword: false,
})
