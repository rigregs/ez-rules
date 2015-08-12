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
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;
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

        validateUniqueExecutor(candidateExecutables, Rule.class, executable);

        AnnotationValidatorUtil.validateAnnotatedMethods(executable, When.class, 1, 1, true, WhenEnum.class, Boolean.class,
                boolean.class);

        AnnotationValidatorUtil.validateAnnotatedMethods(executable, Then.class, 1, Integer.MAX_VALUE, true, Void.TYPE);

        validateValidPriorityMethod(executable);
        validateGroupKeyMethod(executable);
    }

    @Override
    protected void validateGroupKeyMethod(Object executable) throws Exception {

        super.validateGroupKeyMethod(executable);

        validateAmbiguosGroupKey(executable);
    }

    private void validateAmbiguosGroupKey(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, GroupKey.class);
        if (CollectionUtils.isNotEmpty(methods)) {
            if (AnnotationUtil.isAnnotationPresent(executable, Group.class)) {
                ExceptionUtil.throwIllegalArgumentException(
                        "Invalid group definition in rule ''{0}''. Group definitin is ambiguos due you have a ''Group'' annotation in the rule and ''GroupKey'' annotation in at least the method ''{1}''. A rule can provide only one way to identify the group where it belong",
                        executable, methods.iterator().next().getName());
            }
        }
    }
}
