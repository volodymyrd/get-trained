package online.gettrained.backend.messages;

/**
 * Text codes for localization text or message.
 */
public enum TextCode implements MessageCode {
  UNDEFINE("be_text.undefined"),

  COMMON_MESSAGE_NOTHING_TO_DELETE("be_text.common_message_nothing_to_delete"),

  /* We don't need to have a separated enum values for enum text local
  /* because we do it automatically using the {@link Utils.enumToMapWithLocalSortedByLocalText}
  /* method but place them here to be shure don't forget create a translation for them.
  // BlobData.Type
  be_text.enum_blob_data_type_text
  be_text.enum_blob_data_type_pdf
  be_text.enum_blob_data_type_image
  be_text.enum_blob_data_type_audio
  be_text.enum_blob_data_type_video
  be_text.enum_blob_data_type_word
  be_text.enum_blob_data_type_excel
  be_text.enum_blob_data_type_powerpoint
  be_text.enum_blob_data_type_archive
  be_text.enum_blob_data_type_undefine
  */

  AUTH_INFO_NOT_FINISHED_SIGN_UP("be_text.auth_info_not_finished_sign_up"),
  AUTH_SUCCESS_SIGNED_IN("be_text.auth_success_signed_in"),
  AUTH_SUCCESS_SIGNED_OUT("be_text.auth_success_signed_out"),
  AUTH_SUCCESS_SIGNED_UP("be_text.auth_success_signed_up"),
  AUTH_SUCCESS_PASSWORD_RESET("be_text.auth_success_password_reset"),
  AUTH_INFO_PASSWORD_RESET_PROCESS("be_text.auth_info_password_reset_process"),

  LOCAL_LOCALIZATION_SAVED_SUCCESSFULLY("be_text.local_localization_saved_successfully"),
  LOCAL_LOCALIZATION_DELETED_SUCCESSFULLY("be_text.local_localization_deleted_successfully"),

  PROFILE_SUCCESS_AVATAR_DELETE("be_text.profile_success_avatar_delete"),

  SETTINGS_SUCCESS_PASSWORD_CHANGED("be_text.settings_success_password_changed"),

  USERS_USER_UNLOCKED_SUCCESSFULLY("be_text.users_user_unlocked_successfully"),

  ACTIVITY_SUCCESS_TRAINER_ADDED("be_text.activity_success_trainer_added"),
  ACTIVITY_SUCCESS_TRAINER_REMOVED("be_text.activity_success_trainer_removed"),
  ACTIVITY_SUCCESS_SENT_TRAINEE_CONNECTION_REQUEST(
      "be_text.activity_success_sent_trainee_connection_request"),
  ACTIVITY_YOU_ARE_ALREADY_TRAINER("be_text.activity_you_are_already_trainer"),
  ACTIVITY_YOU_ARE_ALREADY_CONNECTED("be_text.activity_you_are_already_connected"),
  ACTIVITY_CONNECTION_REQUEST_EXISTS("be_text.activity_connection_request_exists"),
  ACTIVITY_SUCCESS_ACCEPT_CONNECTION("be_text.activity_success_accept_connection"),
  ACTIVITY_SUCCESS_REMOVE_CONNECTION("be_text.activity_success_accept_connection"),

  NOTIF_MESSAGE_TEMPLATE_DELETED_SUCCESSFULLY(
      "be_text.notif_message_template_deleted_successfully");

  public static final String ENUM_BLOB_DATA_TYPE_PREFIX = "be_text.enum_blob_data_type_";
  public static final String ENUM_PROFILE_GENDER_PREFIX = "be_text.enum_profile_gender_prefix_";

  private final String text;

  /**
   *
   */
  TextCode(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
