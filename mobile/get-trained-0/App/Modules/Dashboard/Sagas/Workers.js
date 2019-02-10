import { put, call } from 'redux-saga/effects'
import DashboardActions from '../Stores/Actions'
import { DashboardService } from '../DashboardService'
import { success } from '../../../Components/Notification'
import { MainService } from '../../../Services/MainService'
import { MENU_MODULE } from '../Metadata'

export function* fetchMetadata({ langCode }) {
  yield put(DashboardActions.fetchMetadataLoading())

  const metadata = yield call(MainService.fetchMetadata, langCode, MENU_MODULE)

  if (metadata && metadata.data) {
    yield put(DashboardActions.fetchMetadataSuccess(metadata.data))
  } else {
    yield put(DashboardActions.fetchMetadataFailure())
  }
}

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
