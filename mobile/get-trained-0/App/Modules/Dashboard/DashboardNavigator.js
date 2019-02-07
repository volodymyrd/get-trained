import React from 'react'
import { createDrawerNavigator } from 'react-navigation'
import SideBar from './Components/SideBar'
import HomeNavigator from 'App/Modules/Home/HomeNavigator'
import ProfileNavigator from 'App/Modules/Profile/ProfileNavigator'
import SettingsNavigator from 'App/Modules/Settings/SettingsNavigator'
import { Body, Button, Header, Icon, Left, Right, Title } from 'native-base'

export const setNavigationOptions = (navigation, title) => {
  return {
    header: (
      <Header>
        <Left>
          <Button transparent onPress={() => navigation.openDrawer()}>
            <Icon name="menu" />
          </Button>
        </Left>
        <Body>
          <Title>{title}</Title>
        </Body>
        <Right />
      </Header>
    ),
  }
}

const DashboardNavigator = createDrawerNavigator(
  {
    Home: { screen: HomeNavigator },
    Profile: { screen: ProfileNavigator },
    Settings: { screen: SettingsNavigator },
  },
  {
    contentComponent: (props) => <SideBar {...props} />,
  }
)

export default DashboardNavigator
