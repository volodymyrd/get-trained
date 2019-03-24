import { takeLatest } from 'redux-saga/effects'
import { TraineeProfileTypes } from '../Stores/Actions'
import {
  fetchMetadata,
  fetchLightProfile,
  fetchGenders,
  fetchTraineeProfile,
  fetchUpdateTraineeProfile,
  fetchUpdateTraineeFitnessProfile,
  fetchTraineeFitnessProfiles,
} from './Workers'

export default function*() {
  yield [
    takeLatest(TraineeProfileTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(TraineeProfileTypes.FETCH_LIGHT_PROFILE, fetchLightProfile),
    takeLatest(TraineeProfileTypes.FETCH_GENDERS, fetchGenders),
    takeLatest(TraineeProfileTypes.FETCH_TRAINEE_PROFILE, fetchTraineeProfile),
    takeLatest(TraineeProfileTypes.FETCH_UPDATE_TRAINEE_PROFILE, fetchUpdateTraineeProfile),
    takeLatest(
      TraineeProfileTypes.FETCH_UPDATE_TRAINEE_FITNESS_PROFILE,
      fetchUpdateTraineeFitnessProfile
    ),
    takeLatest(TraineeProfileTypes.FETCH_TRAINEE_FITNESS_PROFILES, fetchTraineeFitnessProfiles),
  ]
}
