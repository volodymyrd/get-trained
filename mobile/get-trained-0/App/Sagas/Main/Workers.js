import { put, call } from 'redux-saga/effects'
import MainActions from 'App/Stores/Main/Actions'
import { MainService } from 'App/Services/MainService'

export function* fetchAccess() {
  yield put(MainActions.fetchAccessLoading())

  const access = yield call(MainService.fetchAccess)

  if (access && access.data === 'ok') {
    yield put(MainActions.fetchAccessSuccess(true))
  } else {
    yield put(MainActions.fetchAccessFailure())
  }
}
