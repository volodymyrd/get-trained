import { takeLatest } from 'redux-saga/effects'
import { SettingsTypes } from '../Stores/Actions'
import { fetchMetadata, fetchChangePassword } from './Workers'

export default function*() {
  yield [
    takeLatest(SettingsTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(SettingsTypes.FETCH_CHANGE_PASSWORD, fetchChangePassword),
  ]
}
