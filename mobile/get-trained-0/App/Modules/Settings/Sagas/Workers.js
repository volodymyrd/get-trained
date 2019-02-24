import { put, call } from 'redux-saga/effects'
import { error, success } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import { SettingsService } from '../SettingsService'
import SettingsActions from '../Stores/Actions'
import { MODULE } from '../Metadata'

export function* fetchMetadata({ langCode }) {
  yield put(SettingsActions.fetchMetadataLoading())

  const metadata = yield call(MainService.fetchMetadata, langCode, MODULE)

  if (metadata && metadata.data) {
    yield put(SettingsActions.fetchMetadataSuccess(metadata.data))
  } else {
    yield put(SettingsActions.fetchMetadataFailure())
  }
}

export function* fetchChangePassword({ oldPassword, newPassword, repeatPassword, messages }) {
  yield put(SettingsActions.fetchChangePasswordLoading())

  const response = yield call(
    SettingsService.changePassword,
    oldPassword,
    newPassword,
    repeatPassword
  )

  if (response && response.data) {
    if (response.ok === true) {
      yield put(SettingsActions.fetchChangePasswordSuccess())
      yield call(success, response.data.message)
    } else {
      yield put(SettingsActions.fetchChangePasswordFailure())
      yield call(error, response.data.message)
    }
  } else {
    yield put(SettingsActions.fetchChangePasswordFailure())
    yield call(error, messages[0])
  }
}

export function* fetchIsTrainer() {
  yield put(SettingsActions.fetchIsTrainerLoading())

  const response = yield call(SettingsService.fetchIsTrainer)
  if (response && response.data) {
    yield put(SettingsActions.fetchIsTrainerSuccess(response.data.flag))
  } else {
    yield put(SettingsActions.fetchIsTrainerFailure())
  }
}
