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

export function* fetchGenders() {
  yield put(TraineeProfileActions.fetchGendersLoading())

  const genders = yield call(TraineeProfileService.getGenders)

  if (genders && genders.data) {
    yield put(TraineeProfileActions.fetchGendersSuccess(genders.data))
  } else {
    yield put(TraineeProfileActions.fetchGendersFailure())
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

  const profile = yield call(TraineeProfileService.updateTraineeProfile, traineeProfile)

  if (profile && profile.ok && profile.data) {
    yield put(TraineeProfileActions.fetchUpdateTraineeProfileSuccess(profile.data))
  } else {
    yield put(TraineeProfileActions.fetchUpdateTraineeProfileFailure())
  }
}

export function* fetchTraineeFitnessProfiles({ traineeFitnessProfilesConstraint }) {
  yield put(TraineeProfileActions.fetchTraineeFitnessProfilesLoading())

  const profiles = yield call(
    TraineeProfileService.getAllTraineeFitnessProfiles,
    traineeFitnessProfilesConstraint
  )

  if (profiles && profiles.ok && profiles.data) {
    yield put(TraineeProfileActions.fetchTraineeFitnessProfilesSuccess(profiles.data))
  } else {
    yield put(TraineeProfileActions.fetchTraineeFitnessProfilesFailure())
  }
}

export function* fetchUpdateTraineeFitnessProfile({ traineeFitnessProfile }) {
  yield put(TraineeProfileActions.fetchUpdateTraineeFitnessProfileLoading())

  const profile = yield call(
    TraineeProfileService.updateTraineeFitnessProfile,
    traineeFitnessProfile
  )

  if (profile && profile.data) {
    if (profile.ok) {
      yield call(success, 'Saved successfully')
      yield put(TraineeProfileActions.fetchUpdateTraineeFitnessProfileSuccess(profile.data))
    } else {
      yield call(error, profile.data.message)
      yield put(TraineeProfileActions.fetchUpdateTraineeFitnessProfileFailure())
    }
  } else {
    yield put(TraineeProfileActions.fetchUpdateTraineeFitnessProfileFailure())
  }
}

export function* fetchTraineeFitnessProfile({ traineeUserId, traineeProfileId }) {
  yield put(TraineeProfileActions.fetchTraineeFitnessProfileLoading(traineeProfileId))

  const profile = yield call(
    TraineeProfileService.getTraineeFitnessProfile,
    traineeUserId,
    traineeProfileId
  )

  if (profile && profile.ok && profile.data) {
    yield put(TraineeProfileActions.fetchTraineeFitnessProfileSuccess(profile.data))
  } else {
    yield put(TraineeProfileActions.fetchTraineeFitnessProfileFailure())
  }
}

export function* fetchDeleteTraineeFitnessProfile({ traineeUserId, traineeProfileId }) {
  yield put(TraineeProfileActions.fetchDeleteTraineeFitnessProfileLoading(traineeProfileId))

  const response = yield call(
    TraineeProfileService.deleteTraineeFitnessProfile,
    traineeUserId,
    traineeProfileId
  )

  if (response) {
    yield put(TraineeProfileActions.fetchDeleteTraineeFitnessProfileSuccess())
  } else {
    yield put(TraineeProfileActions.fetchDeleteTraineeFitnessProfileFailure())
  }
}
