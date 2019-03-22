import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Dimensions from 'Dimensions'
import { View, Text } from 'react-native'
// import {View} from 'native-base'
import Ionicons from 'react-native-vector-icons/Ionicons'
import color from 'App/Theme/Colors'

import style from './style'

export default class BodyProfile extends Component {
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
        <View style={style.body}>
          <Text style={[style.pos, neckPosition]}>10</Text>
          <Ionicons name="ios-man" size={size} color={color.primary} />
        </View>
      </View>
    )
  }
}

BodyProfile.propTypes = {
  // title: PropTypes.string.isRequired,
}
