package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import cabBookingCore.TripBookingDetails;
import cabBookingCore.TripBookingLocalAgent;
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
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task MakeTripBookingInvoice.
 */

@Stateless
@Remote(MakeTripBookingInvoiceRemote.class)
@Local(MakeTripBookingInvoiceLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class MakeTripBookingInvoiceBean implements MakeTripBookingInvoiceRemote, MakeTripBookingInvoiceLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(MakeTripBookingInvoiceBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> performOnTarget(ParameterContext<DataRef> targetParameter) {
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(targetParameter.getContext());
    // anchor:get-instance:start
    CrudsResult<TripBookingDetails> result = tripBookingAgent.getProjection(new ProjectionRef("details", targetParameter.getValue()));
    // anchor:get-instance:end

    if (result.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to retrieve target instance for 'TripBooking'"
        );
      }
      return TaskResult.error();
    }
    return perform(targetParameter.construct(result.getValue()));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> perform(ParameterContext<TripBookingDetails> targetParameter) {

    TaskResult<Void> taskResult = null;

    // @anchor:prePerform:start
    final MakeTripBookingInvoice implementation;
    try {
      String implName = targetParameter.getProcessingContext().getImplementation();
      implementation = (MakeTripBookingInvoice) Class.forName(implName).newInstance();
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to initialize the implementation class", e
        );
      }
      return TaskResult.error(Diagnostic.error("onlineCabBookingComp", "MakeTripBookingInvoice", "NO_VALID_IMPLEMENTATION_CLASS"));
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
            "Failed to perform MakeTripBookingInvoice on TripBookingDetails", e
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
  // anchor:custom-methods:end

}

