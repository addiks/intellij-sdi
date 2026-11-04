package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;
import com.intellij.notification.impl.NotificationsManagerImpl;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Ref;
import com.intellij.ui.BalloonImpl;
import com.intellij.ui.BalloonLayoutData;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBBox;
import de.addiks.intellijsdi.intellijsdi.settings.IntellijSDISettings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NotificationsListener implements Notifications {

    @Override
    public void notify(@NotNull Notification notification) {
        System.out.println("GOT NOTIFICATION: " + notification);

        if (!IntellijSDISettings.shouldShowNotificationsInSeparateWindow()) {
            return;
        }

        var pane = new JLayeredPane();

        Balloon balloon = NotificationsManagerImpl.createBalloon(
                pane,
                notification,
                true,
                true,
                new Ref<>(),
                () -> {}
        );

        Dimension size = balloon.getPreferredSize();

        var window = new JFrame();
        window.setUndecorated(true);
        window.setBackground(new JBColor(
                new Color(0, 0, 0, 0),
                new Color(0,0,0,0)
        ));
        window.setMinimumSize(size);
        window.setPreferredSize(size);
        window.setVisible(true);
        window.setAlwaysOnTop(true);

        window.add(pane);

        balloon.show(pane);

        balloon.addListener(new JBPopupListener() {
            @Override
            public void onClosed(@NotNull LightweightWindowEvent event) {
                window.setVisible(false);
                window.dispose();
            }
        });

        Disposer.register(balloon, window::dispose);

        // if (balloon instanceof BalloonImpl balloonImpl) {
        //     balloonImpl.pai
        //     box.add(balloonImpl);
        // }
    }
}
