package net.democritus.gui;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import net.democritus.projection.IDataElementProjector;
import net.democritus.projection.IDataProjectorRequired;

import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.diagnostics.DiagnosticHelper;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.DataRefValidation;

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class ThumbnailDetailsProjector implements IDataElementProjector<ThumbnailData, ThumbnailDetails>, IDataProjectorRequired<ThumbnailDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final ThumbnailCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ThumbnailDetailsProjector(ThumbnailCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public ThumbnailDetails project(ParameterContext<ThumbnailData> dataParameter) {

    ThumbnailDetails projection = new ThumbnailDetails();
    ThumbnailData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setFullName(data.getFullName());
      projection.setUri(data.getUri());
      projection.setDepth(data.getDepth());
      projection.setBorder(data.getBorder());
      projection.setThumbType(data.getThumbType());
      projection.setThumbName(data.getThumbName());
      projection.setTargetType(data.getTargetType());
      projection.setTargetName(data.getTargetName());
      projection.setTargetId(data.getTargetId());
      projection.setLeftX(data.getLeftX());
      projection.setTopY(data.getTopY());
      projection.setWidth(data.getWidth());
      projection.setHeight(data.getHeight());
      projection.setClickAction(data.getClickAction());
      projection.setHooverAction(data.getHooverAction());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(ThumbnailData data, ParameterContext<ThumbnailDetails> projectionParameter) {
    // don't set the data id
    ThumbnailDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setFullName(projection.getFullName());
    data.setUri(projection.getUri());
    data.setDepth(projection.getDepth());
    data.setBorder(projection.getBorder());
    data.setThumbType(projection.getThumbType());
    data.setThumbName(projection.getThumbName());
    data.setTargetType(projection.getTargetType());
    data.setTargetName(projection.getTargetName());
    data.setTargetId(projection.getTargetId());
    data.setLeftX(projection.getLeftX());
    data.setTopY(projection.getTopY());
    data.setWidth(projection.getWidth());
    data.setHeight(projection.getHeight());
    data.setClickAction(projection.getClickAction());
    data.setHooverAction(projection.getHooverAction());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(ThumbnailDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<ThumbnailDetails> getProjectionClass() {
    return ThumbnailDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<ThumbnailDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
