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
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.Runner;
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

    private PriorityList<Runner> executors = new PriorityList<>();

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
            for (Runner ruleExecuter : this.executors) {

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

        for (Runner executer : this.executors) {
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

        String ruleGroupKey = resolveRuleGroupKey(rule);
        if (ruleGroupKey == null) {
            registerSimpleRule(rule);
        }
        else {
            registerGroupRule(groupRuleExecuters, rule, ruleGroupKey);
        }
    }

    private String resolveRuleGroupKey(Object rule) throws Exception {

        Group groupAnnotation = AnnotationUtil.resolveAnnotation(rule, Group.class);
        if (groupAnnotation != null) {
            return groupAnnotation.value().getName();
        }

        return resolveGroupKeyFromRuleMethod(rule);
    }

    private String resolveGroupKeyFromRuleMethod(Object rule) throws Exception {

        Method methodWithGroupAnnotation = AnnotationUtil.resolveMethodWithAnnotation(rule, GroupKey.class);

        if (methodWithGroupAnnotation == null) {
            return null;
        }

        String groupKey = (String) methodWithGroupAnnotation.invoke(rule);

        if (StringUtils.isBlank(groupKey)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid Group Key method in the rule. A group method cannot return a blank String. Rule: ''{0}'', Group Key Method: ''{1}''",
                    rule, methodWithGroupAnnotation.getName());
        }

        return groupKey;
    }

    private void registerGroupRule(Map<String, GroupRunner> groupRuleExecuters, Object rule, String groupKey) throws Exception {

        GroupRunner groupExecuter = groupRuleExecuters.get(groupKey);
        if (groupExecuter == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid rule group definition, rule define a group definition that does not exists: Rule: ''{0}'', groupKey: ''{1}''",
                    rule, groupKey);
        }
        else {
            GroupRuleRunner groupRuleExecuter = new GroupRuleRunner(rule);

            LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null,
                    "Group Rule. Description: ''{0}'', Class ''{1}'', Priority: ''{2}''",
                    groupRuleExecuter.getRuleAnnotation().description(), rule, groupRuleExecuter.getPriority());

            groupExecuter.addExecuter(groupRuleExecuter);
        }
    }

    private void registerSimpleRule(Object rule) throws Exception {

        SingleRuleRunner singleRuleExecuter = new SingleRuleRunner(rule);

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null,
                "Simple Rule. Description: ''{0}'', Class ''{1}'', Priority: ''{2}''",
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
                    LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null, "Callback: ''{0}''", callback);
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
        LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null,
                "Group. Name: ''{0}'', Group Instance: ''{1}'' ,Priority: ''{2}''", groupDefinition, groupDefinitionInstance,
                groupRuleRunner.getPriority());

        this.executors.add(groupRuleRunner);

        String groupKey = resolveGroupKey(groupDefinitionInstance);

        validateGroupKey(groupDefinition, groupKey, groupExecuters);

        groupExecuters.put(groupKey, groupRuleRunner);
    }

    private void validateGroupKey(Object groupDefinition, String groupKey, Map<String, GroupRunner> groupExecuters) {

        validateBlankGroupKey(groupDefinition, groupKey);
        validateDuplicateGroupKey(groupDefinition, groupKey, groupExecuters);
    }

    private void validateDuplicateGroupKey(Object groupDefinition, String groupKey, Map<String, GroupRunner> groupExecuters) {

        if (groupExecuters.containsKey(groupKey)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Two Group Definition found with the same Group Key. Group Definition: ''{0}'', Group Key: ''{1}''",
                    groupDefinition, groupKey);
        }
    }

    private void validateBlankGroupKey(Object groupDefinition, String groupKey) {

        if (StringUtils.isBlank(groupKey)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A group key method cannot return a blank String. Group Definition: ''{0}'', Group Key: ''{1}''",
                    groupDefinition, groupKey);
        }
    }

    private String resolveGroupKey(Object executerInstance) throws Exception {

        Method methodWithGroupKeyAnnotation = AnnotationUtil.resolveMethodWithAnnotation(executerInstance, GroupKey.class);

        return methodWithGroupKeyAnnotation != null
                ? (String) methodWithGroupKeyAnnotation.invoke(executerInstance)
                : executerInstance.getClass().getName();
    }
}
