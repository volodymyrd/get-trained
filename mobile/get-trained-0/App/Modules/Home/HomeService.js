import { postPlainWithCredentials } from 'App/Utils/HttpUtils'
import { getWebSocket } from 'App/Utils/WebsocketUtils'

const BASE_URL = 'fe/activity'

const getConnections = (offset, pageSize) => {
  return postPlainWithCredentials(
    `${BASE_URL}/getMyConnections?offset=${offset}&pageSize=${pageSize}`
  )
}

const traineeRequest = (email) => {
  return postPlainWithCredentials(`${BASE_URL}/fitness/request/trainee?email=${email}`)
}

const deleteConnection = (connectionId) => {
  return postPlainWithCredentials(`${BASE_URL}/connection/remove?connectionId=${connectionId}`)
}

const acceptConnection = (connectionId) => {
  return postPlainWithCredentials(`${BASE_URL}/connection/accept?connectionId=${connectionId}`)
}

const sendChatTextMessage = (messageText) => {
  return getWebSocket().send(messageText)
}

export const HomeService = {
  getConnections,
  traineeRequest,
  deleteConnection,
  acceptConnection,
  sendChatTextMessage,
}
