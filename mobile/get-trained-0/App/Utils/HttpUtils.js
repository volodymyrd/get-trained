import { create } from 'apisauce'
import { Config } from 'App/Config'
import base64 from './Base64'

export const getUrl = (url) => {
  return `${Config.API_URL}${url}`
}

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

export const dataUpload = (url, data) => {
  return _post(
    url,
    {
      'Content-Type': 'multipart/form-data',
      credentials: 'include',
    },
    data
  )

  // return apiClient.post(url, data, {
  // onUploadProgress: (e) => {
  //   console.log(e)
  //   const progress = e.loaded / e.total
  //   console.log(progress)
  // },
  // })
}

export const wrapFileForUploading = (files) => {
  const data = new FormData()
  files.forEach((file, index) => {
    data.append('file', {
      uri: file.uri,
      type: file.type,
      name: file.name,
    })
  })
  return data
}

const _post = (url, header, jsonOrData) => {
  const apiClient = _getApiClient()
  apiClient.setHeaders(header)

  if (jsonOrData) {
    return apiClient.post(url, jsonOrData).then((response) => _callBack(response))
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
