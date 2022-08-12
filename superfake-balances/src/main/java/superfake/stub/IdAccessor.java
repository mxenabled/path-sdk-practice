package superfake.stub;

import com.mx.accessors.AccessorConfiguration;
import com.mx.accessors.AccessorResponse;
import com.mx.accessors.AccessorResponseStatus;
import com.mx.accessors.id.IdBaseAccessor;
import com.mx.models.id.Authentication;

import java.util.UUID;

public class IdAccessor extends IdBaseAccessor {
  public IdAccessor(AccessorConfiguration configuration) {
    super(configuration);
  }

  @Override
  public final AccessorResponse<Authentication> authenticate(Authentication authentication) {
    Authentication result = new Authentication();
    result.setUserId("U-" + authentication.getLogin());
    result.setId(UUID.randomUUID().toString());

    return AccessorResponse.<Authentication>builder().status(AccessorResponseStatus.OK).result(result).build();
  }

  @Override
  public AccessorResponse<Void> logout(String sessionId) {
    return AccessorResponse.<Void>builder().status(AccessorResponseStatus.NO_CONTENT).build();
  }
}
