#include <stddef.h>
#include <stdint.h>
#include <jni.h>

#include <android/log.h>
#include <android/bitmap.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "schokoladenbrown", __VA_ARGS__)

static const char *fmt2str(int fmt) {
    switch (fmt) {
#define RETURN_STR(val) case val: return #val
        RETURN_STR(ANDROID_BITMAP_FORMAT_NONE);
        RETURN_STR(ANDROID_BITMAP_FORMAT_A_8);
        RETURN_STR(ANDROID_BITMAP_FORMAT_RGB_565);
        RETURN_STR(ANDROID_BITMAP_FORMAT_RGBA_4444);
        RETURN_STR(ANDROID_BITMAP_FORMAT_RGBA_8888);
#undef RETURN_STR
        default: return "<???>";
    }
}

static void probe(JNIEnv *env, jobject bitmap) {
    AndroidBitmapInfo bi = {0};
    int res = AndroidBitmap_getInfo(env, bitmap, &bi);
    LOGI("result: %d, width %d, height %d, stride %d, format %s",
         res, bi.width, bi.height, bi.stride, fmt2str(bi.format));
}

static jobject JNICALL native_rescale_impl(JNIEnv *env, jclass cls, jobject srcBitmap, jobject dstBitmap) {
    probe(env, srcBitmap);
    probe(env, dstBitmap);

    /*
     * TODO rescale src into dst
     */
}

static const char *g_rescaler_java_class = "com/schokoladenbrown/Smooth";

static const JNINativeMethod g_native_methods[] = {
    {"native_rescale",
        "(Landroid/graphics/Bitmap;Landroid/graphics/Bitmap;)V",
        native_rescale_impl}
};

jint JNI_OnLoad(JavaVM *jvm, void *reserved) {
    JNIEnv *env = NULL;
    jclass cls;

    (*jvm)->AttachCurrentThread(jvm, &env, NULL);
    cls = (*env)->FindClass(env, g_rescaler_java_class);
    (*env)->RegisterNatives(env, cls, g_native_methods, sizeof g_native_methods / sizeof g_native_methods[0]);
    return JNI_VERSION_1_2;
}