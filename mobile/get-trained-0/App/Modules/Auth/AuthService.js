import { signIn } from 'App/Utils/HttpUtils'

const authenticate = (email, password, lang) => {
  return signIn(email, password, lang)
}

export const AuthService = {
  authenticate,
}
