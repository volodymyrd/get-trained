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
  return text(localizations.get(keys.SETTINGS_BTN_CHANGE_PASSWORD), 'Change password')
}

export const txtBecomeTrainerBtn = (localizations) => {
  return text(localizations.get(keys.SETTINGS_BTN_BECOME_TRAINER), 'Become a trainer')
}

export const txtRemoveTrainerBtn = (localizations) => {
  return text(localizations.get(keys.SETTINGS_BTN_REMOVE_TRAINER), 'Remove the trainer')
}

const keys = {
  SETTINGS_TXT_OLD_PASSWORD: 'settings.txt.new_password',
  SETTINGS_TXT_NEW_PASSWORD: 'settings.txt.new_password',
  SETTINGS_TXT_REPEAT_PASSWORD: 'settings.txt.repeat_password',
  SETTINGS_BTN_CHANGE_PASSWORD: 'settings.btn.change_password',
  SETTINGS_BTN_BECOME_TRAINER: 'settings.btn.become_trainer',
  SETTINGS_BTN_REMOVE_TRAINER: 'settings.btn.remove_trainer',
}
