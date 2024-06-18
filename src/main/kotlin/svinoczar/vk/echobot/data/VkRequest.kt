package svinoczar.vk.echobot.data

import com.fasterxml.jackson.annotation.JsonProperty

data class VkRequest(
        val type: String,
        val eventId: Int?,
        val v: String?,
        @JsonProperty("object")
        val obj: VkRequestObject?,
        @JsonProperty("group_id")
        val groupId: Int
)