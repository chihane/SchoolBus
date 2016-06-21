package chihane.schoolbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;

public class MainThreadPoster {
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void post(final Subscription subscription, final Object event) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    subscription.method.invoke(subscription.subscriber, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
