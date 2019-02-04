import { put, call } from 'redux-saga/effects'
import { error, info, success } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import AuthActions from '../Stores/Actions'
import { AuthService } from '../AuthService'
import { AuthType } from '../Stores/InitialState'

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

  const auth = yield call(AuthService.authenticate, email, password, lang)

  if (auth && auth.data) {
    if (auth.data.type === 'S') {
      yield call(success, auth.data.message)
      yield put(AuthActions.fetchAuthenticationSuccess())
    } else if (auth.data.type === 'I') {
      yield call(info, auth.data.message)
      yield put(AuthActions.fetchAuthenticationFailure())
    }
  } else {
    yield put(AuthActions.fetchAuthenticationFailure())
    yield call(error, messages[0])
  }
}

export function* fetchSignUp({ email, password, firstName, lang, messages }) {
  yield put(AuthActions.fetchSignUpLoading())

  const signUp = yield call(AuthService.signUp, email, password, firstName, lang)

  if (signUp && signUp.data) {
    if (signUp.ok === true) {
      yield put(AuthActions.fetchSignUpSuccess())
      yield call(success, signUp.data.message)
      yield put(AuthActions.toggleAuthType(AuthType.SIGN_IN))
    } else {
      yield put(AuthActions.fetchSignUpSuccess())
      yield call(error, signUp.data.message)
      yield put(AuthActions.fetchSignUpFailure())
    }
  } else {
    yield put(AuthActions.fetchSignUpFailure())
    yield call(error, messages[0])
  }
}
