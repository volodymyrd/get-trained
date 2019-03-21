import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  Container,
  Content,
  Form,
  Header,
  Body,
  Left,
  Right,
  Item,
  Label,
  Text,
  Picker,
  Title,
  Button,
  Icon, View,
} from 'native-base'
import Loading from "App/Components/Loading";
import ButtonWithLoader from "App/Components/ButtonWithLoader";
import InputDatePicker from 'App/Components/InputDatePicker'
import {Map} from "immutable";
import TraineeProfile from '../../Stores/Actions'
import {MODULE} from '../../Metadata'

import style from "./style";

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
    console.log(this.datePicker.getSelectedDate())
  }

  render() {
    const {langCode, metadata, genders} = this.props

    if (!Map.isMap(genders)) {
      return <Loading />
    }

    return (
        <Container>
          <Content>
            <Form style={style.form}>
              <InputDatePicker ref={c => this.datePicker = c}
                               labelName={'Birthday:'}/>
              <Item picker>
                <Label>Gender:</Label>
                <View style={style.pickerView}>
                  <Picker
                      renderHeader={backAction =>
                          <Header>
                            <Left>
                              <Button transparent onPress={backAction}>
                                <Icon name="arrow-back"/>
                              </Button>
                            </Left>
                            <Body>
                            <Title>Gender</Title>
                            </Body>
                            <Right/>
                          </Header>}
                      mode="dropdown"
                      iosIcon={<Icon name="arrow-down"/>}
                      style={{width: undefined}}
                      placeholder="Select your SIM"
                      //placeholderStyle={{color: "#bfc6ea"}}
                      //placeholderIconColor="#007aff"
                      //selectedValue={this.state.selected2}
                      //onValueChange={this.onValueChange2.bind(this)}
                  >
                    {genders.entrySeq().toArray().map(([k, v]) =>
                        (<Picker.Item label={v} value={k} key={k}/>)
                    )}
                  </Picker>
                </View>
              </Item>
              <ButtonWithLoader
                  title={'save'}
                  style={{marginTop: 40}}
                  //disabled={buttonDisabled || loading}
                  //loading={loading}
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

  genders: state.traineeProfile.root.get('genders'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(TraineeProfile.fetchMetadata(langCode)),
  fetchTraineeProfile: (traineeUserId) =>
      dispatch(TraineeProfile.fetchTraineeProfile(traineeUserId)),
  fetchGenders: () => dispatch(TraineeProfile.fetchGenders()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CommonProfileScreen)
