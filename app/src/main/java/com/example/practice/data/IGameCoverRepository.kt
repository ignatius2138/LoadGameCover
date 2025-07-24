package com.example.practice.data

interface IGameCoverRepository {
    fun getImageUrl(text: String): String
}