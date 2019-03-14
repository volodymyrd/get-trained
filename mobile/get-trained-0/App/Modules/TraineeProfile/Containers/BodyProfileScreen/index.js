import React, {Component} from 'react'
import Dimensions from 'Dimensions'
import connect from 'react-redux/es/connect/connect'
import {View, Picker, Text} from 'react-native'
//import {View, Picker} from 'native-base'
import {setNavigationOptions} from 'App/Modules/Dashboard/NavigationOptions'
import Ionicons from 'react-native-vector-icons/Ionicons'

import styles from './styles'
import color from "../../../../Theme/Colors";

class BodyProfileScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, true)

  componentDidMount() {
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    const height = Dimensions.get('window').height
    const weight = Dimensions.get('window').weight
    const size = height * 0.8
    const middle = weight / 2

    const neckPosition = {
      left: middle,
      top: height / 6,
    }

    return (
        <View>
          <View style={styles.body}>
            <Text style={[styles.pos, neckPosition]}>10</Text>
            <Ionicons name="ios-man" size={size} color={color.primary}/>
          </View>
          {/*<Picker*/}
          {/*style={styles.neck}*/}
          {/*//mode="dropdown"*/}
          {/*//selectedValue={[1, 2,3 ,4,5]}*/}
          {/*//onValueChange={this.onValueChange.bind(this)}*/}
          {/*>*/}
          {/*<Picker.Item label="1" value="1"/>*/}
          {/*<Picker.Item label="2" value="2"/>*/}
          {/*<Picker.Item label="3" value="3"/>*/}
          {/*<Picker.Item label="4" value="4"/>*/}
          {/*<Picker.Item label="5" value="5"/>*/}
          {/*<Picker.Item label="6" value="6"/>*/}
          {/*<Picker.Item label="7" value="7"/>*/}
          {/*</Picker>*/}
        </View>
    )
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(BodyProfileScreen)
