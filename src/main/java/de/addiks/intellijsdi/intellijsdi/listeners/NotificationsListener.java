package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import de.addiks.intellijsdi.intellijsdi.services.NotificationsService;
import org.jetbrains.annotations.NotNull;

public class NotificationsListener implements Notifications {

    @Override
    public void notify(@NotNull Notification notification) {
        System.out.println("GOT NOTIFICATION: " + notification);

        ApplicationManager.getApplication().getService(NotificationsService.class).onNotification(notification);
    }
}
