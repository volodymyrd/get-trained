import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {Container, Content} from 'native-base';
import {Map} from 'immutable'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Error from 'App/Components/Error'
import Loading from 'App/Components/Loading'
import HomeActions from '../../Stores/Actions'
import {MODULE, titleTraineeEmail, addTraineeBtn} from '../../Metadata'
import AddConnectionByEmail from '../../Components/AddConnectionByEmail';

class AddTraineeScreen extends Component {
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
      fetchingTraineeRequest,
      fetchTraineeRequest,
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
          <Content padder>
            <AddConnectionByEmail txtEmail={titleTraineeEmail(localizations)}
                                  txtBtn={addTraineeBtn(localizations)}
                                  loading={fetchingTraineeRequest}
                                  addHandler={fetchTraineeRequest}/>
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

  fetchingTraineeRequest: state.home.root.get('fetchingTraineeRequest'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(HomeActions.fetchMetadata(langCode)),
  fetchTraineeRequest: (email) =>
      dispatch(HomeActions.fetchTraineeRequest(email)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AddTraineeScreen)
