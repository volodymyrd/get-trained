import { put, call } from 'redux-saga/effects'
import { error, success, info } from 'App/Components/Notification'
import { pageSize } from '../Constants'
import { MainService } from 'App/Services/MainService'
import { HomeService } from '../HomeService'
import HomeActions from '../Stores/Actions'
import { MODULE } from '../Metadata'

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

  if (response && response.data) {
    if (response.ok) {
      yield put(HomeActions.fetchTraineeRequestSuccess())
      yield call(success, response.data.message)
      yield put(HomeActions.fetchConnections(0, pageSize, []))
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

export function* fetchDeleteConnection({ connectionId, messages }) {
  yield put(HomeActions.fetchDeleteConnectionLoading())

  const response = yield call(HomeService.deleteConnection, connectionId)

  if (response && response.data) {
    if (response.ok) {
      yield put(HomeActions.fetchDeleteConnectionSuccess())
      yield call(success, response.data.message)
      yield put(HomeActions.fetchConnections(0, pageSize, []))
    } else {
      if (response.data.type !== 'E') {
        yield put(HomeActions.fetchDeleteConnectionSuccess())
        yield call(info, response.data.message)
      } else {
        yield put(HomeActions.fetchDeleteConnectionFailure())
        yield call(error, response.data.message)
      }
    }
  } else {
    yield put(HomeActions.fetchDeleteConnectionFailure())
    yield call(error, messages[0])
  }
}

export function* fetchAcceptConnection({ connectionId, messages }) {
  yield put(HomeActions.fetchAcceptConnectionLoading())

  const response = yield call(HomeService.acceptConnection, connectionId)

  if (response && response.data) {
    if (response.ok) {
      yield put(HomeActions.fetchAcceptConnectionSuccess())
      yield call(success, response.data.message)
      yield put(HomeActions.fetchConnections(0, pageSize, []))
    } else {
      if (response.data.type !== 'E') {
        yield put(HomeActions.fetchAcceptConnectionSuccess())
        yield call(info, response.data.message)
      } else {
        yield put(HomeActions.fetchAcceptConnectionFailure())
        yield call(error, response.data.message)
      }
    }
  } else {
    yield put(HomeActions.fetchAcceptConnectionFailure())
    yield call(error, messages[0])
  }
}

export function* sendChatMessage({ socket, message }) {
  yield put(HomeActions.sendChatMessageLoading(message))

  const response = yield call(HomeService.sendChatTextMessage, socket, JSON.stringify(message))

  console.log(response)

  yield put(HomeActions.sendChatMessageSuccess())
}
