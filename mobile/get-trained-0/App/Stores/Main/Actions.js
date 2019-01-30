import { createActions } from 'reduxsauce'

const { Types, Creators } = createActions({
  fetchAccess: null,
  fetchAccessLoading: null,
  fetchAccessSuccess: ['isAuthenticated'],
  fetchAccessFailure: null,
})

export const MainTypes = Types
export default Creators
