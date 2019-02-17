import { put, call } from 'redux-saga/effects'
import { error, success } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import ProfileActions from '../Stores/Actions'
import { ProfileService } from '../ProfileService'
import { MODULE } from '../Metadata'

export function* fetchMetadata({ langCode }) {
  yield put(ProfileActions.fetchMetadataLoading())

  const metadata = yield call(MainService.fetchMetadata, langCode, MODULE)

  if (metadata && metadata.data) {
    yield put(ProfileActions.fetchMetadataSuccess(metadata.data))
  } else {
    yield put(ProfileActions.fetchMetadataFailure())
  }
}

export function* fetchLightProfile() {
  yield put(ProfileActions.fetchLightProfileLoading())

  const profile = yield call(ProfileService.getLightProfile)

  if (profile && profile.data) {
    yield put(ProfileActions.fetchLightProfileSuccess(profile.data))
  } else {
    yield put(ProfileActions.fetchLightProfileFailure())
  }
}

export function* uploadAvatar({ image, messages }) {
  yield put(ProfileActions.uploadAvatarLoading())

  const response = yield call(ProfileService.uploadAvatar, image)

  if (response && response.data) {
    if (response.ok === true) {
      yield put(ProfileActions.uploadAvatarSuccess(response.data[0].url))
      yield call(success, messages[0])
    } else {
      yield put(ProfileActions.uploadAvatarFailure())
      yield call(error, messages[1])
    }
  } else {
    yield put(ProfileActions.uploadAvatarFailure())
    yield call(error, messages[1])
  }
}

export function* deleteAvatar({ messages }) {
  yield put(ProfileActions.deleteAvatarLoading())

  const response = yield call(ProfileService.deleteAvatar)

  if (response && response.data) {
    if (response.ok === true) {
      yield put(ProfileActions.deleteAvatarSuccess())
      yield call(success, response.data.message)
    } else {
      yield put(ProfileActions.deleteAvatarFailure())
      yield call(error, messages[0])
    }
  } else {
    yield put(ProfileActions.deleteAvatarFailure())
    yield call(error, messages[0])
  }
}
