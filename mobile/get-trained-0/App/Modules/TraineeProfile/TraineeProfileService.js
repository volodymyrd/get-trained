import { wrapFileForUploading, dataUpload, postPlainWithCredentials } from 'App/Utils/HttpUtils'

const BASE_URL = 'fe/trainee/profile'

const uploadAvatar = (image) => {
  return dataUpload(
    `${BASE_URL}/avatar/load`,
    wrapFileForUploading([
      {
        uri: image.path,
        type: image.mime,
        name: `file_${new Date().toISOString()}`,
      },
    ])
  )
}

const deleteAvatar = () => {
  return postPlainWithCredentials(`${BASE_URL}/avatar/del`)
}

export const TraineeProfileService = {
  uploadAvatar,
  deleteAvatar,
}
