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
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.json.JSONObject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Singleton
public abstract class AppDataManager implements DataManager {
    private static final String TAG = "AppDataManager";
    private final ApiHelper mApiHelper;
    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        this.mContext = context;
        this.mPreferencesHelper = preferencesHelper;
        this.mApiHelper = apiHelper;
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public ApiHeader getApiHeader() {
        return this.mApiHelper.getApiHeader();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> loginWithPassword(LoginRequest loginRequest) {
        return this.mApiHelper.loginWithPassword(loginRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> signUp(SignUpRequest signUpRequest) {
        return this.mApiHelper.signUp(signUpRequest);
    }


//    public Observable<SuccessResponse> suggestNotification(List<MultipartBody.Part> list, RequestBody requestBody, RequestBody requestBody2, RequestBody requestBody3) {
//        return null;
//    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<CheckEmailResponse> checkEmail(String str) {
        return this.mApiHelper.checkEmail(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> loginFacebook(FacebookLoginRequest facebookLoginRequest) {
        return this.mApiHelper.loginFacebook(facebookLoginRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> loginGoogle(GoogleLoginRequest googleLoginRequest) {
        return this.mApiHelper.loginGoogle(googleLoginRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return this.mApiHelper.resetPassword(resetPasswordRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return this.mApiHelper.forgotPassword(forgotPasswordRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SubscriberResponse> getCurrentSubscriber() {
        return this.mApiHelper.getCurrentSubscriber();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<NotificationResponse> getCurrentSubscriberInbox(String str) {
        return this.mApiHelper.getCurrentSubscriberInbox(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<NotificationResponse> getSavedMessages() {
        return this.mApiHelper.getSavedMessages();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> editSavedMessage(EditSaveMessageRequest editSaveMessageRequest) {
        return this.mApiHelper.editSavedMessage(editSaveMessageRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SearchCommunityResponse> searchCommunities(String str) {
        return this.mApiHelper.searchCommunities(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<NotificationsResponse> getAllNotifications(String str) {
        return this.mApiHelper.getAllNotifications(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SingleNotificationResponse> getNotification(String str) {
        return this.mApiHelper.getNotification(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<NotificationLanguageResponse> getLanguages() {
        return this.mApiHelper.getLanguages();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> syncAlertSubscription(AlertSubscriptionRequest alertSubscriptionRequest) {
        return this.mApiHelper.syncAlertSubscription(alertSubscriptionRequest);
    }

//    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
//    public Observable<SuccessResponse> createOrEditGroup(String str, MultipartBody.Part part, RequestBody requestBody, RequestBody requestBody2) {
//        return this.mApiHelper.createOrEditGroup(str, part, requestBody, requestBody2);
//    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> createOrEditGroupNoThumbnail(String str, CreateEditGroupRequest createEditGroupRequest) {
        return this.mApiHelper.createOrEditGroupNoThumbnail(str, createEditGroupRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<CommunityResponse> getCommunity(int i) {
        return this.mApiHelper.getCommunity(i);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SearchCommunityResponse> getSuggestedGroups() {
        return this.mApiHelper.getSuggestedGroups();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> updateSubscription(UpdateSubscriptionRequest updateSubscriptionRequest) {
        return this.mApiHelper.updateSubscription(updateSubscriptionRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<InviteeResponse> getInvitees(String str) {
        return this.mApiHelper.getInvitees(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<ArrayList<BuildingMember>> getBuildingMembers(String str) {
        return this.mApiHelper.getBuildingMembers(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<DeliveryInfoResponse> getDeliveryInformation(String str, String str2) {
        return this.mApiHelper.getDeliveryInformation(str, str2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> inviteOthers(String str, InviteOthersRequest inviteOthersRequest) {
        return this.mApiHelper.inviteOthers(str, inviteOthersRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> editMember(int i, EditCommunityInviteeRequest editCommunityInviteeRequest) {
        return this.mApiHelper.editMember(i, editCommunityInviteeRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> removeMember(int i) {
        return this.mApiHelper.removeMember(i);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SubscriberResponse> updateUser(UpdateSubscriberRequest updateSubscriberRequest) {
        return this.mApiHelper.updateUser(updateSubscriberRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> createNotificationNoThumbnail(CreateNotificationRequest createNotificationRequest) {
        return this.mApiHelper.createNotificationNoThumbnail(createNotificationRequest);
    }


    public Observable<SuccessResponse> createOrEditGroup(String str, MultipartBody.Part part, RequestBody requestBody, RequestBody requestBody2) {
        return null;
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> suggestNotificationNoThumbnail(SuggestNotificationRequest suggestNotificationRequest) {
        return this.mApiHelper.suggestNotificationNoThumbnail(suggestNotificationRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<PushTokenSuccessResponse> registerPushToken(PushTokenRequest pushTokenRequest) {
        return this.mApiHelper.registerPushToken(pushTokenRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LocationModel> getLastLocation(String str) {
        return this.mApiHelper.getLastLocation(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LocationModel> getUpdatedLocation(String str) {
        return this.mApiHelper.getUpdatedLocation(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<String> sendWeatherAlert(String str, LocationModel locationModel) {
        return this.mApiHelper.sendWeatherAlert(str, locationModel);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<JSONObject> receivedMessage(String str) {
        return this.mApiHelper.receivedMessage(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<JSONObject> respondToMessage(String str, HashMap<String, Object> hashMap) {
        return this.mApiHelper.respondToMessage(str, hashMap);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> alertOpened(AlertOpenedRequest alertOpenedRequest) {
        return this.mApiHelper.alertOpened(alertOpenedRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> alertDelivered(AlertOpenedRequest alertOpenedRequest) {
        return this.mApiHelper.alertDelivered(alertOpenedRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<JSONObject> respondPersonalSafety(HashMap<String, Object> hashMap) {
        return this.mApiHelper.respondPersonalSafety(hashMap);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<TranslationResponse> getAlertTranslation(String str, String str2) {
        return this.mApiHelper.getAlertTranslation(str, str2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<IntelligentGeofenceResponse> getGeofences(String str, String str2) {
        return this.mApiHelper.getGeofences(str, str2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> refreshGeofences(LocationModel locationModel) {
        return this.mApiHelper.refreshGeofences(locationModel);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<VoipTokenResponse> getVoipToken() {
        return this.mApiHelper.getVoipToken();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<VoipCallResponse> makeVoipCall(VoipCallRequest voipCallRequest) {
        return this.mApiHelper.makeVoipCall(voipCallRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<CallDetailResponse> getCallDetail(String str) {
        return this.mApiHelper.getCallDetail(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> deletePersonalCommunity(String str) {
        return this.mApiHelper.deletePersonalCommunity(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> deleteAlert(AlertDeleteRequest alertDeleteRequest) {
        return this.mApiHelper.deleteAlert(alertDeleteRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> sendSupportMail(SupportRequest supportRequest) {
        return this.mApiHelper.sendSupportMail(supportRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> updateSubscriptionToCommunity(SubscribeToCommunityRequest subscribeToCommunityRequest) {
        return this.mApiHelper.updateSubscriptionToCommunity(subscribeToCommunityRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> autoSubscribe(AutoSubscribeRequest autoSubscribeRequest) {
        return this.mApiHelper.autoSubscribe(autoSubscribeRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> appOpened() {
        return this.mApiHelper.appOpened();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> ignoreBuilding(String str) {
        return this.mApiHelper.ignoreBuilding(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<AdResponse> getAd() {
        return this.mApiHelper.getAd();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> clickAd(int i) {
        return this.mApiHelper.clickAd(i);
    }

//    public Observable<SuccessResponse> createNotification(List<MultipartBody.Part> list, RequestBody requestBody, RequestBody requestBody2, RequestBody requestBody3, RequestBody requestBody4, RequestBody requestBody5, RequestBody requestBody6) {
//        return null;
//    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public String getCurrentUserId() {
        return this.mPreferencesHelper.getCurrentUserId();
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public void setCurrentUserId(String str) {
        this.mPreferencesHelper.setCurrentUserId(str);
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public int getCurrentUserLoggedInMode() {
        return this.mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode loggedInMode) {
        this.mPreferencesHelper.setCurrentUserLoggedInMode(loggedInMode);
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public String getCurrentUserLanguage() {
        return this.mPreferencesHelper.getCurrentUserLanguage();
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public void setCurrentUserLanguage(String str) {
        this.mPreferencesHelper.setCurrentUserLanguage(str);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getAccessToken() {
        return getAccessToken();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setAccessToken(String str) {
        this.mPreferencesHelper.setAccessToken(str);
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public void clearAfterLogout() {
        this.mPreferencesHelper.clearAfterLogout();
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public boolean isFirstTime() {
        return this.mPreferencesHelper.isFirstTime();
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public void setIsFirstTime(boolean z) {
        this.mPreferencesHelper.setIsFirstTime(z);
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public void setPushToken(String str) {
        this.mPreferencesHelper.setPushToken(str);
    }

    @Override // com.sca.in_telligent.data.prefs.PreferencesHelper
    public String getPushToken() {
        return this.mPreferencesHelper.getPushToken();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setLifeSafetyOverrideExpire(String str) {
        this.mPreferencesHelper.setLifeSafetyOverrideExpire(str);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getLifeSafetyOverrideExpire() {
        return this.mPreferencesHelper.getLifeSafetyOverrideExpire();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setZipcode(String str) {
        this.mPreferencesHelper.setZipcode(str);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getZipcode() {
        return this.mPreferencesHelper.getZipcode();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setSubscribedBuildings(ArrayList<Building> arrayList) {
        this.mPreferencesHelper.setSubscribedBuildings(arrayList);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public ArrayList<Building> getSubscribedBuildings() {
        return this.mPreferencesHelper.getSubscribedBuildings();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setAutoOptOuts(ArrayList<SubscriberOptOut> arrayList) {
        this.mPreferencesHelper.setAutoOptOuts(arrayList);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public ArrayList<SubscriberOptOut> getAutoOptOuts() {
        return this.mPreferencesHelper.getAutoOptOuts();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setSubscriberId(String str) {
        this.mPreferencesHelper.setSubscriberId(str);
    }

    @Override // com.sca.in_telligent.data.DataManager, com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setLastFetchedGeofences(long j) {
        this.mPreferencesHelper.setLastFetchedGeofences(j);
    }

    @Override // com.sca.in_telligent.data.DataManager, com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public long getLastFetchedGeofences() {
        return this.mPreferencesHelper.getLastFetchedGeofences();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getSubscriberId() {
        return this.mPreferencesHelper.getSubscriberId();
    }

    @Override // com.sca.in_telligent.data.DataManager
    public void setUserAsLoggedOut() {
        updateUserInfo(null, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
        clearAfterLogout();
    }

    @Override // com.sca.in_telligent.data.DataManager
    public void updateUserInfo(String str, DataManager.LoggedInMode loggedInMode) {
        setAccessToken(str);
        setCurrentUserLoggedInMode(loggedInMode);
    }
}
