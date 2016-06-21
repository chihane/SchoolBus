package chihane.schoolbus;

import android.os.Looper;

import java.lang.reflect.InvocationTargetException;

public class AsyncThreadPoster {
    public static void post(final Subscription subscription, final Object event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    subscription.method.invoke(subscription.subscriber, event);
                    Looper.loop();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
