package de.addiks.intellijsdi.intellijsdi.services;

import com.intellij.notification.Notification;
import com.intellij.notification.impl.NotificationsManagerImpl;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Ref;
import com.intellij.ui.BalloonImpl;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBBox;
import de.addiks.intellijsdi.intellijsdi.settings.IntellijSDISettings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Service
public final class NotificationsService {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 100;

    private final JFrame window = new JFrame();
    private final JBBox vbox = JBBox.createVerticalBox();
    private final Map<Balloon, JLayeredPane> balloons = new HashMap<>();

    public NotificationsService() {
        window.setName("IntelliJ Notifications");
        window.setTitle("IntelliJ Notifications");
        window.setUndecorated(true);
        window.setBackground(new JBColor(
                new Color(255, 255, 255, 0),
                new Color(0,0,0,0)
        ));
        window.setAlwaysOnTop(true);
        window.add(vbox);
        window.setResizable(false);
        window.setLocation(0, 0);
        Disposer.register(IntelliJSdiPluginDisposable.getInstance(), window::dispose);
    }

    public void onNotification(@NotNull Notification notification) {
        if (!IntellijSDISettings.shouldShowNotificationsInSeparateWindow()) {
            return;
        }

        JLayeredPane pane = new JLayeredPane();
        Balloon balloon = createBalloon(pane, notification);

        add(balloon, pane);
        registerOnClose(balloon);
    }

    private Dimension windowSize() {
        int width = WIDTH;
        int height = 0;

        for (Balloon balloon : balloons.keySet()) {
            Dimension balloonSize = balloon.getPreferredSize();
            width = Math.max(width, balloonSize.width);
            height += balloonSize.height;
        }

        return new Dimension(width, height);
    }

    private void registerOnClose(Balloon balloon) {
        balloon.addListener(new JBPopupListener() {
            @Override
            public void onClosed(@NotNull LightweightWindowEvent event) {
                remove(balloon);
            }
        });
    }

    private void add(Balloon balloon, JLayeredPane pane) {
        Dimension windowSizeBeforeAdd = windowSize();
        balloons.put(balloon, pane);
        Dimension balloonSize = balloon.getPreferredSize();
        pane.setSize(balloonSize);
        updateWindow();
        vbox.add(pane);
        pane.setLocation(0, windowSizeBeforeAdd.height);
        if (balloon instanceof BalloonImpl balloonImpl) {
            balloonImpl.show(pane, new Rectangle(0, windowSizeBeforeAdd.height, WIDTH, HEIGHT));
        } else {
            balloon.show(pane);
        }
    }

    private void remove(Balloon balloon) {
        JLayeredPane pane = balloons.get(balloon);
        vbox.remove(pane);
        balloons.remove(balloon);
        updateWindow();
        balloon.dispose();
    }

    private void updateWindow() {
        Dimension size = windowSize();
        window.setMinimumSize(size);
        window.setPreferredSize(size);
        window.setVisible(!balloons.isEmpty());
    }

    private Balloon createBalloon(JLayeredPane pane, Notification notification) {
        if (notification.getBalloon() instanceof BalloonImpl balloon && !balloon.isVisible()) {
            balloon.show(pane, new Rectangle(WIDTH, HEIGHT));
            return balloon;
        } else if (notification.getBalloon() != null) {
            notification.hideBalloon();
        }
        return NotificationsManagerImpl.createBalloon(
                pane,
                notification,
                true,
                true,
                new Ref<>(),
                IntelliJSdiPluginDisposable.getInstance()
        );
    }
}
