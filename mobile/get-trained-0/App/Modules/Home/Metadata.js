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

export const titleConnectionDeleteRequest = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_CONNECTION_DELETE_REQUEST), 'Deleting request')
}

export const confirmConnectionDeleteRequest = (localizations) => {
  return text(
    localizations.get(keys.HOME_CONFIRM_CONNECTION_DELETE_REQUEST),
    'Are you sure to delete the request?'
  )
}

const keys = {
  HOME_TXT_: 'home.txt_',
  HOME_TITLE_HOME: 'home.title.home',
  HOME_TITLE_ADD_TRAINEE: 'home.title.add_trainee',
  HOME_BTN_ADD_TRAINEE: 'home.btn.add_trainee',
  HOME_TITLE_CONNECTION_DELETE_REQUEST: 'home.title.connection_delete_request',
  HOME_CONFIRM_CONNECTION_DELETE_REQUEST: 'home.confirm.connection_delete_request',
}
