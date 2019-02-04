import { postWithCredentials } from 'App/Utils/HttpUtils'

const SIGN_OUT_URL = 'fe/auth/signout'

const signOut = () => {
  return postWithCredentials(SIGN_OUT_URL)
}

export const DashboardService = {
  signOut,
}
