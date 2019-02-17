import { createActions } from 'reduxsauce'

/**
 * We use reduxsauce's `createActions()` helper to easily create redux actions.
 *
 * @see https://github.com/infinitered/reduxsauce#createactions
 */
const { Types, Creators } = createActions(
  {
    fetchMetadata: ['langCode'],
    fetchMetadataLoading: null,
    fetchMetadataSuccess: ['metadata'],
    fetchMetadataFailure: null,

    fetchLightProfile: null,
    fetchLightProfileLoading: null,
    fetchLightProfileSuccess: ['lightProfile'],
    fetchLightProfileFailure: null,

    uploadAvatar: ['image', 'messages'],
    uploadAvatarLoading: null,
    uploadAvatarSuccess: ['avatarUrl'],
    uploadAvatarFailure: null,

    deleteAvatar: ['messages'],
    deleteAvatarLoading: null,
    deleteAvatarSuccess: null,
    deleteAvatarFailure: null,
  },
  { prefix: 'PROFILE_' }
)

export const ProfileTypes = Types
export default Creators
