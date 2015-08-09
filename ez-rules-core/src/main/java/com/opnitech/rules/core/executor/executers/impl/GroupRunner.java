package com.opnitech.rules.core.executor.executers.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.impl.strategy.AllGroupRunnerStrategy;
import com.opnitech.rules.core.executor.executers.impl.strategy.AllPossibleGroupRunnerStrategy;
import com.opnitech.rules.core.executor.executers.impl.strategy.GroupRunnerStrategy;
import com.opnitech.rules.core.executor.executers.impl.strategy.StopFirstGroupRunnerStrategy;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.LoggerUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupRunner extends AbstractRunner {

    private static final Map<ExecutionStrategyEnum, GroupRunnerStrategy> STRATEGIES = new HashMap<>();

    static {
        GroupRunner.STRATEGIES.put(ExecutionStrategyEnum.STOP_FIRST, new StopFirstGroupRunnerStrategy());
        GroupRunner.STRATEGIES.put(ExecutionStrategyEnum.ALL_POSSIBLE, new AllPossibleGroupRunnerStrategy());
        GroupRunner.STRATEGIES.put(ExecutionStrategyEnum.ALL, new AllGroupRunnerStrategy());
    }

    private final Object groupDefinition;

    private final int priority;

    private final PriorityList<GroupRuleRunner> executors = new PriorityList<>();

    private final GroupRunnerStrategy executerStrategy;

    private final GroupDefinition groupDefinitionAnnotation;

    public GroupRunner(Object groupDefinition) throws Exception {

        this.groupDefinition = groupDefinition;

        AnnotationUtil.validateAnnotationPresent(this.groupDefinition, GroupDefinition.class);

        this.groupDefinitionAnnotation = AnnotationUtil.resolveAnnotation(this.groupDefinition, GroupDefinition.class);
        this.priority = resolvePriority(groupDefinition, this.groupDefinitionAnnotation.priority());

        this.executerStrategy = resolveStrategy();
    }

    @Override
    public void logRuleMetadata(Logger logger, Object producer, int level) {

        LoggerUtil.info(logger, level, producer, null,
                "Group Rule Executer. Rule class: ''{0}'', Description: ''{1}'', Priority: ''{2}''",
                this.groupDefinition.getClass(), this.groupDefinitionAnnotation.description(),
                this.groupDefinitionAnnotation.priority());

        if (CollectionUtils.isNotEmpty(this.executors)) {
            for (GroupRuleRunner groupRuleExecuter : this.executors) {
                groupRuleExecuter.logRuleMetadata(logger, producer, level + 1);
            }
        }
    }

    private GroupRunnerStrategy resolveStrategy() {

        GroupRunnerStrategy strategy = GroupRunner.STRATEGIES.get(this.groupDefinitionAnnotation.value());
        Validate.notNull(strategy);

        return strategy;
    }

    @Override
    public WhenEnum execute(WorkflowState workflowState) throws Throwable {

        return this.executerStrategy.doExecution(workflowState, this.executors);
    }

    public void addExecuter(GroupRuleRunner ruleExecuter) {

        this.executors.add(ruleExecuter);
    }

    @Override
    public int getPriority() {

        return this.priority;
    }
}
