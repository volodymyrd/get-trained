import { Alert } from 'react-native'

export const Confirm = (title, message, ok, cancel) => {
  return Alert.alert(
    title,
    message,
    [
      { text: 'OK', onPress: ok },
      {
        text: 'Cancel',
        onPress: cancel,
        style: 'cancel',
      },
    ],
    { cancelable: false }
  )
}
