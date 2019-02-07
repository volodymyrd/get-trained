import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from 'App/Modules/Dashboard/DashboardNavigator'
import Home from '../Components/Home'
import {
  Body,
  Button,
  Container,
  Content,
  Header,
  Icon,
  Left,
  Right,
  Title
} from 'native-base'

class HomeScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, 'Home')

  componentDidMount() {
  }

  componentDidUpdate(prevProps) {
  }

  render() {
    return (
        <Container>
          <Content padder>
            <Home/>
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({})

const mapDispatchToProps = (dispatch) => ({})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomeScreen)
