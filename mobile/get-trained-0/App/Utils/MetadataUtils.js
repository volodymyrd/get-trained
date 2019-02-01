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
