package com.camunda.dmn.example

import java.util.Map
import org.camunda.bpm.dmn.engine.DmnDecision
import org.camunda.bpm.dmn.engine.DmnDecisionResultEntries
import org.camunda.bpm.dmn.engine.DmnEngine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class CamundaDmnExampleApplication

fun main(args: Array<String>) {
    runApplication<CamundaDmnExampleApplication>(*args)
}

@RestController
@RequestMapping("test")
class Endpoints(val dmnDecision: DmnDecision, val dmnEngine: DmnEngine) {

    @GetMapping("{number}")
    fun makeDecision(@PathVariable number: Int): ResponseEntity<DmnDecisionResultEntries> {
        val variables = Map.of<String, Any>("inputVariable", number)
        return ResponseEntity.ok(dmnEngine.evaluateDecision(dmnDecision, variables).firstResult)
    }
}