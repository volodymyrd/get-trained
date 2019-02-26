import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
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
        && !(metadata && metadata.size && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    } else {
      navigation.setParams({title: titleHome(metadata.get('localizations'))})
    }

    if (!this.props.lightProfile) {
      this.props.fetchLightProfile()
    }

    this.props.fetchIsTrainer()
    this.props.fetchConnections(0, 10, [])
  }

  componentDidUpdate(prevProps) {
    const {metadata, navigation} = this.props
    if (prevProps.metadata && !prevProps.metadata.size && metadata.size) {
      navigation.setParams({title: titleHome(metadata.get('localizations'))})
    }
  }

  render() {
    const {
      navigation,
      fetchingMetadata,
      metadata,
      failedRetrievingMetadata,
      isTrainer,
      connections,
      fetchingTraineeRequest,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata
        || !metadata 
        || !metadata.size
        || !connections
        || !connections.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          <Content padder>
            <Connections isTrainer={isTrainer} connections={connections}/>
          </Content>
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
