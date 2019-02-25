import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'HOME'

export const addTraineeBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_ADD_TRAINEE), 'Add trainee')
}

const keys = {
  HOME_TXT_: 'home.txt_',
  HOME_BTN_ADD_TRAINEE: 'home.btn.add_trainee',
}
