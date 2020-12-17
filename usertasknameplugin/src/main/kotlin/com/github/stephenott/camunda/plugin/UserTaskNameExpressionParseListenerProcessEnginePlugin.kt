package com.github.stephenott.camunda.plugin

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl

open class UserTaskNameExpressionParseListenerProcessEnginePlugin(
    var extensionKeys: String = "name_exp,name_expression"
): AbstractProcessEnginePlugin() {
    override fun preInit(processEngineConfiguration: ProcessEngineConfigurationImpl) {
        if (processEngineConfiguration.customPreBPMNParseListeners == null){
            processEngineConfiguration.customPreBPMNParseListeners = mutableListOf()
        }
        processEngineConfiguration.customPreBPMNParseListeners.add(UserTaskNameExpressionParseListener(extensionKeys.split(",")))
    }
}