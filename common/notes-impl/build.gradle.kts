@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.ksp.get().pluginId).version(libs.plugins.ksp.get().version.requiredVersion)
}

dependencies {
    api(project(":common:auth-api"))
    api(project(":common:notes-api"))
    // Firebase FireStore
    implementation(libs.firebase.firestore)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}