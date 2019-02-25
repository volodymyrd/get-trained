import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Home from '../../Components/Home'
import {
  Body,
  Button,
  Container,
  Content,
  Header,
  Icon,
  Left,
  Right,
  Title
} from 'native-base'
import {MODULE} from "../../Metadata";
import HomeActions from "../../Stores/Actions";

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
    return (
        <Container>
          <Content padder>
            <Home/>
          </Content>
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
