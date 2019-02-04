import { Platform, NativeModules } from 'react-native'
import { put, call } from 'redux-saga/effects'
import MainActions from 'App/Stores/Main/Actions'
import { MainService } from 'App/Services/MainService'

export function* getLocale() {
  let locale
  if (Platform.OS === 'android') {
    locale = NativeModules.I18nManager.localeIdentifier
  } else {
    locale = NativeModules.SettingsManager.settings.AppleLocale
  }

  yield put(MainActions.setLocale(locale))
}

export function* fetchAccess() {
  yield put(MainActions.fetchAccessLoading())

  const access = yield call(MainService.fetchAccess)

  if (access && access.ok === true) {
    yield put(MainActions.fetchAccessSuccess(true))
  } else {
    yield put(MainActions.fetchAccessFailure())
  }
}
