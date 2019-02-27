import React, { Component } from 'react'
import { Button, Text, Spinner, Icon } from 'native-base'
import PropTypes from 'prop-types'

class ButtonWithLoader extends Component {
  render() {
    const {
      title,
      onPressHandler,
      notFull,
      notRounded,
      small,
      transparent,
      style,
      loading,
      disabled,
      icon,
      iconLeft,
      iconRight,
    } = this.props

    return (
      <Button
        full={!notFull}
        rounded={!notRounded}
        small={small}
        transparent={transparent}
        style={style}
        onPress={onPressHandler}
        disabled={disabled}
        iconLeft={iconLeft}
        iconRight={iconRight}
      >
        {icon && <Icon name={icon} />}
        {loading ? <Spinner color="blue" /> : <Text>{title}</Text>}
      </Button>
    )
  }
}

ButtonWithLoader.propTypes = {
  title: PropTypes.string.isRequired,
  onPressHandler: PropTypes.func.isRequired,
  notFull: PropTypes.bool,
  notRounded: PropTypes.bool,
  small: PropTypes.bool,
  transparent: PropTypes.bool,
  style: PropTypes.object,
  loading: PropTypes.bool,
  disabled: PropTypes.bool,
  icon: PropTypes.string,
  iconLeft: PropTypes.bool,
  iconRight: PropTypes.bool,
}

export default ButtonWithLoader
