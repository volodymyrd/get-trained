import React from 'react'
import PropTypes from 'prop-types'
import {Form} from 'native-base'
import Email from 'App/Components/Email';
import ButtonWithLoader from 'App/Components/ButtonWithLoader';

class AddConnectionByEmail extends React.Component {
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
    const {txtBtn, txtEmail, loading, addHandler} = this.props
    const {buttonDisabled, email} = this.state

    return (
        <Form>
          <Email txtEmail={txtEmail}
                 onValid={this._emailValid}
                 onInValid={this._emailInValid}/>
          <ButtonWithLoader
              title={txtBtn}
              style={{marginTop: 40}}
              disabled={buttonDisabled || loading}
              loading={loading}
              onPressHandler={() => addHandler(email)}
          />
        </Form>
    )
  }
}

AddConnectionByEmail.propTypes = {
  txtEmail: PropTypes.string.isRequired,
  txtBtn: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  addHandler: PropTypes.func.isRequired,
}

export default AddConnectionByEmail
