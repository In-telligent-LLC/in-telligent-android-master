package com.sca.in_telligent.ui.group.alert.detail;

import android.support.annotation.NonNull;

import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.inbox.InboxNotificationType;

import static java.util.Objects.*;

/**
 * Created by Marcos Ambrosi on 2/6/19.
 */
public class InboxNotificationTypeMapper {

    public static InboxNotificationType map(@NonNull Notification notification){
        requireNonNull(notification, "Notification cannot be null");
        InboxNotificationType inboxNotificationType = InboxNotificationType.NORMAL;
        inboxNotificationType = inboxNotificationType.getNotificationType(notification.getType());
        return inboxNotificationType;
    }
}
