package com.opnitech.rules.core.executor.executers.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.GroupRunner;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.executers.WhenResult;
import com.opnitech.rules.core.executor.executers.impl.strategy.AllGroupRunnerStrategy;
import com.opnitech.rules.core.executor.executers.impl.strategy.AllPossibleGroupRunnerStrategy;
import com.opnitech.rules.core.executor.executers.impl.strategy.GroupRunnerStrategy;
import com.opnitech.rules.core.executor.executers.impl.strategy.StopFirstGroupRunnerStrategy;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;
import com.opnitech.rules.core.utils.LoggerUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractGroupRunner extends AbstractRunner implements GroupRunner {

    private static final Map<ExecutionStrategyEnum, GroupRunnerStrategy> STRATEGIES = new HashMap<>();

    static {
        AbstractGroupRunner.STRATEGIES.put(ExecutionStrategyEnum.STOP_FIRST, new StopFirstGroupRunnerStrategy());
        AbstractGroupRunner.STRATEGIES.put(ExecutionStrategyEnum.ALL_POSSIBLE, new AllPossibleGroupRunnerStrategy());
        AbstractGroupRunner.STRATEGIES.put(ExecutionStrategyEnum.ALL, new AllGroupRunnerStrategy());
    }

    private int priority;

    private final PriorityList<Runner> executors = new PriorityList<>();

    private GroupRunnerStrategy groupRunnerStrategy;

    private ExecutionStrategyEnum executionStrategy;

    public AbstractGroupRunner(Object executable) throws Exception {
        super(executable);
    }

    protected abstract String retrieveGroupDescription();

    protected abstract Object retrieveGroupDefinitionType();

    @Override
    protected WhenResult createWhenResult(WhenEnum whenEnum) {

        return new WhenResult(whenEnum);
    }

    @Override
    public void logRuleMetadata(Logger logger, Object producer, int level) {

        LoggerUtil.info(logger, level, producer, null,
                "Group Rule Executer. Group class: ''{0}'', Description: ''{1}'', Priority: ''{2}'', Strategy: ''{3}''",
                retrieveGroupDefinitionType(), retrieveGroupDescription(), getPriority(), this.executionStrategy.name());

        if (CollectionUtils.isNotEmpty(this.getExecutors())) {
            for (Runner groupRuleExecuter : this.getExecutors()) {
                groupRuleExecuter.logRuleMetadata(logger, producer, level + 1);
            }
        }
    }

    protected void initialize(int groupPriority, ExecutionStrategyEnum groupExecutionStrategy) {

        Validate.notNull(groupExecutionStrategy);

        this.priority = groupPriority;
        this.executionStrategy = groupExecutionStrategy;
        this.groupRunnerStrategy = resolveStrategy();
    }

    private GroupRunnerStrategy resolveStrategy() {

        GroupRunnerStrategy strategy = AbstractGroupRunner.STRATEGIES.get(this.executionStrategy);
        Validate.notNull(strategy);

        return strategy;
    }

    @Override
    public WhenResult execute(WorkflowState workflowState) throws Throwable {

        return this.groupRunnerStrategy.doExecution(workflowState, this.getExecutors());
    }

    public void addExecuter(Runner ruleExecuter) {

        this.getExecutors().add(ruleExecuter);
    }

    @Override
    public int getPriority() {

        return this.priority;
    }

    @Override
    public PriorityList<Runner> getExecutors() {

        return this.executors;
    }
}
