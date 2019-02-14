import React from 'react'
import { createDrawerNavigator } from 'react-navigation'
import SideBar from './Containers/SideBar'
import HomeNavigator from 'App/Modules/Home/HomeNavigator'
import ProfileNavigator from 'App/Modules/Profile/ProfileNavigator'
import SettingsNavigator from 'App/Modules/Settings/SettingsNavigator'
import { Body, Button, Header, Icon, Left, Right, Title } from 'native-base'

export const setNavigationOptions = (navigation, title) => {
  return {
    header: (
      <Header>
        <Left style={{ maxWidth: 30 }}>
          <Button transparent onPress={() => navigation.openDrawer()}>
            <Icon name="menu" />
          </Button>
        </Left>
        <Body>
          <Title>{title}</Title>
        </Body>
        <Right style={{ maxWidth: 30 }} />
      </Header>
    ),
  }
}

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
