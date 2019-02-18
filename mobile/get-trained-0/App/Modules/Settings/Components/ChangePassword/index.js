import React from 'react'
import PropTypes from 'prop-types'
import {Form} from 'native-base'
import Password from "App/Components/Password";
import ButtonWithLoader from "App/Components/ButtonWithLoader";

import styles from './styles'

class ChangePassword extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      oldPassword: undefined,
      newPassword: undefined,
      passwordRepeat: undefined,
      buttonDisabled: true,
    }
  }

  _oldPasswordValid = (oldPassword) => {
    if (this.state.newPassword
        && this.state.passwordRepeat) {
      this.setState({
        oldPassword,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        oldPassword,
      })
    }
  }

  _oldPasswordInValid = () => {
    this.setState({
      oldPassword: undefined,
      buttonDisabled: true,
    })
  }

  _newPasswordValid = (newPassword) => {
    if (this.state.oldPassword
        && this.state.passwordRepeat) {
      this.setState({
        newPassword,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        newPassword,
      })
    }
  }

  _newPasswordInValid = () => {
    this.setState({
      newPassword: undefined,
      passwordRepeat: undefined,
      buttonDisabled: true,
    })
  }

  _passwordRepeatValid = (passwordRepeat) => {
    if (this.state.oldPassword) {
      this.setState({
        passwordRepeat,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        passwordRepeat,
      })
    }
  }

  _passwordRepeatInValid = () => {
    this.setState({
      passwordRepeat: undefined,
      buttonDisabled: true,
    })
  }

  render() {
    const {
      txtNewPassword,
      txtOldPassword,
      txtRepeatPassword,
      minPasswordLength,
      txtBtn,
      loading,
      changePasswordHandler,
    } = this.props

    const {
      buttonDisabled,
      oldPassword,
      newPassword,
      passwordRepeat,
    } = this.state

    return (
        <Form>
          <Password txtPassword={txtOldPassword}
                    minLength={minPasswordLength}
                    onValid={this._oldPasswordValid}
                    onInValid={this._oldPasswordInValid}/>
          <Password txtPassword={txtNewPassword}
                    minLength={minPasswordLength}
                    onValid={this._newPasswordValid}
                    onInValid={this._newPasswordInValid}/>
          <Password txtPassword={txtRepeatPassword}
                    repeatPassword={newPassword}
                    minLength={minPasswordLength}
                    onValid={this._passwordRepeatValid}
                    onInValid={this._passwordRepeatInValid}/>
          <ButtonWithLoader
              title={txtBtn}
              style={styles.btn}
              disabled={buttonDisabled || loading}
              loading={loading}
              onPressHandler={() => changePasswordHandler(
                  oldPassword, newPassword, passwordRepeat)}
          />
        </Form>
    )
  }
}

ChangePassword.propTypes = {
  txtNewPassword: PropTypes.string.isRequired,
  txtOldPassword: PropTypes.string.isRequired,
  txtRepeatPassword: PropTypes.string.isRequired,
  minPasswordLength: PropTypes.number.isRequired,
  txtBtn: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  changePasswordHandler: PropTypes.func.isRequired,
}

export default ChangePassword
