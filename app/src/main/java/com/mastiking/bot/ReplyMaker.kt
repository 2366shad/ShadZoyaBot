package com.mastiking.bot

object ReplyMaker {
    private val replies = listOf(
        "Bhai itna serious mat ho, zindagi already loading par hai 😂",
        "Wah ji wah! Aaj toh dimaag ne Wi-Fi pakad liya 😎",
        "Is baat par toh chai aur samose bante hain ☕😂",
        "Thoda muskura do, free hai aur GST bhi nahi lagta 😁"
    )

    fun reply(message: String): String {
        val m = message.lowercase()
        return when {
            m.isBlank() -> "Pehle Messenger mein koi message kholo 😄"
            listOf("😂", "🤣", "😆").any { m.contains(it) } -> "Itna has kyun rahe ho? Humein bhi joke suna do 🤣"
            listOf("❤️", "😍", "😘", "❤").any { m.contains(it) } -> "Dil sambhal ke, yahan funny log rehte hain ❤️😂"
            listOf("😡", "🤬", "gussa", "naraz").any { m.contains(it) } -> "Gussa thook do boss, warna chai thandi ho jayegi 😜"
            listOf("hi", "hello", "salam", "assalam").any { m.contains(it) } -> "Oye hoye! Masti ki mehfil mein aapka swagat hai 😎"
            listOf("kya haal", "kaise ho", "kaisa hai").any { m.contains(it) } -> "Mast hain, bas biryani ki kami hai 😂"
            listOf("good morning", "subah").any { m.contains(it) } -> "Good morning! Utho janab, suraj attendance laga gaya ☀️"
            listOf("bye", "allah hafiz", "good night").any { m.contains(it) } -> "Jaao jaao, par kal phir masti karne aana 😂"
            else -> replies[(message.hashCode().ushr(1) % replies.size)]
        }
    }
}
