import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import {Map} from 'immutable'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import Connections from '../../Components/Connections'
import {
  Body,
  Button,
  Container,
  Content,
  Header,
  Footer,
  FooterTab,
  Icon,
  Left,
  Right,
  Title,
  Text,
} from 'native-base'
import HomeActions from '../../Stores/Actions'
import {MODULE, titleHome, titleAddTrainee, addTraineeBtn} from '../../Metadata'

class HomeScreen extends Component {
  static navigationOptions = ({navigation}) => setNavigationOptions(navigation)

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
    this.props.fetchConnections(0, 10, [])
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
          <Connections isTrainer={isTrainer}
                       refreshing={fetchingConnections}
                       refreshHandler={this._getConnections}
                       deleteHandler={() => console.log('deleteHandler')}
                       connections={connections}/>
          {/*<Content padder>*/}
          {/*</Content>*/}
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
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
  fetchLightProfile: () => dispatch(HomeActions.fetchLightProfile()),
  fetchIsTrainer: () => dispatch(HomeActions.fetchIsTrainer()),
  fetchConnections: (offset, pageSize, messages) => dispatch(
      HomeActions.fetchConnections(offset, pageSize, messages)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomeScreen)
