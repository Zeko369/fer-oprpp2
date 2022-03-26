package hr.fer.zemris.java.webserver.Util;

import hr.fer.zemris.java.webserver.IWebWorker;

import java.util.HashMap;
import java.util.Map;

public class WorkerLoader {
    public Map<String, IWebWorker> map = new HashMap<>();

    public IWebWorker get(String className) {
        if (map.containsKey(className)) {
            return map.get(className);
        }

        try {
            Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(className);

            @SuppressWarnings("deprecation")
            Object newObject = referenceToClass.newInstance();
            if (newObject instanceof IWebWorker) {
                map.put(className, (IWebWorker) newObject);
                return map.get(className);
            }

            throw new RuntimeException("Class " + className + " is not instance of IWebWorker");
        } catch (ClassNotFoundException e) {
            return null;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
