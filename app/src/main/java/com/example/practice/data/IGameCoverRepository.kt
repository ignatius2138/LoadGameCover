package com.example.practice.data

interface IGameCoverRepository {
    suspend fun getImageUrl(text: String): String?
}