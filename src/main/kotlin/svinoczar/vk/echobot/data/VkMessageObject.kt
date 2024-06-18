package svinoczar.vk.echobot.data

import com.fasterxml.jackson.annotation.JsonProperty

data class VkMessageObject(
        val text: String?,
        @JsonProperty("from_id")
        val sender: Int?
)
