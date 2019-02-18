import React, { Component } from 'react'
import { Button, Text, Spinner } from 'native-base'
import PropTypes from 'prop-types'

class ButtonWithLoader extends Component {
  render() {
    const { title, onPressHandler, style, loading, disabled } = this.props

    return (
      <Button full rounded style={style} onPress={onPressHandler} disabled={disabled}>
        {loading ? <Spinner color="blue" /> : <Text>{title}</Text>}
      </Button>
    )
  }
}

ButtonWithLoader.propTypes = {
  title: PropTypes.string.isRequired,
  onPressHandler: PropTypes.func.isRequired,
  style: PropTypes.object,
  loading: PropTypes.bool,
  disabled: PropTypes.bool,
}

export default ButtonWithLoader
