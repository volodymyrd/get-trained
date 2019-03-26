import { text } from 'App/Utils/MetadataUtils'

export const MODULE = 'HOME'

export const cancel = (localizations) => {
  return text(localizations.get(keys.COMMON_CANCEL), 'Cancel')
}

export const titleHome = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_HOME), 'Home')
}

export const titleAddTrainee = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_ADD_TRAINEE), 'Add a Trainee')
}

export const titleTraineeEmail = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_TRAINEE_EMAIL), 'Email of a Trainee')
}

export const addTraineeBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_ADD_TRAINEE), 'Add trainee')
}

export const connectionRequestDeleteBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_CONNECTION_REQUEST_DELETE), 'Delete request')
}

export const connectionRequestRejectBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_CONNECTION_REQUEST_REJECT), 'Reject')
}

export const connectionRequestAcceptBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_CONNECTION_REQUEST_REJECT), 'Accept')
}

export const connectionDeleteBtn = (localizations) => {
  return text(localizations.get(keys.HOME_BTN_CONNECTION_DELETE), 'Delete')
}

export const titleConnectionRequestDelete = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_CONNECTION_REQUEST_DELETE), 'Deleting request')
}

export const titleConnectionRequestReject = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_CONNECTION_REQUEST_REJECT), 'Rejecting request')
}

export const titleConnectionDelete = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_CONNECTION_DELETE), 'Deleting connection')
}

export const confirmConnectionRequestDelete = (localizations) => {
  return text(
    localizations.get(keys.HOME_CONFIRM_CONNECTION_REQUEST_DELETE),
    'Are you sure to delete the request?'
  )
}

export const confirmConnectionRequestReject = (localizations) => {
  return text(
    localizations.get(keys.HOME_CONFIRM_CONNECTION_REQUEST_REJECT),
    'Are you sure to reject the request?'
  )
}

export const confirmConnectionDelete = (localizations) => {
  return text(
    localizations.get(keys.HOME_CONFIRM_CONNECTION_DELETE),
    'Are you sure to delete the connection?'
  )
}

export const traineeProfile = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_TRAINEE_PROFILE), 'Profile')
}

export const titleChat = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_CHAT), 'Chat')
}

export const titleActions = (localizations) => {
  return text(localizations.get(keys.HOME_TITLE_ACTIONS), 'Actions for:')
}

const keys = {
  COMMON_CANCEL: 'common.cancel',
  HOME_TXT_: 'home.txt_',
  HOME_TITLE_HOME: 'home.title.home',
  HOME_TITLE_ADD_TRAINEE: 'home.title.add_trainee',
  HOME_TITLE_TRAINEE_EMAIL: 'home.title.trainee_email',
  HOME_BTN_ADD_TRAINEE: 'home.btn.add_trainee',
  HOME_BTN_CONNECTION_REQUEST_DELETE: 'home.btn.connection_request_delete',
  HOME_BTN_CONNECTION_REQUEST_REJECT: 'home.btn.connection_request_reject',
  HOME_BTN_CONNECTION_REQUEST_ACCEPT: 'home.btn.connection_request_accept',
  HOME_BTN_CONNECTION_DELETE: 'home.btn.connection_delete',
  HOME_TITLE_CONNECTION_REQUEST_DELETE: 'home.title.connection_request_delete',
  HOME_TITLE_CONNECTION_REQUEST_REJECT: 'home.title.connection_request_reject',
  HOME_TITLE_CONNECTION_DELETE: 'home.title.connection_delete',
  HOME_CONFIRM_CONNECTION_REQUEST_DELETE: 'home.confirm.connection_request_delete',
  HOME_CONFIRM_CONNECTION_REQUEST_REJECT: 'home.confirm.connection_request_reject',
  HOME_CONFIRM_CONNECTION_DELETE: 'home.confirm.connection_delete',
  HOME_TITLE_ACTIONS: 'home.title.actions',
  HOME_TITLE_TRAINEE_PROFILE: 'home.title.trainee_profile',
  HOME_TITLE_CHAT: 'home.title.chat',
}
