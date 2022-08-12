package superfake.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mx.common.serialization.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class Logger {
  private static final Gson gson;

  static {
    GsonBuilder gsonBuilder = new GsonBuilder()
        .registerTypeAdapter(Throwable.class, new ThrowableAdapter())
        .registerTypeAdapter(LocalDateTime.class, LocalDateTimeDeserializer.builder().build())
        .setPrettyPrinting();
    com.mx.models.Resources.registerResources(gsonBuilder);
    gson = gsonBuilder.create();
  }

  public static void log(Object obj) {
    if (obj instanceof String) {
      System.out.println("\n=> " + obj);
    } else {
      System.out.println("\n" + gson.toJson(obj));
    }
  }
}
