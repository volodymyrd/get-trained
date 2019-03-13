import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'TRAINEE_PROFILE'

export const titleCommonTraineeProfile = (localizations) => {
  return text(localizations.get(keys.TRAINEE_PROFILE_TITLE_COMMON), 'Common')
}

const keys = {
  TRAINEE_PROFILE_TITLE_COMMON: 'trainee_profile.title.common',
}
