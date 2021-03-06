import React from 'react'
import PropTypes from 'prop-types'
import {Form} from 'native-base'
import Email from "App/Components/Email";
import Password from "App/Components/Password";
import ButtonWithLoader from "App/Components/ButtonWithLoader";

class SignIn extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      email: undefined,
      password: undefined,
      buttonDisabled: true,
    }
  }

  _emailValid = (email) => {
    if (this.state.password) {
      this.setState({
        email,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        email,
      })
    }
  }

  _emailInValid = () => {
    this.setState({
      email: undefined,
      buttonDisabled: true,
    })
  }

  _passwordValid = (password) => {
    if (this.state.email) {
      this.setState({
        password,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        password,
      })
    }
  }

  _passwordInValid = () => {
    this.setState({
      password: undefined,
      buttonDisabled: true,
    })
  }

  render() {
    const {
      loading,
      authenticationHandler,
      txtEmail,
      txtPassword,
      txtBtn,
      minPasswordLength,
    } = this.props

    const {buttonDisabled, email, password} = this.state

    return (
        <Form>
          <Email txtEmail={txtEmail}
                 onValid={this._emailValid}
                 onInValid={this._emailInValid}/>
          <Password txtPassword={txtPassword}
                    minLength={minPasswordLength}
                    onValid={this._passwordValid}
                    onInValid={this._passwordInValid}/>
          <ButtonWithLoader
              title={txtBtn}
              style={{marginTop: 40}}
              disabled={buttonDisabled || loading}
              loading={loading}
              onPressHandler={() => authenticationHandler(email, password)}
          />
        </Form>
    )
  }
}

SignIn.propTypes = {
  txtEmail: PropTypes.string.isRequired,
  txtPassword: PropTypes.string.isRequired,
  txtBtn: PropTypes.string.isRequired,
  minPasswordLength: PropTypes.number.isRequired,
  loading: PropTypes.bool.isRequired,
  authenticationHandler: PropTypes.func.isRequired,
}

export default SignIn
