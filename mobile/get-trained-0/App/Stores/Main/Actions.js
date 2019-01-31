import { createActions } from 'reduxsauce'

const { Types, Creators } = createActions({
  getLocale: null,
  setLocale: ['locale'],

  fetchAccess: null,
  fetchAccessLoading: null,
  fetchAccessSuccess: ['isAuthenticated'],
  fetchAccessFailure: null,
})

export const MainTypes = Types
export default Creators
