package com.opnitech.rules.core.executor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
     *            Allow to execute the rules with an specific array of exchanges
     * @return The final workflow state of the rule execution
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
     *            List of defined callbacks
     * @param definedRules
     *            List of defined rules
     * @param definedGroupDefinitions
     *            List of defined group definitions
     * @throws Exception
     *             Throw an exception if something fail in the initialization
     */
    public void initialize(List<Object> definedCallbacks, List<Object> definedRules, List<Object> definedGroupDefinitions)
            throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, this, null, "Initializing Rule Engine...");

        initializeCallbacks(definedCallbacks);
        Map<String, GroupRunner> groupRuleExecuters = createGroupRuleExecutors(definedGroupDefinitions);

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

    private void createRuleExecutors(List<Object> definedRules, Map<String, GroupRunner> groupRuleExecuters) throws Exception {

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

    private void registerRule(Map<String, GroupRunner> groupRuleExecuters, Object rule) throws Exception {

        Group ruleGroup = resolveRuleGroup(rule);
        if (ruleGroup == null) {
            registerSimpleRule(rule);
        }
        else {
            registerGroupRule(groupRuleExecuters, rule, ruleGroup);
        }
    }

    private Group resolveRuleGroup(Object rule) throws Exception {

        Group groupAnnotation = AnnotationUtil.resolveAnnotation(rule, Group.class);
        if (groupAnnotation == null) {
            groupAnnotation = resolveGroupAnnotationFromRuleMethod(rule);
        }

        return groupAnnotation;
    }

    private Group resolveGroupAnnotationFromRuleMethod(Object rule) throws Exception {

        Method methodWithGroupAnnotation = AnnotationUtil.resolveMethodWithAnnotation(rule, Group.class);

        return methodWithGroupAnnotation != null
                ? (Group) methodWithGroupAnnotation.invoke(rule)
                : null;
    }

    private void registerGroupRule(Map<String, GroupRunner> groupRuleExecuters, Object rule, Group ruleGroup) throws Exception {

        String groupKey = ruleGroup.groupKey();

        String groupDefinitionId = StringUtils.isNotBlank(groupKey)
                ? groupKey
                : ruleGroup.groupDefinitionClass() != null
                        ? ruleGroup.groupDefinitionClass().getName()
                        : null;

        GroupRunner groupExecuter = groupRuleExecuters.get(groupDefinitionId);
        if (groupExecuter == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid rule group definition, rule define a group definition that does not exists: {0}, Rule: {1}, Rule Group: {2}",
                    groupDefinitionId, rule, ruleGroup);
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

    private Map<String, GroupRunner> createGroupRuleExecutors(List<Object> definedGroupDefinitions) throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Initializating groups...");

        Map<String, GroupRunner> groupExecuters = new HashMap<>();

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

    private void registerGroupRunner(Map<String, GroupRunner> groupExecuters, Object groupDefinition) throws Exception {

        Object groupDefinitionInstance = ClassUtil.createInstance(groupDefinition);

        GroupRunner groupRuleRunner = new GroupRunner(groupDefinition);
        LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null, "Group. Name: {0}, Group Instance: {1},Priority: {2}",
                groupDefinition, groupDefinitionInstance, groupRuleRunner.getPriority());

        this.executors.add(groupRuleRunner);
        groupExecuters.put(resolveGroupKey(groupDefinitionInstance), groupRuleRunner);
    }

    private String resolveGroupKey(Object groupDefinitionInstance) {

        // TOD Rigre here we need to check if the group support dynamic naming
        return groupDefinitionInstance.getClass().getName();
    }
}
