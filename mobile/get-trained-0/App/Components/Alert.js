import { Alert } from 'react-native'
import { text } from 'App/Utils/MetadataUtils'

export const Confirm = ({ title, message, ok, cancel, localizations }) => {
  return Alert.alert(
    title,
    message,
    [
      { text: okBtn(localizations), onPress: ok },
      {
        text: cancelBtn(localizations),
        onPress: cancel,
        style: 'cancel',
      },
    ],
    { cancelable: false }
  )
}

export const okBtn = (localizations) => {
  return text(localizations.get(keys.OK), 'OK')
}

export const cancelBtn = (localizations) => {
  return text(localizations.get(keys.CANCEL), 'Cancel')
}

const keys = {
  OK: 'common.ok',
  CANCEL: 'common.cancel',
}
