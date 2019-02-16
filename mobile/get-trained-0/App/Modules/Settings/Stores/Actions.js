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

    fetchChangePassword: ['oldPassword', 'newPassword', 'repeatPassword', 'messages'],
    fetchChangePasswordLoading: null,
    fetchChangePasswordSuccess: null,
    fetchChangePasswordFailure: null,
  },
  { prefix: 'SETTINGS_' }
)

export const SettingsTypes = Types
export default Creators
