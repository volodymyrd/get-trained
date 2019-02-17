import { wrapFileForUploading, dataUpload, postPlainWithCredentials } from 'App/Utils/HttpUtils'

const BASE_URL = 'fe/profile/user'

const getLightProfile = () => {
  return postPlainWithCredentials(`${BASE_URL}/getLight`)
}

const uploadAvatar = (image) => {
  return dataUpload(
    `${BASE_URL}/avatar/load`,
    wrapFileForUploading([
      {
        uri: image.sourceURL,
        type: image.mime,
        name: image.filename,
      },
    ])
  )
}

const deleteAvatar = () => {
  return postPlainWithCredentials(`${BASE_URL}/avatar/del`)
}

export const ProfileService = {
  getLightProfile,
  uploadAvatar,
  deleteAvatar,
}
