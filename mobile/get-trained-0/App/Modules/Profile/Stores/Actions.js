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

    uploadAvatar: ['data'],
    uploadAvatarLoading: null,
    uploadAvatarSuccess: null,
    uploadAvatarFailure: null,

    deleteAvatar: ['data'],
    deleteAvatarLoading: null,
    deleteAvatarSuccess: null,
    deleteAvatarFailure: null,
  },
  { prefix: 'PROFILE_' }
)

export const ProfileTypes = Types
export default Creators
