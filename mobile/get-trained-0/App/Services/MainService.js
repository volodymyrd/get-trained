import { postWithCredentials } from 'App/Utils/HttpUtils'

const AUTH_CHECK_URL = 'fe/auth/check'
const METADATA_URL = 'metadata/get'

const fetchAccess = () => {
  return postWithCredentials(AUTH_CHECK_URL)
}

const fetchMetadata = (langCode, module) => {
  return postWithCredentials(`${METADATA_URL}?lang=${langCode.toUpperCase()}&module=${module}`)
}

export const MainService = {
  fetchAccess,
  fetchMetadata,
}
