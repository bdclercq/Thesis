package net.democritus.wfe.action;

import com.opensymphony.xwork2.ActionSupport;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

import java.util.LinkedList;
import java.util.List;

public class FlowResultToStrutsMapper {

  private final ActionSupport actionSupport;

  public FlowResultToStrutsMapper(ActionSupport actionSupport) {
    this.actionSupport = actionSupport;
  }

  public void mapDiagnostics(List<Diagnostic> diagnostics) {
    this.mapErrorMessages(diagnostics);
    this.mapFieldErrorMessages(diagnostics);
  }

  public void mapErrorMessages(List<Diagnostic> diagnostics) {
    for (Diagnostic diagnostic : getElementDiagnostics(diagnostics)) {
      String message = this.getMessage(diagnostic);
      if (diagnostic.isError()) {
        this.actionSupport.addActionError(message);
      } else {
        this.actionSupport.addActionMessage(message);
      }
    }

  }

  public void mapFieldErrorMessages(List<Diagnostic> diagnostics) {
    for (Diagnostic diagnostic : getFieldDiagnostics(diagnostics)) {
      String fieldName = diagnostic.getQualifiedName();
      this.actionSupport.addFieldError(fieldName, this.getMessage(diagnostic));
    }
  }

  private static List<Diagnostic> getElementDiagnostics(List<Diagnostic> diagnostics) {
    List<Diagnostic> filtered = new LinkedList();

    for (Diagnostic diagnostic : diagnostics) {
      if (!diagnostic.isFieldDiagnostic()) {
        filtered.add(diagnostic);
      }
    }

    return filtered;
  }

  private static List<Diagnostic> getFieldDiagnostics(List<Diagnostic> diagnostics) {
    List<Diagnostic> filtered = new LinkedList();

    for (Diagnostic diagnostic : diagnostics) {
      if (diagnostic.isFieldDiagnostic()) {
        filtered.add(diagnostic);
      }
    }

    return filtered;
  }

  private String getMessage(Diagnostic diagnostic) {
    String key = diagnostic.getKey();
    if (!diagnostic.hasDiagnosticReasons()) {
      return this.getMessage(key);
    } else {
      List<String> arguments = new LinkedList();

      for (DiagnosticReason reason : diagnostic.getDiagnosticReasons()) {
        arguments.add(reason.getValue() != null ? reason.getValue().toString() : "[null]");
      }

      return this.getMessage(key, arguments);
    }
  }

  private String getMessage(String key, List<String> arguments) {
    return this.actionSupport.getText(key, this.defaultMessage(key), arguments);
  }

  private String getMessage(String key) {
    return this.actionSupport.getText(key, this.defaultMessage(key));
  }

  private String defaultMessage(String key) {
    return "not translated: " + key;
  }
}

