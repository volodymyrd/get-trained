/**
 * Reducers specify how the application's state changes in response to actions sent to the store.
 *
 * @see https://redux.js.org/basics/reducers
 */

import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { AuthTypes } from './Actions'

export const toggleAuthType = (state, { authType }) =>
  state.merge({
    authType,
  })

export const fetchAuthenticationLoading = (state) =>
  state.merge({
    fetching: { authenticating: true },
  })

export const fetchAuthenticationSuccess = (state) =>
  state.merge({
    fetching: { authenticating: false },
    authenticated: true,
  })

export const fetchAuthenticationFailure = (state) =>
  state.merge({
    fetching: { authenticating: false },
    authenticated: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const auth = createReducer(INITIAL_STATE, {
  [AuthTypes.TOGGLE_AUTH_TYPE]: toggleAuthType,

  [AuthTypes.FETCH_AUTHENTICATION_LOADING]: fetchAuthenticationLoading,
  [AuthTypes.FETCH_AUTHENTICATION_SUCCESS]: fetchAuthenticationSuccess,
  [AuthTypes.FETCH_AUTHENTICATION_FAILURE]: fetchAuthenticationFailure,
})
