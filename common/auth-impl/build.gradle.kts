dependencies {
    api(project(":common:auth-api"))
    // Firebase Auth
    implementation(libs.firebase.auth)
    // Preferences datastore
    implementation(libs.datastore)
    //Play-service Coroutine extension
    implementation(libs.kotlinx.coroutines.play.services)
}