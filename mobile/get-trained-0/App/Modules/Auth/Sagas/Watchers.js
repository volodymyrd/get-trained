import { takeLatest } from 'redux-saga/effects'
import { AuthTypes } from '../Stores/Actions'
import { fetchMetadata, fetchAuthentication, fetchSignUp } from './Workers'

export default function*() {
  yield [
    takeLatest(AuthTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(AuthTypes.FETCH_AUTHENTICATION, fetchAuthentication),
    takeLatest(AuthTypes.FETCH_SIGN_UP, fetchSignUp),
  ]
}
