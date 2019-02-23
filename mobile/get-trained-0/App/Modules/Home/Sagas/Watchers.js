import { takeLatest } from 'redux-saga/effects'
import { HomeTypes } from '../Stores/Actions'
import { fetchMetadata, fetchLightProfile, fetchConnections } from './Workers'

export default function*() {
  yield [
    takeLatest(HomeTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(HomeTypes.FETCH_LIGHT_PROFILE, fetchLightProfile),
    takeLatest(HomeTypes.FETCH_CONNECTIONS, fetchConnections),
  ]
}
