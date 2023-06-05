package net.democritus.sys;

//@feature:param-target-value
public class ParamTargetValueProvider implements IParamTargetValueProvider {

  @Override public String getParamTargetValue(UserContext userContext, String parameterName, String targetName) {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(userContext);

    return paramTargetValueAgent.getParamTargetValue(parameterName, targetName);
  }

}
