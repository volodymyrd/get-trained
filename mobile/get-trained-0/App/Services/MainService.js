import { postPlainWithCredentials, postWithCredentials } from 'App/Utils/HttpUtils'

const AUTH_CHECK_URL = 'fe/auth/check'
const METADATA_URL = 'metadata/get'
const PROFILE_URL = 'fe/profile/user'

const fetchAccess = () => {
  return postWithCredentials(AUTH_CHECK_URL)
}

const fetchMetadata = (langCode, module) => {
  return postWithCredentials(`${METADATA_URL}?lang=${langCode.toUpperCase()}&module=${module}`)
}

const getLightProfile = () => {
  return postPlainWithCredentials(`${PROFILE_URL}/getLight`)
}

export const MainService = {
  fetchAccess,
  fetchMetadata,
  getLightProfile,
}
