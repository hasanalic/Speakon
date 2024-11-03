# BTK Akademi HACKATHON 2024 | Better Takımı - Speakon Mobil Uygulaması (Hasan Ali Çalışkan - Abdurrahim Çalışkan)

## Youtube -> https://www.youtube.com/watch?v=R1WCgkqn2Uo

İngilizce konuşma becerilerini geliştirmek isteyenler için özel olarak tasarlanmış bu uygulama, kullanıcılarına her gün belirlenen bir konuda kendilerini İngilizce ifade etme fırsatı sunuyor. Her gün tüm kullanıcıların erişebileceği yeni bir konuşma konusu belirleniyor. Örneğin, "Why do people like watching movies?" gibi bir konu, 24 saat boyunca geçerli oluyor. Bu sayede kullanıcılar aynı konuda kendi düşüncelerini ifade etme ve başkalarının fikirlerini dinleme olanağına sahip oluyorlar.

Uygulamanın temel amacı, kullanıcıların konuşma yeteneklerini pratik yaparak geliştirmesi ve dil bilgisi, akıcılık, ve tutarlılık gibi becerilerini geliştirmesine yardımcı olmaktır. Kullanıcılar, o günkü konu hakkında düşüncelerini sesli olarak ifade edebilirler. Ardından, Gemini, konuşmaları analiz ederek, dil bilgisi ve akıcılık açısından düzeltir ve kullanıcıya geribildirim verir. Gemini, kullanıcıların konuşmalarını puanlayarak gelişimlerini daha objektif bir şekilde takip etmelerine yardımcı olur. Bu puanlama sistemi sayesinde kullanıcılar kendi ilerlemelerini ölçebilir ve motivasyonlarını yüksek tutabilir.

Uygulamanın sürdürülebilirlik hedefleri doğrultusunda, topluluk etkileşimini de teşvik ediyor. Kullanıcılar, diğer kullanıcıların gönderilerini görebilir, yapay zekanın yaptığı düzeltmeleri inceleyerek öğrenebilir ve farklı bakış açıları kazanabilir. Bu etkileşim, kullanıcıların dil pratiği yapmalarına da yardımcı olur.

Bu uygulama, öğrenme sürecini yalnızca bireysel bir çalışma değil, aynı zamanda topluluk desteği ile güçlendirilmiş bir deneyime dönüştürmeyi hedeflemektedir.

Mobil uygulama Kotlin kullanılarak MVVM mimarisi ile geliştirilmiştir.

## Libraries
* [Foundation]
  * [Android KTX](https://developer.android.com/kotlin/ktx) - It is used to make the application more readable and easier to use.
  * [AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat) - It is a library that ensures Android applications are compatible with material design.
* [Architecture]
  * [Firebase Authentication](https://firebase.google.com/docs/auth) - Firebase Authentication provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users to your app.
  * [Firebase Authentication](https://firebase.google.com/docs/firestore) - Cloud Firestore is a flexible, scalable database for mobile, web, and server development from Firebase and Google Cloud.
  * [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - It is used to facilitate lifecycle management of activities and fragments.
  * [Flow](https://developer.android.com/kotlin/flow?hl=en) - Flows are built on top of coroutines and can provide multiple values.
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - It is used for storing data and sharing data between UI components.
* [UI]
  * [Jetpack Compose](https://developer.android.com/compose) - Jetpack Compose is Android’s recommended modern toolkit for building native UI.
  * [Navigation Compose](https://developer.android.com/develop/ui/compose/navigation?hl=en) - You can navigate between composables while taking advantage of the Navigation component's infrastructure and features.
* [Third Party]
  * [Gemini](https://ai.google.dev/) - Build with state-of-the-art generative AI models and tools to make AI helpful for everyone
  * [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - It is used for asynchronous operations.
  * [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - It is used to perform Dependency Injection.
  * [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences) - It is used to save small data.
  * [Material Components](https://material.io/develop/android) - It is used for designing modern UI components.
