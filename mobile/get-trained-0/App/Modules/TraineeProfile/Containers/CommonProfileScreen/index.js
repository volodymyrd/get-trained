import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  Container,
  Content,
  Form,
} from 'native-base'
import Loading from 'App/Components/Loading';
import ButtonWithLoader from 'App/Components/ButtonWithLoader';
import CommonPicker from 'App/Components/CommonPicker'
import InputDatePicker from 'App/Components/InputDatePicker'
import {Map} from 'immutable';
import TraineeProfile from '../../Stores/Actions'
import {MODULE} from '../../Metadata'

import style from './style';

class CommonProfileScreen extends Component {
  static navigationOptions = ({navigation, navigationOptions}) => {
    return {
      title: 'common1',
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
      birthdayStr: this.datePicker.getSelectedFormattedDate()
    })
  }

  render() {
    const {
      langCode,
      metadata,
      fetchingGenders,
      fetchTraineeProfile,
      fetchingUpdateTraineeProfile,
      genders,
      traineeProfile
    } = this.props

    if (fetchingGenders
        && fetchTraineeProfile
        && !Map.isMap(genders)
        && !Map.isMap(traineeProfile)) {
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
                               labelName={'Birthday:'}
                               placeholder={'Select date of birthday'}
                               date={traineeProfile.get('birthdayStr')}/>

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
  fetchMetadata: (langCode) => dispatch(TraineeProfile.fetchMetadata(langCode)),
  fetchGenders: () => dispatch(TraineeProfile.fetchGenders()),
  fetchTraineeProfile: (traineeUserId) =>
      dispatch(TraineeProfile.fetchTraineeProfile(traineeUserId)),
  fetchUpdateTraineeProfile: (traineeProfile) =>
      dispatch(TraineeProfile.fetchUpdateTraineeProfile(traineeProfile)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CommonProfileScreen)
