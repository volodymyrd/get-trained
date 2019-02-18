import React from 'react'
import PropTypes from 'prop-types'
import {Form} from 'native-base'
import Email from "App/Components/Email";
import Name from "App/Components/Name";
import Password from "App/Components/Password";
import ButtonWithLoader from "App/Components/ButtonWithLoader";

class SignUp extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      email: undefined,
      password: undefined,
      passwordRepeat: undefined,
      firstName: false,
      buttonDisabled: true,
    }
  }

  _emailValid = (email) => {
    if (this.state.password
        && this.state.passwordRepeat
        && this.state.firstName) {
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
    if (this.state.email
        && this.state.passwordRepeat
        && this.state.firstName) {
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
      passwordRepeat: undefined,
      buttonDisabled: true,
    })
  }

  _passwordRepeatValid = (passwordRepeat) => {
    if (this.state.email && this.state.firstName) {
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

  _firstNameValid = (firstName) => {
    if (this.state.email
        && this.state.password
        && this.state.passwordRepeat) {
      this.setState({
        firstName,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        firstName,
      })
    }
  }

  _firstNameInValid = () => {
    this.setState({
      firstName: undefined,
      buttonDisabled: true,
    })
  }

  render() {
    const {
      txtEmail,
      txtFirstName,
      minFirstNameLength,
      txtPassword,
      txtRepeatPassword,
      minPasswordLength,
      txtBtn,
      loading,
      signUpHandler,
    } = this.props

    const {
      buttonDisabled,
      email,
      password,
      firstName
    } = this.state

    return (
        <Form>
          <Email txtEmail={txtEmail}
                 onValid={this._emailValid}
                 onInValid={this._emailInValid}/>
          <Name txtName={txtFirstName}
                minLength={minFirstNameLength}
                onValid={this._firstNameValid}
                onInValid={this._firstNameInValid}/>
          <Password txtPassword={txtPassword}
                    minLength={minPasswordLength}
                    onValid={this._passwordValid}
                    onInValid={this._passwordInValid}/>
          <Password txtPassword={txtRepeatPassword}
                    repeatPassword={password}
                    minLength={minPasswordLength}
                    onValid={this._passwordRepeatValid}
                    onInValid={this._passwordRepeatInValid}/>
          <ButtonWithLoader
              title={txtBtn}
              style={{marginTop: 40}}
              disabled={buttonDisabled || loading}
              loading={loading}
              onPressHandler={() => signUpHandler(email, password, firstName)}
          />
        </Form>
    )
  }
}

SignUp.propTypes = {
  txtEmail: PropTypes.string.isRequired,
  txtFirstName: PropTypes.string.isRequired,
  minFirstNameLength: PropTypes.number.isRequired,
  txtPassword: PropTypes.string.isRequired,
  txtRepeatPassword: PropTypes.string.isRequired,
  minPasswordLength: PropTypes.number.isRequired,
  txtBtn: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  signUpHandler: PropTypes.func.isRequired,
}

export default SignUp
