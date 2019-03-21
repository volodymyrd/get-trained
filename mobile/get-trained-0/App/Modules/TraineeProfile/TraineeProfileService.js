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

export const TraineeProfileService = {
  getGenders,
  getTraineeProfile,
  updateTraineeProfile,
}
