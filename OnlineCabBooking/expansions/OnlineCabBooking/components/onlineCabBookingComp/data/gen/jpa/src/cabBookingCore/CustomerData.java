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
 * Persistency pojo class for the entity Customer,
 */

@Entity(name=CustomerData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Customer", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=CustomerData.QUERY_FINDALL, query="select o FROM " + CustomerData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class CustomerData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Customer";
  public static final String QUERY_FINDALL = "cabBookingCore.Customer.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Boolean mJourneyStatus;
  private Long mPerson;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public CustomerData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public CustomerData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Boolean journeyStatus
      , Long person
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mJourneyStatus = journeyStatus;
    this.mPerson = person;
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
  // @anchor:annotations-getter-journeyStatus:start
  // @anchor:annotations-getter-journeyStatus:end
  // anchor:custom-annotations-getter-journeyStatus:start
  // anchor:custom-annotations-getter-journeyStatus:end
  public Boolean getJourneyStatus() {
    return this.mJourneyStatus;
  }

  // @anchor:annotations-setter-journeyStatus:start
  // @anchor:annotations-setter-journeyStatus:end
  // anchor:custom-annotations-setter-journeyStatus:start
  // anchor:custom-annotations-setter-journeyStatus:end
  public void setJourneyStatus(Boolean journeyStatus) {
    this.mJourneyStatus = journeyStatus;
  }

  // @anchor:annotations-getter-person:start
  @Column(name="person_id")
  // @anchor:annotations-getter-person:end
  // anchor:custom-annotations-getter-person:start
  // anchor:custom-annotations-getter-person:end
  public Long getPerson() {
    return this.mPerson;
  }

  // @anchor:annotations-setter-person:start
  // @anchor:annotations-setter-person:end
  // anchor:custom-annotations-setter-person:start
  // anchor:custom-annotations-setter-person:end
  public void setPerson(Long person) {
    this.mPerson = person;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
