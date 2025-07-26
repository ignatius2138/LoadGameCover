package com.example.practice.data

class GameCoverRepository: IGameCoverRepository {
    override suspend fun getImageUrl(text: String): String? {
        return ""
    }

}