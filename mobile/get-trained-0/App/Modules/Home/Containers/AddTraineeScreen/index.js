import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {Container, Content, Text} from "native-base";
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import HomeActions from '../../Stores/Actions'
import {MODULE, addTraineeBtn} from '../../Metadata'
import AddConnectionByEmail from "../../Components/AddConnectionByEmail";

class HomeScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, true)

  componentDidMount() {
    const {langCode, metadata, fetchMetadata} = this.props

    if (langCode
        && !(metadata && metadata.size && metadata.get('module') === MODULE)) {
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

    if (fetchingMetadata || !metadata || !metadata.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          <Content padder>
            <AddConnectionByEmail txtEmail={'Email of a Trainee'}
                                  txtBtn={'Add'}
                                  loading={false}
                                  addHandler={() => console.log('add')}/>
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
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomeScreen)
