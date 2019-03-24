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

    fetchGenders: null,
    fetchGendersLoading: null,
    fetchGendersSuccess: ['genders'],
    fetchGendersFailure: null,

    fetchTraineeProfile: ['traineeUserId'],
    fetchTraineeProfileLoading: null,
    fetchTraineeProfileSuccess: ['traineeProfile'],
    fetchTraineeProfileFailure: null,

    fetchUpdateTraineeProfile: ['traineeProfile'],
    fetchUpdateTraineeProfileLoading: null,
    fetchUpdateTraineeProfileSuccess: ['traineeProfile'],
    fetchUpdateTraineeProfileFailure: null,

    fetchTraineeFitnessProfiles: ['traineeFitnessProfilesConstraint'],
    fetchTraineeFitnessProfilesLoading: null,
    fetchTraineeFitnessProfilesSuccess: ['traineeFitnessProfiles'],
    fetchTraineeFitnessProfilesFailure: null,

    newTraineeFitnessProfile: null,
    fetchUpdateTraineeFitnessProfile: ['traineeFitnessProfile'],
    fetchUpdateTraineeFitnessProfileLoading: null,
    fetchUpdateTraineeFitnessProfileSuccess: ['traineeFitnessProfile'],
    fetchUpdateTraineeFitnessProfileFailure: null,

    fetchTraineeFitnessProfile: ['traineeUserId', 'traineeProfileId'],
    fetchTraineeFitnessProfileLoading: ['traineeProfileId'],
    fetchTraineeFitnessProfileSuccess: ['traineeFitnessProfile'],
    fetchTraineeFitnessProfileFailure: null,

    fetchDeleteTraineeFitnessProfile: ['traineeUserId', 'traineeProfileId'],
    fetchDeleteTraineeFitnessProfileLoading: ['traineeProfileId'],
    fetchDeleteTraineeFitnessProfileSuccess: null,
    fetchDeleteTraineeFitnessProfileFailure: null,
  },
  { prefix: 'TRAINEE_PROFILE_' }
)

export const TraineeProfileTypes = Types
export default Creators
