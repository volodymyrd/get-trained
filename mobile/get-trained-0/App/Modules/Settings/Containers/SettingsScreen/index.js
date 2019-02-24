import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {Container, Content} from "native-base";
import {setNavigationOptions} from "App/Modules/Dashboard/NavigationOptions";
import ButtonWithLoader from "App/Components/ButtonWithLoader";
import Error from "App/Components/Error";
import Loading from "App/Components/Loading";
import SettingsActions from "../../Stores/Actions";
import {MODULE, txtBecomeTrainerBtn, txtRemoveTrainerBtn} from "../../Metadata";

import styles from './styles'

class SettingsScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, navigation.getParam('title', 'Settings'))

  componentDidMount() {
    if (this.props.langCode
        && !(this.props.metadata.size
            && this.props.metadata.get('module') === MODULE)) {
      this.props.fetchMetadata(this.props.langCode.toUpperCase())
    }
    this.props.fetchIsTrainer()
  }

  componentDidUpdate(prevProps) {
  }

  becomeTrainer = () => {
    console.log('becomeTrainer')
  }

  removeTrainer = () => {
    console.log('removeTrainer')
  }

  render() {
    const {
      fetchingMetadata,
      metadata,
      failedRetrievingMetadata,
      isTrainer
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata || !metadata || !metadata.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    return (
        <Container>
          <Content style={styles.content}>
            <ButtonWithLoader
                title={isTrainer ?
                    txtRemoveTrainerBtn(localizations) :
                    txtBecomeTrainerBtn(localizations)
                }
                //style={styles.btn}
                //disabled={buttonDisabled || loading}
                //loading={loading}
                onPressHandler={isTrainer ?
                    this.removeTrainer : this.becomeTrainer}
            />
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.settings.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.settings.root.get('failedRetrievingMetadata'),
  metadata: state.settings.root.get('metadata'),

  isTrainer: state.settings.root.get('isTrainer'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(
      SettingsActions.fetchMetadata(langCode)),
  fetchIsTrainer: () => dispatch(SettingsActions.fetchIsTrainer()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SettingsScreen)
