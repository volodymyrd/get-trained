import { put, call } from 'redux-saga/effects'
import { error, info, success } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import AuthActions from '../Stores/Actions'
import { AuthService } from '../AuthService'
import { AuthStep } from '../Stores/InitialState'

export function* fetchMetadata({ langCode }) {
  yield put(AuthActions.fetchMetadataLoading())

  const metadata = yield call(MainService.fetchMetadata, langCode, 'AUTH')

  if (metadata && metadata.data) {
    yield put(AuthActions.fetchMetadataSuccess(metadata.data))
  } else {
    yield put(AuthActions.fetchMetadataFailure())
  }
}

export function* fetchAuthentication({ email, password, lang, messages }) {
  yield put(AuthActions.fetchAuthenticationLoading())

  const response = yield call(AuthService.authenticate, email, password, lang)

  if (response && response.data) {
    if (response.data.type === 'S') {
      yield put(AuthActions.fetchAuthenticationSuccess())
      yield call(success, response.data.message)
    } else if (response.data.type === 'I') {
      yield put(AuthActions.fetchAuthenticationFailure())
      yield call(info, response.data.message)
    }
  } else {
    yield put(AuthActions.fetchAuthenticationFailure())
    yield call(error, messages[0])
  }
}

export function* fetchSignUp({ email, password, firstName, lang, messages }) {
  yield put(AuthActions.fetchSignUpLoading())

  const response = yield call(AuthService.signUp, email, password, firstName, lang)

  if (response && response.data) {
    if (response.ok === true) {
      yield put(AuthActions.fetchSignUpSuccess())
      yield call(success, response.data.message)
      yield put(AuthActions.toggleAuthStep(AuthStep.SIGN_IN))
    } else {
      yield put(AuthActions.fetchSignUpFailure())
      yield call(error, response.data.message)
    }
  } else {
    yield put(AuthActions.fetchSignUpFailure())
    yield call(error, messages[0])
  }
}

export function* fetchRestorePassword({ email, lang, messages }) {
  yield put(AuthActions.fetchRestorePasswordLoading())

  const response = yield call(AuthService.restorePassword, email, lang)

  if (response && response.data) {
    if (response.data.type === 'S') {
      yield put(AuthActions.fetchRestorePasswordSuccess())
      yield call(success, response.data.message)
      yield put(AuthActions.toggleAuthStep(AuthStep.SIGN_IN))
    } else if (response.data.type === 'I') {
      yield put(AuthActions.fetchRestorePasswordFailure())
      yield call(info, response.data.message)
    }
  } else {
    yield put(AuthActions.fetchRestorePasswordFailure())
    yield call(error, messages[0])
  }
}
