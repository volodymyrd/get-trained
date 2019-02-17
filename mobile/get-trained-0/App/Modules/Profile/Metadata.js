import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'PROFILE'

export const successAvatarUpload = (localizations) => {
  return text(
    localizations.get(keys.PROFILE_TXT_SUCCESS_AVATAR_UPLOAD),
    'Avatar uploaded successfully'
  )
}

export const btnAvatarUpload = (localizations) => {
  return text(localizations.get(keys.PROFILE_BTN_AVATAR_UPLOAD), 'Upload Avatar')
}

export const btnAvatarDelete = (localizations) => {
  return text(localizations.get(keys.PROFILE_BTN_AVATAR_DELETE), 'Delete Avatar')
}

const keys = {
  PROFILE_TXT_SUCCESS_AVATAR_UPLOAD: 'profile.txt_success_avatar_upload',
  PROFILE_BTN_AVATAR_UPLOAD: 'profile.btn_avatar_upload',
  PROFILE_BTN_AVATAR_DELETE: 'profile.btn_avatar_delete',
}
