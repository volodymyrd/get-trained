import { put, call } from 'redux-saga/effects'
import { error, success } from 'App/Components/Notification'
import { MainService } from 'App/Services/MainService'
import TraineeProfileActions from '../Stores/Actions'
import { TraineeProfileService } from '../TraineeProfileService'
import { MODULE } from '../Metadata'

export function* fetchMetadata({ langCode }) {
  yield put(TraineeProfileActions.fetchMetadataLoading())

  const metadata = yield call(MainService.fetchMetadata, langCode, MODULE)

  if (metadata && metadata.data) {
    yield put(TraineeProfileActions.fetchMetadataSuccess(metadata.data))
  } else {
    yield put(TraineeProfileActions.fetchMetadataFailure())
  }
}

export function* fetchLightProfile() {
  yield put(TraineeProfileActions.fetchLightProfileLoading())

  const profile = yield call(MainService.getLightProfile)

  if (profile && profile.data) {
    yield put(TraineeProfileActions.fetchLightProfileSuccess(profile.data))
  } else {
    yield put(TraineeProfileActions.fetchLightProfileFailure())
  }
}

export function* fetchTraineeProfile({ traineeUserId }) {
  yield put(TraineeProfileActions.fetchTraineeProfileLoading())

  const profile = yield call(TraineeProfileService.getTraineeProfile, traineeUserId)

  if (profile && profile.data) {
    yield put(TraineeProfileActions.fetchTraineeProfileSuccess(profile.data))
  } else {
    yield put(TraineeProfileActions.fetchTraineeProfileFailure())
  }
}

export function* fetchUpdateTraineeProfile({ traineeProfile }) {
  yield put(TraineeProfileActions.fetchUpdateTraineeProfileLoading())

  const profile = yield call(TraineeProfileService.getTraineeProfile, traineeProfile)

  if (profile && profile.data) {
    yield put(TraineeProfileActions.fetchUpdateTraineeProfileSuccess(profile.data))
  } else {
    yield put(TraineeProfileActions.fetchUpdateTraineeProfileFailure())
  }
}
