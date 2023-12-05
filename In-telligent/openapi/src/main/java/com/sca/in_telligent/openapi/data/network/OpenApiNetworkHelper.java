package com.sca.in_telligent.openapi.data.network;


import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.AutoSubscribeRequest;
import com.sca.in_telligent.openapi.data.network.model.BeaconsResponse;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import retrofit2.http.Body;


public class OpenApiNetworkHelper implements ApiHelper {

    private ApiHelper protectedApiService;
    private ApiHelper publicApiService;
    private ApiHelper uploadService;

    public OpenApiNetworkHelper(ApiHelper protectedApiService,
                                ApiHelper publicApiService,
                                ApiHelper uploadService) {
        this.protectedApiService = protectedApiService;
        this.publicApiService = publicApiService;
        this.uploadService = uploadService;
    }

    @Override
    public ApiHeader getApiHeader() {
        return null;
    }

    @Override
    public Observable<LoginResponse> loginWithPassword(
            @Body LoginRequest loginRequest) {
        return publicApiService.loginWithPassword(loginRequest);
    }

    @Override
    public Observable<LoginResponse> signUp(SignUpRequest signUpRequest) {
        return publicApiService.signUp(signUpRequest);
    }

    @Override
    public Observable<CheckEmailResponse> checkEmail(String email) {
        return publicApiService.checkEmail(email);
    }

    @Override
    public Observable<LoginResponse> loginFacebook(FacebookLoginRequest facebookLoginRequest) {
        return publicApiService.loginFacebook(facebookLoginRequest);
    }

    @Override
    public Observable<LoginResponse> loginGoogle(GoogleLoginRequest googleLoginRequest) {
        return publicApiService.loginGoogle(googleLoginRequest);
    }

    @Override
    public Observable<LoginResponse> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return publicApiService.resetPassword(resetPasswordRequest);
    }

    @Override
    public Observable<LoginResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return publicApiService.forgotPassword(forgotPasswordRequest);
    }

    @Override
    public Observable<SubscriberResponse> getCurrentSubscriber() {
        return protectedApiService.getCurrentSubscriber();
    }

    @Override
    public Observable<NotificationResponse> getCurrentSubscriberInbox(String page) {
        return protectedApiService.getCurrentSubscriberInbox(page);
    }

    @Override
    public Observable<NotificationResponse> getSavedMessages() {
        return protectedApiService.getSavedMessages();
    }

    @Override
    public Observable<SuccessResponse> editSavedMessage(
            EditSaveMessageRequest editSaveMessageRequest) {
        return protectedApiService.editSavedMessage(editSaveMessageRequest);
    }

    @Override
    public Observable<SearchCommunityResponse> searchCommunities(String query) {
        return protectedApiService.searchCommunities(query);
    }

    @Override
    public Observable<CommunityResponse> getCommunity(int buildingId) {
        return protectedApiService.getCommunity(buildingId);
    }

    @Override
    public Observable<NotificationsResponse> getAllNotifications(String buildingId) {
        return protectedApiService.getAllNotifications(buildingId);
    }

    @Override
    public Observable<SingleNotificationResponse> getNotification(String notificationId) {
        return protectedApiService.getNotification(notificationId);
    }

    @Override
    public Observable<NotificationLanguageResponse> getLanguages() {
        return protectedApiService.getLanguages();
    }

    @Override
    public Observable<SuccessResponse> syncAlertSubscription(
            AlertSubscriptionRequest alertSubscriptionRequest) {
        return protectedApiService.syncAlertSubscription(alertSubscriptionRequest);
    }

    @Override
    public Observable<SuccessResponse> createOrEditGroup(String buildingId, Part filePart,
                                                         RequestBody name, RequestBody description) {
        return uploadService.createOrEditGroup(buildingId, filePart, name, description);
    }

    @Override
    public Observable<SuccessResponse> createOrEditGroupNoThumbnail(String buildingId,
                                                                    CreateEditGroupRequest createEditGroupRequest) {
        return uploadService.createOrEditGroupNoThumbnail(buildingId, createEditGroupRequest);
    }

    @Override
    public Observable<SearchCommunityResponse> getSuggestedGroups() {
        return protectedApiService.getSuggestedGroups();
    }

    @Override
    public Observable<SuccessResponse> updateSubscription(
            UpdateSubscriptionRequest updateSubscriptionRequest) {
        return protectedApiService.updateSubscription(updateSubscriptionRequest);
    }

    @Override
    public Observable<InviteeResponse> getInvitees(String buildingId) {
        return protectedApiService.getInvitees(buildingId);
    }

    @Override
    public Observable<ArrayList<BuildingMember>> getBuildingMembers(String buildingId) {
        return protectedApiService.getBuildingMembers(buildingId);
    }

    @Override
    public Observable<DeliveryInfoResponse> getDeliveryInformation(String buildingId,
                                                                   String notificationId) {
        return protectedApiService.getDeliveryInformation(buildingId, notificationId);
    }

    @Override
    public Observable<SuccessResponse> inviteOthers(String buildingId,
                                                    InviteOthersRequest inviteOthersRequest) {
        return protectedApiService.inviteOthers(buildingId, inviteOthersRequest);
    }

    @Override
    public Observable<SuccessResponse> editMember(int inviteeId,
                                                  EditCommunityInviteeRequest editCommunityInviteeRequest) {
        return protectedApiService.editMember(inviteeId, editCommunityInviteeRequest);
    }

    @Override
    public Observable<SuccessResponse> removeMember(int inviteId) {
        return protectedApiService.removeMember(inviteId);
    }

    @Override
    public Observable<SubscriberResponse> updateUser(
            UpdateSubscriberRequest updateSubscriberRequest) {
        return protectedApiService.updateUser(updateSubscriberRequest);
    }

    @Override
    public Observable<SuccessResponse> createNotification(List<Part> attachments,
                                                          RequestBody buildingId,
                                                          RequestBody title, RequestBody body, RequestBody type, RequestBody subscribers,
                                                          RequestBody send_to_email) {
        return uploadService
                .createNotification(attachments, buildingId, title, body, type, subscribers,
                        send_to_email);
    }

    @Override
    public Observable<SuccessResponse> suggestNotification(List<Part> attachments,
                                                           RequestBody buildingId, RequestBody title, RequestBody body) {
        return uploadService
                .suggestNotification(attachments, buildingId, title, body);
    }

    @Override
    public Observable<SuccessResponse> createNotificationNoThumbnail(
            CreateNotificationRequest createNotificationRequest) {
        return uploadService
                .createNotificationNoThumbnail(createNotificationRequest);
    }

    @Override
    public Observable<SuccessResponse> suggestNotificationNoThumbnail(
            SuggestNotificationRequest suggestNotificationRequest) {
        return uploadService
                .suggestNotificationNoThumbnail(suggestNotificationRequest);
    }

    @Override
    public Observable<PushTokenSuccessResponse> registerPushToken(PushTokenRequest pushTokenRequest) {
        return protectedApiService.registerPushToken(pushTokenRequest);
    }

    @Override
    public Observable<LocationModel> getLastLocation(String subscriberId) {
        return protectedApiService.getLastLocation(subscriberId);
    }

    @Override
    public Observable<LocationModel> getUpdatedLocation(String subscriberId) {
        return protectedApiService.getUpdatedLocation(subscriberId);
    }

    @Override
    public Observable<String> sendWeatherAlert(String weatherAlertId, LocationModel locationModel) {
        return protectedApiService.sendWeatherAlert(weatherAlertId, locationModel);
    }

    @Override
    public Observable<JSONObject> receivedMessage(String msgId) {
        return protectedApiService.receivedMessage(msgId);
    }

    @Override
    public Observable<JSONObject> respondToMessage(String msgId, HashMap<String, Object> map) {
        return protectedApiService.respondToMessage(msgId, map);
    }

    @Override
    public Observable<SuccessResponse> alertOpened(AlertOpenedRequest alertOpenedRequest) {
        return protectedApiService.alertOpened(alertOpenedRequest);
    }

    @Override
    public Observable<SuccessResponse> alertDelivered(AlertOpenedRequest alertDeliveredRequest) {
        return protectedApiService.alertDelivered(alertDeliveredRequest);
    }

    @Override
    public Observable<JSONObject> respondPersonalSafety(HashMap<String, Object> map) {
        return uploadService.respondPersonalSafety(map);
    }

    @Override
    public Observable<TranslationResponse> getAlertTranslation(String id, String lang) {
        return protectedApiService.getAlertTranslation(id, lang);
    }

    @Override
    public Observable<IntelligentGeofenceResponse> getGeofences(String lat, String lng) {
        return protectedApiService.getGeofences(lat, lng);
    }

    @Override
    public Observable<SuccessResponse> refreshGeofences(LocationModel refreshGeofenceRequest) {
        return protectedApiService.refreshGeofences(refreshGeofenceRequest);
    }

    @Override
    public Observable<VoipTokenResponse> getVoipToken() {
        return protectedApiService.getVoipToken();
    }

    @Override
    public Observable<VoipCallResponse> makeVoipCall(VoipCallRequest voipCallRequest) {
        return protectedApiService.makeVoipCall(voipCallRequest);
    }

    @Override
    public Observable<CallDetailResponse> getCallDetail(String conferenceId) {
        return protectedApiService.getCallDetail(conferenceId);
    }

    @Override
    public Observable<SuccessResponse> deletePersonalCommunity(String buildingId) {
        return uploadService.deletePersonalCommunity(buildingId);
    }

    @Override
    public Observable<SuccessResponse> deleteAlert(AlertDeleteRequest alertDeleteRequest) {
        return protectedApiService.deleteAlert(alertDeleteRequest);
    }

    @Override
    public Observable<SuccessResponse> sendSupportMail(SupportRequest supportRequest) {
        return protectedApiService.sendSupportMail(supportRequest);
    }

    @Override
    public Observable<SuccessResponse> updateSubscriptionToCommunity(SubscribeToCommunityRequest subscribeToCommunityRequest) {
        return protectedApiService.updateSubscriptionToCommunity(subscribeToCommunityRequest);
    }

    @Override
    public Observable<SuccessResponse> autoSubscribe(AutoSubscribeRequest autoSubscribeRequest) {
        return protectedApiService.autoSubscribe(autoSubscribeRequest);
    }

    @Override
    public Observable<SuccessResponse> appOpened() {
        return protectedApiService.appOpened();
    }

    @Override
    public Observable<BeaconsResponse> getAllBeacons() {
        return protectedApiService.getAllBeacons();
    }

    @Override
    public Observable<SuccessResponse> ignoreBuilding(String buildingId) {
        return protectedApiService.ignoreBuilding(buildingId);
    }

    @Override
    public Observable<AdResponse> getAd() {
        return protectedApiService.getAd();
    }

    @Override
    public Observable<SuccessResponse> clickAd(int adId) {
        return protectedApiService.clickAd(adId);
    }

    public List<Class<?>> getInnerClasses(Class<?> modelClass) {
        System.out.println();
        List<Class<?>> classList = new ArrayList<Class<?>>();
        classList.add(modelClass);
        classList.addAll(Arrays
                .asList(modelClass.getDeclaredClasses()));
        return classList;
    }
}
