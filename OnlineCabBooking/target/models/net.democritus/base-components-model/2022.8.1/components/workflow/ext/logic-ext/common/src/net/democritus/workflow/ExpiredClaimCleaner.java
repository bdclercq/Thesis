package net.democritus.workflow;

import net.democritus.claims.ClaimCleaner;
import net.democritus.claims.ClaimCleanerFactory;
import net.democritus.metadata.ComponentMetaDataRegister;
import net.democritus.metadata.DataElementDef;
import net.democritus.metadata.IComponentMetaData;
import net.democritus.properties.RuntimeProperties;
import net.democritus.sys.Context;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

// @feature:claims
public class ExpiredClaimCleaner {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredClaimCleaner.class);

  private final Context context;
  private final ComponentMetaDataRegister componentMetaDataRegister;

  public ExpiredClaimCleaner(Context context) {
    this.context = context;
    componentMetaDataRegister = ComponentMetaDataRegister.getComponentMetaDataRegister();
  }

  public void cleanExpiredClaims() {
    LOGGER.debug("Cleaning expired claims");

    String[] components = RuntimeProperties.getRuntimeProperties().getComponents();
    for (String component : components) {
      cleanComponent(component);
    }
  }

  private void cleanComponent(String component) {
    IComponentMetaData componentMetaData = componentMetaDataRegister.getComponentMetaData(component).getValue();
    for (DataElementDef dataElementDef : componentMetaData.getDataElementDefs()) {
      if (dataElementDef.isClaimElement()) {
        cleanClaimElement(dataElementDef);
      }
    }
  }

  private void cleanClaimElement(DataElementDef dataElementDef) {
    ClaimCleaner claimCleaner = new ClaimCleanerFactory().makeClaimCleaner(dataElementDef);
    claimCleaner.cleanExpiredClaims(context.emptyParameter());
  }

}
