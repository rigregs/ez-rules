package com.opnitech.rules.core.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.RuleRunner;
import com.opnitech.rules.core.executor.executers.impl.GroupRuleRunner;
import com.opnitech.rules.core.executor.executers.impl.GroupRunner;
import com.opnitech.rules.core.executor.executers.impl.SingleRuleRunner;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ClassUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;
import com.opnitech.rules.core.utils.LoggerUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleEngineExecuter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineExecuter.class);

    private List<Object> callbacks = new ArrayList<>();

    private PriorityList<RuleRunner> executors = new PriorityList<>();

    public RuleEngineExecuter() {
        // Default constructor
    }

    /**
     * Manage the execution of the rule
     * 
     * @param exchanges
     * @return
     */
    public WorkflowState execute(Object... exchanges) {

        WorkflowState workflowState = new WorkflowState(this.callbacks, exchanges);

        try {
            for (RuleRunner ruleExecuter : this.executors) {

                WhenEnum acceptExecution = ruleExecuter.execute(workflowState);
                Validate.notNull(acceptExecution);
                // TODO Rigre put some kind of trace here
            }
        }
        catch (Throwable throwable) {
            RuleEngineExecuter.LOGGER.error("Internal error executing the rules", throwable);
            workflowState.setThrowable(throwable);
        }

        return workflowState;
    }

    /**
     * Initialize all the elements that allow the rule engine to work
     * 
     * @param definedCallbacks
     * @param definedRules
     * @param definedGroupDefinitions
     * @throws Exception
     */
    public void initialize(List<Object> definedCallbacks, List<Object> definedRules, List<Object> definedGroupDefinitions)
            throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, this, null, "Initializing Rule Engine...");

        initializeCallbacks(definedCallbacks);
        Map<Class<?>, GroupRunner> groupRuleExecuters = createGroupRuleExecutors(definedGroupDefinitions);

        createRuleExecutors(definedRules, groupRuleExecuters);

        logRuleEngineState();

        LoggerUtil.info(RuleEngineExecuter.LOGGER, this, null, "Rule Engine Initialized...");
    }

    private void logRuleEngineState() {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Final rule engine state:");

        for (RuleRunner executer : this.executors) {
            executer.logRuleMetadata(RuleEngineExecuter.LOGGER, this, 2);
        }
    }

    private void createRuleExecutors(List<Object> definedRules, Map<Class<?>, GroupRunner> groupRuleExecuters) throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Initializating rules...");

        if (CollectionUtils.isNotEmpty(definedRules)) {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, 2, this, null, "Registered rules:");

            List<Object> ruleInstances = ClassUtil.createInstances(definedRules);

            for (Object rule : ruleInstances) {
                registerRule(groupRuleExecuters, rule);
            }
        }
        else {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, 2, this, null, "No rule is registered...");
        }

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Rules initialized...");
    }

    private void registerRule(Map<Class<?>, GroupRunner> groupRuleExecuters, Object rule) throws Exception {

        Group ruleGroup = AnnotationUtil.resolveAnnotation(rule, Group.class);
        if (ruleGroup == null) {
            registerSimpleRule(rule);
        }
        else {
            registerGroupRule(groupRuleExecuters, rule, ruleGroup);
        }
    }

    private void registerGroupRule(Map<Class<?>, GroupRunner> groupRuleExecuters, Object rule, Group ruleGroup) throws Exception {

        Class<?> groupDefinitionId = ruleGroup.group();

        GroupRunner groupExecuter = groupRuleExecuters.get(groupDefinitionId);
        if (groupExecuter == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid rule group definition, rule define a group definition that does not exists: {0}", groupDefinitionId);
        }
        else {
            GroupRuleRunner groupRuleExecuter = new GroupRuleRunner(rule);

            LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null, "Group Rule. Description: {0}, Class {1}, Priority: {2}",
                    groupRuleExecuter.getRuleAnnotation().description(), rule, groupRuleExecuter.getPriority());

            groupExecuter.addExecuter(groupRuleExecuter);
        }
    }

    private void registerSimpleRule(Object rule) throws Exception {

        SingleRuleRunner singleRuleExecuter = new SingleRuleRunner(rule);

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null, "Simple Rule. Description: {0}, Class {1}, Priority: {2}",
                singleRuleExecuter.getRuleAnnotation().description(), rule, singleRuleExecuter.getPriority());

        this.executors.add(singleRuleExecuter);
    }

    private void initializeCallbacks(List<Object> definedCallbacks) throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Initializating callbacks...");

        this.callbacks = ClassUtil.createInstances(definedCallbacks);

        logCallbacks();
    }

    private void logCallbacks() {

        if (RuleEngineExecuter.LOGGER.isInfoEnabled()) {

            if (CollectionUtils.isNotEmpty(this.callbacks)) {

                LoggerUtil.info(RuleEngineExecuter.LOGGER, 2, this, null, "Registered callbacks:");
                for (Object callback : this.callbacks) {
                    LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null, "Callback: {0}", callback);
                }
            }
            else {
                LoggerUtil.info(RuleEngineExecuter.LOGGER, 2, this, null, "No callback registered");
            }

            LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Callbacks initialized...");
        }
    }

    private Map<Class<?>, GroupRunner> createGroupRuleExecutors(List<Object> definedGroupDefinitions) throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Initializating groups...");

        Map<Class<?>, GroupRunner> groupExecuters = new HashMap<>();

        if (CollectionUtils.isNotEmpty(definedGroupDefinitions)) {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, 2, this, null, "Registered groups:");

            List<Object> groupDefinitions = ClassUtil.createInstances(definedGroupDefinitions);
            for (Object groupDefinition : groupDefinitions) {

                registerGroupRunner(groupExecuters, groupDefinition);
            }
        }
        else {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, 2, this, null, "No group is registered...");
        }

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Groups initialized...");

        return groupExecuters;
    }

    private void registerGroupRunner(Map<Class<?>, GroupRunner> groupExecuters, Object groupDefinition) throws Exception {

        GroupRunner groupRuleRunner = new GroupRunner(groupDefinition);
        LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null, "Group. Name: {0}, Priority: {1}", groupDefinition,
                groupRuleRunner.getPriority());

        this.executors.add(groupRuleRunner);
        groupExecuters.put(groupDefinition.getClass(), groupRuleRunner);
    }
}
