import React, {Component} from 'react'
import {
  Container,
  Content,
  View,
  Thumbnail,
} from "native-base";
import ImagePicker from 'react-native-image-crop-picker';
import connect from 'react-redux/es/connect/connect'
import {getUrl} from 'App/Utils/HttpUtils'
import {setNavigationOptions} from "App/Modules/Dashboard/NavigationOptions";
import {somethingWentWrong} from 'App/Utils/MetadataUtils'
import Error from "App/Components/Error";
import Loading from "App/Components/Loading";
import ButtonWithLoader from "App/Components/ButtonWithLoader";
import ProfileActions from "../../Stores/Actions";
import {
  MODULE,
  btnAvatarUpload,
  btnAvatarDelete,
  successAvatarUpload,
} from "../../Metadata";

import styles from './styles'

class AvatarUploaderScreen extends Component {
  static navigationOptions = ({navigation}) => setNavigationOptions(navigation)

  componentDidMount() {
    if (this.props.langCode
        && !(this.props.metadata
            && this.props.metadata.size
            && this.props.metadata.get('module') === MODULE)) {
      this.props.fetchMetadata(this.props.langCode.toUpperCase())
    }
    if (!this.props.lightProfile) {
      this.props.fetchLightProfile()
    }
  }

  componentDidUpdate(prevProps) {
  }

  _openImages = () => {
    ImagePicker.openPicker({
      width: 300,
      height: 400,
      cropping: true
    }).then(image => {
      const localizations = this.props.metadata.get('localizations')
      this.props.uploadAvatar(image,
          [successAvatarUpload(localizations),
            somethingWentWrong(localizations)])
    })
    .catch((err) => {
      // console.log("openCamera catch" + err.toString())
    })
  }

  render() {
    const {
      metadata,
      lightProfile,
      failedRetrievingMetadata,
      fetchingMetadata,
      uploadingAvatar,
      deleteAvatar,
      deletingAvatar,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata || !metadata || !metadata.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')

    const avatarUrl = lightProfile && lightProfile.get('avatarUrl') ?
        getUrl(lightProfile.get('avatarUrl')) : undefined

    return (
        <Container>
          <Content style={styles.content}>
            {avatarUrl ?
                <View>
                  <View style={styles.avatar}>
                    <Thumbnail large source={{uri: avatarUrl}}/>
                  </View>
                  <ButtonWithLoader
                      title={btnAvatarDelete(localizations)}
                      style={{marginTop: 40}}
                      disabled={deletingAvatar}
                      loading={deletingAvatar}
                      onPressHandler={() => deleteAvatar(
                          [somethingWentWrong(localizations)])}
                  />
                </View> :
                <ButtonWithLoader
                    title={btnAvatarUpload(localizations)}
                    style={{marginTop: 40}}
                    disabled={uploadingAvatar}
                    loading={uploadingAvatar}
                    onPressHandler={this._openImages}
                />
            }
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.profile.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.profile.root.get('failedRetrievingMetadata'),
  metadata: state.profile.root.get('metadata'),

  fetchingLightProfile: state.profile.root.get('fetchingLightProfile'),
  lightProfile: state.profile.root.get('lightProfile'),

  uploadingAvatar: state.profile.root.get('uploadingAvatar'),
  deletingAvatar: state.profile.root.get('deletingAvatar'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(ProfileActions.fetchMetadata(langCode)),
  fetchLightProfile: () => dispatch(ProfileActions.fetchLightProfile()),
  uploadAvatar: (image, messages) => dispatch(
      ProfileActions.uploadAvatar(image, messages)),
  deleteAvatar: (messages) => dispatch(ProfileActions.deleteAvatar(messages)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AvatarUploaderScreen)
