import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'SETTINGS'

export const txtOldPassword = (localizations) => {
  return text(localizations.get(keys.SETTINGS_TXT_OLD_PASSWORD), 'Old password')
}

export const txtNewPassword = (localizations) => {
  return text(localizations.get(keys.SETTINGS_TXT_NEW_PASSWORD), 'New password')
}

export const txtRepeatPassword = (localizations) => {
  return text(localizations.get(keys.SETTINGS_TXT_REPEAT_PASSWORD), 'Repeat password')
}

export const txtChangePasswordBtn = (localizations) => {
  return text(localizations.get(keys.SETTINGS_TXT_CHANGE_PASSWORD_BTN), 'Change password')
}

const keys = {
  SETTINGS_TXT_OLD_PASSWORD: 'settings.txt.new_password',
  SETTINGS_TXT_NEW_PASSWORD: 'settings.txt.new_password',
  SETTINGS_TXT_REPEAT_PASSWORD: 'settings.txt.repeat_password',
  SETTINGS_TXT_CHANGE_PASSWORD_BTN: 'settings.txt.change_password_btn',
}
