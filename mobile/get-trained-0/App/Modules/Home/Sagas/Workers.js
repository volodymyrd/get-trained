import { put, call } from 'redux-saga/effects'
import { error, success, info } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import { HomeService } from '../HomeService'
import HomeActions from '../Stores/Actions'
import { MODULE } from '../Metadata'
import { fetchTraineeRequestLoading } from '../Stores/Reducers'

export function* fetchMetadata({ langCode }) {
  yield put(HomeActions.fetchMetadataLoading())

  const metadata = yield call(MainService.fetchMetadata, langCode, MODULE)

  if (metadata && metadata.data) {
    yield put(HomeActions.fetchMetadataSuccess(metadata.data))
  } else {
    yield put(HomeActions.fetchMetadataFailure())
  }
}

export function* fetchLightProfile() {
  yield put(HomeActions.fetchLightProfileLoading())

  const profile = yield call(MainService.getLightProfile)

  if (profile && profile.data) {
    yield put(HomeActions.fetchLightProfileSuccess(profile.data))
  } else {
    yield put(HomeActions.fetchLightProfileFailure())
  }
}

export function* fetchIsTrainer() {
  yield put(HomeActions.fetchIsTrainerLoading())

  const response = yield call(MainService.fetchIsTrainer)
  if (response && response.data) {
    yield put(HomeActions.fetchIsTrainerSuccess(response.data.flag))
  } else {
    yield put(HomeActions.fetchIsTrainerFailure())
  }
}

export function* fetchConnections({ offset, pageSize, messages }) {
  yield put(HomeActions.fetchConnectionsLoading())

  const response = yield call(HomeService.getConnections, offset, pageSize)
  console.log(response)
  if (response && response.data) {
    yield put(HomeActions.fetchConnectionsSuccess(response.data))
  } else {
    yield put(HomeActions.fetchConnectionsFailure())
    yield call(error, messages[0])
  }
}

export function* fetchTraineeRequest({ email, messages }) {
  yield put(HomeActions.fetchTraineeRequestLoading())

  const response = yield call(HomeService.traineeRequest, email)
  console.log(response)
  if (response && response.data) {
    if (response.ok) {
      yield put(HomeActions.fetchTraineeRequestSuccess())
      yield call(success, response.data.message)
    } else {
      if (response.data.type !== 'E') {
        yield put(HomeActions.fetchTraineeRequestSuccess())
        yield call(info, response.data.message)
      } else {
        yield put(HomeActions.fetchTraineeRequestFailure())
        yield call(error, response.data.message)
      }
    }
  } else {
    yield put(HomeActions.fetchTraineeRequestFailure())
    yield call(error, messages[0])
  }
}