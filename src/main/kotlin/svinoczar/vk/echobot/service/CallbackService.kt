package svinoczar.vk.echobot.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import svinoczar.vk.echobot.data.VkRequest
import svinoczar.vk.echobot.exception.VkException
import kotlin.random.Random

@Service
class CallbackService{
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun sendMessage(message: VkRequest, sendMessageUrl: String, apiToken: String) {
        val randomId = Random.nextInt()
        val restTemplate = RestTemplate()
        try {
            restTemplate.getForObject(UriComponentsBuilder.fromHttpUrl(sendMessageUrl)
                    .queryParam("user_id", message.obj?.message?.sender)
                    .queryParam("message", "Вы сказали: " + message.obj?.message?.text)
                    .queryParam("random_id", randomId)
                    .queryParam("access_token", apiToken)
                    .queryParam("v", message.v)
                    .build().toUri(), String::class.java)
            log.info("Message sent successfully")
        } catch (e: VkException) {
            e.printStackTrace()
        }
    }
}