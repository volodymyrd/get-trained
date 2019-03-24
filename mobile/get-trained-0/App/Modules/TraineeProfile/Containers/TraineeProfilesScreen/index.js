import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  Container,
  Footer,
  FooterTab,
} from 'native-base'
import {Map} from 'immutable';
import {formatToDDMMYYY} from 'App/Utils/DateUtils'
import Loading from 'App/Components/Loading'
import ModalDialog from 'App/Components/ModalDialog'
import ButtonWithLoader from 'App/Components/ButtonWithLoader';
import TraineeProfiles from '../../Components/TraineeProfiles'
import BodyProfile from '../../Components/BodyProfile'
import TraineeProfileActions from '../../Stores/Actions';
import {MODULE} from '../../Metadata';

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

    this._getTraineeProfiles()
  }

  componentDidUpdate(prevProps) {
  }

  _getTraineeProfiles = () => {
    const {navigation, fetchTraineeFitnessProfiles} = this.props

    const {traineeUserId, connectionId} = navigation.getParam('item')
    fetchTraineeFitnessProfiles({
      traineeUserId,
      connectionId,
      pageable: {
        page: 0,
        size: 10
      }
    })
  }

  render() {
    const {
      locale,
      metadata,
      navigation,
      fetchingMetadata,
      fetchingTraineeFitnessProfiles,
      traineeFitnessProfiles,
      newTraineeFitnessProfile,
      traineeFitnessProfile,
      fetchingUpdateTraineeFitnessProfile,
      fetchUpdateTraineeFitnessProfile
    } = this.props

    if (fetchingMetadata
        || !metadata
        || !Map.isMap(metadata)
        || !traineeFitnessProfiles
        || !Map.isMap(traineeFitnessProfiles)) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    const traineeUserId = navigation.getParam('item').traineeUserId
    const traineeProfileId =
        traineeFitnessProfile && Map.isMap(traineeFitnessProfile) ?
            traineeFitnessProfile.get('traineeProfileId') : undefined

    return (
        <Container>
          <ModalDialog ref={c => this.modal = c} title={'Measures'}>
            <BodyProfile
                locale={locale}
                traineeProfileId={traineeProfileId}
                fetchingUpdateTraineeFitnessProfile={fetchingUpdateTraineeFitnessProfile}
                fetchUpdateTraineeFitnessProfile={fetchUpdateTraineeFitnessProfile}/>
          </ModalDialog>
          <TraineeProfiles
              profiles={traineeFitnessProfiles}
              refreshing={fetchingTraineeFitnessProfiles}
              refreshHandler={this._getTraineeProfiles}
          />
          <Footer>
            <FooterTab>
              <ButtonWithLoader
                  title={'add measure'}
                  ionicons='ios-man'
                  iconSize={30}
                  onPressHandler={() => {
                    newTraineeFitnessProfile()
                    this.modal.setModalVisible(
                        true,
                        {
                          traineeFitnessProfile: {
                            traineeUserId,
                            measure: formatToDDMMYYY(new Date(), '.')
                          }
                        })
                  }
                  }
              />
            </FooterTab>
          </Footer>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),
  locale: state.main.get('locale'),

  fetchingMetadata: state.home.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.home.root.get('failedRetrievingMetadata'),
  metadata: state.home.root.get('metadata'),

  fetchingTraineeFitnessProfiles: state.traineeProfile.root.get(
      'fetchingTraineeFitnessProfiles'),
  traineeFitnessProfiles: state.traineeProfile.root.get(
      'traineeFitnessProfiles'),

  traineeFitnessProfile: state.traineeProfile.root.get('traineeFitnessProfile'),
  fetchingUpdateTraineeFitnessProfile: state.traineeProfile.root.get(
      'fetchingUpdateTraineeFitnessProfile'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(
      TraineeProfileActions.fetchMetadata(langCode)),
  newTraineeFitnessProfile: () =>
      dispatch(TraineeProfileActions.newTraineeFitnessProfile()),
  fetchUpdateTraineeFitnessProfile: (traineeFitnessProfile) =>
      dispatch(TraineeProfileActions
      .fetchUpdateTraineeFitnessProfile(traineeFitnessProfile)),
  fetchTraineeFitnessProfiles: (traineeFitnessProfilesConstraint) =>
      dispatch(TraineeProfileActions
      .fetchTraineeFitnessProfiles(traineeFitnessProfilesConstraint)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TraineeProfilesScreen)
