import React, { Component } from 'react'
import { createSwitchNavigator, createStackNavigator } from 'react-navigation'
import NavigationService from 'App/Services/NavigationService'
import { Root } from 'native-base'
import { connect } from 'react-redux'
import SplashScreen from 'App/Containers/SplashScreen/SplashScreen'
import AuthScreen from 'App/Modules/Auth/Containers/AuthScreen'
import DashboardScreen from 'App/Modules/Dashboard/Containers/DashboardScreen'

const AppStack = createStackNavigator({ Dashboard: DashboardScreen })
const AuthStack = createStackNavigator({ Auth: AuthScreen })

/**
 * The root screen contains the application's navigation.
 *
 * @see https://reactnavigation.org/docs/en/hello-react-navigation.html#creating-a-stack-navigator
 */
const AppNav = createSwitchNavigator(
  {
    SplashScreen: SplashScreen,
    App: AppStack,
    Auth: AuthStack,
  },
  {
    initialRouteName: 'SplashScreen',
    headerMode: 'none',
  }
)

class RootScreen extends Component {
  componentDidMount() {
    // Run the startup saga when the application is starting
    // this.props.startup()
  }

  render() {
    return (
      <Root>
        <AppNav
          // Initialize the NavigationService (see https://reactnavigation.org/docs/en/navigating-without-navigation-prop.html)
          ref={(navigatorRef) => {
            NavigationService.setTopLevelNavigator(navigatorRef)
          }}
        />
      </Root>
    )
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({
  // startup: () => dispatch(StartupActions.startup()),
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RootScreen)
