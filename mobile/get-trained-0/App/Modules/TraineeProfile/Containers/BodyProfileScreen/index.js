import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {View, Text} from 'native-base'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'

class BodyProfileScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, true)

  componentDidMount() {
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    return <View><Text>BodyProfileScreen</Text></View>
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(BodyProfileScreen)
