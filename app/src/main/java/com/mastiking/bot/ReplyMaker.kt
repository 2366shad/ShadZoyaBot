package com.mastiking.bot

object ReplyMaker {

    private val funny = listOf(
        "Shad ke group mein aaye ho, hasi free milegi 😂",
        "Shad se panga mat lena, ye dil ka bhi king hai 😎",
        "Shad abhi joke sunayega 🤣"
    )

    private val romance = listOf(
        "Shad ke dil mein mohabbat ki kami nahi ❤️",
        "Shad ki muskurahat kisi khaas ke naam hai 🥰",
        "Shad ki duniya mein mohabbat hi mohabbat hai 🌹"
    )

    private val sad = listOf(
        "Shad hasta zaroor hai, par dard bhi rakhta hai 💔",
        "Kuch yaadein Shad kabhi bhula nahi pata 😔"
    )

    private val flirt = listOf(
        "Shad se baat karke dekho, muskurana aadat ban jayegi 😉",
        "Shad keh raha hai, itni pyari smile ka raaz kya hai? 😍"
    )

    fun reply(message: String): String {
        val m = message.lowercase().trim()

        return when {
            m.isBlank() -> "Koi message toh bhejo 😄"

            listOf("assalamualaikum", "assalamu alaikum", "assalam o alaikum", "assalam", "salam").any { m.contains(it) } ->
                "Wa Alaikum Assalam Wa Rahmatullahi Wa Barakatuh ❤️🤲"

            listOf("allah hafiz", "khuda hafiz").any { m.contains(it) } ->
                "Allah Hafiz! Allah aapki hifazat kare 🤲❤️"

            listOf("sad", "udaas", "dard", "bewafa", "breakup", "dhoka").any { m.contains(it) } ->
                sad[message.hashCode().ushr(1) % sad.size]

            listOf("flirt", "flirt karo", "patana").any { m.contains(it) } ->
                flirt[message.hashCode().ushr(1) % flirt.size]

            listOf("love", "mohabbat", "romance", "i love you", "pyar").any { m.contains(it) } ->
                romance[message.hashCode().ushr(1) % romance.size]

            listOf("😂", "🤣", "😆", "joke", "funny").any { m.contains(it) } ->
                funny[message.hashCode().ushr(1) % funny.size]

            m == "hi" || m == "hello" || m == "hey" ->
                "Hello ji! Shad ki Masti Mehfil mein swagat hai 😎"

            else -> funny[message.hashCode().ushr(1) % funny.size]
        }
    }
}
