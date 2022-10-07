package superfake;

import com.mx.common.accessors.AccessorResponse;
import com.mx.common.collections.ObjectArray;
import com.mx.common.collections.ObjectMap;
import com.mx.common.lang.Strings;
import com.mx.common.models.MdxList;
import com.mx.common.serialization.ObjectMapYamlDeserializer;
import com.mx.models.account.Account;
import com.mx.models.id.Authentication;
import com.mx.path.gateway.api.Gateway;
import com.mx.path.gateway.api.GatewayConfigurator;
import com.mx.path.model.context.RequestContext;
import com.mx.path.model.context.Session;
import org.apache.commons.io.IOUtil;
import superfake.lib.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
  public static void main(String... args) throws IOException {
    String gatewayYaml = loadFile("gateway.yml");
    String usersYaml = loadFile("users.yml");

    Map<String, Gateway> gateways = new GatewayConfigurator().buildFromYaml(gatewayYaml);
    Gateway gateway = gateways.get("superfake");

    Logger.log(gateway.describe());

    ObjectMapYamlDeserializer yamlSerializer = new ObjectMapYamlDeserializer(ObjectMapYamlDeserializer.Parameters.builder().maxYamlAliases(100).build());
    ObjectArray users = (ObjectArray) yamlSerializer.fromYaml(usersYaml);

    Map<String, BigDecimal> ledger = new HashMap<>();

    users.forEach((user) -> {
      Authentication authentication = new Authentication();
      authentication.setLogin(((ObjectMap) user).getAsString("login"));
      authentication.setPassword(((ObjectMap) user).getAsString("password").toCharArray());

      Session.createSession();
      Session.current().setClientId("superfake");
      RequestContext.builder().clientId("superfake").build().register();

      AccessorResponse<Authentication> authenticationResult = gateway.id().authenticate(authentication);

      if (Strings.isNotBlank(authenticationResult.getResult().getUserId())) {
        AccessorResponse<MdxList<Account>> accounts = gateway.accounts().list();
        accounts.getResult().forEach((account) -> {
          ledger.computeIfAbsent(account.getType(), (k) -> BigDecimal.valueOf(0));
          BigDecimal currentBalance = ledger.get(account.getType());
          currentBalance = currentBalance.add(account.getBalance());
          ledger.put(account.getType(), currentBalance); // update the balance total
        });

        gateway.id().logout(authenticationResult.getResult().getId());
      }

      Session.clearSession();
    });

    System.out.println("\n\n\n");
    System.out.println("=========================================");
    System.out.println("Totals By Account Type");
    System.out.println("=========================================");
    ledger.forEach((type, balance) -> {
      System.out.println("  - " + type + ": " + balance.toString());
    });
  }

  /**
   * Loads file from resources
   *
   * @param filename path inside resources
   * @return contents of file
   * @throws IOException
   */
  private static String loadFile(String filename) throws IOException {
    // The class loader that loaded the class
    ClassLoader classLoader = Main.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
      return IOUtil.toString(inputStream, StandardCharsets.UTF_8.toString());
    }
  }
}
