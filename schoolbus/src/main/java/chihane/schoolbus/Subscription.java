package chihane.schoolbus;

import java.lang.reflect.Method;

public class Subscription {
    Object subscriber;
    Method method;
    Class eventType;
    ThreadMode threadMode;
    boolean sticky;

    public Subscription(Object subscriber, Method method, Class eventType, ThreadMode threadMode, boolean sticky) {
        this.subscriber = subscriber;
        this.method = method;
        this.eventType = eventType;
        this.threadMode = threadMode;
        this.sticky = sticky;
    }
}
