import { signIn, post } from 'App/Utils/HttpUtils'

const authenticate = (email, password, lang) => {
  return signIn(email, password, lang)
}

const signUp = (email, password, firstName, lang) => {
  return post('fe/auth/signup', { email, newPassword: password, firstName, lang })
}

const restorePassword = (email, lang) => {
  return post(`fe/auth/passwordrestore?email=${email}&lang=${lang}`)
}

export const AuthService = {
  authenticate,
  signUp,
  restorePassword,
}
