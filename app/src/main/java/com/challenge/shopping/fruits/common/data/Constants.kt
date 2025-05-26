package com.challenge.shopping.fruits.common.data

object AppConstants {
    // API URLs
    const val FRUITS_API_BASE_URL = "https://www.fruityvice.com/api/"
    const val AI_IMAGE_GENERATION_API_BASE_URL = "https://api.cloudflare.com/client/v4/"

    // API DATA
    const val AI_MODEL_NAME = "@cf/bytedance/stable-diffusion-xl-lightning"
    const val API_TOKEN = "FTZRFoBZtgEBiXvpWkhkYeqhKKDcJRB3VzWNht03"

    // Mock API URLs
    const val MOCK_FRUITS_API_BASE_URL = "http://10.0.2.2:3000/"
    const val MOCK_AI_IMAGE_GENERATION_API_BASE_URL = "http://10.0.2.2:3000/"

    // Banco de dados
    const val FRUITS_DATABASE_NAME = "fruits.db"
}