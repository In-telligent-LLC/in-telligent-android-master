package com.sca.in_telligent.openapi.data.network;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApiEndPoint {
    public static final String AD = "subscribers/ad";
    public static final String ALERT_DELIVERED = "subscribers/deliveredAlert";
    public static final String ALERT_OPENED = "subscribers/openedAlert";
    public static final String AUTO_SUBSCRIBE = "subscribers/autosubscribeOptOut";
    public static final String BASE_IMAGE_URL = "https://app.in-telligent.com";
    public static final String BASE_UPLOAD = "https://app.in-telligent.com/api/";
    public static final String BASE_URL = "https://api.in-telligent.com/api/";
    public static final String BUILDING_INVITEES = "buildings/personal-communities/{buildingId}/invites";
    public static final String BUILDING_MEMBERS = "buildings/{buildingId}/subscribers";
    public static final String CHECK_EMAIL = "subscribers/checkEmail";
    public static final String CLICK_AD = "subscribers/clickAd/{adId}";
    public static final String CREATE_NOTIFICATION = "buildings/send_building_alert";
    public static final String CREATE_UPDATE_GROUP = "buildings/create_personal_community/{buildingId}";
    public static final String CURRENT_SUBSCRIBER = "subscribers/me";
    public static final String CURRENT_SUBSCRIBER_INBOX = "subscribers/inbox/{page}";
    public static final String DELETE_ALERT = "subscribers/deletedAlert";
    public static final String DELETE_PERSONAL_COMMUNITY = "buildings/delete_personal_community/{buildingId}";
    public static final String DELIVERY_INFO = "buildings/{buildingId}/delivery-info/{notificationId}";
    public static final String EDIT_MEMBER = "buildings/personalCommunities/editInvite/{inviteeId}";
    public static final String FACEBOOK_LOGIN = "auth/tokenFacebook";
    public static final String FEED_ALERT_SEND = "/feed-alerts/send/{weatherAlertId}";
    public static final String FORGOT_PASSWORD = "auth/forgotPassword";
    public static final String GET_COMMUNITY = "buildings/get/{buildingId}";
    public static final String GET_GEOFENCES = "geofences_new/byLocation?";
    public static final String GET_NOTIFICATION = "alerts/getNotification/{notificationId}";
    public static final String GET_NOTIFICATIONS = "buildings/allNotifications/{buildingId}";
    public static final String GOOGLE_LOGIN = "auth/tokenGoogle";
    public static final String INVITE_OTHERS = "buildings/personalCommunities/{buildingId}/invite";
    public static final String LIST_LANGUAGES = "subscribers/listLanguages";
    public static final String LOGIN_WITH_PASSWORD = "auth/token";
    public static final String PERSONAL_SAFETY_RESPONSE = "subscribers/personal_safety_response";
    public static final String RECEIVE_MESSAGE = "receivedMessage/{msgId}";
    public static final String REFRESH_GEOFENCES = "subscribers/refreshGeofences";
    public static final String REGISTER_PUSH = "subscribers/registerPush";
    public static final String REMOVE_MEMBER_FROM_COMMUNITY = "buildings/personalCommunities/deleteInvite/{inviteId}";
    public static final String RESET_PASSWORD = "auth/resetPassword";
    public static final String RESPOND_TO_MESSAGE = "respondToMessage/{msgId}";
    public static final String SAVED_MESSAGES_CURRENT = "subscribers/savedMessages";
    public static final String SEARCH_COMMUNITIES = "buildings/search";
    public static final String SEND_HELP_EMAIL = "subscribers/sendHelpEmail";
    public static final String SIGN_UP = "auth/signup";
    public static final String SUBSCRIBER_LAST_LOCATION = "location-ping/get-subscriber-last-known/{subscriberId}";
    public static final String SUBSCRIBER_OPENED_APP = "subscribers/openedApp";
    public static final String SUBSCRIBE_TO_COMMUNITY = "buildings/update-subscription";
    public static final String SUGGESTED_BUILDINGS = "buildings/suggested";
    public static final String SUGGESTED_BUILDINGS_IGNORE = "buildings/suggestedOptOut/{buildingId}";
    public static final String SUGGEST_NOTIFICATION = "subscribers/suggestNotification";
    public static final String SYNC_ALERT_SUBSCRIPTION = "subscribers/syncAlertSubscription";
    public static final String SYNC_METADA = "subscribers/syncMetadata";
    public static final String TRANSLATE_MESSAGE_ALERT = "buildings/translate-alert/{id}/{lang}";
    public static final String UPDATED_LOCATION = "location-ping/get-subscriber/{subscriberId}";
    public static final String UPDATE_SUBSCRIPTION = "buildings/update-subscription?onlySuccess=1";
    public static final String VOIP_CALL_DETAILS = "voip/callDetails/{conferenceId}";
    public static final String VOIP_MAKE_CALL = "voip/makeCall";
    public static final String VOIP_TOKEN = "voip/token";

    private ApiEndPoint() {
    }
}
