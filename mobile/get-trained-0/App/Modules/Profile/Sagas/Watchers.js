import { takeLatest } from 'redux-saga/effects'
import { ProfileTypes } from '../Stores/Actions'
import { fetchMetadata, fetchLightProfile, uploadAvatar, deleteAvatar } from './Workers'

export default function*() {
  yield [
    takeLatest(ProfileTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(ProfileTypes.FETCH_LIGHT_PROFILE, fetchLightProfile),
    takeLatest(ProfileTypes.UPLOAD_AVATAR, uploadAvatar),
    takeLatest(ProfileTypes.DELETE_AVATAR, deleteAvatar),
  ]
}
