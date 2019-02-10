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

    toggleAuthStep: ['authStep'],
    fetchAuthentication: ['email', 'password', 'lang', 'messages'],
    fetchAuthenticationLoading: null,
    fetchAuthenticationSuccess: null,
    fetchAuthenticationFailure: null,

    fetchSignUp: ['email', 'password', 'firstName', 'lang', 'messages'],
    fetchSignUpLoading: null,
    fetchSignUpSuccess: null,
    fetchSignUpFailure: null,

    fetchRestorePassword: ['email', 'lang', 'messages'],
    fetchRestorePasswordLoading: null,
    fetchRestorePasswordSuccess: null,
    fetchRestorePasswordFailure: null,
  },
  { prefix: 'AUTH_' }
)

export const AuthTypes = Types
export default Creators
