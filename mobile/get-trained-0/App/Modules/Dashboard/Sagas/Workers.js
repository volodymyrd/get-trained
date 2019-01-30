import { put, call } from 'redux-saga/effects'
import DashboardActions from '../Stores/Actions'
import { DashboardService } from '../DashboardService'

export function* fetchSignOut() {
  yield put(DashboardActions.fetchSignOutLoading())

  const response = yield call(DashboardService.signOut)

  if (response && response.data === 'ok') {
    yield put(DashboardActions.fetchSignOutSuccess())
  } else {
    yield put(DashboardActions.fetchSignOutFailure())
  }
}
