import { postPlainWithCredentials } from 'App/Utils/HttpUtils'

const changePassword = (password, newPassword, confirmPassword) => {
  return postPlainWithCredentials(`fe/profile/user/update-sec/password`, {
    password,
    newPassword,
    confirmPassword,
  })
}

const fetchIsTrainer = () => {
  return postPlainWithCredentials(`fe/activity/fitness/isTrainer`)
}

export const SettingsService = {
  changePassword,
  fetchIsTrainer,
}
