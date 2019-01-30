import { createActions } from 'reduxsauce'

/**
 * We use reduxsauce's `createActions()` helper to easily create redux actions.
 *
 * @see https://github.com/infinitered/reduxsauce#createactions
 */
const { Types, Creators } = createActions({
  toggleAuthType: ['authType'],
  fetchAuthentication: ['email', 'password', 'lang'],
  fetchAuthenticationLoading: null,
  fetchAuthenticationSuccess: null,
  fetchAuthenticationFailure: null,
})

export const AuthTypes = Types
export default Creators