import { postPlainWithCredentials } from 'App/Utils/HttpUtils'

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

export const HomeService = {
  getConnections,
  traineeRequest,
  deleteConnection,
}
