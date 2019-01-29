package online.gettrained.backend.messages;

/**
 * Text codes for localization text, message
 */
public enum TextCode {
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

  // Assessment.Status
  be_text.enum_assessment_status_open
  be_text.enum_assessment_status_close
  be_text.enum_assessment_status_finished

  // Assessment.Acknowledgment
  be_text.enum_assessment_acknowledgment_accepted
  be_text.enum_assessment_acknowledgment_rejected
  be_text.enum_assessment_acknowledgment_undefined

  // Assessment.AppealStatus
  be_text.enum_assessment_appeal_status_open
  be_text.enum_assessment_appeal_status_close
  be_text.enum_assessment_appeal_status_undefined
  */

  LOCAL_LOCALIZATION_SAVED_SUCCESSFULLY(
    "be_text.local_localization_saved_successfully"),
  LOCAL_LOCALIZATION_DELETED_SUCCESSFULLY(
    "be_text.local_localization_deleted_successfully"),

  USERS_USER_UNLOCKED_SUCCESSFULLY("be_text.users_user_unlocked_successfully"),

  NOTIF_MESSAGE_TEMPLATE_DELETED_SUCCESSFULLY(
    "be_text.notif_message_template_deleted_successfully"),

  FILE_MANAGER_NAME_CHANGED_SUCCESSFULLY("be_text.file_manager_name_changed_successfully"),

  CONTENT_COURSE_PUBLISHED_SUCCESSFULLY("be_text.content_course_published_successfully"),
  CONTENT_COURSE_UNPUBLISHED_SUCCESSFULLY("be_text.content_course_unpublished_successfully"),
  CONTENT_COURSE_DELETED_SUCCESSFULLY("be_text.content_course_deleted_successfully"),
  CONTENT_TOPIC_DELETED_SUCCESSFULLY("be_text.content_topic_deleted_successfully"),
  CONTENT_TOPICS_SWAPPED_SUCCESSFULLY("be_text.content_topics_swapped_successfully"),
  CONTENT_LESSON_DELETED_SUCCESSFULLY("be_text.content_lesson_deleted_successfully"),
  CONTENT_LESSONS_SWAPPED_SUCCESSFULLY("be_text.content_lessons_swapped_successfully"),

  MEMBERS_ADDED_TO_COMPANY_SUCCESSFULLY("be_text.members_added_to_company_successfully"),
  MEMBERS_ADDED_TO_COMPANY_PARTLY_SUCCESSFULLY(
    "be_text.members_added_to_company_partly_successfully"),
  MEMBERS_ADDED_TO_COMPANY_FAILED("be_text.members_added_to_company_failed"),
  MEMBERS_DELETED_FROM_COMPANY_SUCCESSFULLY("be_text.members_deleted_from_company_successfully"),
  MEMBERS_ASSIGNED_ROLES_SUCCESSFULLY("be_text.members_assigned_roles_successfully"),
  MEMBERS_ASSIGNED_GROUPS_SUCCESSFULLY("be_text.members_assigned_groups_successfully"),
  MEMBERS_UNASSIGNED_GROUPS_SUCCESSFULLY("be_text.members_unassigned_groups_successfully"),

  MEMBER_GROUPS_DELETED_FROM_COMPANY_SUCCESSFULLY(
    "be_text.member_groups_deleted_from_company_successfully"),

  ASSESSMENT_CASES_DELETED_FROM_COMPANY_SUCCESSFULLY(
    "be_text.assessment_cases_deleted_from_company_successfully"),
  ASSESSMENT_CLOSED_SUCCESSFULLY("be_text.assessment_closed_successfully"),
  ASSESSMENT_REOPENED_SUCCESSFULLY("be_text.assessment_reopened_successfully"),
  ASSESSMENT_ACCEPTED_SUCCESSFULLY("be_text.assessment_accepted_successfully"),
  ASSESSMENT_REJECTED_SUCCESSFULLY("be_text.assessment_rejected_successfully"),
  ASSESSMENT_DELETED_FROM_COMPANY_SUCCESSFULLY(
    "be_text.assessment_deleted_from_company_successfully"),
  ASSESSMENT_SNIPPET_DELETED_SUCCESSFULLY("be_text.assessment_snippet_deleted_successfully"),
  ASSESSMENT_RESULT_GRADES_SET("be_text.assessment_result_grades_set"),
  ASSESSMENT_RECOMMENDATIONS_SWAPPED_SUCCESSFULLY(
    "be_text.assessment_recommendations_swapped_successfully"),
  ASSESSMENT_RECOMMENDATION_DELETED_SUCCESSFULLY(
    "be_text.assessment_recommendation_deleted_successfully"),
  ASSESSMENT_ASSESSORS_ASSIGNED_SUCCESSFULLY("be_text.assessment_assessors_assigned_successfully"),
  ASSESSMENT_CHAT_MESSAGE_DELETED_SUCCESSFULLY(
    "be_text.assessment_chat_message_deleted_successfully"),
  ASSESSMENT_TIME_TO_REJECT_HAS_NO_LIMIT("be_text.assessment_time_to_reject_has_no_limit"),
  ASSESSMENT_APPEAL_ESCALATED_SUCCESSFULLY("be_text.assessment_appeal_escalated_successfully"),
  ASSESSMENT_APPEAL_CLOSED_SUCCESSFULLY("be_text.assessment_appeal_closed_successfully"),

  ASSESSMENT_SETTINGS_SYSTEM_UNPUBLISHED_SUCCESSFULLY(
    "be_text.assessment_settings_system_unpublished_successfully"),
  ASSESSMENT_SETTINGS_SYSTEM_PUBLISHED_SUCCESSFULLY(
    "be_text.assessment_settings_system_published_successfully"),
  ASSESSMENT_SETTINGS_SYSTEM_DELETED_SUCCESSFULLY(
    "be_text.assessment_settings_system_deleted_successfully"),
  ASSESSMENT_REPORT_TO_EXCEL_REQUESTED_SUCCESSFULLY(
    "be_text.assessment_report_to_excel_requested_successfully"),
  ASSESSMENT_SUMMARY_SAVED_SUCCESSFULLY("be_text.ASSESSMENT_SUMMARY_SAVED_SUCCESSFULLY"),

  TASKS_TASK_DELETED_SUCCESSFULLY("be_text.tasks_task_deleted_successfully"),

  TASK_LOG_TASK_STARTED("be_text.task_log_task_started"),
  TASK_LOG_TASK_FINISHED("be_text.task_log_task_finished"),
  TASK_LOG_TASK_ZOOM_ASSESSMENTS_CREATED_FOR_ASSESSEE(
    "be_text.task_log_task_zoom_assessments_created_for_assessee"),
  TASK_LOG_TASK_ZOOM_ASSESSMENTS_CREATED("be_text.task_log_task_zoom_assessments_created"),

  @Deprecated
  FRAGMENTS_FRAGMENT_SHARED_SUCCESSFULLY("be_text.fragments_fragment_shared_successfully"),
  @Deprecated
  FRAGMENTS_FRAGMENTS_DELETED_SUCCESSFULLY("be_text.fragments_fragments_deleted_successfully"),
  @Deprecated
  FRAGMENTS_FRAGMENT_APPRAISED_SUCCESSFULLY("be_text.fragments_fragment_appraised_successfully"),

  LIBRARY_ELEMENT_SHARED_SUCCESSFULLY("be_text.library_element_shared_successfully"),
  LIBRARY_ELEMENTS_DELETED_SUCCESSFULLY("be_text.library_elements_deleted_successfully"),
  LIBRARY_ELEMENT_APPRAISED_SUCCESSFULLY("be_text.library_element_appraised_successfully"),
  LIBRARY_ELEMENT_COMMENT_DELETED_SUCCESSFULLY(
    "be_text.library_element_comment_deleted_successfully"),

  CONTESTS_CONTEST_STATUS_CHANGED_SUCCESSFULLY(
    "be_text.contests_contest_status_changed_successfully"),
  CONTESTS_CONTEST_LOGO_DELETED_SUCCESSFULLY("be_text.contests_contest_logo_deleted_successfully"),
  CONTESTS_STAGE_TASK_DELETED_SUCCESSFULLY("be_text.contests_stage_task_deleted_successfully"),
  CONTESTS_CONTESTS_DELETED_SUCCESSFULLY("be_text.contests_contests_deleted_successfully"),
  CONTESTS_COMPLAINT_SENT_SUCCESSFULLY("be_text.contests_complaint_sent_successfully"),
  CONTESTS_COMPLAINT_DELETED_SUCCESSFULLY("be_text.contests_complaint_deleted_successfully");

  public static final String ENUM_BLOB_DATA_TYPE_PREFIX = "be_text.enum_blob_data_type_";
  public static final String ENUM_ASSESSMENT_STATUS_PREFIX = "be_text.enum_assessment_status_";
  public static final String ENUM_ASSESSMENT_ACKNOWLEDGMENT_PREFIX = "be_text.enum_assessment_acknowledgment_";
  public static final String ENUM_ASSESSMENT_APPEAL_STATUS_PREFIX = "be_text.enum_assessment_appeal_status_";
  public static final String ENUM_CONTEST_STATUS_PREFIX = "be_text.enum_contest_status_";
  public static final String ENUM_TASK_TYPE_PREFIX = "be_text.enum_task_type_";
  public static final String ENUM_TASK_FILTER_PERIODS_PREFIX = "be_text.enum_task_filter_periods_";

  private final String text;

  /**
   * @param text
   */
  TextCode(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
