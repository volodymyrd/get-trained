import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'AUTH'

export const txtSignIn = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_SIGN_IN), 'Sign In')
}

export const txtSignUp = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_SIGN_UP), 'Sign Up')
}

export const txtRestorePass = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_RESTORE_PASSWORD), 'Restore a password')
}

export const txtEmail = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_EMAIL), 'Email')
}

export const txtFirstName = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_FIRST_NAME), 'First Name')
}

export const txtPassword = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_PASSWORD), 'Password')
}

export const txtRepeatPassword = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_REPEAT_PASSWORD), 'Repeat Password')
}

export const hintMinPasswordLength = (localizations, minLength) => {
  return text(
    localizations.get(keys.AUTH_REG_HINT_PASSWORD),
    `Min length ${minLength} symbols`,
    minLength
  )
}

export const txtForgotPass = (localizations) => {
  return text(localizations.get(keys.AUTH_TXT_FORGOT_PASSWORD), 'Forgot a password?')
}

export const btnSignIn = (localizations) => {
  return text(localizations.get(keys.AUTH_BTN_SIGN_IN), 'Sign In')
}

export const btnSignUp = (localizations) => {
  return text(localizations.get(keys.AUTH_BTN_SIGN_UP), 'Sign Up')
}

export const btnGenNewPassword = (localizations) => {
  return text(localizations.get(keys.AUTH_BTN_GEN_NEW_PASSWORD), 'Send a new password')
}

export const errorBadCredentials = (localizations) => {
  return text(localizations.get(keys.AUTH_ERROR_WRONG_CREDENTIAL), 'Wrong email or password!')
}

export const undefinedError = (localizations) => {
  return text(localizations.get(keys.AUTH_ERROR_UNDEFINED), 'Undefined error')
}

const keys = {
  AUTH_TXT_SIGN_UP: 'auth.txt.signup',
  AUTH_TXT_SIGN_IN: 'auth.txt.signin',
  AUTH_TXT_EMAIL: 'auth.txt.email',
  AUTH_TXT_EMAIL_OR_PASSWORD: 'auth.txt.email_or_username',
  AUTH_TXT_FIRST_NAME: 'auth.txt.first_name',
  AUTH_TXT_PASSWORD: 'auth.txt.password',
  AUTH_TXT_REPEAT_PASSWORD: 'auth.txt.repeat_password',
  AUTH_TXT_FORGOT_PASSWORD: 'auth.txt.forgot_password',
  AUTH_TXT_LDAP_CUSTOM_MESSAGE: 'auth.txt.ldap.custom.message',
  AUTH_TXT_RESTORE_PASSWORD: 'auth.txt.restorepassword',
  AUTH_BTN_SIGN_UP: 'auth.btn.signup',
  AUTH_BTN_SIGN_IN: 'auth.btn.signin',
  AUTH_BTN_GEN_NEW_PASSWORD: 'auth.btn.getnewpassword',
  AUTH_ERROR_EMAIL: 'auth.error.email',
  AUTH_ERROR_PASSWORD: 'auth.error.password',
  AUTH_SIGN_IN_MESSAGE_SUCCESS: 'auth.signin.message.success',
  AUTH_REG_MESSAGE_SUCCESS: 'auth.reg.message.success',
  AUTH_RESETPASSWORD_MESSAGE_SUCCESS: 'auth.resetpassword.message.success',
  REG_ERROR_EMAIL_EXIST: 'reg.error.email_exist',
  AUTH_ERROR_EMAIL_NOT_FOUND: 'auth.error.email_not_found',
  AUTH_REG_ERROR_PASSWORD: 'auth.reg.error.password',
  AUTH_REG_HINT_PASSWORD: 'auth.reg.hint.password',
  AUTH_ERROR_WRONG_CREDENTIAL: 'auth.error.wrong_credential',
  AUTH_ERROR_NOT_FINISHED_REGISTRATION: 'auth.error.not_finished_registration',
  AUTH_ERROR_UNDEFINED: 'auth.error.undefined',
}
