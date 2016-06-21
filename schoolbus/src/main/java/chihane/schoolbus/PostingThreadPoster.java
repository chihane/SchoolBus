package chihane.schoolbus;

import java.lang.reflect.InvocationTargetException;

public class PostingThreadPoster {
    public static void post(Subscription subscription, Object event) {
        try {
            subscription.method.invoke(subscription.subscriber, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
