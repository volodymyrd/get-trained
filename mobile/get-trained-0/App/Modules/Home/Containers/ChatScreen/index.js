// great example https://github.com/FaridSafi/react-native-gifted-chat
import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {Container, Footer} from 'native-base';
import {GiftedChat} from 'react-native-gifted-chat'
import {Map} from 'immutable'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import MainActions from "App/Stores/Main/Actions";
import HomeActions from '../../Stores/Actions'
import {openWebSocket} from "App/Utils/WebsocketUtils";
import {getUrl} from 'App/Utils/HttpUtils'
import {MODULE} from '../../Metadata'

class ChatScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, true)

  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const {fetchAccess, langCode, metadata, fetchMetadata} = this.props

    fetchAccess()

    if (langCode
        && !(metadata
            && Map.isMap(metadata)
            && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    }

    this.socket = openWebSocket()
    this.socket.onmessage = ({data}) => this.props.receiveChatMessage(data)
  }

  componentDidUpdate(prevProps) {
  }

  componentWillUnmount() {
    this.socket.close()
  }

  render() {
    const {
      fetchingMetadata,
      metadata,
      failedRetrievingMetadata,
      userProfile,
      chatMessages,
      sendChatMessage,
    } = this.props

    if (chatMessages) {
      chatMessages.map(e => {
        return e.user.avatar = getUrl(e.user.avatar)
      })
    }
    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata || !metadata || !Map.isMap(metadata)) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          {chatMessages && <GiftedChat
              messages={chatMessages.toJS()}
              user={{
                _id: userProfile.userId,
              }}
              onSend={messages => sendChatMessage(this.socket, messages[0])}
              //minComposerHeight={100}
              bottomOffset={0}
              inverted={true}
          />}
          <Footer/>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.home.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.home.root.get('failedRetrievingMetadata'),
  metadata: state.home.root.get('metadata'),

  userProfile: state.home.root.get('lightProfile'),

  fetchingChatMessages: state.home.root.get('fetchingChatMessages'),
  sendingChatMessage: state.home.root.get('sendingChatMessage'),
  chatMessages: state.home.root.get('chatMessages'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchAccess: () => dispatch(MainActions.fetchAccess()),
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
  sendChatMessage: (socket, message) =>
      dispatch(HomeActions.sendChatMessage(socket, message)),
  receiveChatMessage: (message) =>
      dispatch(HomeActions.receiveChatMessage(message))
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ChatScreen)
