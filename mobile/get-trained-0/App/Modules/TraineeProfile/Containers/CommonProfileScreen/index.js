import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {View, Text} from 'native-base'

class CommonProfileScreen extends Component {
  static navigationOptions = ({ navigation }) => {
    return {
      title: 'common1',
    }
  }

  componentDidMount() {
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    return <View><Text>CommonProfileScreen</Text></View>
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CommonProfileScreen)
