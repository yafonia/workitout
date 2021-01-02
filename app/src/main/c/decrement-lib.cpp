//
// Created by Yafonia Hutabarat on 2021-01-02.
//
#include <jni.h>
#include <stdlib.h>
#include <time.h>

extern "C"
JNIEXPORT jint JNICALL
Java_id_ac_ui_cs_mobileprogramming_yafonia_workitout_CountdownFragment_decrement(JNIEnv *env, jobject thiz, jint num) {
    return num - 1;
}

