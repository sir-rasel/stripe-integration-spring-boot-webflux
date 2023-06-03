package org.sir.stripeintegration.core.domain;

import lombok.Data;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class TransactionEntity  extends EntityAuditFields{
}
