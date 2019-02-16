import { takeLatest } from 'redux-saga/effects'
import { ProfileTypes } from '../Stores/Actions'
import { fetchMetadata, uploadAvatar } from './Workers'

export default function*() {
  yield [
    takeLatest(ProfileTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(ProfileTypes.uploadAvatar, uploadAvatar),
  ]
}
