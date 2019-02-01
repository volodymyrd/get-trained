import React from 'react'
import PropTypes from 'prop-types'
import {Form, Item, Input, Label, Text, Button, Spinner} from 'native-base'
import Email from "App/Components/Email";
import Password from "../../../Components/Password";

class RestorePassword extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      emailValid: false,
      buttonDisabled: true,
    }
  }

  emailValid = () => {
    this.setState({
      emailValid: true,
      buttonDisabled: false,
    })
  }

  emailInValid = () => {
    this.setState({
      emailValid: false,
      buttonDisabled: true,
    })
  }

  render() {
    const {txtBtn, txtEmail, loading, authenticationHandler} = this.props
    const {buttonDisabled} = this.state

    return (
        <Form>
          <Email txtEmail={txtEmail}
                 onValid={this.emailValid}
                 onInValid={this.emailInValid}/>
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

RestorePassword.propTypes = {
  txtEmail: PropTypes.string.isRequired,
  txtBtn: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  authenticationHandler: PropTypes.func.isRequired,
}

export default RestorePassword
