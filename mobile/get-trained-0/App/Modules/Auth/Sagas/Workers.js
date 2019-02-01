import { put, call } from 'redux-saga/effects'
import { error, success } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import AuthActions from '../Stores/Actions'
import { AuthService } from '../AuthService'

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
