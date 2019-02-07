import React, { Component } from 'react'
import connect from 'react-redux/es/connect/connect'
import Settings from '../Components/Settings'
import {setNavigationOptions} from "../../Dashboard/DashboardNavigator";

class SettingsScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, 'Settings')

  componentDidMount() {}

  componentDidUpdate(prevProps) {}

  render() {
    return <Settings />
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SettingsScreen)
