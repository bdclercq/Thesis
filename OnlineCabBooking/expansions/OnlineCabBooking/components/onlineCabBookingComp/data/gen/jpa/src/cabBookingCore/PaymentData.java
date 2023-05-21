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
 * Persistency pojo class for the entity Payment,
 */

@Entity(name=PaymentData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Payment", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=PaymentData.QUERY_FINDALL, query="select o FROM " + PaymentData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class PaymentData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Payment";
  public static final String QUERY_FINDALL = "cabBookingCore.Payment.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Boolean mStatusPayed;
  private Double mTotalAmount;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public PaymentData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public PaymentData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Boolean statusPayed
      , Double totalAmount
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mStatusPayed = statusPayed;
    this.mTotalAmount = totalAmount;
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
  // @anchor:annotations-getter-statusPayed:start
  // @anchor:annotations-getter-statusPayed:end
  // anchor:custom-annotations-getter-statusPayed:start
  // anchor:custom-annotations-getter-statusPayed:end
  public Boolean getStatusPayed() {
    return this.mStatusPayed;
  }

  // @anchor:annotations-setter-statusPayed:start
  // @anchor:annotations-setter-statusPayed:end
  // anchor:custom-annotations-setter-statusPayed:start
  // anchor:custom-annotations-setter-statusPayed:end
  public void setStatusPayed(Boolean statusPayed) {
    this.mStatusPayed = statusPayed;
  }

  // @anchor:annotations-getter-totalAmount:start
  // @anchor:annotations-getter-totalAmount:end
  // anchor:custom-annotations-getter-totalAmount:start
  // anchor:custom-annotations-getter-totalAmount:end
  public Double getTotalAmount() {
    return this.mTotalAmount;
  }

  // @anchor:annotations-setter-totalAmount:start
  // @anchor:annotations-setter-totalAmount:end
  // anchor:custom-annotations-setter-totalAmount:start
  // anchor:custom-annotations-setter-totalAmount:end
  public void setTotalAmount(Double totalAmount) {
    this.mTotalAmount = totalAmount;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
