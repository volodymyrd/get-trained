import { postPlainWithCredentials } from 'App/Utils/HttpUtils'

const changePassword = (password, newPassword, confirmPassword) => {
  return postPlainWithCredentials(`fe/profile/user/update-sec/password`, {
    password,
    newPassword,
    confirmPassword,
  })
}

const fetchAddTrainer = () => {
  return postPlainWithCredentials(`fe/activity/fitness/trainer/add`)
}

const fetchRemoveTrainer = () => {
  return postPlainWithCredentials(`fe/activity/fitness/trainer/remove`)
}

export const SettingsService = {
  changePassword,
  fetchAddTrainer,
  fetchRemoveTrainer,
}
