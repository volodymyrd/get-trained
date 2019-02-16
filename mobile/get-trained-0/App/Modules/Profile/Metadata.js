import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'PROFILE'

export const btnUpload = (localizations) => {
  return text(localizations.get(keys.PROFILE_BTN_UPLOAD), 'Upload')
}

export const btnDelete = (localizations) => {
  return text(localizations.get(keys.PROFILE_BTN_DELETE), 'Delete')
}

const keys = {
  PROFILE_BTN_UPLOAD: 'profile.btn.upload',
  PROFILE_BTN_DELETE: 'profile.btn.delete',
}
