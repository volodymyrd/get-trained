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

export const fetchLightProfileLoading = (state) =>
  state.merge({
    fetchingLightProfile: true,
  })

export const fetchLightProfileSuccess = (state, { lightProfile }) =>
  state.merge({
    fetchingLightProfile: false,
    lightProfile,
  })

export const fetchLightProfileFailure = (state) =>
  state.merge({
    fetchingLightProfile: false,
    lightProfile: undefined,
  })

export const uploadAvatarLoading = (state) =>
  state.merge({
    uploadingAvatar: true,
  })

export const uploadAvatarSuccess = (state, { avatarUrl }) =>
  state.mergeDeep({
    uploadingAvatar: false,
    lightProfile: { avatarUrl },
  })

export const uploadAvatarFailure = (state) =>
  state.merge({
    uploadingAvatar: false,
  })

export const deleteAvatarLoading = (state) =>
  state.merge({
    deletingAvatar: true,
  })

export const deleteAvatarSuccess = (state) =>
  state.mergeDeep({
    deletingAvatar: false,
    lightProfile: { avatarUrl: undefined },
  })

export const deleteAvatarFailure = (state) =>
  state.merge({
    deletingAvatar: false,
  })

/**
 * @see https://github.com/infinitered/reduxsauce#createreducer
 */
export const profile = createReducer(INITIAL_STATE, {
  [ProfileTypes.FETCH_METADATA_LOADING]: fetchMetadataLoading,
  [ProfileTypes.FETCH_METADATA_SUCCESS]: fetchMetadataSuccess,
  [ProfileTypes.FETCH_METADATA_FAILURE]: fetchMetadataFailure,

  [ProfileTypes.FETCH_LIGHT_PROFILE_LOADING]: fetchLightProfileLoading,
  [ProfileTypes.FETCH_LIGHT_PROFILE_SUCCESS]: fetchLightProfileSuccess,
  [ProfileTypes.FETCH_LIGHT_PROFILE_FAILURE]: fetchLightProfileFailure,

  [ProfileTypes.UPLOAD_AVATAR_LOADING]: uploadAvatarLoading,
  [ProfileTypes.UPLOAD_AVATAR_SUCCESS]: uploadAvatarSuccess,
  [ProfileTypes.UPLOAD_AVATAR_FAILURE]: uploadAvatarFailure,

  [ProfileTypes.DELETE_AVATAR_LOADING]: deleteAvatarLoading,
  [ProfileTypes.DELETE_AVATAR_SUCCESS]: deleteAvatarSuccess,
  [ProfileTypes.DELETE_AVATAR_FAILURE]: deleteAvatarFailure,
})
