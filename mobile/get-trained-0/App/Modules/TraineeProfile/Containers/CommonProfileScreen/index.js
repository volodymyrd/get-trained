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
  Title,
  Button,
  Picker,
  Icon, View,
} from 'native-base'
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
    const {langCode, metadata, fetchMetadata, selectedTrainee, fetchTraineeProfile} = this.props

    if (langCode
        && !(metadata
            && Map.isMap(metadata)
            && metadata.get('module') === MODULE)) {
      fetchMetadata(langCode.toUpperCase())
    }

    fetchTraineeProfile(this.props.navigation.getParam('item').traineeUserId)
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    const {langCode, metadata} = this.props

    return (
        <Container>
          <Content>
            <Form style={style.form}>
              <InputDatePicker labelName={'Birthday:'}/>
              <Item picker>
                <Label>Gender:</Label>
                <View style={style.pickerView}>
                  <Picker renderHeader={backAction =>
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
                    <Picker.Item label="Wallet" value="key0"/>
                    <Picker.Item label="ATM Card" value="key1"/>
                    <Picker.Item label="Debit Card" value="key2"/>
                    <Picker.Item label="Credit Card" value="key3"/>
                    <Picker.Item label="Net Banking" value="key4"/>
                  </Picker>
                </View>
              </Item>
              <ButtonWithLoader
                  title={'save'}
                  style={{marginTop: 40}}
                  //disabled={buttonDisabled || loading}
                  //loading={loading}
                  //onPressHandler={() => authenticationHandler(email, password)}
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
