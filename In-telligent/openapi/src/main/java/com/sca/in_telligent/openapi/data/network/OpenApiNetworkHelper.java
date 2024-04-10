package com.sca.in_telligent.openapi.data.network;


import android.annotation.SuppressLint;

import com.sca.in_telligent.openapi.OpenAPI;
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
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
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
import com.sca.in_telligent.openapi.util.mock.BuildingMock;
import com.sca.in_telligent.openapi.util.mock.LoginResponseMock;
import com.sca.in_telligent.openapi.util.mock.SubscriberMock;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public class OpenApiNetworkHelper implements ApiHelper {
    private final ApiHelper protectedApiService;
    private final ApiHelper publicApiService;
    private final ApiHelper uploadService;

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
    public Observable<LoginResponse> loginWithPassword(@Body LoginRequest loginRequest) {
        if (OpenAPI.Configuration.isMocked()) {
            LoginResponse mockResponse = LoginResponseMock.createMockLoginResponse();
            return Observable.create(emitter -> {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(mockResponse.getToken());
                emitter.onNext(loginResponse);
                emitter.onComplete();
            });

        } else {
            return this.publicApiService.loginWithPassword(loginRequest);
        }
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> signUp(SignUpRequest signUpRequest) {
        return this.publicApiService.signUp(signUpRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<CheckEmailResponse> checkEmail(String str) {
        return this.publicApiService.checkEmail(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> loginFacebook(FacebookLoginRequest facebookLoginRequest) {
        return this.publicApiService.loginFacebook(facebookLoginRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> loginGoogle(GoogleLoginRequest googleLoginRequest) {
        return this.publicApiService.loginGoogle(googleLoginRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return this.publicApiService.resetPassword(resetPasswordRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LoginResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return this.publicApiService.forgotPassword(forgotPasswordRequest);
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<SubscriberResponse> getCurrentSubscriber() {

        if(OpenAPI.Configuration.isMocked()){


            Subscriber mockSubscriber = SubscriberMock.createMockSubscriber();

            return Observable.create(emitter -> {
                SubscriberResponse subscriberResponse = new SubscriberResponse();
                subscriberResponse.setSuccess(true);
                subscriberResponse.setSubscriber(mockSubscriber);
                emitter.onNext(subscriberResponse);
                emitter.onComplete();
            });
        }

        return this.protectedApiService.getCurrentSubscriber();
    }

    @Override
    public Observable<NotificationResponse> getCurrentSubscriberInbox(String str) {
        return this.protectedApiService.getCurrentSubscriberInbox(str);
    }

    @Override
    public Observable<NotificationResponse> getSavedMessages() {
        return this.protectedApiService.getSavedMessages();
    }

    @Override
    public Observable<SuccessResponse> editSavedMessage(EditSaveMessageRequest editSaveMessageRequest) {
        return this.protectedApiService.editSavedMessage(editSaveMessageRequest);
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<SearchCommunityResponse> searchCommunities(String str) {
        return this.protectedApiService.searchCommunities(str);
    }

    @Override
    public Observable<CommunityResponse> getCommunity(int i) {
        return this.protectedApiService.getCommunity(i);
    }

    @Override
    public Observable<NotificationsResponse> getAllNotifications(String str) {
        return this.protectedApiService.getAllNotifications(str);
    }

    @Override
    public Observable<SingleNotificationResponse> getNotification(String str) {
        return this.protectedApiService.getNotification(str);
    }

    @Override
    public Observable<NotificationLanguageResponse> getLanguages() {
        return this.protectedApiService.getLanguages();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> syncAlertSubscription(AlertSubscriptionRequest alertSubscriptionRequest) {
        return this.protectedApiService.syncAlertSubscription(alertSubscriptionRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> createOrEditGroup(String str, MultipartBody.Part part, RequestBody requestBody, RequestBody requestBody2) {
        return this.uploadService.createOrEditGroup(str, part, requestBody, requestBody2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> createOrEditGroupNoThumbnail(String str, CreateEditGroupRequest createEditGroupRequest) {
        return this.uploadService.createOrEditGroupNoThumbnail(str, createEditGroupRequest);
    }

    @Override
    public Observable<SearchCommunityResponse> getSuggestedGroups() {
        if (OpenAPI.Configuration.isMocked()) {
            return Observable.create(emitter -> {
                Building mockBuilding = BuildingMock.createMockBuilding();
                SearchCommunityResponse searchCommunityResponse = new SearchCommunityResponse();
                searchCommunityResponse.setSuccess(true);
                searchCommunityResponse.setBuildings(new ArrayList<>(Collections.singletonList(mockBuilding)));
                emitter.onNext(searchCommunityResponse);
                emitter.onComplete();
            });
        }
        return this.protectedApiService.getSuggestedGroups();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> updateSubscription(UpdateSubscriptionRequest updateSubscriptionRequest) {
        return this.protectedApiService.updateSubscription(updateSubscriptionRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<InviteeResponse> getInvitees(String str) {
        return this.protectedApiService.getInvitees(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<ArrayList<BuildingMember>> getBuildingMembers(String str) {
        return this.protectedApiService.getBuildingMembers(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<DeliveryInfoResponse> getDeliveryInformation(String str, String str2) {
        return this.protectedApiService.getDeliveryInformation(str, str2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> inviteOthers(String str, InviteOthersRequest inviteOthersRequest) {
        return this.protectedApiService.inviteOthers(str, inviteOthersRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> editMember(int i, EditCommunityInviteeRequest editCommunityInviteeRequest) {
        return this.protectedApiService.editMember(i, editCommunityInviteeRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> removeMember(int i) {
        return this.protectedApiService.removeMember(i);
    }

    @Override
    public Observable<SubscriberResponse> updateUser(UpdateSubscriberRequest updateSubscriberRequest) {
        if (OpenAPI.Configuration.isMocked()) {
            Subscriber mockSubscriber = SubscriberMock.createMockSubscriber();
            return Observable.create(emitter -> {
                SubscriberResponse subscriberResponse = new SubscriberResponse();
                subscriberResponse.setSuccess(true);
                subscriberResponse.setSubscriber(mockSubscriber);
                emitter.onNext(subscriberResponse);
                emitter.onComplete();
            });
        }
        return this.protectedApiService.updateUser(updateSubscriberRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> createNotification(List<MultipartBody.Part> list, RequestBody requestBody, RequestBody requestBody2, RequestBody requestBody3, RequestBody requestBody4, RequestBody requestBody5, RequestBody requestBody6) {
        return this.uploadService.createNotification(list, requestBody, requestBody2, requestBody3, requestBody4, requestBody5, requestBody6);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> suggestNotification(SuggestNotificationRequest suggestNotificationRequest) {
        return this.uploadService.suggestNotification(suggestNotificationRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> createNotificationNoThumbnail(CreateNotificationRequest createNotificationRequest) {
        return this.uploadService.createNotificationNoThumbnail(createNotificationRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> suggestNotificationNoThumbnail(SuggestNotificationRequest suggestNotificationRequest) {
        return this.uploadService.suggestNotificationNoThumbnail(suggestNotificationRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<PushTokenSuccessResponse> registerPushToken(PushTokenRequest pushTokenRequest) {
        return this.protectedApiService.registerPushToken(pushTokenRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LocationModel> getLastLocation(String str) {
        return this.protectedApiService.getLastLocation(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<LocationModel> getUpdatedLocation(String str) {
        return this.protectedApiService.getUpdatedLocation(str);
    }

    @Override
    public Observable<VoipTokenResponse> getVoipToken(VoipCallRequest voiceCallRequest) {
        return this.protectedApiService.getVoipToken(voiceCallRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<String> sendWeatherAlert(String str, LocationModel locationModel) {
        return this.protectedApiService.sendWeatherAlert(str, locationModel);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<JSONObject> receivedMessage(String str) {
        return this.protectedApiService.receivedMessage(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<JSONObject> respondToMessage(String str, HashMap<String, Object> hashMap) {
        return this.protectedApiService.respondToMessage(str, hashMap);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> alertOpened(AlertOpenedRequest alertOpenedRequest) {
        return this.protectedApiService.alertOpened(alertOpenedRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> alertDelivered(AlertOpenedRequest alertOpenedRequest) {
        return this.protectedApiService.alertDelivered(alertOpenedRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<JSONObject> respondPersonalSafety(HashMap<String, Object> hashMap) {
        return this.uploadService.respondPersonalSafety(hashMap);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<TranslationResponse> getAlertTranslation(String str, String str2) {
        return this.protectedApiService.getAlertTranslation(str, str2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<IntelligentGeofenceResponse> getGeofences(String str, String str2) {
        return this.protectedApiService.getGeofences(str, str2);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> refreshGeofences(LocationModel locationModel) {
        return this.protectedApiService.refreshGeofences(locationModel);
    }


    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<VoipCallResponse> makeVoipCall(VoipCallRequest voipCallRequest) {
        return this.protectedApiService.makeVoipCall(voipCallRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<CallDetailResponse> getCallDetail(String str) {
        return this.protectedApiService.getCallDetail(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> deletePersonalCommunity(String str) {
        return this.uploadService.deletePersonalCommunity(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> deleteAlert(AlertDeleteRequest alertDeleteRequest) {
        return this.protectedApiService.deleteAlert(alertDeleteRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> sendSupportMail(SupportRequest supportRequest) {
        return this.protectedApiService.sendSupportMail(supportRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> updateSubscriptionToCommunity(SubscribeToCommunityRequest subscribeToCommunityRequest) {
        return this.protectedApiService.updateSubscriptionToCommunity(subscribeToCommunityRequest);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> autoSubscribe(AutoSubscribeRequest autoSubscribeRequest) {
        return this.protectedApiService.autoSubscribe(autoSubscribeRequest);
    }

    @Override
    public Observable<SuccessResponse> appOpened() {
        if (OpenAPI.Configuration.isMocked()) {
            return Observable.create(emitter -> {
                SuccessResponse successResponse = new SuccessResponse();
                successResponse.setSuccess(true);
                emitter.onNext(successResponse);
                emitter.onComplete();
            });
        }
        return this.protectedApiService.appOpened();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> ignoreBuilding(String str) {
        return this.protectedApiService.ignoreBuilding(str);
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<AdResponse> getAd() {
        return this.protectedApiService.getAd();
    }

    @Override // com.sca.in_telligent.openapi.data.network.ApiHelper
    public Observable<SuccessResponse> clickAd(int i) {
        return this.protectedApiService.clickAd(i);
    }

    public List<Class<?>> getInnerClasses(Class<?> cls) {
        System.out.println();
        ArrayList arrayList = new ArrayList();
        arrayList.add(cls);
        arrayList.addAll(Arrays.asList(cls.getDeclaredClasses()));
        return arrayList;
    }
}
