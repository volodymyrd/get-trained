import { put, call } from 'redux-saga/effects'
import { error, success } from 'App/Components/Notification'
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

  if (auth) {
    yield put(AuthActions.fetchAuthenticationSuccess())
    yield call(success, messages[0])
  } else {
    yield put(AuthActions.fetchAuthenticationFailure())
    yield call(error, messages[1])
  }
}

export function* fetchSignUp({ email, password, firstName, lang, messages }) {
  yield put(AuthActions.fetchSignUpLoading())

  const signUp = yield call(AuthService.signUp, email, password, firstName, lang)

  if (signUp) {
    yield put(AuthActions.fetchSignUpSuccess())
    yield call(success, messages[0])
    yield put(AuthActions.toggleAuthType(AuthType.SIGN_IN))
  } else {
    yield put(AuthActions.fetchSignUpFailure())
    yield call(error, messages[1])
  }
}
