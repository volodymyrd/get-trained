import { WORKSPACE_INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { WorkspaceTypes } from './Actions'

export const fetchSignOutLoading = (state) =>
  state.merge({
    fetching: { signOut: true },
  })

export const fetchSignOutSuccess = (state) =>
  state.merge({
    fetching: { signOut: false },
  })

export const fetchSignOutFailure = (state) =>
  state.merge({
    fetching: { signOut: false },
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const workspace = createReducer(WORKSPACE_INITIAL_STATE, {
  [WorkspaceTypes.FETCH_SIGN_OUT_LOADING]: fetchSignOutLoading,
  [WorkspaceTypes.FETCH_SIGN_OUT_SUCCESS]: fetchSignOutSuccess,
  [WorkspaceTypes.FETCH_SIGN_OUT_FAILURE]: fetchSignOutFailure,
})
