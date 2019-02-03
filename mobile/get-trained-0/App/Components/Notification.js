import { Toast } from 'native-base'

const DURATION = 3000

export const info = (message) => {
  _show(message)
}

export const success = (message) => {
  _show(message, 'success')
}

export const warn = (message) => {
  _show(message, 'warning')
}

export const error = (message) => {
  _show(message, 'danger')
}

const _show = (message, type) => {
  Toast.show({
    text: message,
    buttonText: 'OK',
    duration: DURATION,
    type: type,
    position: 'top',
  })
}
