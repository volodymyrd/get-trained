import React from 'react'
import PropTypes from 'prop-types'
import {Form, Item, Input, Label, Text, Button, Spinner} from 'native-base'
import Email from "App/Components/Email";
import Password from "../../../Components/Password";

class SignUp extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      emailValid: false,
      passwordValid: false,
      buttonDisabled: true,
    }
  }

  emailValid = () => {
    if (this.state.passwordValid) {
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

  passwordValid = () => {
    if (this.state.emailValid) {
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
    const {authType, loading, authenticationHandler} = this.props
    const {buttonDisabled} = this.state

    return (
        <Form>
          <Email onValid={this.emailValid} onInValid={this.emailInValid}/>
          <Password name='Password'
                    onValid={this.passwordValid}
                    onInValid={this.passwordInValid}/>
          <Password name='Repeat Password'
                    onValid={this.passwordValid}
                    onInValid={this.passwordInValid}/>
          <Button
              full
              rounded
              style={{marginTop: 40}}
              onPress={authenticationHandler}
              disabled={buttonDisabled || loading}
          >
            {loading ? <Spinner color='blue'/> : <Text>Sign in</Text>}
          </Button>
        </Form>
    )
  }
}

SignUp.propTypes = {
  loading: PropTypes.bool.isRequired,
  authenticationHandler: PropTypes.func.isRequired,
}

export default SignUp
