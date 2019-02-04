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
  COMMON_LANG_NAME: 'common.lang.name',
  COMMON_ASSIGN_TO: 'common.assign_to',
  COMMON_ID: 'common.id',
  COMMON_AND: 'common.and',
  COMMON_SUBMIT: 'common.submit',
  COMMON_RESET: 'common.reset',
  COMMON_CONFIRM: 'common.confirm',
  COMMON_CLOSE: 'common.close',
  COMMON_ONLINE: 'common.online',
  COMMON_OFFLINE: 'common.offline',
  COMMON_SAVE: 'common.save',
  COMMON_TOTAL: 'common.total',
  COMMON_CREATE: 'common.create',
  COMMON_DESCRIPTION: 'common.description',
  COMMON_CANCEL: 'common.cancel',
  COMMON_OK: 'common.ok',
  COMMON_IMPORT: 'common.import',
  COMMON_EXPORT: 'common.export',
  COMMON_CHANGE: 'common.change',
  COMMON_LANGUAGE: 'common.language',
  COMMON_TITLE: 'common.title',
  COMMON_DELETE: 'common.delete',
  COMMON_UPLOAD: 'common.upload',
  COMMON_FORCE_DELETE: 'common.force.delete',
  COMMON_EDIT: 'common.edit',
  COMMON_SETTINGS: 'common.settings',
  COMMON_REQUIRED_FIELD: 'common.required_field',
  COMMON_ALL_CHANGES_SAVED: 'common.all_changes_saved',
  COMMON_LAST_SEEN: 'common.lsat_seen',
  COMMON_TO_COMMENT: 'common.to_comment',
  COMMON_A_COMMENT: 'common.a_comment',
  COMMON_BACK: 'common.back',
  COMMON_NAME: 'common.name',
  COMMON_GROUP: 'common.group',
  COMMON_AUTHOR: 'common.author',
  COMMON_CREATED: 'common.created',
  COMMON_UPDATED: 'common.updated',
  COMMON_UPDATE: 'common.update',
  COMMON_DATE_OF_CREATION: 'common.date.of.creation',
  COMMON_DATE_OF_UPDATE: 'common.date.of.update',
  COMMON_PUBLISHED: 'common.published',
  COMMON_UNPUBLISHED: 'common.unpublished',
  COMMON_ALL: 'common.all',
  COMMON_SERVER_UNAVAILABLE: 'common.server.unavailable',
  COMMON_NO_ERROR_MSG: 'common.no.error.msg',
  COMMON_JSON_NOT_PARSING_CORRECTLY: 'common.json.not.parsing.correctly',
  COMMON_ERROR_PARSE_JSON_ERROR_RESPONSE: 'common.error.parse.json.error.response',
  COMMON_ERROR_PARSE_JSON_SUCCESS_RESPONSE: 'common.error.parse.json.success.response',
  COMMON_MONDAY: 'common.monday',
  COMMON_TUESDAY: 'common.tuesday',
  COMMON_WEDNESDAY: 'common.wednesday',
  COMMON_THURSDAY: 'common.thursday',
  COMMON_FRIDAY: 'common.friday',
  COMMON_SATURDAY: 'common.saturday',
  COMMON_SUNDAY: 'common.sunday',
  COMMON_JANUARY: 'common.january',
  COMMON_FEBRUARY: 'common.february',
  COMMON_APRIL: 'common.april',
  COMMON_MAY: 'common.may',
  COMMON_JUNE: 'common.june',
  COMMON_JULY: 'common.july',
  COMMON_AUGUST: 'common.august',
  COMMON_SEPTEMBER: 'common.september',
  COMMON_OCTOBER: 'common.october',
  COMMON_NOVEMBER: 'common.november',
  COMMON_DECEMBER: 'common.december',
  COMMON_BACK_TO_LIST: 'common.back.to.list',
  COMMON_ACTIVATE: 'common.activate',
  COMMON_ACTIVE: 'common.active',
  COMMON_NOT_ACTIVE: 'common.not.active',
  COMMON_SAVE_EXIT: 'common.save.exit',
  COMMON_NEXT: 'common.next',

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
