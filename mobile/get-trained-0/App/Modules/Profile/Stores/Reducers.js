/**
 * Reducers specify how the application's state changes in response to actions sent to the store.
 *
 * @see https://redux.js.org/basics/reducers
 */

import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { ProfileTypes } from './Actions'

export const fetchMetadataLoading = (state) =>
  state.merge({
    fetchingMetadata: true,
  })

export const fetchMetadataSuccess = (state, { metadata }) =>
  state.merge({
    fetchingMetadata: false,
    failedRetrievingMetadata: false,
    metadata,
  })

export const fetchMetadataFailure = (state) =>
  state.merge({
    fetchingMetadata: false,
    failedRetrievingMetadata: true,
  })

export const uploadAvatarLoading = (state) =>
  state.merge({
    uploadingAvatar: true,
  })

export const uploadAvatarSuccess = (state) =>
  state.merge({
    uploadingAvatar: false,
  })

export const uploadAvatarFailure = (state) =>
  state.merge({
    uploadingAvatar: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const profile = createReducer(INITIAL_STATE, {
  [ProfileTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [ProfileTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [ProfileTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [ProfileTypes.UPLOAD_AVATAR_LOADING]: uploadAvatarLoading,
  [ProfileTypes.UPLOAD_AVATAR_SUCCESS]: uploadAvatarSuccess,
  [ProfileTypes.UPLOAD_AVATAR_FAILURE]: uploadAvatarFailure,
})
