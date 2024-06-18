package svinoczar.vk.echobot

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import svinoczar.vk.echobot.data.VkRequest
import kotlin.random.Random

@RestController
@RequestMapping("/api/v1/vk")
class VkRestController {
    @Value("\${vk.token.confirmation}")
    private lateinit var confirmationToken: String
    @Value("\${vk.token.api}")
    private lateinit var apiToken: String
    @Value("\${vk.rest.messages.send}")
    private lateinit var sendMessageUrl: String

    @PostMapping("/callback")
    fun callback(@RequestBody request: VkRequest): ResponseEntity<String> {
        return when (request.type){
            "confirmation" -> ResponseEntity.ok(confirmationToken)
            "message_new" -> {
                if (request.obj?.message?.text.isNullOrEmpty() || request.obj?.message?.sender.toString().isNullOrEmpty()){
                    return ResponseEntity.badRequest().body("Message text or user ID was missing.")
                }
                sendMessage(request)
                ResponseEntity.ok("OK.")
            }
            else -> {ResponseEntity.badRequest().body("INVALID METHOD.")}
        }
    }

    fun sendMessage(message: VkRequest) {
        val randomId = Random.nextInt()
        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject(UriComponentsBuilder.fromHttpUrl(sendMessageUrl)
                .queryParam("user_id", message.obj?.message?.sender)
                .queryParam("message", "Вы сказали: " + message.obj?.message?.text)
                .queryParam("random_id", randomId)
                .queryParam("access_token", apiToken)
                .queryParam("v", message.v)
                .build().toUri(), String::class.java)
        println(response)
    }
}