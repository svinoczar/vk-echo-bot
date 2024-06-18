package svinoczar.vk.echobot

import com.fasterxml.jackson.annotation.JsonProperty

data class VkMessage(
        val type: String,
        val eventId: Int,
        val v: String,
        @JsonProperty("object")
        val obj: VkMessageObject,
        @JsonProperty("group_id")
        val groupId: Int
)