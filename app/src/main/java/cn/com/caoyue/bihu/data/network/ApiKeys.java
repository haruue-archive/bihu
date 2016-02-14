package cn.com.caoyue.bihu.data.network;

public class ApiKeys {

    static {
        System.loadLibrary("JniApiKeys");
    }

    public static final String HARUUE_KNOW_WEB_APIKEY = getHaruueKnowWebApikey();
    public static final String HARUUE_STORAGE_SERVER_2_APIKEY = getHaruueStorageServer2Apikey();

    public static native String getHaruueKnowWebApikey();
    public static native String getHaruueStorageServer2Apikey();

}
