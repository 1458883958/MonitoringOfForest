package com.wdl.monitoringofforest.alipay;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.alipay
 * 创建者：   wdl
 * 创建时间： 2018/9/6 15:34
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface AlipayConstance {
    /**
     * 支付宝支付业务：入参app_id
     */
    String APPID = "2018050302630873";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    String RSA_PRIVATE = "";
    String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDFn30WF18sReT5\n" +
            "HIkyYJoBtC7s8Wy/X145opIu/uSFNx6SG9qC16GbW6eR/xPWGslt24M9fr61vCwb\n" +
            "XS4U5LQfXhXDyo0qezNmZ4pzfDsLXZnzBTjgYiMW9iyjRAOXq45LpaaKMHfvEBXI\n" +
            "YyaCYVUARSbvvaCvE/NpLkAyjos1it6JCN5PDD7YVd5RGOq8pVaeqPVwFE5VJJSv\n" +
            "HfqFQvJa9o6UWqZw3FS0uEPvyghv555ImnQjwi7Q6QNxDZDYEvdbEBpxqY+PJJtH\n" +
            "9q9htMFeAMw2bwytFvRGhYeA57gdoJbKSlAszazL2rVn+3l0ZL2lLuR+crhw2Mye\n" +
            "DCkPpz59AgMBAAECggEAOcOIeHCUbQhxCS+CGquJ2H2mSbO3qahijk4qlHoEqbuW\n" +
            "v/ZMRHpqzlJYBKCeV7K0tdu33L2zWMOL0OPHmGvRCmohynzmDki8mW4iVec2Cvht\n" +
            "C0+3dD67KvcBzScQNcD7crkUmMfaBpKZC12P4AQ7KD0OSH0Q/pvCRjgyo5RutFNC\n" +
            "tk6K4El1GmBBXSTIs9/8yUK8zr+PDaEm1Xi5ELsGO/oIEeaAfaYwYmdg24VIumTb\n" +
            "YUUT5tBewJhKckdKnh/yuoeTip8FzFxT+zAy3sADUnghxNpsm8UfaoUpm3/GPA3J\n" +
            "yuXEtiIhzRt2hZl/4qV7syOebYCR3oDaPJhHghUXAQKBgQD7JQRWNfUZIl/2mE2G\n" +
            "gOEkrLiNxFmJ1yt+QB6MED/GKNUSm3eOSI8P57kZCsJSomKPD5VY9e7aY27HU9XC\n" +
            "zO2e+YxW5AQRwFGAxTZEnDjS/LE+vw4+7695K35usYktYZgSq6PP3TFwtGoNnLXn\n" +
            "jnDRcLSf1S303JHe4N0HZ+/irQKBgQDJcZQm0vOwANWDr/IRoqjezfg8Pqy88F2h\n" +
            "wgVHGBreDoaRkbyFT+4kxdY68jSAubhMxoLs8O+Ilm/77suLeNs+eaVcI+4UL/qa\n" +
            "VIKKicny8bPHc+ajNpDCeVDq76NesG7b/fD4bXRAKhRJ6VBLcQbQj1yUS6CcCMZA\n" +
            "JgfkvMUVEQKBgQD66SzABQBusA04DUnHC5PkVDu+R2E9eZ+WbvEV9lYA4GKgSHrV\n" +
            "yGBF/7jJ8Bnc/SeCzorjajHMNnNC6dsy2BK/NYqkdQIryfyjrmRdf8a15IggrDjL\n" +
            "YCPr5yxV40Gt+Uc5wAnVbqQqedVK7DpAseq9FOEgnpdff86s4fNxpsHfsQKBgQCV\n" +
            "kjyzuwxvkZ73oWt2hrUmgf6gFNVDkKGlGp+6kv5YBKcMbQ5yWc/0A+U2bWJodf2P\n" +
            "HBJYhekFHNZXVI5Tp8/6im4qhQ8gdCN59efbn8WQGsEnBjqo0zoW++cGGhrOkugt\n" +
            "dlc2zmZzwgrAenlBGtI5h0jxX1oIoUc6mEJiJ3uaUQKBgQCm2j+0vvez2ylV7IGu\n" +
            "WjvlLLnz9EP3l+m0gqhOWvfAh8PA5lv3utCYAhWiaKWMmHtr8nXyoM3pTE5ml08e\n" +
            "RkS8pCUzYbf5Zj8gKmpAYVkyaaLa32A+rFdqPgMuvk7TDF34A4DCSyqEFBNbySwQ\n" +
            "rJigpOY4zTKPPrNW9nyIUbGikQ==";
}
