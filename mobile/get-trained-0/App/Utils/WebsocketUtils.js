import { Config } from 'App/Config'

const getBaseUrl = () => {
  if (Config.PORT === '80' || Config.PORT === '443') return `${Config.WS}${Config.HOST}/`
  else return `${Config.WS}${Config.HOST}:${Config.PORT}/`
}

export const getWebSocket = () => {
  return new WebSocket(`${getBaseUrl()}fe-ws`)
}

// export const send = () => {
//   console.log(`${getBaseUrl()}fe-ws`)
//   console.log(ws)
//   ws.send('something')
//   ws.onopen = () => {
//     // connection opened
//     ws.send('something')
//   }
// }

// ws.onmessage = (e) => {
//   // a message was received
//   console.log(e.data)
// }
//
// ws.onerror = (e) => {
//   // an error occurred
//   console.log(e.message)
// }
//
// ws.onclose = (e) => {
//   // connection closed
//   console.log(e.code, e.reason)
// }
