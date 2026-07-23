# Add project specific ProGuard rules here.
# For more details, see http://developer.android.com/guide/developing/tools/proguard.html
#
# Room, Retrofit, OkHttp, Gson, Coroutines, Navigation and Coil all ship
# their own R8 consumer rules bundled in their AARs, so this file only needs
# to cover DivineIQ's own code plus a couple of well-known reflection edge
# cases that consumer rules can't infer on their own.

# Keep line numbers for readable release stack traces; hide the source file
# name so a mapping file is still required to deobfuscate them.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# --- Networking DTOs & domain models -----------------------------------
# Gson uses reflection to populate these at runtime, so field names must
# survive obfuscation.
-keep class com.divineiq.app.network.dto.** { *; }
-keep class com.divineiq.app.model.** { *; }
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# --- Room entities --------------------------------------------------------
-keep class com.divineiq.app.data.local.entity.** { *; }

# --- Retrofit / OkHttp (defensive; consumer rules normally suffice) ------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

# --- Kotlin coroutines debug metadata (safe to drop) ---------------------
-dontwarn kotlinx.coroutines.debug.internal.**
