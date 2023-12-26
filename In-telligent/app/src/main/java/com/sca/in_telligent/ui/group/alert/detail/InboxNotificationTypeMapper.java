package com.sca.in_telligent.ui.group.alert.detail;

import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.inbox.InboxNotificationType;
import java.util.Objects;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class InboxNotificationTypeMapper {
    public static InboxNotificationType map(Notification notification) {
        Objects.requireNonNull(notification, "Notification cannot be null");
        return InboxNotificationType.NORMAL.getNotificationType(notification.getType());
    }
}
