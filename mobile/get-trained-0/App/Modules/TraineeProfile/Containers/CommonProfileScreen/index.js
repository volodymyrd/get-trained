import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {View, Text} from 'native-base'
import {Map} from "immutable";
import TraineeProfile from '../../Stores/Actions'
import {MODULE} from '../../Metadata'

class CommonProfileScreen extends Component {
  static navigationOptions = ({navigation, navigationOptions}) => {
    return {
      title: 'common1',
    }
  }

  componentDidMount() {
    const {langCode, metadata, fetchMetadata, selectedTrainee, fetchTraineeProfile} = this.props

    if (langCode
        && !(metadata
            && Map.isMap(metadata)
            && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    }

    //fetchTraineeProfile(this.props.navigation.getParam('item').traineeUserId)
  }

  componentDidUpdate(prevProps) {
  }


  render() {
    return <View><Text>CommonProfileScreen</Text></View>
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  selectedTrainee: state.home.root.get('selectConnectionItem'),

  fetchingMetadata: state.traineeProfile.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.traineeProfile.root.get(
      'failedRetrievingMetadata'),
  metadata: state.traineeProfile.root.get('metadata'),

})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(TraineeProfile.fetchMetadata(langCode)),
  fetchTraineeProfile: (traineeUserId) =>
      dispatch(TraineeProfile.fetchTraineeProfile(traineeUserId)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CommonProfileScreen)
