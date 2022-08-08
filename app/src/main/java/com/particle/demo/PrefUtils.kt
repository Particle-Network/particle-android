package com.particle.demo

import android.content.Context
import android.content.SharedPreferences

internal object SharedUtils {
    private lateinit var settings: SharedPreferences


    fun init(context: Context) {
        settings = context.getSharedPreferences("main", Context.MODE_PRIVATE)
    }


    operator fun contains(key: String?): Boolean {
        return settings.contains(key)
    }

    fun getSettingString(
        key: String?,
        defaultValue: String?
    ): String? {
        return settings.getString(key, defaultValue)
    }

    fun getSettingBoolean(
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    fun getSettingInt(key: String?, defaultValue: Int): Int {
        return settings.getInt(key, defaultValue)
    }

    fun getSettingLong(key: String?, defaultValue: Long): Long {
        return settings.getLong(key, defaultValue)
    }

    fun setSettingString(key: String?, value: String?): Boolean {
        val settingsEditor = settings.edit()
        settingsEditor.putString(key, value)
        return settingsEditor.commit()
    }

    fun getSettingFloat(key: String?, defaultValue: Float): Float {
        return settings.getFloat(key, defaultValue)
    }

    fun setSettingFloat(key: String?, value: Float): Boolean {
        val settingsEditor = settings.edit()
        settingsEditor.putFloat(key, value)
        return settingsEditor.commit()
    }

    fun setSettingBoolean(
        key: String?,
        value: Boolean
    ): Boolean {
        val settingsEditor = settings.edit()
        settingsEditor.putBoolean(key, value)
        return settingsEditor.commit()
    }

    fun setSettingInt(key: String?, value: Int): Boolean {
        val settingsEditor = settings.edit()
        settingsEditor.putInt(key, value)
        return settingsEditor.commit()
    }

    fun setSettingLong(key: String?, value: Long): Boolean {
        val settingsEditor = settings.edit()
        settingsEditor.putLong(key, value)
        return settingsEditor.commit()
    }

    fun clearAll() {
        settings.edit().clear().apply()
    }

    fun removeKey(key: String?) {
        settings.edit().remove(key).apply()
    }
}
