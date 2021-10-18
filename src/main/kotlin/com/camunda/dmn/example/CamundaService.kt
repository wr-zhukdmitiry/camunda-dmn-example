package com.camunda.dmn.example

import java.io.InputStream
import java.util.Map
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.model.dmn.Dmn

class CamundaService(private val processEngine: ProcessEngine) {

    fun uploadDmn(fileName: String, name: String, file: InputStream) {
        val model = Dmn.readModelFromStream(file)
        Dmn.validateModel(model)
        processEngine.repositoryService.createDeployment()
            .addModelInstance(fileName, model)
            .name(name)
            .deploy()
    }

    fun evaluateDmn(number: Int): MutableList<MutableMap<String, Any>> {
        val decisionDefinition = processEngine.repositoryService.createDecisionDefinitionQuery()
            .includeDecisionDefinitionsWithoutTenantId()
            .latestVersion().singleResult()

        val variables = Map.of<String, Any>("inputVariable", number)

        return processEngine.decisionService.evaluateDecisionTableById(
            decisionDefinition.id,
            variables
        ).resultList
    }
}