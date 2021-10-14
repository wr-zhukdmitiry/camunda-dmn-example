package com.camunda.dmn.example

import org.camunda.bpm.dmn.engine.DmnDecision
import org.camunda.bpm.dmn.engine.DmnEngine
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader


@Configuration
class CamundaDmnExampleConfiguration {

    @Bean
    fun dmnEngine(): DmnEngine {
        return DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine()
    }

    @Bean
    fun dmnDecision(dmnEngine: DmnEngine, resourceLoader: ResourceLoader): DmnDecision {
        val resource = resourceLoader.getResource("classpath:dmn/dmn-example.dmn")
        return dmnEngine.parseDecisions(resource.inputStream)[0]
    }
}