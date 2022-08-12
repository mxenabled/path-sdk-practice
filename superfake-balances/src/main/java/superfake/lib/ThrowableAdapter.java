package superfake.lib;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Data;

import java.lang.reflect.Type;

public class ThrowableAdapter implements JsonSerializer<Throwable> {
  @Override
  public JsonElement serialize(Throwable src, Type typeOfSrc, JsonSerializationContext context) {
    return new Gson().toJsonTree(new ThrowableContainer(src));
  }

  @Data
  public static class ThrowableContainer {
    private String message;
    private String klass;

    public ThrowableContainer(Throwable src) {
      this.message = src.getMessage();
      this.klass = src.getClass().getCanonicalName();
    }
  }
}
