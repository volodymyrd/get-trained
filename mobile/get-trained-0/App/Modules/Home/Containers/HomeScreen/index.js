import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {getWebSocket} from 'App/Utils/WebsocketUtils'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import {Map} from 'immutable'
import {pageSize} from '../../Constants'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import Connections from '../../Components/Connections'
import {
  Container,
  Footer,
  FooterTab,
  Button,
  Text,
} from 'native-base'
import HomeActions from '../../Stores/Actions'
import {MODULE, titleHome, titleAddTrainee, addTraineeBtn} from '../../Metadata'

class HomeScreen extends Component {
  static navigationOptions = ({navigation}) => setNavigationOptions(navigation)

  constructor(props) {
    super(props);

    //this.socket = getWebSocket()
    //this.socket.onmessage = ({data}) => console.log(data)
  }

  componentDidMount() {
    const {langCode, metadata, fetchMetadata, navigation} = this.props

    if (langCode
        && !(metadata
            && Map.isMap(metadata)
            && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    } else {
      navigation.setParams({title: titleHome(metadata.get('localizations'))})
    }

    if (!this.props.lightProfile) {
      this.props.fetchLightProfile()
    }

    this.props.fetchIsTrainer()
    this._getConnections()

    // this.socket.onopen = () => this.socket.send("");
  }

  componentDidUpdate(prevProps) {
    const {metadata, navigation} = this.props
    if (prevProps.metadata
        && !Map.isMap(prevProps.metadata)
        && Map.isMap(metadata)) {
      navigation.setParams({title: titleHome(metadata.get('localizations'))})
    }
  }

  _getConnections = () => {
    this.props.fetchConnections(0, pageSize, [])
  }

  render() {
    const {
      navigation,
      fetchingMetadata,
      metadata,
      failedRetrievingMetadata,
      isTrainer,
      fetchingConnections,
      connections,
      fetchingTraineeRequest,
      fetchDeleteConnection,
      fetchingDeleteConnection,
      fetchAcceptConnection,
      fetchingAcceptConnection,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata
        || !metadata
        || !Map.isMap(metadata)
        || !connections
        || !Map.isMap(connections)) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          {/*<Button onPress={send}><Text>ws</Text></Button>*/}
          <Connections navigation={navigation}
                       isTrainer={isTrainer}
                       refreshing={fetchingConnections}
                       refreshHandler={this._getConnections}
                       deleteHandler={fetchDeleteConnection}
                       acceptHandler={fetchAcceptConnection}
                       connections={connections}
                       localizations={localizations}
                       fetches={{
                         fetchingDeleteConnection,
                         fetchingAcceptConnection
                       }}/>

          {isTrainer &&
          <Footer>
            <FooterTab>
              <ButtonWithLoader
                  title={addTraineeBtn(localizations)}
                  //style={styles.btn}
                  disabled={fetchingTraineeRequest}
                  loading={fetchingTraineeRequest}
                  icon='md-person-add'
                  onPressHandler={() => navigation.navigate(
                      'AddTrainee',
                      {title: titleAddTrainee(localizations)})}
              />
            </FooterTab>
          </Footer>
          }
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.home.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.home.root.get('failedRetrievingMetadata'),
  metadata: state.home.root.get('metadata'),

  isTrainer: state.home.root.get('isTrainer'),

  fetchingLightProfile: state.home.root.get('fetchingLightProfile'),
  lightProfile: state.home.root.get('lightProfile'),

  fetchingConnections: state.home.root.get('fetchingConnections'),
  connections: state.home.root.get('connections'),

  fetchingTraineeRequest: state.home.root.get('fetchingTraineeRequest'),

  fetchingDeleteConnection: state.home.root.get('fetchingDeleteConnection'),
  fetchingAcceptConnection: state.home.root.get('fetchingAcceptConnection'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
  fetchLightProfile: () => dispatch(HomeActions.fetchLightProfile()),
  fetchIsTrainer: () => dispatch(HomeActions.fetchIsTrainer()),
  fetchConnections: (offset, pageSize, messages) => dispatch(
      HomeActions.fetchConnections(offset, pageSize, messages)),
  fetchDeleteConnection: (connectionId) =>
      dispatch(HomeActions.fetchDeleteConnection(connectionId)),
  fetchAcceptConnection: (connectionId) =>
      dispatch(HomeActions.fetchAcceptConnection(connectionId)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomeScreen)
