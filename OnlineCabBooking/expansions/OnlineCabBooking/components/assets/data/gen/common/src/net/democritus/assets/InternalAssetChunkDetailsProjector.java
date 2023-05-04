package net.democritus.assets;

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

public class InternalAssetChunkDetailsProjector implements IDataElementProjector<InternalAssetChunkData, InternalAssetChunkDetails>, IDataProjectorRequired<InternalAssetChunkDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final InternalAssetChunkCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public InternalAssetChunkDetailsProjector(InternalAssetChunkCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public InternalAssetChunkDetails project(ParameterContext<InternalAssetChunkData> dataParameter) {

    InternalAssetChunkDetails projection = new InternalAssetChunkDetails();
    InternalAssetChunkData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setContent(data.getContent());
      projection.setByteSize(data.getByteSize());
      projection.setPosition(data.getPosition());
      projection.setIsLast(data.getIsLast());
      projection.setInternalAsset(crudsInternal.getInternalAsset(dataParameter.construct(data.getInternalAsset())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(InternalAssetChunkData data, ParameterContext<InternalAssetChunkDetails> projectionParameter) {
    // don't set the data id
    InternalAssetChunkDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setContent(projection.getContent());
    data.setByteSize(projection.getByteSize());
    data.setPosition(projection.getPosition());
    data.setIsLast(projection.getIsLast());
    crudsInternal.setInternalAsset(data, projectionParameter.construct(projection.getInternalAsset()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(InternalAssetChunkDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<InternalAssetChunkDetails> getProjectionClass() {
    return InternalAssetChunkDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<InternalAssetChunkDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
