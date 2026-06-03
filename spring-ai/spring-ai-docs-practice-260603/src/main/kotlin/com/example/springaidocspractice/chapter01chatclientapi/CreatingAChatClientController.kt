package com.example.springaidocspractice.chapter01chatclientapi

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("chapter01")
class CreatingAChatClientController(val chatClientBuilder: ChatClient.Builder) {

    private val chatClient = chatClientBuilder.build()

    @GetMapping("/ai")
    fun generation(userInput: String) = chatClient.prompt()
        .user(userInput)
        .call()
        .content()
}