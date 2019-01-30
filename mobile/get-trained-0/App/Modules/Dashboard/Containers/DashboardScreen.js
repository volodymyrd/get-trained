import React from 'react'
import {View, Text, Button} from 'react-native'
import connect from 'react-redux/es/connect/connect'
import DashboardActions from '../Stores/Actions'

class DashboardScreen extends React.Component {

  componentDidUpdate(prevProps) {
    if (prevProps.fetching.get('signOut')
        && !this.props.fetching.get('signOut')) {
      this.props.navigation.navigate('Auth')
    }
  }

  signOutHandler = () => {
    this.props.signOut();
  }

  render() {
    return (
        <View>
          <Text>Dashboard</Text>
          <Button title="Sign out!" onPress={this.signOutHandler}/>
        </View>
    )
  }
}

const mapStateToProps = (state) => ({
  fetching: state.dashboard.workspace.get('fetching'),
})

const mapDispatchToProps = (dispatch) => ({
  signOut: () => dispatch(DashboardActions.fetchSignOut()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(DashboardScreen)
