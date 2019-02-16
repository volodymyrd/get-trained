import { create } from 'apisauce'
import { Config } from 'App/Config'
import base64 from './Base64'

export const post = (url, json) => {
  return _post(url, { 'Content-Type': 'application/json' }, json)
}

export const postWithCredentials = (url, json) => {
  return _post(
    url,
    {
      'Content-Type': 'application/x-www-form-urlencoded',
      credentials: 'include',
    },
    json
  )
}

export const postPlainWithCredentials = (url, json) => {
  return _post(
    url,
    {
      'Content-Type': 'text/plain;charset=UTF-8',
      credentials: 'include',
    },
    json
  )
}

export const signIn = (email, password, lang) => {
  return _post('fe/auth/signin', {
    'Content-Type': 'application/x-www-form-urlencoded',
    Authorization: 'Basic ' + base64.btoa(unescape(encodeURIComponent(email + ':' + password))),
    credentials: 'include',
    lang: lang,
  })
}

const _post = (url, header, json) => {
  const apiClient = _getApiClient()
  apiClient.setHeaders(header)

  if (json) {
    return apiClient.post(url, json).then((response) => _callBack(response))
  } else {
    return apiClient.post(url).then((response) => _callBack(response))
  }
}

const _getApiClient = () => {
  return create({
    baseURL: Config.API_URL,
    headers: {},
    timeout: 5000,
  })
}

const _callBack = (response) => {
  return response
}
