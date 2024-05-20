package com.sca.in_telligent.util.twilio;

import com.sca.in_telligent.util.twilio.AppTwilioUtil.TwilioUtilListener;

public interface TwilioUtil {

  void endCall();

  void answerCall();

  void makeCall(String buildingId);

  void rejectCall();

  void fillFields(String subscriberId,String uuid, String remoteUsername, String conferenceId);

  void failCall();

  void callWasAnswered();

  void setListener(TwilioUtilListener listener);

}
