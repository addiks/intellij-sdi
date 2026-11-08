package de.addiks.intellijsdi.intellijsdi.actions;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CreateTestNotificationAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        var group = NotificationGroupManager.getInstance().getNotificationGroup("Error Report");

        for (int i = 1; i<4; i++) {
            final int j = i;
            Thread.startVirtualThread(() -> {
                try {
                    Thread.sleep(2000 * j);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                var notification = group.createNotification(
                        "Test-Titel " + j,
                        "Das ist <b>HTML</b>-Content mit <a href=\"https://www.jetbrains.com\">Link</a>.",
                        NotificationType.INFORMATION
                );
                notification.notify(e.getProject());
                System.out.println("CREATED NOTIFICATION: " + notification);
            });
        }
    }
}
