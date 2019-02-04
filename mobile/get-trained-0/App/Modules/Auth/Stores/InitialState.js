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
  authStep: 'signIn', // AuthStep
  fetchingAuthenticating: false,
  metadata: {},
  authenticated: false,
  fetchingSignUp: false,
  fetchingRestorePassword: false,
})
