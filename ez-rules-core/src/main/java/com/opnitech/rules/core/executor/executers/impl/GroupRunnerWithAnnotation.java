package com.opnitech.rules.core.executor.executers.impl;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
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

        initialize(resolvePriority(this.groupDefinitionAnnotation.priority()), this.groupDefinitionAnnotation.value());
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
