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

    fetchIsTrainer: null,
    fetchIsTrainerLoading: null,
    fetchIsTrainerSuccess: ['isTrainer'],
    fetchIsTrainerFailure: null,

    fetchConnections: ['offset', 'pageSize'],
    fetchConnectionsLoading: null,
    fetchConnectionsSuccess: ['connections'],
    fetchConnectionsFailure: null,

    fetchTraineeRequest: ['email'],
    fetchTraineeRequestLoading: null,
    fetchTraineeRequestSuccess: null,
    fetchTraineeRequestFailure: null,
  },
  { prefix: 'HOME_' }
)

export const HomeTypes = Types
export default Creators
