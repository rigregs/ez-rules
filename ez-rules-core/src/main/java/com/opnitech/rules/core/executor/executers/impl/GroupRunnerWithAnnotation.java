package com.opnitech.rules.core.executor.executers.impl;

import java.lang.reflect.Method;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupDefinitionExecutionStrategy;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupRunnerWithAnnotation extends AbstractGroupRunner {

    private final GroupDefinition groupDefinitionAnnotation;

    public GroupRunnerWithAnnotation(Object groupDefinition) throws Exception {

        super(groupDefinition);

        AnnotationValidatorUtil.validateAnnotationPresent(groupDefinition, GroupDefinition.class);

        this.groupDefinitionAnnotation = AnnotationUtil.resolveAnnotation(groupDefinition, GroupDefinition.class);

        initialize(resolvePriority(this.groupDefinitionAnnotation.priority()),
                resolveExecutionStrategy(this.groupDefinitionAnnotation.value()));
    }

    private ExecutionStrategyEnum resolveExecutionStrategy(ExecutionStrategyEnum executionStrategyEnum) throws Exception {

        Method methodWithGroupDefinitionExecutionStrategyAnnotation = AnnotationUtil.resolveMethodWithAnnotation(getExecutable(),
                GroupDefinitionExecutionStrategy.class);

        if (methodWithGroupDefinitionExecutionStrategyAnnotation != null) {
            return (ExecutionStrategyEnum) methodWithGroupDefinitionExecutionStrategyAnnotation.invoke(getExecutable());
        }

        return executionStrategyEnum;
    }

    @Override
    protected String retrieveGroupDescription() {

        return this.groupDefinitionAnnotation.description();
    }

    @Override
    protected Object retrieveGroupDefinitionType() {

        return getExecutable().getClass();
    }
}
