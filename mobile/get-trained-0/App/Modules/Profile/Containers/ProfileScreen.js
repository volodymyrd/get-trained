import React, { Component } from 'react'
import connect from 'react-redux/es/connect/connect'
import Profile from '../Components/Profile'
import {setNavigationOptions} from "../../Dashboard/DashboardNavigator";

class ProfileScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, 'Profile')

  componentDidMount() {}

  componentDidUpdate(prevProps) {}

  render() {
    return <Profile />
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProfileScreen)
