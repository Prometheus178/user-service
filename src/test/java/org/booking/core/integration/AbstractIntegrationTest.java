package org.booking.core.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractIntegrationTest {
    public static final String BASE_URI = "http://localhost:8888";
    public static double delta = 0.1; // Define the delta value

    protected static String getRequestBody(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }


}
