import { postPlainWithCredentials } from 'App/Utils/HttpUtils'

const uploadAvatar = () => {
  return postPlainWithCredentials(`fe/profile/user/update-sec/password`)
}

const deleteAvatar = () => {
  return postPlainWithCredentials(`fe/profile/user/update-sec/password`)
}

export const ProfileService = {
  uploadAvatar,
  deleteAvatar,
}
