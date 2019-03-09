import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  View,
  Container,
  Content,
  Item,
  Input,
  Button,
  Text,
} from "native-base";
import Icon from 'react-native-vector-icons/Ionicons';
import {Map} from 'immutable'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import HomeActions from '../../Stores/Actions'
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
          <Content>
            <Text>Chat</Text>
          </Content>
          <View style={{marginBottom: 30, alignItems: 'center',}}>
            <Item rounded style={{width: '90%'}}>
              <Input placeholder='Type a message...'/>
              <Button transparent><Icon name='ios-more' size={30}/></Button>
            </Item>
          </View>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.home.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.home.root.get('failedRetrievingMetadata'),
  metadata: state.home.root.get('metadata'),

})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ChatScreen)
