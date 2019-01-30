import { Toast } from 'native-base'

const DURATION = 3000

export const info = (message) => {
  Toast.show({
    text: message,
    buttonText: 'OK',
    duration: DURATION,
  })
}

export const success = (message) => {
  Toast.show({
    text: message,
    buttonText: 'OK',
    duration: DURATION,
    type: 'success',
  })
}

export const warn = (message) => {
  Toast.show({
    text: message,
    buttonText: 'OK',
    duration: DURATION,
    type: 'warning',
  })
}

export const error = (message) => {
  Toast.show({
    text: message,
    buttonText: 'OK',
    duration: DURATION,
    type: 'danger',
  })
}
