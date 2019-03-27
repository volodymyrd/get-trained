import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  Container,
  Content,
  Form,
} from 'native-base'
import Loading from 'App/Components/Loading'
import ButtonWithLoader from 'App/Components/ButtonWithLoader';
import CommonPicker from 'App/Components/CommonPicker'
import InputDatePicker from 'App/Components/InputDatePicker'
import NumberPicker from 'App/Components/NumberPicker'
import {Map} from 'immutable'
import TraineeProfileActions from '../../Stores/Actions'
import {MODULE} from '../../Metadata'

import style from './style';

class CommonProfileScreen extends Component {
  static navigationOptions = ({navigation, navigationOptions}) => {
    return {
      title: 'Common',
    }
  }

  componentDidMount() {
    const {
      langCode,
      metadata,
      fetchMetadata,
      selectedTrainee,
      fetchGenders,
      fetchTraineeProfile
    } = this.props

    if (langCode
        && !(metadata
            && Map.isMap(metadata)
            && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    }

    fetchGenders()
    fetchTraineeProfile(this.props.navigation.getParam('item').traineeUserId)
  }

  componentDidUpdate(prevProps) {
  }

  updateProfile = () => {
    this.props.fetchUpdateTraineeProfile({
      userId: this.props.traineeProfile.get('userId'),
      gender: this.genderPicker.getSelectedValue(),
      birthdayStr: this.datePicker.getSelectedFormattedDate(),
      height: this.height.getSelectedValue(),
    })
  }

  render() {
    const {
      langCode,
      locale,
      metadata,
      fetchingGenders,
      fetchingTraineeProfile,
      fetchTraineeProfile,
      fetchingUpdateTraineeProfile,
      genders,
      traineeProfile
    } = this.props

    if (fetchingGenders
        || fetchingTraineeProfile
        || !Map.isMap(genders)
        || !Map.isMap(traineeProfile)) {
      return <Loading/>
    }

    return (
        <Container>
          <Content>
            <Form style={style.form}>
              <CommonPicker ref={c => this.genderPicker = c}
                            labelName={'Gender:'}
                            placeholder={'Select gender'}
                            value={traineeProfile.get('gender')}
                            values={genders}
              />

              <InputDatePicker ref={c => this.datePicker = c}
                               locale={locale}
                               labelName={'Birthday:'}
                               placeholder={'Select date of birthday'}
                               date={traineeProfile.get('birthdayStr')}/>
              <NumberPicker ref={c => this.height = c}
                            labelName={'Height:'}
                            min={0}
                            max={300}
                            value={traineeProfile.get('height')}
              />
              <ButtonWithLoader
                  title={'save'}
                  style={{marginTop: 40}}
                  disabled={fetchingUpdateTraineeProfile}
                  loading={fetchingUpdateTraineeProfile}
                  onPressHandler={this.updateProfile}
              />
            </Form>
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),
  locale: state.main.get('locale'),

  selectedTrainee: state.home.root.get('selectConnectionItem'),

  fetchingMetadata: state.traineeProfile.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.traineeProfile.root.get(
      'failedRetrievingMetadata'),
  metadata: state.traineeProfile.root.get('metadata'),

  fetchingGenders: state.traineeProfile.root.get('fetchingGenders'),
  fetchingTraineeProfile: state.traineeProfile.root.get(
      'fetchingTraineeProfile'),
  fetchingUpdateTraineeProfile: state.traineeProfile.root.get(
      'fetchingUpdateTraineeProfile'),

  genders: state.traineeProfile.root.get('genders'),
  traineeProfile: state.traineeProfile.root.get('traineeProfile'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(
      TraineeProfileActions.fetchMetadata(langCode)),
  fetchGenders: () => dispatch(TraineeProfileActions.fetchGenders()),
  fetchTraineeProfile: (traineeUserId) =>
      dispatch(TraineeProfileActions.fetchTraineeProfile(traineeUserId)),
  fetchUpdateTraineeProfile: (traineeProfile) =>
      dispatch(TraineeProfileActions.fetchUpdateTraineeProfile(traineeProfile)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CommonProfileScreen)
