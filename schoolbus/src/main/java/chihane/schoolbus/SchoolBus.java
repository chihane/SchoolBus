package chihane.schoolbus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolBus {
    private static final Map<Class, List<Subscription>> typeToSubscriptions = new HashMap<>();
    private static final Map<Object, List<Class>> subscriberToType = new HashMap<>();

    public static final SchoolBus defaultInstance = new SchoolBus();

    public void register(Object subscriber) {
        List<Class> types = subscriberToType.get(subscriber);
        if (types == null) {
            types = new ArrayList<>();
            subscriberToType.put(subscriber, types);
        }

        List<Subscription> subscriptions = SubscriptionFinder.find(subscriber);

        for (Subscription subscription : subscriptions) {
            types.add(subscription.eventType);

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

    public boolean isRegistered(Object subscriber) {
        return subscriberToType.containsKey(subscriber);
    }

    public void unregister(Object subscriber) {
        List<Class> types = subscriberToType.get(subscriber);

        if (types == null) {
            throw new SchoolBusException("Subscriber unregistering was not registered before");
        }

        for (Class type : types) {
            List<Subscription> subscriptions = typeToSubscriptions.get(type);
            for (Subscription subscription : subscriptions) {
                if (subscription.subscriber == subscriber) {
                    subscriptions.remove(subscription);
                }
            }
        }

        subscriberToType.remove(subscriber);
    }
}
