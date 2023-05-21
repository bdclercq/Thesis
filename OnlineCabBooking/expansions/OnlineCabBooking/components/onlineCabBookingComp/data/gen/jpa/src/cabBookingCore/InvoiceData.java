package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.democritus.sys.DataRef;

// anchor:imports:start
// anchor:imports:end

// @anchor:imports:start
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Persistency pojo class for the entity Invoice,
 */

@Entity(name=InvoiceData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Invoice", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=InvoiceData.QUERY_FINDALL, query="select o FROM " + InvoiceData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class InvoiceData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Invoice";
  public static final String QUERY_FINDALL = "cabBookingCore.Invoice.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Long mPayment;
  private Long mCustomer;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public InvoiceData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public InvoiceData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Long payment
      , Long customer
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mPayment = payment;
    this.mCustomer = customer;
    // anchor:constructor-assign:end
  }

  /*========== Getters and Setters ==========*/

  @Id
  // @anchor:generateId:start
  @GeneratedValue(strategy=GenerationType.AUTO)
  // @anchor:generateId:end
  // anchor:custom-annotations-getter-id:start
  // anchor:custom-annotations-getter-id:end
  public Long getId() {
    return this.mId;
  }

  // anchor:custom-annotations-setter-id:start
  // anchor:custom-annotations-setter-id:end
  public void setId(Long id) {
    this.mId = id;
  }

  // @anchor:methods:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-payment:start
  @Column(name="payment_id")
  // @anchor:annotations-getter-payment:end
  // anchor:custom-annotations-getter-payment:start
  // anchor:custom-annotations-getter-payment:end
  public Long getPayment() {
    return this.mPayment;
  }

  // @anchor:annotations-setter-payment:start
  // @anchor:annotations-setter-payment:end
  // anchor:custom-annotations-setter-payment:start
  // anchor:custom-annotations-setter-payment:end
  public void setPayment(Long payment) {
    this.mPayment = payment;
  }

  // @anchor:annotations-getter-customer:start
  @Column(name="customer_id")
  // @anchor:annotations-getter-customer:end
  // anchor:custom-annotations-getter-customer:start
  // anchor:custom-annotations-getter-customer:end
  public Long getCustomer() {
    return this.mCustomer;
  }

  // @anchor:annotations-setter-customer:start
  // @anchor:annotations-setter-customer:end
  // anchor:custom-annotations-setter-customer:start
  // anchor:custom-annotations-setter-customer:end
  public void setCustomer(Long customer) {
    this.mCustomer = customer;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
