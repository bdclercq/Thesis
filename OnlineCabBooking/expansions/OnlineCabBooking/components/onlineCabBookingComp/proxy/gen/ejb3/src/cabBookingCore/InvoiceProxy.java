package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.Diagnostic;

import net.democritus.sys.search.SearchDetails;

import net.democritus.sys.ParameterContext;
import net.democritus.jndi.ComponentJNDI;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton representing the agent in the webtier,
 * to the business logic of the Invoice in the EJB tier
 */

public class InvoiceProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("onlineCabBookingComp", "Invoice", "unknownError");
  private static final Diagnostic CREATE_ERROR = Diagnostic.error("onlineCabBookingComp", "Invoice", "onlineCabBookingComp.invoice.createFailed");
  private static final Diagnostic MODIFY_ERROR = Diagnostic.error("onlineCabBookingComp", "Invoice", "onlineCabBookingComp.invoice.modifyFailed");
  private static final Diagnostic DELETE_ERROR = Diagnostic.error("onlineCabBookingComp", "Invoice", "onlineCabBookingComp.invoice.deleteFailed");
  private static final Diagnostic SEARCH_ERROR = Diagnostic.error("onlineCabBookingComp", "Invoice", "onlineCabBookingComp.invoice.findFailed");

  private static final Logger logger = LoggerFactory.getLogger(InvoiceProxy.class);

  private InvoiceRemote invoiceRemote = null;
  private static InvoiceProxy invoiceProxy = null;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private InvoiceProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("onlineCabBookingComp");
      String remoteName = componentJNDI.getDataRemoteName("Invoice");
      invoiceRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static InvoiceProxy getInvoiceProxy() {
    if (invoiceProxy == null) {
      invoiceProxy = new InvoiceProxy();
    }
    return invoiceProxy;
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance:start
  public CrudsResult<InvoiceDetails> getDetails(ParameterContext<Long> idParameter) {
    try {
      return invoiceRemote.getDetails(idParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getDetails() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    try {
      return (CrudsResult<T>) invoiceRemote.getProjection(projectionRefParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getProjection() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    try {
      return (CrudsResult<DataRef>) invoiceRemote.resolveDataRef(dataRefParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "resolveDataRef() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }
  // anchor:instance:end

  /*========== Individual finders ==========*/

  public <S extends InvoiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    try {
      return invoiceRemote.find(searchParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "find() failed", e
        );
      }
      return SearchResult.error(SEARCH_ERROR);
    }
  }

  /*========== Name/Id resolution methods ==========*/

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    try {
      return invoiceRemote.getName(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve name for invoiceId", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    try {
      return invoiceRemote.getId(nameParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve id for invoiceName", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  /*========== Create/Modify CRUD methods ==========*/

  // anchor:crud:start
  public CrudsResult<DataRef> create(ParameterContext<InvoiceDetails> detailsParameter) {
    try {
      return invoiceRemote.create(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create Invoice", re
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }

  public CrudsResult<DataRef> modify(ParameterContext<InvoiceDetails> detailsParameter) {
    try {
      return invoiceRemote.modify(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not modify Invoice", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    try {
      return invoiceRemote.createOrModify(projectionParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create nor modify Invoice", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    try {
      return invoiceRemote.delete(idParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete Invoice", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }

  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> parameter) {
    try {
      return invoiceRemote.deleteByDataRef(parameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete Invoice", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }
  // anchor:crud:end

  // @anchor:methods:start
  // @anchor:methods:end

  /*=========== Customisation ===========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

