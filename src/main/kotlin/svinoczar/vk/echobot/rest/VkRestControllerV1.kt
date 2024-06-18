package svinoczar.vk.echobot.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import svinoczar.vk.echobot.data.VkRequest
import svinoczar.vk.echobot.exception.VkException
import svinoczar.vk.echobot.service.CallbackService


@RestController
@RequestMapping("/api/v1/vk")
class VkRestControllerV1 {
    @Value("\${vk.token.confirmation}")
    private lateinit var confirmationToken: String
    @Value("\${vk.token.access}")
    private lateinit var apiToken: String
    @Value("\${vk.rest.messages.send}")
    private lateinit var sendMessageUrl: String
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val callBackService = CallbackService()

    @PostMapping("/callback")
    fun callback(@RequestBody request: VkRequest): ResponseEntity<String> {
        try {
            return when (request.type) {
                "confirmation" -> {
                    log.info("Confirmation request")
                    ResponseEntity.ok(confirmationToken)
                }
                "message_new" -> {
                    log.info("Message_new request")
                    if (request.obj?.message?.text.isNullOrEmpty() || request.obj?.message?.sender!!.equals(null)) {
                        return ResponseEntity.badRequest().body("Message text or user ID was missing")
                    }
                    callBackService.sendMessage(request, sendMessageUrl, apiToken)
                    ResponseEntity.ok("OK")
                }
                else -> {
                    log.warn("INVALID METHOD")
                    ResponseEntity.badRequest().body("INVALID METHOD")
                }
            }
        } catch (e: VkException) {
            log.error("BAD REQUEST")
            e.printStackTrace()
            return ResponseEntity.badRequest().body("BAD REQUEST")
        }
    }
}