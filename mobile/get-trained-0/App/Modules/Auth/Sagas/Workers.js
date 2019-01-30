import { put, call } from 'redux-saga/effects'
import AuthActions from '../Stores/Actions'
import { AuthService } from '../AuthService'
import { error, success } from 'App/Components/Notification'

export function* fetchAuthentication({ email, password, lang }) {
  yield put(AuthActions.fetchAuthenticationLoading())

  const auth = yield call(AuthService.authenticate, email, password, lang)

  if (auth) {
    yield put(AuthActions.fetchAuthenticationSuccess())
    yield call(success, 'Successful login!')
  } else {
    yield put(AuthActions.fetchAuthenticationFailure())
    yield call(error, 'Wrong email or password')
  }
}
