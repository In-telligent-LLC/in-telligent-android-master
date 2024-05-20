package com.sca.in_telligent.data;


import android.content.Context;

import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.openapi.data.network.ApiHeader;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.AutoSubscribeRequest;
import com.sca.in_telligent.openapi.data.network.model.BeaconsResponse;
import com.sca.in_telligent.openapi.data.network.model.Building;
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
import com.sca.in_telligent.openapi.data.network.model.SubscriberOptOut;
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

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<LoginResponse> loginWithPassword(LoginRequest loginRequest) {
        return mApiHelper.loginWithPassword(loginRequest);
    }

    @Override
    public Observable<LoginResponse> signUp(SignUpRequest signUpRequest) {
        return mApiHelper.signUp(signUpRequest);
    }

    @Override
    public Observable<CheckEmailResponse> checkEmail(String email) {
        return mApiHelper.checkEmail(email);
    }

    @Override
    public Observable<LoginResponse> loginFacebook(FacebookLoginRequest facebookLoginRequest) {
        return mApiHelper.loginFacebook(facebookLoginRequest);
    }

    @Override
    public Observable<LoginResponse> loginGoogle(GoogleLoginRequest googleLoginRequest) {
        return mApiHelper.loginGoogle(googleLoginRequest);
    }

    @Override
    public Observable<LoginResponse> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return mApiHelper.resetPassword(resetPasswordRequest);
    }

    @Override
    public Observable<LoginResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return mApiHelper.forgotPassword(forgotPasswordRequest);
    }

    @Override
    public Observable<SubscriberResponse> getCurrentSubscriber() {
        return mApiHelper.getCurrentSubscriber();
    }

    @Override
    public Observable<NotificationResponse> getCurrentSubscriberInbox(String page) {
        return mApiHelper.getCurrentSubscriberInbox(page);
    }

    @Override
    public Observable<NotificationResponse> getSavedMessages() {
        return mApiHelper.getSavedMessages();
    }

    @Override
    public Observable<SuccessResponse> editSavedMessage(
            EditSaveMessageRequest editSaveMessageRequest) {
        return mApiHelper.editSavedMessage(editSaveMessageRequest);
    }

    @Override
    public Observable<SearchCommunityResponse> searchCommunities(String query) {
        return mApiHelper.searchCommunities(query);
    }

    @Override
    public Observable<NotificationsResponse> getAllNotifications(String buildingId) {
        return mApiHelper.getAllNotifications(buildingId);
    }

    @Override
    public Observable<SingleNotificationResponse> getNotification(String notificationId) {
        return mApiHelper.getNotification(notificationId);
    }

    @Override
    public Observable<NotificationLanguageResponse> getLanguages() {
        return mApiHelper.getLanguages();
    }

    @Override
    public Observable<SuccessResponse> syncAlertSubscription(
            AlertSubscriptionRequest alertSubscriptionRequest) {
        return mApiHelper.syncAlertSubscription(alertSubscriptionRequest);
    }

    @Override
    public Observable<SuccessResponse> createOrEditGroup(String buildingId, Part filePart,
                                                         RequestBody name, RequestBody description) {
        return mApiHelper.createOrEditGroup(buildingId, filePart, name, description);
    }

    @Override
    public Observable<SuccessResponse> createOrEditGroupNoThumbnail(String buildingId,
                                                                    CreateEditGroupRequest createEditGroupRequest) {
        return mApiHelper.createOrEditGroupNoThumbnail(buildingId, createEditGroupRequest);
    }

    @Override
    public Observable<CommunityResponse> getCommunity(int buildingId) {
        return mApiHelper.getCommunity(buildingId);
    }

    @Override
    public Observable<SearchCommunityResponse> getSuggestedGroups() {
        return mApiHelper.getSuggestedGroups();
    }

    @Override
    public Observable<SuccessResponse> updateSubscription(
            UpdateSubscriptionRequest updateSubscriptionRequest) {
        return mApiHelper.updateSubscription(updateSubscriptionRequest);
    }

    @Override
    public Observable<InviteeResponse> getInvitees(String buildingId) {
        return mApiHelper.getInvitees(buildingId);
    }

    @Override
    public Observable<ArrayList<BuildingMember>> getBuildingMembers(String buildingId) {
        return mApiHelper.getBuildingMembers(buildingId);
    }

    @Override
    public Observable<DeliveryInfoResponse> getDeliveryInformation(String buildingId,
                                                                   String notificationId) {
        return mApiHelper.getDeliveryInformation(buildingId, notificationId);
    }

    @Override
    public Observable<SuccessResponse> inviteOthers(String buildingId,
                                                    InviteOthersRequest inviteOthersRequest) {
        return mApiHelper.inviteOthers(buildingId, inviteOthersRequest);
    }

    @Override
    public Observable<SuccessResponse> editMember(int inviteeId, EditCommunityInviteeRequest editCommunityInviteeRequest) {
        return mApiHelper.editMember(inviteeId, editCommunityInviteeRequest);
    }

    @Override
    public Observable<SuccessResponse> removeMember(int inviteId) {
        return mApiHelper.removeMember(inviteId);
    }

    @Override
    public Observable<SubscriberResponse> updateUser(
            UpdateSubscriberRequest updateSubscriberRequest) {
        return mApiHelper.updateUser(updateSubscriberRequest);
    }

    @Override
    public Observable<SuccessResponse> createNotification(List<Part> attachments,
                                                          RequestBody buildingId,
                                                          RequestBody title, RequestBody body, RequestBody type, RequestBody subscribers,
                                                          RequestBody send_to_email) {
        return mApiHelper
                .createNotification(attachments, buildingId, title, body, type, subscribers,
                        send_to_email);
    }

    @Override
    public Observable<SuccessResponse> suggestNotification(List<Part> attachments,
                                                           RequestBody buildingId, RequestBody title, RequestBody body) {
        return mApiHelper.suggestNotification(attachments, buildingId, title, body);
    }

    @Override
    public Observable<SuccessResponse> createNotificationNoThumbnail(
            CreateNotificationRequest createNotificationRequest) {
        return mApiHelper.createNotificationNoThumbnail(createNotificationRequest);
    }

    @Override
    public Observable<SuccessResponse> suggestNotificationNoThumbnail(
            SuggestNotificationRequest suggestNotificationRequest) {
        return mApiHelper.suggestNotificationNoThumbnail(suggestNotificationRequest);
    }

    @Override
    public Observable<PushTokenSuccessResponse> registerPushToken(PushTokenRequest pushTokenRequest) {
        return mApiHelper.registerPushToken(pushTokenRequest);
    }

    @Override
    public Observable<LocationModel> getLastLocation(String subscriberId) {
        return mApiHelper.getLastLocation(subscriberId);
    }

    @Override
    public Observable<LocationModel> getUpdatedLocation(String subscriberId) {
        return mApiHelper.getUpdatedLocation(subscriberId);
    }

    @Override
    public Observable<String> sendWeatherAlert(String weatherAlertId, LocationModel locationModel) {
        return mApiHelper.sendWeatherAlert(weatherAlertId, locationModel);
    }

    @Override
    public Observable<JSONObject> receivedMessage(String msgId) {
        return mApiHelper.receivedMessage(msgId);
    }

    @Override
    public Observable<JSONObject> respondToMessage(String msgId, HashMap<String, Object> map) {
        return mApiHelper.respondToMessage(msgId, map);
    }

    @Override
    public Observable<SuccessResponse> alertOpened(AlertOpenedRequest alertOpenedRequest) {
        return mApiHelper.alertOpened(alertOpenedRequest);
    }

    @Override
    public Observable<SuccessResponse> alertDelivered(AlertOpenedRequest alertDeliveredRequest) {
        return mApiHelper.alertDelivered(alertDeliveredRequest);
    }

    @Override
    public Observable<JSONObject> respondPersonalSafety(HashMap<String, Object> map) {
        return mApiHelper.respondPersonalSafety(map);
    }

    @Override
    public Observable<TranslationResponse> getAlertTranslation(String id, String lang) {
        return mApiHelper.getAlertTranslation(id, lang);
    }

    @Override
    public Observable<IntelligentGeofenceResponse> getGeofences(String lat, String lng) {
        return mApiHelper.getGeofences(lat, lng);
    }

    @Override
    public Observable<SuccessResponse> refreshGeofences(LocationModel refreshGeofencesRequest) {
        return mApiHelper.refreshGeofences(refreshGeofencesRequest);
    }

    @Override
    public Observable<VoipTokenResponse> getVoipToken() {
        return mApiHelper.getVoipToken();
    }

    @Override
    public Observable<VoipCallResponse> makeVoipCall(VoipCallRequest voipCallRequest) {
        return mApiHelper.makeVoipCall(voipCallRequest);
    }

    @Override
    public Observable<CallDetailResponse> getCallDetail(String conferenceId) {
        return mApiHelper.getCallDetail(conferenceId);
    }

    @Override
    public Observable<SuccessResponse> deletePersonalCommunity(String buildingId) {
        return mApiHelper.deletePersonalCommunity(buildingId);
    }

    @Override
    public Observable<SuccessResponse> deleteAlert(AlertDeleteRequest alertDeleteRequest) {
        return mApiHelper.deleteAlert(alertDeleteRequest);
    }

    @Override
    public Observable<SuccessResponse> sendSupportMail(SupportRequest supportRequest) {
        return mApiHelper.sendSupportMail(supportRequest);
    }

    @Override
    public Observable<SuccessResponse> updateSubscriptionToCommunity(SubscribeToCommunityRequest subscribeToCommunityRequest) {
        return mApiHelper.updateSubscriptionToCommunity(subscribeToCommunityRequest);
    }

    @Override
    public Observable<SuccessResponse> autoSubscribe(AutoSubscribeRequest autoSubscribeRequest) {
        return mApiHelper.autoSubscribe(autoSubscribeRequest);
    }

    @Override
    public Observable<SuccessResponse> appOpened() {
        return mApiHelper.appOpened();
    }

    @Override
    public Observable<BeaconsResponse> getAllBeacons() {
        return mApiHelper.getAllBeacons();
    }

    @Override
    public Observable<SuccessResponse> ignoreBuilding(String buildingId) {
        return mApiHelper.ignoreBuilding(buildingId);
    }

    @Override
    public Observable<AdResponse> getAd() {
        return mApiHelper.getAd();
    }

    @Override
    public Observable<SuccessResponse> clickAd(int adId) {
        return mApiHelper.clickAd(adId);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserLanguage() {
        return mPreferencesHelper.getCurrentUserLanguage();
    }

    @Override
    public void setCurrentUserLanguage(String locale) {
        mPreferencesHelper.setCurrentUserLanguage(locale);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public void clearAfterLogout() {
        mPreferencesHelper.clearAfterLogout();
    }

    @Override
    public boolean isFirstTime() {
        return mPreferencesHelper.isFirstTime();
    }

    @Override
    public void setIsFirstTime(boolean isFirstTime) {
        mPreferencesHelper.setIsFirstTime(isFirstTime);
    }

    @Override
    public void setPushToken(String pushToken) {
        mPreferencesHelper.setPushToken(pushToken);
    }

    @Override
    public String getPushToken() {
        return mPreferencesHelper.getPushToken();
    }

    @Override
    public void setLifeSafetyOverrideExpire(String dateExpire) {
        mPreferencesHelper.setLifeSafetyOverrideExpire(dateExpire);
    }

    @Override
    public String getLifeSafetyOverrideExpire() {
        return mPreferencesHelper.getLifeSafetyOverrideExpire();
    }

    @Override
    public void setZipcode(String zipcode) {
        mPreferencesHelper.setZipcode(zipcode);
    }

    @Override
    public String getZipcode() {
        return mPreferencesHelper.getZipcode();
    }

    @Override
    public void setSubscribedBuildings(ArrayList<Building> managedBuildings) {
        mPreferencesHelper.setSubscribedBuildings(managedBuildings);
    }

    @Override
    public ArrayList<Building> getSubscribedBuildings() {
        return mPreferencesHelper.getSubscribedBuildings();
    }

    @Override
    public void setAutoOptOuts(ArrayList<SubscriberOptOut> optOuts) {
        mPreferencesHelper.setAutoOptOuts(optOuts);
    }

    @Override
    public ArrayList<SubscriberOptOut> getAutoOptOuts() {
        return mPreferencesHelper.getAutoOptOuts();
    }

    @Override
    public void setSubscriberId(String subscriberId) {
        mPreferencesHelper.setSubscriberId(subscriberId);
    }

    @Override
    public void setLastFetchedGeofences(long timeInMillis) {
        mPreferencesHelper.setLastFetchedGeofences(timeInMillis);
    }

    @Override
    public long getLastFetchedGeofences() {
        return mPreferencesHelper.getLastFetchedGeofences();
    }

    @Override
    public String getSubscriberId() {
        return mPreferencesHelper.getSubscriberId();
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null, LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
        clearAfterLogout();
    }

    @Override
    public void updateUserInfo(String accessToken, LoggedInMode loggedInMode) {
        setAccessToken(accessToken);
        setCurrentUserLoggedInMode(loggedInMode);
    }


}
