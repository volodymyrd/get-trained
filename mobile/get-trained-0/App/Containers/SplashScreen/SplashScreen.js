import React from 'react'
import { Text, View } from 'react-native'
import styles from './SplashScreenStyle'
import connect from 'react-redux/es/connect/connect'
import MainActions from '../../Stores/Main/Actions'

class SplashScreen extends React.Component {
  componentDidMount() {
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
        <View style={styles.logo}>
          <Text>Get Trained Online</Text>
        </View>
      </View>
    )
  }
}

const mapStateToProps = (state) => ({
  accessIsLoading: state.main.get('accessIsLoading'),
  isAuthenticated: state.main.get('isAuthenticated'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchAccess: () => dispatch(MainActions.fetchAccess()),
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SplashScreen)
