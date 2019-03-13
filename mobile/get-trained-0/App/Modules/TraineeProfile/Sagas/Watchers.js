import { takeLatest } from 'redux-saga/effects'
import { TraineeProfileTypes } from '../Stores/Actions'
import { fetchMetadata, fetchLightProfile } from './Workers'

export default function*() {
  yield [
    takeLatest(TraineeProfileTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(TraineeProfileTypes.FETCH_LIGHT_PROFILE, fetchLightProfile),
  ]
}
