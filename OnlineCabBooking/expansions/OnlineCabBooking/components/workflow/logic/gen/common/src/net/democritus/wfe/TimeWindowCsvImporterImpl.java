package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProjectionRef;
import net.democritus.upload.CsvElementReader;
import net.democritus.upload.ImportError;
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
import net.democritus.upload.CsvReadResult;
import net.democritus.upload.IImporter;
import net.democritus.upload.ImportLine;
import net.democritus.validation.Result;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class TimeWindowCsvImporterImpl implements IImporter {
  private static final Logger logger = LoggerFactory.getLogger(TimeWindowCsvImporterImpl.class);

  private TimeWindowMapper timeWindowMapper = new TimeWindowMapper();

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ImportResult importFile(ParameterContext<ImportFile> parameter) {
    ImportFile importFile = parameter.getValue();

    // @anchor:pre-read:start
    // @anchor:pre-read:end
    // anchor:custom-pre-read:start
    // anchor:custom-pre-read:end

    if (logger.isDebugEnabled()) {
      logger.debug(
          "timeWindow CSV import: start"
      );
    }

    ImportResult importResult = new ImportResult();
    CsvReadResult readResult = new CsvElementReader().readInstances(importFile);
    importResult.addErrors(readResult.getReadErrors());

    // @anchor:post-read:start
    // @anchor:post-read:end
    // anchor:custom-post-read:start
    // anchor:custom-post-read:end

    if (logger.isDebugEnabled()) {
      logger.debug(
          "timeWindow CSV import: parsed"
              + ", nbLinesRead=" + readResult.getInstances().size()
              + ", nbLinesFailed=" + readResult.getNumberOfReadErrors()
      );
    }

    importInstances(parameter.construct(readResult), importResult);

    if (logger.isInfoEnabled()) {
      logger.info(
          "timeWindow CSV import: imported"
              + ", nbLinesImported=" + importResult.getNbLinesImported()
              + ", nbLinesFailed=" + importResult.getNbLinesFailed()
      );
    }

    // @anchor:post-import:start
    // @anchor:post-import:end
    // anchor:custom-post-import:start
    // anchor:custom-post-import:end

    return importResult;
  }

  private void importInstances(ParameterContext<CsvReadResult> readParam, ImportResult importResult) {
    for (ImportLine line : readParam.getValue().getInstances()) {
      importInstance(readParam.construct(line), importResult);
    }
  }

  private void importInstance(ParameterContext<ImportLine> importLineParam, ImportResult importResult) {
    ImportLine line = importLineParam.getValue();

    Result<TimeWindowDetails> mappingResult = timeWindowMapper.convertToDetails(importLineParam.construct(line.getValues()));
    if (mappingResult.isError()) {
      importResult.addError(new ImportError(line.getIndex(), mappingResult.getMessage()));
      return;
    }
    TimeWindowDetails timeWindowDetails = mappingResult.getValue();

    // @anchor:pre-persist:start
    // @anchor:pre-persist:end
    // anchor:custom-pre-persist:start
    // anchor:custom-pre-persist:end

    CrudsResult<DataRef> crudsResult = persistTimeWindow(importLineParam.construct(timeWindowDetails));
    if (crudsResult.isError()) {
      for (Diagnostic diagnostic : crudsResult.getDiagnostics()) {
        importResult.addError(new ImportError(line.getIndex(), "Failed to persist instance", diagnostic.getKey()));
      }
      return;
    }

    // @anchor:post-persist:start
    // @anchor:post-persist:end
    // anchor:custom-post-persist:start
    // anchor:custom-post-persist:end

    importResult.incrementNbLinesImported();
  }

  private CrudsResult<DataRef> persistTimeWindow(ParameterContext<TimeWindowDetails> detailsParameter) {
    TimeWindowLocalAgent timeWindowAgent = TimeWindowLocalAgent.getTimeWindowAgent(detailsParameter.getContext());
    TimeWindowDetails timeWindowDetails = detailsParameter.getValue();
    // @anchor:persist:start
    // @anchor:persist:end
    // anchor:custom-persist:start
    // anchor:custom-persist:end
    return timeWindowAgent.createOrModify(timeWindowDetails);
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
