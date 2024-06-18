package svinoczar.vk.echobot

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import kotlin.random.Random

@RestController
@RequestMapping("/api/v1/vk")
class VkRestController {
    @Value("\${vk.token.confirmation}")
    private lateinit var confirmationToken: String
    @Value("\${vk.token.api}")
    private lateinit var apiToken: String
    private val sendMessageUrl: String = "https://api.vk.com/method/messages.send?"

    @PostMapping("/callback")
    fun callback(@RequestBody message: VkMessage): ResponseEntity<String> {
        return when (message.type){
            "confirmation" -> ResponseEntity.ok(confirmationToken)
            "new_message" -> {
                sendMessage(message)
                ResponseEntity.ok("Ok")
            }
            else -> {ResponseEntity.ok("BAD_REQUEST")}
        }
    }

    fun sendMessage(message: VkMessage) {
        val randomId = Random.nextInt()
        val restTemplate = RestTemplate()
//        try {
            val response = restTemplate.getForObject(UriComponentsBuilder.fromHttpUrl(sendMessageUrl)
                    .queryParam("user_id", message.obj.sender)
                    .queryParam("message", "Вы сказали: " + message.obj.text)
                    .queryParam("random_id", randomId)
                    .queryParam("access_token", apiToken)
                    .queryParam("v", message.v)
                    .build().toUri(), String::class.java)
        println(response)
//        } catch (e: Exception) {
//            //
//        }
    }
}