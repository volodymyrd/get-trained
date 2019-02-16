import { postPlainWithCredentials } from 'App/Utils/HttpUtils'

const changePassword = (password, newPassword, confirmPassword) => {
  return postPlainWithCredentials(`fe/profile/user/update-sec/password`, {
    password,
    newPassword,
    confirmPassword,
  })
}

export const SettingsService = {
  changePassword,
}
