import { createActions } from 'reduxsauce'

/**
 * We use reduxsauce's `createActions()` helper to easily create redux actions.
 *
 * @see https://github.com/infinitered/reduxsauce#createactions
 */
const { Types, Creators } = createActions({
  fetchMetadata: ['langCode'],
  fetchMetadataLoading: null,
  fetchMetadataSuccess: ['metadata'],
  fetchMetadataFailure: null,

  toggleAuthType: ['authType'],
  fetchAuthentication: ['email', 'password', 'lang', 'messages'],
  fetchAuthenticationLoading: null,
  fetchAuthenticationSuccess: null,
  fetchAuthenticationFailure: null,

  fetchSignUp: ['email', 'password', 'firstName', 'lang', 'messages'],
  fetchSignUpLoading: null,
  fetchSignUpSuccess: null,
  fetchSignUpFailure: null,
})

export const AuthTypes = Types
export default Creators
