import { postWithCredentials } from 'App/Utils/HttpUtils'

const AUTH_CHECK_URL = 'auth/check'

const fetchAccess = () => {
  return postWithCredentials(AUTH_CHECK_URL)
}

export const MainService = {
  fetchAccess,
}
