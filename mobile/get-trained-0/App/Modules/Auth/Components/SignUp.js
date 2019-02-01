import React from 'react'
import PropTypes from 'prop-types'
import {Form, Text, Button, Spinner} from 'native-base'
import Email from "App/Components/Email";
import Name from "App/Components/Name";
import Password from "App/Components/Password";

class SignUp extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      emailValid: false,
      firstNameValid: false,
      passwordValid: false,
      buttonDisabled: true,
    }
  }

  emailValid = () => {
    if (this.state.passwordValid && this.state.firstNameValid) {
      this.setState({
        emailValid: true,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        emailValid: true,
      })
    }
  }

  emailInValid = () => {
    this.setState({
      emailValid: false,
      buttonDisabled: true,
    })
  }

  firstNameValid = () => {
    if (this.state.emailValid && this.state.passwordValid) {
      this.setState({
        firstNameValid: true,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        firstNameValid: true,
      })
    }
  }

  firstNameInValid = () => {
    this.setState({
      firstNameValid: false,
      buttonDisabled: true,
    })
  }

  passwordValid = () => {
    if (this.state.emailValid && this.state.firstNameValid) {
      this.setState({
        passwordValid: true,
        buttonDisabled: false,
      })
    } else {
      this.setState({
        passwordValid: true,
      })
    }
  }

  passwordInValid = () => {
    this.setState({
      passwordValid: false,
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
      authenticationHandler
    } = this.props

    const {buttonDisabled} = this.state

    return (
        <Form>
          <Email txtEmail={txtEmail}
                 onValid={this.emailValid}
                 onInValid={this.emailInValid}/>
          <Name txtName={txtFirstName}
                minLength={minFirstNameLength}
                onValid={this.firstNameValid}
                onInValid={this.firstNameInValid}/>
          <Password txtPassword={txtPassword}
                    minLength={minPasswordLength}
                    onValid={this.passwordValid}
                    onInValid={this.passwordInValid}/>
          <Password txtPassword={txtRepeatPassword}
                    minLength={minPasswordLength}
                    onValid={this.passwordValid}
                    onInValid={this.passwordInValid}/>
          <Button
              full
              rounded
              style={{marginTop: 40}}
              onPress={authenticationHandler}
              disabled={buttonDisabled || loading}
          >
            {loading ? <Spinner color='blue'/> : <Text>{txtBtn}</Text>}
          </Button>
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
  authenticationHandler: PropTypes.func.isRequired,
}

export default SignUp
