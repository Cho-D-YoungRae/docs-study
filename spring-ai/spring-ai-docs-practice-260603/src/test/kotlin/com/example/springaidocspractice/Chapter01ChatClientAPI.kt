package com.example.springaidocspractice

import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.AdvisorParams
import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference


@SpringBootTest
class Chapter01ChatClientAPI {

    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @Test
    fun `Creating chat client`() {
        val chatClient = chatClientBuilder.build()

        val content = chatClient.prompt()
            .user("Hello")
            .call()
            .content()

        println(content)
    }

    @Test
    fun `Returning a ChatResponse`() {
        val chatClient = chatClientBuilder.build()

        val chatResponse = chatClient.prompt()
            .user("Tell me a joke")
            .call()
            .chatResponse()

        println(chatResponse)
    }

    data class ActorFilms(val actor: String, val movies: List<String>)

    @Test
    fun `Returning an Entity`() {
        val chatClient = chatClientBuilder.build()

        val actorFilms = chatClient.prompt()
            .user("Generate the filmography for a random actor.")
            .call()
            .entity(ActorFilms::class.java)

        println(actorFilms)

        val actorFilmsList = chatClient.prompt()
            .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
            .call()
            .entity(object : ParameterizedTypeReference<List<ActorFilms>>() {})

        println(actorFilmsList)

        val actorFilmNative = chatClient.prompt()
            .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
            .user("Generate the filmography for a random actor.")
            .call()
            .entity(ActorFilms::class.java)
        println(actorFilmNative)
    }
}