export const MODULE = 'COMMON'

export const somethingWentWrong = (localizations) => {
  return text(localizations.get(keys.SOMETHING_WENT_WRONG), 'Something went wrong!')
}

export const text = (value, defaultValue, ...args) => {
  if (value) {
    if (args.length <= 0) {
      return value
    } else {
      return value.replace(/{(\d+)}/g, (match, number) => {
        return typeof args[number] !== 'undefined' ? args[number] : match
      })
    }
  } else {
    return defaultValue
  }
}

const keys = {
  SOMETHING_WENT_WRONG: 'be_error.something_went_wrong',
}
