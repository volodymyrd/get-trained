import React, {Component} from 'react'
import {AppRegistry, CameraRoll} from 'react-native';
import {Container, Content, Button, Text, Form} from "native-base";
import ImagePicker from 'react-native-image-crop-picker';
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from "App/Modules/Dashboard/DashboardNavigator";
import {} from "../../Metadata";
import {somethingWentWrong} from 'App/Utils/MetadataUtils'
import Error from "App/Components/Error";
import Loading from "App/Components/Loading";
import ProfileActions from "../../Stores/Actions";
import {MODULE} from "../../Metadata";

import styles from './styles'

class AvatarUploaderScreen extends Component {
  static navigationOptions = ({navigation}) =>
      setNavigationOptions(navigation, navigation.getParam('title', 'Avatar'))

  componentDidMount() {
    if (this.props.langCode
        && !(this.props.metadata.size
            && this.props.metadata.get('module') === MODULE)) {
      //this.props.fetchMetadata(this.props.langCode.toUpperCase())
    }
  }

  componentDidUpdate(prevProps) {
  }

  _uploaderHandler = () => {
  }

  _addImage = () => {
    CameraRoll.getPhotos({
      first: 20,
      assetType: 'Photos',
    })
    .then(r => {
      this.setState({photos: r.edges});
    })
    .catch((err) => {
      //Error Loading Images
    });
  }

  _openImages = () => {
    ImagePicker.openPicker({
      width: 300,
      height: 400,
      cropping: true
    }).then(image => {
      console.log(image);
    });
  }

  render() {
    const {
      metadata,
      failedRetrievingMetadata,
      fetchingMetadata,
      uploadingAvatar,
    } = this.props

    // if (failedRetrievingMetadata) {
    //   return <Error/>
    // }
    //
    // if (fetchingMetadata || !metadata || !metadata.size) {
    //   return <Loading/>
    // }
    //
    // const localizations = metadata.get('localizations')

    return (
        <Container>
          <Content style={styles.content}>
            <Button
                full
                rounded
                //style={{marginTop: 40}}
                onPress={this._addImage}
                //disabled={buttonDisabled || loading}
            >
              <Text>avatar</Text>
            </Button>
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
  uploadingAvatar: state.settings.root.get('uploadingAvatar'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(ProfileActions.fetchMetadata(langCode)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AvatarUploaderScreen)

// AppRegistry.registerComponent(
//     'AvatarUploaderScreen',
//     () => UploadFromCameraRoll);
