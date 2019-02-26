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

export const HomeService = {
  getConnections,
  traineeRequest,
}
