import { createActions } from 'reduxsauce'

const { Types, Creators } = createActions(
  {
    fetchMetadata: ['langCode'],
    fetchMetadataLoading: null,
    fetchMetadataSuccess: ['metadata'],
    fetchMetadataFailure: null,

    fetchSignOut: null,
    fetchSignOutLoading: null,
    fetchSignOutSuccess: null,
    fetchSignOutFailure: null,
  },
  { prefix: 'DASHBOARD_' }
)

export const WorkspaceTypes = Types
export default Creators
