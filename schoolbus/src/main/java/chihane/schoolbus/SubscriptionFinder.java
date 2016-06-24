package chihane.schoolbus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionFinder {
    public static List<Subscription> find(Object subscriber) {
        List<Subscription> result = new ArrayList<>();

        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Subscribe.class) {
                    Class[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new SchoolBusException("One and only one event type must declared as @Subscriber methods' parameter");
                    }
                    Class eventType = parameterTypes[0];

                    Subscribe subscribe = (Subscribe) annotation;
                    result.add(new Subscription(subscriber, method, eventType, subscribe.threadMode(), subscribe.sticky()));
                }
            }
        }

        if (result.size() == 0) {
            throw new SchoolBusException("No @Subscriber method found in registered subscriber");
        }

        return result;
    }
}
