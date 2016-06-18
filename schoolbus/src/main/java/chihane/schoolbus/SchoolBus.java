package chihane.schoolbus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolBus {
    private static final Map<Class, List<Subscription>> typeToSubscriptions = new HashMap<>();

    public static final SchoolBus defaultInstance = new SchoolBus();

    public void register(Object subscriber) {
        List<Subscription> subscriptions = MethodFinder.find(subscriber);

        for (Subscription subscription : subscriptions) {
            List<Subscription> subscriptionsByType = typeToSubscriptions.get(subscription.eventType);

            if (subscriptionsByType == null) {
                subscriptionsByType = new ArrayList<>();
                typeToSubscriptions.put(subscription.eventType, subscriptionsByType);
            }

            subscriptionsByType.add(subscription);
        }
    }

    public void post(Object event) {
        Class eventType = event.getClass();
        List<Subscription> subscriptions = typeToSubscriptions.get(eventType);

        for (Subscription subscription : subscriptions) {
            postEvent(subscription, event);
        }
    }

    private void postEvent(Subscription subscription, Object event) {
        try {
            subscription.method.invoke(subscription.subscriber, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
