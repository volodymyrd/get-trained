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

export function* uploadAvatar({ data, messages }) {
  yield put(ProfileActions.uploadAvatarLoading)

  const response = yield call(ProfileService.uploadAvatar)

  console.log(response)
  if (response && response.data) {
    if (response.ok === true) {
      yield put(ProfileActions.uploadAvatarSuccess())
      yield call(success, response.data.message)
    } else {
      yield put(ProfileActions.uploadAvatarFailure())
      yield call(error, response.data.message)
    }
  } else {
    yield put(ProfileActions.uploadAvatarFailure())
    yield call(error, messages[0])
  }
}
