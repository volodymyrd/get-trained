import React from 'react'
import { createBottomTabNavigator } from 'react-navigation'
import Ionicons from 'react-native-vector-icons/Ionicons'

import CommonProfileScreen from './Containers/CommonProfileScreen'
import BodyProfileScreen from './Containers/BodyProfileScreen'

const TraineeProfileNavigator = createBottomTabNavigator(
  {
    Common: { screen: CommonProfileScreen },
    Body: { screen: BodyProfileScreen },
  },
  {
    navigationOptions: ({ navigation }) => ({
      tabBarIcon: ({ focused, horizontal, tintColor }) => {
        const { routeName } = navigation.state
        let iconName
        if (routeName === 'Common') {
          iconName = `ios-information-circle${focused ? '' : '-outline'}`
        } else if (routeName === 'Body') {
          iconName = `ios-options${focused ? '' : ''}`
        }
        return <Ionicons name={iconName} size={horizontal ? 20 : 25} color={tintColor} />
      },
    }),
    tabBarOptions: {
      activeTintColor: 'tomato',
      inactiveTintColor: 'gray',
    },
  }
)

TraineeProfileNavigator.navigationOptions = ({ navigation }) => {
  const headerTitle = navigation.getParam('item').traineeFullName

  return {
    headerTitle,
  }
}

export default TraineeProfileNavigator
