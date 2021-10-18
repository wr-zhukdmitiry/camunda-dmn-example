package com.camunda.dmn.example

import org.camunda.bpm.dmn.engine.DmnEngine
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.model.dmn.Dmn
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class CamundaDmnExampleConfiguration {

    @Bean
    fun dmnEngine(
        resourceLoader: ResourceLoader,
        processEngine: ProcessEngine
    ): DmnEngine {
        val dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine()
        val count = processEngine.repositoryService.createDeploymentQuery().count()
        if (count < 1) {
            val resource = resourceLoader.getResource("classpath:dmn/dmn-example.dmn")
            val model = Dmn.readModelFromFile(resource.file)
            Dmn.validateModel(model)
            processEngine.repositoryService.createDeployment()
                .addModelInstance("dmn-example.dmn", model)
                .name("dmn-example")
                .deploy()
        }
        return dmnEngine
    }

    @Bean
    fun camundaService(processEngine: ProcessEngine): CamundaService {
        return CamundaService(processEngine)
    }
}