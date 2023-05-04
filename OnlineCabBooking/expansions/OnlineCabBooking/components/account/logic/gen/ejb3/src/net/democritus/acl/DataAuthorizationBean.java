package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.DataAccessLocalAgent;
import net.democritus.acl.DataAccessRights;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProcessingContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ProjectionRef;
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
import net.democritus.sys.CrudsResult;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task DataAuthorization.
 */

@Stateless
@Remote(DataAuthorizationRemote.class)
@Local(DataAuthorizationLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class DataAuthorizationBean implements DataAuthorizationRemote, DataAuthorizationLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DataAuthorizationBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
	@EJB
	private DataAccessLocal dataAccessLocal;
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<DataAccessRights> performOnTarget(ParameterContext<DataRef> targetParameter) {
    DataAccessLocalAgent dataAccessAgent = DataAccessLocalAgent.getDataAccessAgent(targetParameter.getContext());
    // anchor:get-instance:start
    CrudsResult<DataAccessQuery> result = dataAccessAgent.getProjection(new ProjectionRef("query", targetParameter.getValue()));
    // anchor:get-instance:end

    if (result.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to retrieve target instance for 'DataAccess'"
        );
      }
      return TaskResult.error();
    }
    return perform(targetParameter.construct(result.getValue()));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<DataAccessRights> perform(ParameterContext<DataAccessQuery> targetParameter) {

    TaskResult<DataAccessRights> taskResult = null;

    // @anchor:prePerform:start
    final DataAuthorization implementation;
    try {
      String implName = targetParameter.getProcessingContext().getImplementation();
      implementation = (DataAuthorization) Class.forName(implName).newInstance();
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to initialize the implementation class", e
        );
      }
      return TaskResult.error(Diagnostic.error("account", "DataAuthorization", "NO_VALID_IMPLEMENTATION_CLASS"));
    }
    // @anchor:prePerform:end
    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      // @anchor:perform:start
      taskResult = implementation.perform(targetParameter);
      // @anchor:perform:end
      // anchor:custom-perform:start
      // anchor:custom-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform DataAuthorization on DataAccessQuery", e
        );
      }
    }

    // @anchor:postPerform:start
    // @anchor:postPerform:end
    // anchor:custom-postPerform:start
    // anchor:custom-postPerform:end

    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // For now just link to dataAccess implementation
  // later different implementations can be added (AD, ...)

  @Override
  public CrudsResult<ProfileAccessRights> getProfileAccessRights(ParameterContext<ProfileAccessQuery> queryParameter) {
    return dataAccessLocal.getProfileAccessRights(queryParameter);
  }
  // anchor:custom-methods:end

}

