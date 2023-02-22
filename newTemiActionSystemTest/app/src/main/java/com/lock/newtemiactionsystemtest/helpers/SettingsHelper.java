package com.lock.newtemiactionsystemtest.helpers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.lock.newtemiactionsystemtest.ContextGetter;
import com.lock.newtemiactionsystemtest.R;

public class SettingsHelper {

  private static SharedPreferences sharedData;
  private final Context context;

  //movement deafult settings
  private String defaultFallbackLocation;
  private int defaultSpeed;
  private boolean defaultBackwards;
  private int defaultTiltAngle;
  
  //speak message settings
  //may be a locale, check later
  private String defaultLanguage;
  
  //locker settings
  private String defaultUnlockCode;
    
  //teletransport settings
  private String robot_id;
  private String connection_URL;


  public SettingsHelper() {
    context = ContextGetter.getContext();
    sharedData = context.getSharedPreferences(context.getString(R.string.SHARED_SETTINGS), MODE_PRIVATE);
  }

  public SettingsHelper(Context context) {
    this.context = context;
    sharedData = context.getSharedPreferences(context.getString(R.string.SHARED_SETTINGS), MODE_PRIVATE);
  }

  public void applyChanges(){

    SharedPreferences.Editor editor = sharedData.edit();

    //movement settings ˅
    editor.putString("defaultFallbackLocation", defaultFallbackLocation);
    editor.putInt("defaultSpeed", defaultSpeed);
    editor.putBoolean("defaultBackwards", defaultBackwards);
    editor.putInt("defaultTiltAngle", defaultTiltAngle);
    //tts settings ˅
    editor.putString("defaultLanguage", defaultLanguage);
    //locker settings ˅
    editor.putString("defaultUnlockCode", defaultUnlockCode);
    //network settings˅
    editor.putString("robot_id", robot_id);
    editor.putString("connection_URL", connection_URL);

    editor.apply();

  }

  public void resetSettingsToDefault(){
    SharedPreferences.Editor editor = sharedData.edit();

    //movement settings ˅
    editor.putString("defaultFallbackLocation", context.getString(R.string.defaultFallbackLocation));
    editor.putInt("defaultSpeed", R.integer.defaultSpeed);
    editor.putBoolean("defaultBackwards", context.getResources().getBoolean(R.bool.defaultBackwards));
    editor.putInt("defaultTiltAngle", R.integer.defaultTiltAngle);
    //tts settings ˅
    editor.putString("defaultLanguage", context.getString(R.string.defaultLanguage));
    //locker settings ˅
    editor.putString("defaultUnlockCode", context.getString(R.string.defaultUnlockCode));
    //network ˅
    editor.putString("robot_id", context.getString(R.string.robot_id));
    editor.putString("connection_URL", context.getString(R.string.connection_URL));

    editor.apply();
  }


  public String getDefaultFallbackLocation() {
    return sharedData.getString("defaultFallbackLocation", context.getString(R.string.defaultFallbackLocation));
  }

  public void setDefaultFallbackLocation(String defaultFallbackLocation) {
    this.defaultFallbackLocation = defaultFallbackLocation;
  }

  public int getDefaultSpeed() {
    return sharedData.getInt("getDefaultSpeed", context.getResources().getInteger(R.integer.defaultSpeed));
  }

  public void setDefaultSpeed(int defaultSpeed) {
    this.defaultSpeed = defaultSpeed;
  }

  public boolean getIsDefaultBackwards() {
    return sharedData.getBoolean("defaultBackwards", context.getResources().getBoolean(R.bool.defaultBackwards));
  }

  public void setIsDefaultBackwards(boolean defaultBackwards) {
    this.defaultBackwards = defaultBackwards;
  }

  public int getDefaultTiltAngle() {
    return sharedData.getInt("defaultTiltAngle", context.getResources().getInteger(R.integer.defaultTiltAngle));
  }

  public void setDefaultTiltAngle(int defaultTiltAngle) {
    this.defaultTiltAngle = defaultTiltAngle;
  }

  public String getDefaultLanguage() {
    return sharedData.getString("defaultLanguage", context.getString(R.string.defaultLanguage));
  }

  public void setDefaultLanguage(String defaultLanguage) {
    this.defaultLanguage = defaultLanguage;
  }

  public String getDefaultUnlockCode() {
    return sharedData.getString("defaultUnlockCode", context.getString(R.string.defaultUnlockCode));
  }

  public void setDefaultUnlockCode(String defaultUnlockCode) {
    this.defaultUnlockCode = defaultUnlockCode;
  }

  public String getRobot_id() {
    return sharedData.getString("robot_id", context.getString(R.string.robot_id));
  }

  public void setRobot_id(String robot_id) {
    this.robot_id = robot_id;
  }

  public String getConnection_URL() {
    return sharedData.getString("connection_URL", context.getString(R.string.connection_URL));
  }

  public void setConnection_URL(String connection_URL) {
    this.connection_URL = connection_URL;
  }

  @Override
  public String toString() {
    return "SettingsHelper{" +
            "defaultFallbackLocation='" + getDefaultFallbackLocation() + '\'' +
            ", defaultSpeed=" + getDefaultSpeed() +
            ", defaultBackwards=" + getIsDefaultBackwards() +
            ", defaultTiltAngle=" + getDefaultTiltAngle() +
            ", defaultLanguage='" + getDefaultLanguage() + '\'' +
            ", defaultUnlockCode='" + getDefaultUnlockCode() + '\'' +
            ", robot_id='" + getRobot_id() + '\'' +
            ", connection_URL='" + getConnection_URL() + '\'' +
            '}';
  }
}
