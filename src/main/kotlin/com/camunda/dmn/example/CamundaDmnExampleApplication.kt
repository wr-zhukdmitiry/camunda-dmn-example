package com.camunda.dmn.example

import java.util.UUID
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@SpringBootApplication
class CamundaDmnExampleApplication

fun main(args: Array<String>) {
    runApplication<CamundaDmnExampleApplication>(*args)
}

@RestController
@RequestMapping("dmn")
class Endpoints(val camundaService: CamundaService) {

    @GetMapping("{number}")
    fun makeDecision(@PathVariable number: Int): ResponseEntity<MutableList<MutableMap<String, Any>>> {
        return ResponseEntity.ok(camundaService.evaluateDmn(number))
    }

    @PostMapping("upload")
    fun uploadDmn(@RequestParam("file") file: MultipartFile) {
        camundaService.uploadDmn(file.originalFilename!!, UUID.randomUUID().toString(), file.inputStream)
    }
}