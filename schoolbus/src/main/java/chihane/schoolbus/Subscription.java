package chihane.schoolbus;

import java.lang.reflect.Method;

public class Subscription {
    Object subscriber;
    Method method;
    Class eventType;

    public Subscription(Object subscriber, Method method, Class eventType) {
        this.subscriber = subscriber;
        this.method = method;
        this.eventType = eventType;
    }
}
