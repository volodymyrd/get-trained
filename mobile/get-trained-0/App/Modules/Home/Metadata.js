import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'HOME'

export const successAvatarUpload = (localizations) => {
  return text(
    localizations.get(keys.PROFILE_TXT_SUCCESS_AVATAR_UPLOAD),
    'Avatar uploaded successfully'
  )
}

const keys = {
  HOME_TXT_: 'home.txt_',
}
