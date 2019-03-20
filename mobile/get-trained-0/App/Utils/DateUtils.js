export const formatToDDMMYYY = (date, delimiter) => {
  if (date instanceof Date) {
    return [toStr(date.getDate()), toStr(date.getMonth() + 1), date.getFullYear()].join(delimiter)
  }
}

const toStr = (num) => {
  num = num.toString()
  if (num.length === 1) {
    return '0' + num
  }
  return num
}
