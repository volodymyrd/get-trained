import { WORKSPACE_INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { WorkspaceTypes } from './Actions'

export const fetchMetadataLoading = (state) =>
  state.merge({
    fetchingMetadata: true,
  })

export const fetchMetadataSuccess = (state, { metadata }) =>
  state.merge({
    fetchingMetadata: false,
    metadata,
  })

export const fetchMetadataFailure = (state) =>
  state.merge({
    fetchingMetadata: false,
    failedRetrievingMetadata: true,
  })

export const fetchSignOutLoading = (state) =>
  state.merge({
    fetchingSignOut: true,
  })

export const fetchSignOutSuccess = (state) =>
  state.merge({
    fetchingSignOut: false,
    // TODO remove this in prod
    metadata: [],
  })

export const fetchSignOutFailure = (state) =>
  state.merge({
    fetchingSignOut: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const workspace = createReducer(WORKSPACE_INITIAL_STATE, {
  [WorkspaceTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [WorkspaceTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [WorkspaceTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [WorkspaceTypes.FETCH_SIGN_OUT_LOADING]: fetchSignOutLoading,
  [WorkspaceTypes.FETCH_SIGN_OUT_SUCCESS]: fetchSignOutSuccess,
  [WorkspaceTypes.FETCH_SIGN_OUT_FAILURE]: fetchSignOutFailure,
})
