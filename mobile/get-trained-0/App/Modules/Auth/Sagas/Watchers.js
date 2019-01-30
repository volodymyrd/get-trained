import { takeLatest } from 'redux-saga/effects'
import { AuthTypes } from '../Stores/Actions'
import { fetchAuthentication } from './Workers'

export default function*() {
  yield [takeLatest(AuthTypes.FETCH_AUTHENTICATION, fetchAuthentication)]
}
