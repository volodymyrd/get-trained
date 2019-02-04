import React from 'react'
import PropTypes from 'prop-types'
import {Form, Text, Button, Spinner} from 'native-base'
import Email from "App/Components/Email";

class RestorePassword extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      email: undefined,
      buttonDisabled: true,
    }
  }

  _emailValid = (email) => {
    this.setState({
      email,
      buttonDisabled: false,
    })
  }

  _emailInValid = () => {
    this.setState({
      email: undefined,
      buttonDisabled: true,
    })
  }

  render() {
    const {txtBtn, txtEmail, loading, restorePasswordHandler} = this.props
    const {buttonDisabled, email} = this.state

    return (
        <Form>
          <Email txtEmail={txtEmail}
                 onValid={this._emailValid}
                 onInValid={this._emailInValid}/>
          <Button
              full
              rounded
              style={{marginTop: 40}}
              onPress={() => restorePasswordHandler(email)}
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
  restorePasswordHandler: PropTypes.func.isRequired,
}

export default RestorePassword
