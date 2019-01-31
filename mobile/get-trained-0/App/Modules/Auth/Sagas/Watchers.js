import { takeLatest } from 'redux-saga/effects'
import { AuthTypes } from '../Stores/Actions'
import { fetchMetadata, fetchAuthentication } from './Workers'

export default function*() {
  yield [
    takeLatest(AuthTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(AuthTypes.FETCH_AUTHENTICATION, fetchAuthentication),
  ]
}
