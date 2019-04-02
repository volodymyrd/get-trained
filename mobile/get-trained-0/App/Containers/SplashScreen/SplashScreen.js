import React from 'react'
import { View, Image } from 'react-native'
import styles from './SplashScreenStyle'
import connect from 'react-redux/es/connect/connect'
import MainActions from '../../Stores/Main/Actions'
import { LOGO_HEAD } from 'App/Images/'

class SplashScreen extends React.Component {
  componentDidMount() {
    this.props.getLocale()
    this.props.fetchAccess()
  }

  componentDidUpdate(prevProps) {
    if (prevProps.accessIsLoading && !this.props.accessIsLoading) {
      if (this.props.isAuthenticated) {
        this.props.navigation.navigate('App')
      } else {
        this.props.navigation.navigate('Auth')
      }
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Image square style={styles.logo} source={LOGO_HEAD} />
      </View>
    )
  }
}

const mapStateToProps = (state) => ({
  accessIsLoading: state.main.get('accessIsLoading'),
  isAuthenticated: state.main.get('isAuthenticated'),
})

const mapDispatchToProps = (dispatch) => ({
  getLocale: () => dispatch(MainActions.getLocale()),
  fetchAccess: () => dispatch(MainActions.fetchAccess()),
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SplashScreen)
