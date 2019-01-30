import React from 'react'
import PropTypes from 'prop-types'
import {Form, Item, Input, Label, Text, Button, Spinner} from 'native-base'
import Email from "App/Components/Email";
import Password from "../../../Components/Password";

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
    const {loading, authenticationHandler} = this.props
    const {buttonDisabled, email, password} = this.state

    return (
        <Form>
          <Email
              onValid={this._emailValid}
              onInValid={this._emailInValid}/>
          <Password name='Password'
                    onValid={this._passwordValid}
                    onInValid={this._passwordInValid}/>
          <Button
              full
              rounded
              style={{marginTop: 40}}
              onPress={() => authenticationHandler(email, password)}
              disabled={buttonDisabled || loading}
          >
            {loading ? <Spinner color='blue'/> : <Text>Sign in</Text>}
          </Button>
        </Form>
    )
  }
}

SignIn.propTypes = {
  loading: PropTypes.bool.isRequired,
  authenticationHandler: PropTypes.func.isRequired,
}

export default SignIn
