package chihane.schoolbus;

import android.os.Handler;
import android.os.HandlerThread;

import java.lang.reflect.InvocationTargetException;

public class BackgroundThreadPoster {
    private static final HandlerThread thread;
    private static final Handler handler;

    static {
        thread = new HandlerThread("background");
        thread.start();
        handler = new Handler(thread.getLooper());
    }

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
