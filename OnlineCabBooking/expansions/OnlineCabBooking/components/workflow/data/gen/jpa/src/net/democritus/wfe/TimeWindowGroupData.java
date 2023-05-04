package net.democritus.wfe;

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
import net.democritus.wfe.TimeWindowData;
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
 * Persistency pojo class for the entity TimeWindowGroup,
 */

@Entity(name=TimeWindowGroupData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="TimeWindowGroup", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=TimeWindowGroupData.QUERY_FINDALL, query="select o FROM " + TimeWindowGroupData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class TimeWindowGroupData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.wfe.TimeWindowGroup";
  public static final String QUERY_FINDALL = "net.democritus.wfe.TimeWindowGroup.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mVisible;
  private Collection<TimeWindowData> mTimeWindows;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TimeWindowGroupData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TimeWindowGroupData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String visible
      , Collection<TimeWindowData> timeWindows
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mVisible = visible;
    this.mTimeWindows = timeWindows;
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
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-name:start
  // @anchor:annotations-getter-name:end
  // anchor:custom-annotations-getter-name:start
  // anchor:custom-annotations-getter-name:end
  public String getName() {
    return this.mName;
  }

  // @anchor:annotations-setter-name:start
  // @anchor:annotations-setter-name:end
  // anchor:custom-annotations-setter-name:start
  // anchor:custom-annotations-setter-name:end
  public void setName(String name) {
    this.mName = name;
  }

  // @anchor:annotations-getter-visible:start
  // @anchor:annotations-getter-visible:end
  // anchor:custom-annotations-getter-visible:start
  // anchor:custom-annotations-getter-visible:end
  public String getVisible() {
    return this.mVisible;
  }

  // @anchor:annotations-setter-visible:start
  // @anchor:annotations-setter-visible:end
  // anchor:custom-annotations-setter-visible:start
  // anchor:custom-annotations-setter-visible:end
  public void setVisible(String visible) {
    this.mVisible = visible;
  }

  // @anchor:annotations-getter-timeWindows:start
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="timeWindowGroup_timeWindows",
    schema="WORKFLOW",
    joinColumns=@JoinColumn(name="timeWindowGroup_id"),
    inverseJoinColumns=@JoinColumn(name="timeWindow_id"))
  // @anchor:annotations-getter-timeWindows:end
  // anchor:custom-annotations-getter-timeWindows:start
  // anchor:custom-annotations-getter-timeWindows:end
  public Collection<TimeWindowData> getTimeWindows() {
    return this.mTimeWindows;
  }

  // @anchor:annotations-setter-timeWindows:start
  // @anchor:annotations-setter-timeWindows:end
  // anchor:custom-annotations-setter-timeWindows:start
  // anchor:custom-annotations-setter-timeWindows:end
  public void setTimeWindows(final Collection<TimeWindowData> timeWindows) {
    this.mTimeWindows = timeWindows;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
