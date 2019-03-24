import { postPlainWithCredentials } from 'App/Utils/HttpUtils'

const BASE_URL = 'fe/activity/trainee/profile'

const getGenders = () => {
  return postPlainWithCredentials(`${BASE_URL}/genders`)
}

const getTraineeProfile = (traineeUserId) => {
  return postPlainWithCredentials(`${BASE_URL}/get?traineeUserId=${traineeUserId}`)
}

const updateTraineeProfile = (traineeProfile) => {
  return postPlainWithCredentials(`${BASE_URL}/update`, traineeProfile)
}

const getAllTraineeFitnessProfiles = (constraint) => {
  return postPlainWithCredentials(`${BASE_URL}/fitness/getAll`, constraint)
}

const updateTraineeFitnessProfile = (traineeFitnessProfile) => {
  return postPlainWithCredentials(`${BASE_URL}/fitness/save`, traineeFitnessProfile)
}

export const TraineeProfileService = {
  getGenders,
  getTraineeProfile,
  updateTraineeProfile,
  getAllTraineeFitnessProfiles,
  updateTraineeFitnessProfile,
}
