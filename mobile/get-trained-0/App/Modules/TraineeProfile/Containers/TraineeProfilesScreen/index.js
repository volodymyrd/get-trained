import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  Container,
  Text,
  Footer,
  FooterTab,
} from 'native-base'
import ModalDialog from 'App/Components/ModalDialog'
import ButtonWithLoader from 'App/Components/ButtonWithLoader';
import TraineeProfiles from '../../Components/TraineeProfiles'
import BodyProfile from '../../Components/BodyProfile'
import {Map} from 'immutable';
import {MODULE} from '../../Metadata';
import TraineeProfileActions from '../../Stores/Actions';

class TraineeProfilesScreen extends Component {
  static navigationOptions = ({navigation, navigationOptions}) => {
    return {
      title: 'Profiles',
    }
  }

  componentDidMount() {
    const {
      langCode,
      metadata,
      fetchMetadata,
    } = this.props

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
    return (
        <Container>
          <ModalDialog ref={c => this.modal = c} title={'Measures'}>
            <BodyProfile/>
          </ModalDialog>
          <TraineeProfiles/>
          <Footer>
            <FooterTab>
              <ButtonWithLoader
                  title={'add measure'}
                  //style={styles.btn}
                  //disabled={fetchingTraineeRequest}
                  //loading={fetchingTraineeRequest}
                  ionicons='ios-man'
                  iconSize={30}
                  onPressHandler={() => this.modal.setModalVisible(true)}
              />
            </FooterTab>
          </Footer>
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
  fetchMetadata: (langCode) => dispatch(
      TraineeProfileActions.fetchMetadata(langCode)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TraineeProfilesScreen)
