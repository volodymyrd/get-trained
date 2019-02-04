import { put, call } from 'redux-saga/effects'
import DashboardActions from '../Stores/Actions'
import { DashboardService } from '../DashboardService'
import { success } from '../../../Components/Notification'

export function* fetchSignOut() {
  yield put(DashboardActions.fetchSignOutLoading())

  const response = yield call(DashboardService.signOut)

  if (response && response.data) {
    yield put(DashboardActions.fetchSignOutSuccess())
    yield call(success, response.data.message)
  } else {
    yield put(DashboardActions.fetchSignOutFailure())
  }
}
