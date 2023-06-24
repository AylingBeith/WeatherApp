package com.windmill.atlantisweather;

public interface ICallback {

    /**
     * this method should pass a jsons tring to a implementation
     * @param jsonString
     */
    public void handleJSonString(String jsonString);

}
