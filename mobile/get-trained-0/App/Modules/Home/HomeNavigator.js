import { createStackNavigator } from 'react-navigation'
import HomeScreen from './Containers/HomeScreen'

const HomeNavigator = createStackNavigator(
  {
    Home: { screen: HomeScreen },
  },
  {
    initialRouteName: 'Home',
  }
)
export default HomeNavigator
