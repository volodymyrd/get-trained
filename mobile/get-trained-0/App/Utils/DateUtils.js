export const formatToDDMMYYY = (date, delimiter) => {
  if (date instanceof Date) {
    return [toStr(date.getDate()), toStr(date.getMonth() + 1), date.getFullYear()].join(delimiter)
  }
}

export const formatDDMMYYYToDate = (str, delimiter) => {
  if (typeof str === 'string' || str instanceof String) {
    const datePart = str.split(delimiter)
    return new Date(datePart[2], datePart[1] - 1, datePart[0])
  }
}

const toStr = (num) => {
  num = num.toString()
  if (num.length === 1) {
    return '0' + num
  }
  return num
}
