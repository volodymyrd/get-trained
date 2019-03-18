import { takeLatest } from 'redux-saga/effects'
import { TraineeProfileTypes } from '../Stores/Actions'
import {
  fetchMetadata,
  fetchLightProfile,
  fetchTraineeProfile,
  fetchUpdateTraineeProfile,
} from './Workers'

export default function*() {
  yield [
    takeLatest(TraineeProfileTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(TraineeProfileTypes.FETCH_LIGHT_PROFILE, fetchLightProfile),
    takeLatest(TraineeProfileTypes.FETCH_TRAINEE_PROFILE, fetchTraineeProfile),
    takeLatest(TraineeProfileTypes.FETCH_UPDATE_TRAINEE_PROFILE, fetchUpdateTraineeProfile),
  ]
}
