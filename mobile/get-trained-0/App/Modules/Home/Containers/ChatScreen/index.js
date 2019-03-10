// great example https://github.com/FaridSafi/react-native-gifted-chat
import React, {Component} from 'react'
import {ScrollView} from 'react-native'
import connect from 'react-redux/es/connect/connect'
import {
  View,
  Container,
  Content,
  Item,
  Text,
} from 'native-base';
import {Map} from 'immutable'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import HomeActions from '../../Stores/Actions'
import ChatInput from '../../Components/Chat/ChatInput'
import {MODULE} from '../../Metadata'

class ChatScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, true)

  componentDidMount() {
    const {langCode, metadata, fetchMetadata} = this.props

    if (langCode
        && !(metadata
            && Map.isMap(metadata)
            && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    }
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    const {
      fetchingMetadata,
      metadata,
      failedRetrievingMetadata,
      chatMessages,
      sendChatMessage,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata || !metadata || !Map.isMap(metadata)) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          <View style={{flex: 1, justifyContent: 'flex-end', marginBottom: 36}}>
            {chatMessages && chatMessages.toJS().map(
                (v, i) =>
                    <View key={i} style={{marginBottom: 20, marginTop: 20}}>
                      <Item rounded
                            style={{width: '70%', height: 50, paddingLeft: 20}}>
                        <Text>{v.message}</Text>
                      </Item>
                    </View>
            )}
          </View>
          <ChatInput placeholder={'Type a message...'}
                     sendDisabled={false}
                     sendHandler={(message) => sendChatMessage(message)}/>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.home.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.home.root.get('failedRetrievingMetadata'),
  metadata: state.home.root.get('metadata'),

  fetchingChatMessages: state.home.root.get('fetchingChatMessages'),
  sendingChatMessage: state.home.root.get('sendingChatMessage'),
  chatMessages: state.home.root.get('chatMessages'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
  sendChatMessage: (message) => dispatch(HomeActions.sendChatMessage(message)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ChatScreen)
