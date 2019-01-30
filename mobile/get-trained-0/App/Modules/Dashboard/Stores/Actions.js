import { createActions } from 'reduxsauce'

const { Types, Creators } = createActions({
  fetchSignOut: null,
  fetchSignOutLoading: null,
  fetchSignOutSuccess: null,
  fetchSignOutFailure: null,
})

export const WorkspaceTypes = Types
export default Creators
