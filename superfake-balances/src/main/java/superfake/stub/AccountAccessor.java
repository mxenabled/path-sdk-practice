package superfake.stub;

import com.mx.accessors.account.AccountBaseAccessor;
import com.mx.common.accessors.AccessorConfiguration;
import com.mx.common.accessors.AccessorResponse;
import com.mx.common.models.MdxList;
import com.mx.models.account.Account;

import java.math.BigDecimal;

public class AccountAccessor extends AccountBaseAccessor {
  public AccountAccessor(AccessorConfiguration configuration) {
    super(configuration);
  }

  @Override
  public final AccessorResponse<MdxList<Account>> list() {
    MdxList<Account> accounts = new MdxList<>();
    Account checking = new Account();
    checking.setAccountNumber("12345-0");
    checking.setBalance(BigDecimal.valueOf(98.99));
    checking.setName("Checking");
    checking.setType("CHECKING");

    Account savings = new Account();
    savings.setAccountNumber("12345-1");
    savings.setBalance(BigDecimal.valueOf(4508.96));
    savings.setName("Savings");
    savings.setType("SAVINGS");

    accounts.add(checking);
    accounts.add(savings);

    return AccessorResponse.<MdxList<Account>>builder().result(accounts).build();
  }
}
