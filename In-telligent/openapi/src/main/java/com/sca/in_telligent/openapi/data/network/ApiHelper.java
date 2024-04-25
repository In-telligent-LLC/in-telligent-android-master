package com.sca.in_telligent.openapi.data.network;

import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.AutoSubscribeRequest;
import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.CallDetailResponse;
import com.sca.in_telligent.openapi.data.network.model.CheckEmailResponse;
import com.sca.in_telligent.openapi.data.network.model.CommunityResponse;
import com.sca.in_telligent.openapi.data.network.model.CreateEditGroupRequest;
import com.sca.in_telligent.openapi.data.network.model.CreateNotificationRequest;
import com.sca.in_telligent.openapi.data.network.model.DeliveryInfoResponse;
import com.sca.in_telligent.openapi.data.network.model.EditCommunityInviteeRequest;
import com.sca.in_telligent.openapi.data.network.model.EditSaveMessageRequest;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.ForgotPasswordRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.IntelligentGeofenceResponse;
import com.sca.in_telligent.openapi.data.network.model.InviteOthersRequest;
import com.sca.in_telligent.openapi.data.network.model.InviteeResponse;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.openapi.data.network.model.LoginRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguageResponse;
import com.sca.in_telligent.openapi.data.network.model.NotificationResponse;
import com.sca.in_telligent.openapi.data.network.model.NotificationsResponse;
import com.sca.in_telligent.openapi.data.network.model.PushTokenRequest;
import com.sca.in_telligent.openapi.data.network.model.PushTokenSuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.ResetPasswordRequest;
import com.sca.in_telligent.openapi.data.network.model.SearchCommunityResponse;
import com.sca.in_telligent.openapi.data.network.model.SignUpRequest;
import com.sca.in_telligent.openapi.data.network.model.SingleNotificationResponse;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.SuggestNotificationRequest;
import com.sca.in_telligent.openapi.data.network.model.SupportRequest;
import com.sca.in_telligent.openapi.data.network.model.TranslationResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.VoipCallRequest;
import com.sca.in_telligent.openapi.data.network.model.VoipCallResponse;
import com.sca.in_telligent.openapi.data.network.model.VoipTokenResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper {
    @POST(ApiEndPoint.ALERT_DELIVERED)
    Observable<SuccessResponse> alertDelivered(@Body AlertOpenedRequest alertOpenedRequest);

    @POST(ApiEndPoint.ALERT_OPENED)
    Observable<SuccessResponse> alertOpened(@Body AlertOpenedRequest alertOpenedRequest);

    @POST(ApiEndPoint.SUBSCRIBER_OPENED_APP)
    Observable<SuccessResponse> appOpened();

    @POST(ApiEndPoint.AUTO_SUBSCRIBE)
    Observable<SuccessResponse> autoSubscribe(@Body AutoSubscribeRequest autoSubscribeRequest);

    @POST(ApiEndPoint.CHECK_EMAIL)
    Observable<CheckEmailResponse> checkEmail(@Query("email") String str);

    @POST(ApiEndPoint.CLICK_AD)
    Observable<SuccessResponse> clickAd(@Path("adId") int i);

    @POST(ApiEndPoint.CREATE_NOTIFICATION)
    @Multipart
    Observable<SuccessResponse> createNotification(@Part List<MultipartBody.Part> list, @Part("buildingId") RequestBody requestBody, @Part("title") RequestBody requestBody2, @Part("body") RequestBody requestBody3, @Part("type") RequestBody requestBody4, @Part("subscribers") RequestBody requestBody5, @Part("send_to_email") RequestBody requestBody6);

    @POST(ApiEndPoint.CREATE_NOTIFICATION)
    Observable<SuccessResponse> createNotificationNoThumbnail(@Body CreateNotificationRequest createNotificationRequest);

    @POST(ApiEndPoint.CREATE_UPDATE_GROUP)
    @Multipart
    Observable<SuccessResponse> createOrEditGroup(@Path("buildingId") String str, @Part MultipartBody.Part part, @Part("name") RequestBody requestBody, @Part("description") RequestBody requestBody2);

    @POST(ApiEndPoint.CREATE_UPDATE_GROUP)
    Observable<SuccessResponse> createOrEditGroupNoThumbnail(@Path("buildingId") String str, @Body CreateEditGroupRequest createEditGroupRequest);

    @POST(ApiEndPoint.DELETE_ALERT)
    Observable<SuccessResponse> deleteAlert(@Body AlertDeleteRequest alertDeleteRequest);

    @POST(ApiEndPoint.DELETE_PERSONAL_COMMUNITY)
    Observable<SuccessResponse> deletePersonalCommunity(@Path("buildingId") String str);

    @POST(ApiEndPoint.EDIT_MEMBER)
    Observable<SuccessResponse> editMember(@Path("inviteeId") int i, @Body EditCommunityInviteeRequest editCommunityInviteeRequest);

    @POST(ApiEndPoint.SAVED_MESSAGES_CURRENT)
    Observable<SuccessResponse> editSavedMessage(@Body EditSaveMessageRequest editSaveMessageRequest);

    @POST(ApiEndPoint.FORGOT_PASSWORD)
    Observable<LoginResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @GET(ApiEndPoint.AD)
    Observable<AdResponse> getAd();

    @GET(ApiEndPoint.TRANSLATE_MESSAGE_ALERT)
    Observable<TranslationResponse> getAlertTranslation(@Path("id") String str, @Path("lang") String str2);

    @GET(ApiEndPoint.GET_NOTIFICATIONS)
    Observable<NotificationsResponse> getAllNotifications(@Path("buildingId") String str);

    ApiHeader getApiHeader();

    @GET(ApiEndPoint.BUILDING_MEMBERS)
    Observable<ArrayList<BuildingMember>> getBuildingMembers(@Path(encoded = false, value = "buildingId") String str);

    @GET(ApiEndPoint.VOIP_CALL_DETAILS)
    Observable<CallDetailResponse> getCallDetail(@Path("conferenceId") String str);

    @GET(ApiEndPoint.GET_COMMUNITY)
    Observable<CommunityResponse> getCommunity(@Path("buildingId") int i);

    @GET(ApiEndPoint.CURRENT_SUBSCRIBER)
    Observable<SubscriberResponse> getCurrentSubscriber();

    @GET(ApiEndPoint.CURRENT_SUBSCRIBER_INBOX)
    Observable<NotificationResponse> getCurrentSubscriberInbox(@Path("page") String str);

    @GET(ApiEndPoint.DELIVERY_INFO)
    Observable<DeliveryInfoResponse> getDeliveryInformation(@Path("buildingId") String str, @Path("notificationId") String str2);

    @POST(ApiEndPoint.GET_GEOFENCES)
    Observable<IntelligentGeofenceResponse> getGeofences(@Query("lat") String str, @Query("lng") String str2);

    @GET(ApiEndPoint.BUILDING_INVITEES)
    Observable<InviteeResponse> getInvitees(@Path("buildingId") String str);

    @GET(ApiEndPoint.LIST_LANGUAGES)
    Observable<NotificationLanguageResponse> getLanguages();

    @GET(ApiEndPoint.SUBSCRIBER_LAST_LOCATION)
    Observable<LocationModel> getLastLocation(@Path("subscriberId") String str);

    @GET(ApiEndPoint.GET_NOTIFICATION)
    Observable<SingleNotificationResponse> getNotification(@Path("notificationId") String str);

    @GET(ApiEndPoint.SAVED_MESSAGES_CURRENT)
    Observable<NotificationResponse> getSavedMessages();

    @GET(ApiEndPoint.SUGGESTED_BUILDINGS)
    Observable<SearchCommunityResponse> getSuggestedGroups();

    @GET(ApiEndPoint.UPDATED_LOCATION)
    Observable<LocationModel> getUpdatedLocation(@Path("subscriberId") String str);

    @POST(ApiEndPoint.VOIP_TOKEN)
    Observable<VoipTokenResponse> getVoipToken(@Body VoipCallRequest voipRequest);

    @POST(ApiEndPoint.SUGGESTED_BUILDINGS_IGNORE)
    Observable<SuccessResponse> ignoreBuilding(@Path("buildingId") String str);

    @POST(ApiEndPoint.INVITE_OTHERS)
    Observable<SuccessResponse> inviteOthers(@Path("buildingId") String str, @Body InviteOthersRequest inviteOthersRequest);

    @POST(ApiEndPoint.FACEBOOK_LOGIN)
    Observable<LoginResponse> loginFacebook(@Body FacebookLoginRequest facebookLoginRequest);

    @POST(ApiEndPoint.GOOGLE_LOGIN)
    Observable<LoginResponse> loginGoogle(@Body GoogleLoginRequest googleLoginRequest);

    @POST(ApiEndPoint.LOGIN_WITH_PASSWORD)
    @NonNull
    Observable<LoginResponse> loginWithPassword(@Body LoginRequest loginRequest);

    @POST(ApiEndPoint.VOIP_MAKE_CALL)
    Observable<VoipCallResponse> makeVoipCall(@Body VoipCallRequest voipCallRequest);

    @POST(ApiEndPoint.RECEIVE_MESSAGE)
    Observable<JSONObject> receivedMessage(@Path("msgId") String str);

    @POST(ApiEndPoint.REFRESH_GEOFENCES)
    Observable<SuccessResponse> refreshGeofences(@Body LocationModel locationModel);

    @POST(ApiEndPoint.REGISTER_PUSH)
    Observable<PushTokenSuccessResponse> registerPushToken(@Body PushTokenRequest pushTokenRequest);

    @POST(ApiEndPoint.REMOVE_MEMBER_FROM_COMMUNITY)
    Observable<SuccessResponse> removeMember(@Path("inviteId") int i);

    @POST(ApiEndPoint.RESET_PASSWORD)
    Observable<LoginResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @POST(ApiEndPoint.PERSONAL_SAFETY_RESPONSE)
    Observable<JSONObject> respondPersonalSafety(@Body HashMap<String, Object> hashMap);

    @POST(ApiEndPoint.RESPOND_TO_MESSAGE)
    Observable<JSONObject> respondToMessage(@Path("msgId") String str, @Body HashMap<String, Object> hashMap);

    @GET(ApiEndPoint.SEARCH_COMMUNITIES)
    Observable<SearchCommunityResponse> searchCommunities(@Query("query") String str);

    @POST(ApiEndPoint.SEND_HELP_EMAIL)
    Observable<SuccessResponse> sendSupportMail(@Body SupportRequest supportRequest);

    @POST(ApiEndPoint.FEED_ALERT_SEND)
    Observable<String> sendWeatherAlert(@Path("weatherAlertId") String str, @Body LocationModel locationModel);

    @POST(ApiEndPoint.SIGN_UP)
    Observable<LoginResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST(ApiEndPoint.SUGGEST_NOTIFICATION)

    Observable<SuccessResponse> suggestNotification(@Body SuggestNotificationRequest suggestNotificationRequest);

    @POST(ApiEndPoint.SUGGEST_NOTIFICATION)
    Observable<SuccessResponse> suggestNotificationNoThumbnail(@Body SuggestNotificationRequest suggestNotificationRequest);

    @POST(ApiEndPoint.SYNC_ALERT_SUBSCRIPTION)
    Observable<SuccessResponse> syncAlertSubscription(@Body AlertSubscriptionRequest alertSubscriptionRequest);

    @POST(ApiEndPoint.UPDATE_SUBSCRIPTION)
    Observable<SuccessResponse> updateSubscription(@Body UpdateSubscriptionRequest updateSubscriptionRequest);

    @POST(ApiEndPoint.SUBSCRIBE_TO_COMMUNITY)
    Observable<SuccessResponse> updateSubscriptionToCommunity(@Body SubscribeToCommunityRequest subscribeToCommunityRequest);

    @POST(ApiEndPoint.SYNC_METADA)
    Observable<SubscriberResponse> updateUser(@Body UpdateSubscriberRequest updateSubscriberRequest);
}
