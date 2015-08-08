package com.opnitech.rules.core.validators.impl;

import java.util.List;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;
import com.opnitech.rules.core.validators.RunnerDefinitionValidator;

/**
 * Allow to validate all the constrains that a Group Definition must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupDefinitionRunnerDefinitionValidator extends AbstractValidator
        implements RunnerDefinitionConditionValidator, RunnerDefinitionValidator {

    public GroupDefinitionRunnerDefinitionValidator() {
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

        return AnnotationUtil.isAnnotationPresent(executable, GroupDefinition.class);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.validators.RunnerDefinitionValidator#validate(
     * java.util.List, java.lang.Object)
     */
    @Override
    public void validate(List<Object> candidateExecutables, Object executable) throws Exception {

        checkUniqueExecutor(candidateExecutables, GroupDefinition.class, executable);
        validateExecutionStrategy(executable);

        checkGroupKeyMethods(executable);
    }

    private void validateExecutionStrategy(Object executable) throws Exception {

        GroupDefinition groupDefinition = AnnotationUtil.resolveAnnotation(executable, GroupDefinition.class);
        if (groupDefinition.ruleExecutionStrategy() == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid Group Execution Strategy in the Group Definition. You are trying to register a Group Definition without a valid Rule Execution Strategy. Group Definition: ''{0}''",
                    executable);
        }
    }
}
