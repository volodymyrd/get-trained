import { takeLatest } from 'redux-saga/effects'
import { HomeTypes } from '../Stores/Actions'
import {
  fetchMetadata,
  fetchLightProfile,
  fetchIsTrainer,
  fetchConnections,
  fetchTraineeRequest,
  fetchDeleteConnection,
} from './Workers'

export default function*() {
  yield [
    takeLatest(HomeTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(HomeTypes.FETCH_LIGHT_PROFILE, fetchLightProfile),
    takeLatest(HomeTypes.FETCH_IS_TRAINER, fetchIsTrainer),
    takeLatest(HomeTypes.FETCH_CONNECTIONS, fetchConnections),
    takeLatest(HomeTypes.FETCH_TRAINEE_REQUEST, fetchTraineeRequest),
    takeLatest(HomeTypes.FETCH_DELETE_CONNECTION, fetchDeleteConnection),
  ]
}
