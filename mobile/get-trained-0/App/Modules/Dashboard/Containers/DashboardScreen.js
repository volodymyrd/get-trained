import React from 'react'
import connect from 'react-redux/es/connect/connect'
import DashboardActions from '../Stores/Actions'
import {Drawer, Button, Icon} from 'native-base';
import SideBar from "../Components/SideBar";
import Home from "../Components/Home";

class DashboardScreen extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      drawerOpen: false
    }
  }

  static navigationOptions = ({navigation}) => {
    return {
      title: 'Home',
      headerStyle: {
        backgroundColor: '#f4511e',
      },
      headerTintColor: '#fff',
      headerTitleStyle: {
        fontWeight: 'bold',
      },
      headerLeft: (
          <Button transparent onPress={navigation.getParam('toggleDrawer')}>
            <Icon name="menu"/>
          </Button>
      ),
    }
  };

  componentDidMount() {
    this.props.navigation.setParams({toggleDrawer: this.toggleDrawer});
  }

  componentDidUpdate(prevProps) {
    if (prevProps.fetching.get('signOut')
        && !this.props.fetching.get('signOut')) {
      this.props.navigation.navigate('Auth')
    }
  }

  signOutHandler = () => {
    this.props.signOut();
  }

  closeDrawer = () => {
    this.setState({
      drawerOpen: false,
    })
    this.drawer._root.close()
  }

  toggleDrawer = () => {
    const open = this.state.drawerOpen
    if (open) {
      this.closeDrawer()
    } else {
      this.setState({
        drawerOpen: true,
      })
      this.drawer._root.open()
    }
  }

  render() {
    return (
        <Drawer
            ref={(ref) => {
              this.drawer = ref
            }}
            content={<SideBar navigator={this.navigator}
                              signOutHandler={this.signOutHandler}/>}
            onClose={() => this.closeDrawer()}>
          <Home/>
        </Drawer>
    )
  }
}

const mapStateToProps = (state) => ({
  fetching: state.dashboard.workspace.get('fetching'),
})

const mapDispatchToProps = (dispatch) => ({
  signOut: () => dispatch(DashboardActions.fetchSignOut()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(DashboardScreen)
