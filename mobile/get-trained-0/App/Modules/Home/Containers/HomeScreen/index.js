import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import Home from '../../Components/Home'
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
import {MODULE, addTraineeBtn} from '../../Metadata';
import HomeActions from '../../Stores/Actions';
import Error from "../../../Settings/Containers/SettingsScreen";

class HomeScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, 'Home')

  componentDidMount() {
    if (this.props.langCode
        && !(this.props.metadata
            && this.props.metadata.size
            && this.props.metadata.get('module') === MODULE)) {
      this.props.fetchMetadata(this.props.langCode.toUpperCase())
    }
    if (!this.props.lightProfile) {
      this.props.fetchLightProfile()
    }

    this.props.fetchIsTrainer()
    this.props.fetchConnections(0, 10, [])
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    const {
      fetchingMetadata,
      metadata,
      failedRetrievingMetadata,
      isTrainer,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata || !metadata || !metadata.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          <Content padder>
            <Home/>
          </Content>
          {isTrainer &&
          <Footer>
            <FooterTab>
              <ButtonWithLoader
                  title={addTraineeBtn(localizations)}
                  //style={styles.btn}
                  //disabled={}
                  //loading={}
                  icon='md-person-add'
                  onPressHandler={() => console.log('add')}
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

  isTrainer: state.settings.root.get('isTrainer'),

  fetchingLightProfile: state.home.root.get('fetchingLightProfile'),
  lightProfile: state.home.root.get('lightProfile'),
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
