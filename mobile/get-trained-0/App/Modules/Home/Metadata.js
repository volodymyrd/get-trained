import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'HOME'

export const titleHome = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_HOME), 'Home')
}

export const titleAddTrainee = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_ADD_TRAINEE), 'Add a Trainee')
}

export const addTraineeBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_ADD_TRAINEE), 'Add trainee')
}

const keys = {
  HOME_TXT_: 'home.txt_',
  HOME_TITLE_HOME: 'home.title.home',
  HOME_TITLE_ADD_TRAINEE: 'home.title.add_trainee',
  HOME_BTN_ADD_TRAINEE: 'home.btn.add_trainee',
}
