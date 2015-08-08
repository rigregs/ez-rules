package com.opnitech.rules.core.validators.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;
import com.opnitech.rules.core.validators.RunnerDefinitionValidator;

/**
 * Allow to validate all the constrains that a Rule must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleRunnerDefinitionValidator extends AbstractValidator
        implements RunnerDefinitionConditionValidator, RunnerDefinitionValidator {

    public RuleRunnerDefinitionValidator() {
        // Default constructor
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator#
     * acceptRunner(java.lang.Object)
     */
    @Override
    public boolean acceptRunner(Object executable) throws Exception {

        return AnnotationUtil.isAnnotationPresent(executable, Rule.class);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.validators.RunnerDefinitionValidator#validate(
     * java.util.List, java.lang.Object)
     */
    @Override
    public void validate(List<Object> candidateExecutables, Object executable) throws Exception {

        checkUniqueExecutor(candidateExecutables, Rule.class, executable);

        checkValidWhenMethod(executable);
        checkValidThenMethod(executable);
        checkValidPriorityMethod(executable);

        checkGroupKeyMethod(executable);
    }

    private void checkGroupKeyMethod(Object executable) throws Exception {

        checkGroupKeyMethods(executable);
        checkAmbiguosGroupKey(executable);
    }

    private void checkAmbiguosGroupKey(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, GroupKey.class);
        if (CollectionUtils.isNotEmpty(methods)) {
            if (AnnotationUtil.isAnnotationPresent(executable, Group.class)) {
                ExceptionUtil.throwIllegalArgumentException(
                        "Invalid group definition in rule ''{0}''. Group definitin is ambiguos due you have a ''Group'' annotation and ''GroupKey'' annotation in the method ''{1}''. A rule can provide only one way to identify the group where it belong",
                        executable, methods.iterator().next().getName());
            }
        }
    }

    private void checkValidThenMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, Then.class);

        checkValidThenMethodCount(executable, methods);
        checkValidMethodResultValue(executable, methods.get(0), Void.TYPE);
    }

    private void checkValidThenMethodCount(Object executable, List<Method> methods) {

        if (CollectionUtils.isEmpty(methods)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule must have at least one method annotated with a 'Then' annotation, please check the method signature. Rule: ''{0}''",
                    executable);
        }
    }

    private void checkValidWhenMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, When.class);

        checkValidWhenMethodCount(executable, methods);
        checkValidMethodResultValue(executable, methods.get(0), WhenEnum.class, Boolean.class, boolean.class);
    }

    private void checkValidWhenMethodCount(Object executable, List<Method> methods) {

        if (CollectionUtils.isEmpty(methods) || methods.size() > 1) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule must have one method annotated with a 'When' annotation, please check the method signature. Rule: ''{0}''",
                    executable);
        }
    }
}
