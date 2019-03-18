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

    fetchTraineeProfile: ['traineeUserId'],
    fetchTraineeProfileLoading: null,
    fetchTraineeProfileSuccess: ['traineeProfile'],
    fetchTraineeProfileFailure: null,

    fetchUpdateTraineeProfile: ['traineeProfile'],
    fetchUpdateTraineeProfileLoading: null,
    fetchUpdateTraineeProfileSuccess: ['traineeProfile'],
    fetchUpdateTraineeProfileFailure: null,
  },
  { prefix: 'TRAINEE_PROFILE_' }
)

export const TraineeProfileTypes = Types
export default Creators
