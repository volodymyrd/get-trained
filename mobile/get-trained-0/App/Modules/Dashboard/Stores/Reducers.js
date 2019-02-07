import { WORKSPACE_INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { WorkspaceTypes } from './Actions'

export const fetchSignOutLoading = (state) =>
  state.merge({
    fetchingSignOut: true,
  })

export const fetchSignOutSuccess = (state) =>
  state.merge({
    fetchingSignOut: false,
  })

export const fetchSignOutFailure = (state) =>
  state.merge({
    fetchingSignOut: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const workspace = createReducer(WORKSPACE_INITIAL_STATE, {
  [WorkspaceTypes.FETCH_SIGN_OUT_LOADING]: fetchSignOutLoading,
  [WorkspaceTypes.FETCH_SIGN_OUT_SUCCESS]: fetchSignOutSuccess,
  [WorkspaceTypes.FETCH_SIGN_OUT_FAILURE]: fetchSignOutFailure,
})
