import { create } from 'apisauce'
import { Config } from 'App/Config'
import base64 from './Base64'

const apiClient = create({
  /**
   * Import the config from the App/Config/index.js file
   */
  baseURL: Config.API_URL,
  headers: {},
  timeout: 5000,
})

export const postWithCredentials = (url) => {
  apiClient.setHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    credentials: 'include',
  })

  return apiClient.post(url).then((response) => {
    if (response.ok) {
      return response
    }

    return null
  })
}

export const signIn = (email, password, lang) => {
  apiClient.setHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    Authorization: 'Basic ' + base64.btoa(unescape(encodeURIComponent(email + ':' + password))),
    credentials: 'include',
    lang: lang,
  })

  return apiClient.post('fe/auth/signin').then((response) => {
    if (response.ok) {
      return response
    }

    return null
  })
}
