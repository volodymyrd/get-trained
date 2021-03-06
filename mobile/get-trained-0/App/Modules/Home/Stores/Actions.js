import { createActions } from 'reduxsauce'

/**
 * We use reduxsauce's `createActions()` helper to easily create redux actions.
 *
 * @see https://github.com/infinitered/reduxsauce#createactions
 */
const { Types, Creators } = createActions(
  {
    fetchMetadata: ['langCode'],
    fetchMetadataLoading: null,
    fetchMetadataSuccess: ['metadata'],
    fetchMetadataFailure: null,

    fetchLightProfile: null,
    fetchLightProfileLoading: null,
    fetchLightProfileSuccess: ['lightProfile'],
    fetchLightProfileFailure: null,

    fetchIsTrainer: null,
    fetchIsTrainerLoading: null,
    fetchIsTrainerSuccess: ['isTrainer'],
    fetchIsTrainerFailure: null,

    fetchConnections: ['offset', 'pageSize'],
    fetchConnectionsLoading: null,
    fetchConnectionsSuccess: ['connections'],
    fetchConnectionsFailure: null,

    fetchTraineeRequest: ['email'],
    fetchTraineeRequestLoading: null,
    fetchTraineeRequestSuccess: null,
    fetchTraineeRequestFailure: null,

    fetchDeleteConnection: ['connectionId'],
    fetchDeleteConnectionLoading: null,
    fetchDeleteConnectionSuccess: null,
    fetchDeleteConnectionFailure: null,

    fetchAcceptConnection: ['connectionId'],
    fetchAcceptConnectionLoading: null,
    fetchAcceptConnectionSuccess: null,
    fetchAcceptConnectionFailure: null,

    fetchChatMessages: ['offset', 'pageSize', 'chatId'],
    fetchChatMessagesLoading: null,
    fetchChatMessagesSuccess: ['connections'],
    fetchChatMessagesFailure: null,

    receiveChatMessage: ['message'],
    sendChatMessage: ['socket', 'message'],
    sendChatMessageLoading: ['message'],
    sendChatMessageSuccess: null,
    sendChatMessageFailure: null,

    selectConnectionItem: ['item'],
  },
  { prefix: 'HOME_' }
)

export const HomeTypes = Types
export default Creators
