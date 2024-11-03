package com.better.betterapp.feature_onboarding

import androidx.annotation.DrawableRes
import com.better.betterapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.onboarding_one,
        title = "Hoş Geldiniz! Hedefinize Bir Adım Daha Yaklaşın.",
        description = "İngilizce konuşma becerilerinizi geliştirmek için doğru yerdesiniz! Her gün yeni konularla pratik yaparak kendinizi ifade etme yeteneğinizi artırın. Hadi başlayalım!"
    )

    object Second : OnBoardingPage(
        image = R.drawable.onboarding_two,
        title = "Her Gün Yeni Bir Konu!",
        description = "Her gün sizin için belirlenen bir konu ile düşüncelerinizi sesli olarak ifade etme şansı yakalayın. Bugünün konusunu keşfedin ve kendinizi geliştirin!"
    )

    object Third : OnBoardingPage(
        image = R.drawable.onboarding_three,
        title = "Akıllı Geribildirim ile Gelişin!",
        description = "Yaptığınız her konuşma, yapay zeka tarafından analiz edilir. Dil bilgisi, akıcılık ve tutarlılık gibi önemli yönlerde alacağınız puanlarla ilerlemenizi takip edin ve daha iyi hale gelin!"
    )
}