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

public class AssetDetailsProjector implements IDataElementProjector<AssetData, AssetDetails>, IDataProjectorRequired<AssetDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final AssetCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public AssetDetailsProjector(AssetCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public AssetDetails project(ParameterContext<AssetData> dataParameter) {

    AssetDetails projection = new AssetDetails();
    AssetData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setType(data.getType());
      projection.setContentType(data.getContentType());
      projection.setByteSize(data.getByteSize());
      projection.setFileId(data.getFileId());
      projection.setComplete(data.getComplete());
      projection.setFileAsset(crudsInternal.getFileAsset(dataParameter.construct(data.getFileAsset())).getValueOrElse(EMPTY_DATA_REF));
      projection.setInternalAsset(crudsInternal.getInternalAsset(dataParameter.construct(data.getInternalAsset())).getValueOrElse(EMPTY_DATA_REF));
      projection.setExternalAsset(crudsInternal.getExternalAsset(dataParameter.construct(data.getExternalAsset())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(AssetData data, ParameterContext<AssetDetails> projectionParameter) {
    // don't set the data id
    AssetDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setType(projection.getType());
    data.setContentType(projection.getContentType());
    data.setByteSize(projection.getByteSize());
    data.setFileId(projection.getFileId());
    data.setComplete(projection.getComplete());
    crudsInternal.setFileAsset(data, projectionParameter.construct(projection.getFileAsset()));
    crudsInternal.setInternalAsset(data, projectionParameter.construct(projection.getInternalAsset()));
    crudsInternal.setExternalAsset(data, projectionParameter.construct(projection.getExternalAsset()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(AssetDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<AssetDetails> getProjectionClass() {
    return AssetDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<AssetDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
