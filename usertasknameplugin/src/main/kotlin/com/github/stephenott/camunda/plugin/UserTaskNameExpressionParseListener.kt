package com.github.stephenott.camunda.plugin

import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl
import org.camunda.bpm.engine.impl.util.xml.Element

/**
 * A Parse Listener for defining a User Task Name expression in a extension property rather than in the Name field.
 *
 * If multiple matches matches are found for the expression keys then the first result will be used.
 *
 * @param taskNameExpressionKeys list of extension property keys that can be used in extension properties of a user task
 * to define the task name expression.
 */
class UserTaskNameExpressionParseListener(
    val taskNameExpressionKeys: List<String> = listOf("name_exp", "name_expression")
): AbstractBpmnParseListener() {

    override fun parseUserTask(userTaskElement: Element, scope: ScopeImpl, activity: ActivityImpl) {
        getExtensionPropertyValue(taskNameExpressionKeys, userTaskElement)?.let {
            val behavior = (activity.activityBehavior as UserTaskActivityBehavior)
            behavior.taskDefinition.nameExpression = behavior.expressionManager.createExpression(it)
        }
    }

    /**
     * Get the extension property value based on a list of possible matching property names.
     * If multiple matches, then first result is returned.
     * If no match, then returns null.
     */
    private fun getExtensionPropertyValue(propertyNames: List<String>, element: Element): String?{
        return element.element("extensionElements")
            ?.element("properties")
            ?.elements("property")
            ?.firstOrNull { it.attribute("name") in propertyNames }
            ?.attribute("value")
    }
}