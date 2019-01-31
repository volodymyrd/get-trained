import { Map } from 'immutable'

/**
 * The initial values for the Auth redux state.
 */
export const AuthType = {
  SIGN_IN: 'signIn',
  SIGN_UP: 'signUp',
  RESTORE_PASSWORD: 'restorePassword',
}

export const INITIAL_STATE = Map({
  fetchingMetadata: false,
  authType: 'signIn', // AuthType
  fetchingAuthenticating: false,
  metadata: {},
  authenticated: false,
})
