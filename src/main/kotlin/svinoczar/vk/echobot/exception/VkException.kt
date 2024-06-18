package svinoczar.vk.echobot.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class VkException(message: String) : RuntimeException(message) {

}