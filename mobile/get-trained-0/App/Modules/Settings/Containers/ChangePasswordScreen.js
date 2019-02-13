import React, { Component } from 'react'
import connect from 'react-redux/es/connect/connect'
import Settings from '../Components/Settings'
import {setNavigationOptions} from "../../Dashboard/DashboardNavigator";
import {View} from "react-native";
import {Text} from "native-base";

class ChangePasswordScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, 'Change Password')

  componentDidMount() {}

  componentDidUpdate(prevProps) {}

  render() {
    return (
        <View>
          <Text>Change Password</Text>
        </View>
    )
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ChangePasswordScreen)
