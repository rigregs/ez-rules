package com.opnitech.rules.core.executor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.group.GroupParentKey;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.executers.impl.AbstractGroupRunner;
import com.opnitech.rules.core.executor.executers.impl.GroupRunnerWithAnnotation;
import com.opnitech.rules.core.executor.executers.impl.MainGroupRunner;
import com.opnitech.rules.core.executor.executers.impl.SingleRuleRunner;
import com.opnitech.rules.core.executor.flow.WorkflowState;
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

    // private PriorityList<Runner> executors = new PriorityList<>();

    private ExecutionStrategyEnum executionStrategy;

    private MainGroupRunner mainGroupRunner;

    public RuleEngineExecuter(ExecutionStrategyEnum executionStrategy) {
        this.executionStrategy = executionStrategy;
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
            this.mainGroupRunner.execute(workflowState);
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

        this.mainGroupRunner = new MainGroupRunner(this.executionStrategy);

        initializeCallbacks(definedCallbacks);
        Map<String, AbstractGroupRunner> groupRuleExecuters = createGroupRuleExecutors(definedGroupDefinitions);

        createRuleExecutors(definedRules, groupRuleExecuters);

        logRuleEngineState();

        LoggerUtil.info(RuleEngineExecuter.LOGGER, this, null, "Rule Engine Initialized...");
    }

    private void logRuleEngineState() {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 1, this, null, "Final rule engine state:");

        for (Runner executer : this.mainGroupRunner.getExecutors()) {
            executer.logRuleMetadata(RuleEngineExecuter.LOGGER, this, 2);
        }
    }

    private void createRuleExecutors(List<Object> definedRules, Map<String, AbstractGroupRunner> groupRuleExecuters)
            throws Exception {

        int index = 0;
        LoggerUtil.info(RuleEngineExecuter.LOGGER, ++index, this, null, "Initializating rules...");

        if (CollectionUtils.isNotEmpty(definedRules)) {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, ++index, this, null, "Registered rules:");

            List<Object> ruleInstances = ClassUtil.createInstances(definedRules);

            for (Object rule : ruleInstances) {
                registerExecutable(groupRuleExecuters, rule, index + 1);
            }
        }
        else {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, --index, this, null, "No rule is registered...");
        }

        LoggerUtil.info(RuleEngineExecuter.LOGGER, --index, this, null, "Rules initialized...");
    }

    private void registerExecutable(Map<String, AbstractGroupRunner> groupRuleExecuters, Object executable, int index)
            throws Exception {

        String groupKey = resolveRuleGroupKey(executable);
        AbstractGroupRunner groupRunner = StringUtils.isNotBlank(groupKey)
                ? resolveGroupRunner(groupKey, groupRuleExecuters, executable)
                : this.mainGroupRunner;

        SingleRuleRunner singleRuleExecuter = createSimpleRuleRunner(executable);

        LoggerUtil.info(RuleEngineExecuter.LOGGER, index, this, null,
                "Registering Rule. Description: ''{0}'', Class ''{1}'', Priority: ''{2}'', Group Key: ''{3}''",
                singleRuleExecuter.getRuleAnnotation().description(), executable, singleRuleExecuter.getPriority(), groupKey);

        groupRunner.addExecuter(singleRuleExecuter);
    }

    private AbstractGroupRunner resolveGroupRunner(String ruleGroupKey, Map<String, AbstractGroupRunner> groupRuleExecuters,
            Object executer) {

        AbstractGroupRunner groupExecuter = groupRuleExecuters.get(ruleGroupKey);
        if (groupExecuter == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid rule group definition, rule define a group definition that does not exists: Rule: ''{0}'', groupKey: ''{1}''",
                    executer, ruleGroupKey);
        }

        return groupExecuter;
    }

    private SingleRuleRunner createSimpleRuleRunner(Object rule) throws Exception {

        SingleRuleRunner singleRuleExecuter = new SingleRuleRunner(rule);

        LoggerUtil.info(RuleEngineExecuter.LOGGER, 3, this, null,
                "Simple Rule. Description: ''{0}'', Class ''{1}'', Priority: ''{2}''",
                singleRuleExecuter.getRuleAnnotation().description(), rule, singleRuleExecuter.getPriority());
        return singleRuleExecuter;
    }

    private String resolveRuleGroupKey(Object executable) throws Exception {

        Group groupAnnotation = AnnotationUtil.resolveAnnotation(executable, Group.class);
        if (groupAnnotation != null) {
            return groupAnnotation.value().getName();
        }

        return resolveGroupKeyFromRuleMethod(executable);
    }

    private String resolveGroupKeyFromRuleMethod(Object executable) throws Exception {

        Method methodWithGroupAnnotation = AnnotationUtil.resolveMethodWithAnnotation(executable, GroupKey.class);

        if (methodWithGroupAnnotation == null) {
            return null;
        }

        String groupKey = (String) methodWithGroupAnnotation.invoke(executable);

        if (StringUtils.isBlank(groupKey)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid Group Key method in the rule. A group method cannot return a blank String. Rule: ''{0}'', Group Key Method: ''{1}''",
                    executable, methodWithGroupAnnotation.getName());
        }

        return groupKey;
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

    private Map<String, AbstractGroupRunner> createGroupRuleExecutors(List<Object> definedGroupDefinitions) throws Exception {

        int index = 0;
        LoggerUtil.info(RuleEngineExecuter.LOGGER, ++index, this, null, "Initializating groups...");

        Map<String, AbstractGroupRunner> groupExecuters = new HashMap<>();

        if (CollectionUtils.isNotEmpty(definedGroupDefinitions)) {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, ++index, this, null, "Registered groups:");

            registerGroups(definedGroupDefinitions, groupExecuters, ++index);
        }
        else {
            LoggerUtil.info(RuleEngineExecuter.LOGGER, ++index, this, null, "No group is registered...");
        }

        LoggerUtil.info(RuleEngineExecuter.LOGGER, --index, this, null, "Groups initialized...");

        return groupExecuters;
    }

    private void registerGroups(List<Object> definedGroupDefinitions, Map<String, AbstractGroupRunner> groupExecuters, int index)
            throws Exception {

        Map<String, String> groupParentKeys = new HashMap<>();

        populateGroups(definedGroupDefinitions, groupExecuters, groupParentKeys, index);

        validateGroupParentKey(groupParentKeys, groupExecuters);

        registerGroupTree(groupParentKeys, groupExecuters);
    }

    private void registerGroupTree(Map<String, String> groupParentKeys, Map<String, AbstractGroupRunner> groupExecuters) {

        for (Entry<String, AbstractGroupRunner> groupExecuterEntry : groupExecuters.entrySet()) {

            String groupKey = groupExecuterEntry.getKey();
            String groupParentKey = groupParentKeys.get(groupKey);
            AbstractGroupRunner groupRunner = groupExecuterEntry.getValue();

            AbstractGroupRunner parentGroupRunner = Objects.equals(GroupDefinition.DEFAULT_GROUP_PARENT_KEY, groupParentKey)
                    ? this.mainGroupRunner
                    : groupExecuters.get(groupParentKey);
            Validate.notNull(parentGroupRunner);

            parentGroupRunner.addExecuter(groupRunner);
        }
    }

    private void validateGroupParentKey(Map<String, String> groupParentKeys, Map<String, AbstractGroupRunner> groupExecuters) {

        if (groupExecuters != null) {
            for (Entry<String, String> parentKeyEntry : groupParentKeys.entrySet()) {
                String groupKey = parentKeyEntry.getKey();
                String groupParentKey = parentKeyEntry.getValue();

                if (!Objects.equals(GroupDefinition.DEFAULT_GROUP_PARENT_KEY, groupParentKey)
                        && !groupExecuters.containsKey(groupParentKey)) {
                    ExceptionUtil.throwIllegalArgumentException(
                            "Invalid Group Parent Definition. A group have a parent key defined that cannot be found inside the group keys. Parent Key ''{0}'', Group Key: ''{1}'', Group: ''{2}''",
                            groupParentKey, groupKey, groupExecuters.get(groupKey));

                }
            }
        }
    }

    private void populateGroups(List<Object> definedGroupDefinitions, Map<String, AbstractGroupRunner> groupExecuters,
            Map<String, String> groupParentKeys, int index) throws Exception {

        LoggerUtil.info(RuleEngineExecuter.LOGGER, index, this, null, "Creating Groups:");

        List<Object> groupDefinitions = ClassUtil.createInstances(definedGroupDefinitions);
        for (Object groupDefinition : groupDefinitions) {

            createGroupRunner(groupParentKeys, groupExecuters, groupDefinition, index + 1);
        }
    }

    private void createGroupRunner(Map<String, String> groupParentKeys, Map<String, AbstractGroupRunner> groupExecuters,
            Object groupDefinition, int index) throws Exception {

        Object groupDefinitionInstance = ClassUtil.createInstance(groupDefinition);

        GroupRunnerWithAnnotation groupRuleRunner = new GroupRunnerWithAnnotation(groupDefinition);

        String groupKey = resolveGroupKey(groupDefinitionInstance);
        String groupParentKey = resolveGroupParentKey(groupDefinitionInstance);

        validateGroupKey(groupDefinition, groupKey, groupExecuters);

        groupExecuters.put(groupKey, groupRuleRunner);
        groupParentKeys.put(groupKey, groupParentKey);

        LoggerUtil.info(RuleEngineExecuter.LOGGER, index, this, null,
                "Group. Name: ''{0}'', Group Instance: ''{1}'', Priority: ''{2}'', Group Parent Key: ''{3}'', Group Key: ''{4}''",
                groupDefinition, groupDefinitionInstance, groupRuleRunner.getPriority(), groupParentKey, groupKey);
    }

    private String resolveGroupParentKey(Object executerInstance) throws Exception {

        Method methodWithGroupParentKeyAnnotation = AnnotationUtil.resolveMethodWithAnnotation(executerInstance,
                GroupParentKey.class);
        if (methodWithGroupParentKeyAnnotation != null) {
            return (String) methodWithGroupParentKeyAnnotation.invoke(executerInstance);
        }

        GroupDefinition groupDefinition = AnnotationUtil.resolveAnnotation(executerInstance, GroupDefinition.class);
        Validate.notNull(groupDefinition);

        return groupDefinition.groupParentKey().getName();
    }

    private String resolveGroupKey(Object executerInstance) throws Exception {

        Method methodWithGroupKeyAnnotation = AnnotationUtil.resolveMethodWithAnnotation(executerInstance, GroupKey.class);

        return methodWithGroupKeyAnnotation != null
                ? (String) methodWithGroupKeyAnnotation.invoke(executerInstance)
                : executerInstance.getClass().getName();
    }

    private void validateGroupKey(Object groupDefinition, String groupKey, Map<String, AbstractGroupRunner> groupExecuters) {

        validateBlankGroupKey(groupDefinition, groupKey);
        validateDuplicateGroupKey(groupDefinition, groupKey, groupExecuters);
    }

    private void validateDuplicateGroupKey(Object groupDefinition, String groupKey,
            Map<String, AbstractGroupRunner> groupExecuters) {

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
}
