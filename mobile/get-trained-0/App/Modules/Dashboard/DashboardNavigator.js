import React from 'react'
import { createDrawerNavigator } from 'react-navigation'
import SideBar from './Containers/SideBar'
import HomeNavigator from 'App/Modules/Home/HomeNavigator'
import ProfileNavigator from 'App/Modules/Profile/ProfileNavigator'
import SettingsNavigator from 'App/Modules/Settings/SettingsNavigator'

const DashboardNavigator = createDrawerNavigator(
  {
    _Home: { screen: HomeNavigator },
    _Profile: { screen: ProfileNavigator },
    _Settings: { screen: SettingsNavigator },
  },
  {
    contentComponent: (props) => <SideBar {...props} />,
  }
)

export default DashboardNavigator
