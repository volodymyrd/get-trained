import { createStackNavigator } from 'react-navigation'

import DashboardScreen from './Containers/DashboardScreen'

export const dashboardNavigator = () => {
  return createStackNavigator(
    { Dashboard: DashboardScreen },
    {
      initialRouteName: 'Dashboard',
    }
  )
}
