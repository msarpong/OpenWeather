object Versions {

    const val kotlin = "1.3.70"
    const val koin = "2.1.3"
    const val lifecycle_version = "2.2.0-rc03"
}

@Suppress("unused")
object Libs {
    const val kotlinLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val androidConstraint = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val androidCore = "androidx.core:core-ktx:1.2.0"

    //    View Model
    const val lifeCycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.1.0"
    const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:2.2.0"
    const val lifeCycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"

    const val materialDesign = "com.google.android.material:material:1.2.0-alpha05"

    //    Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:2.6.2"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:2.6.2"

    //    okhttp
    const val okttp3 = "com.squareup.okhttp3:okhttp:4.2.2"
    const val okttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:4.2.2"

    //    threetenabp
    const val dateThreetenAdp = "com.jakewharton.threetenabp:threetenabp:1.2.2"

    //    swipe
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"

    //    koin
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"

}

object TestLibs {
    const val junit = "junit:junit:4.12"
    const val junitAndroid = "androidx.test.ext:junit:1.1.1"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
}