package XQBHClient.FreeTest;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class entrypt {
    //    public static void main(String[] args) {
//        String publicKeystr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhGq+VmOPBeSJ1BUHbnS1iiPVdrXsmqXg17qIiV/7dcff/OwbsXbbvz77yN50iZNNeiCG4Rj/TK5oTQgTjbLikAfEtPLROGHh5mH3eyefHYtuIETUXGstXm6+xMFHFvuH91yjOoVH0C55f6JpLhcy37/p3WzWGbvWyEVoYWs6VBt8bWYd/rqHadHpk+9II+/qe1S6I0a3qGdtOMpSucjSSo5EZd6ByVb5V5Vv3YEcF2NISc3gue0O2InhidEA5LfMNaMm+ZVmo6d+RaApnL8B8bcvZpn51l7d6lEZ/GbSaPeZGMQq5xZvdo3SFa92GmdX0OmnVS7CVGoHdLOnpkIerwIDAQAB";
//        String privateKeystr = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCEar5WY48F5InUFQdudLWKI9V2teyapeDXuoiJX/t1x9/87Buxdtu/PvvI3nSJk016IIbhGP9MrmhNCBONsuKQB8S08tE4YeHmYfd7J58di24gRNRcay1ebr7EwUcW+4f3XKM6hUfQLnl/omkuFzLfv+ndbNYZu9bIRWhhazpUG3xtZh3+uodp0emT70gj7+p7VLojRreoZ204ylK5yNJKjkRl3oHJVvlXlW/dgRwXY0hJzeC57Q7YieGJ0QDkt8w1oyb5lWajp35FoCmcvwHxty9mmfnWXt3qURn8ZtJo95kYxCrnFm92jdIVr3YaZ1fQ6adVLsJUagd0s6emQh6vAgMBAAECggEAb0hZ/7YZy4T+RAsMPMq+ioKE8gf/+ROwuvwbpP/SD3DTj/ZJa8IM+VOQPIaff8MYmKtfTys32xSzuRExhaMxfoPYz41FQVIZjAkG+CwbL7Qu79WIdsbn0PXXQvl/qhPnd34V+6do688y8o3mQQLkEWByxVCjOes/nP2ftEduNKHIWCRASCtQJ5pGcgEOp3BipEXRzBPklVAbUUYTNTDXKzJxrubH90HTXjOuKXORDBk4UTiTI8cWdAg0KDxnpagAKBC9dmAloXajDzysfbgX1bhq9LlFqR10+A4YkzpTAbL0V+Xh7ZCTl25hhy+GaKpd/RSBF+ERb6o0XgVR9Elc0QKBgQDwReY1zT9ClDkYhRmGj/O95pWo/7Opkl5+p0y/NUL/pChHJ4J1GaiatXQTmFkUGa7J6CO9NTZ3zdJ7ZcAm95GyI7LXaaIfm35ZdN6fYiPSu7sys1amW7f3anF6GHKzRQRYUZgbhwPaGvS1HFUfnYDjl21CqtSbgW9BWmBwikosGQKBgQCNFZHDRtAxEkB4nSCwU68U1yfHEqz0mZSZDn0HugfeX0aiVe13/G0FHdsxI/Anou141/i8Qrb19Xn5aqTHwJNmvTlbXictbEmacHWOGoNvXSznkdG9fWE7BY4yguTLxLndD0fdjQasp1tKMTG04k64bFLonxgG9oxMkv1VZc96BwKBgHFoPKmGT+aH+Y8GO68UwPIQJPGYh19xU6KqKoJRjGcHP2+eSWgmDTvAi6I4FUt0d9ia9kt3E1dm0YMm2pRJ4/3V9bLRDBGpHfDxRaaq9sefjlL27N4mimWAW0FKytCssclR8d6EUqAeewQE9HSwrcY+kfaWlTU02aNaGgzkaO/5AoGAV7CWXsd+02FCzTTsgmwhIFTylltXQNjMca19rPXFukOBxZie9rrgkBOUj6CEvj4YV8n1Ah59Vbbzz0CnlrhtZagrJE0LEMKDpQhNKLv2AZvqMyyBLsPlUSgMz/xndPebhnje9CeZhGqo5R5ahNE8mIhLp+ZqqrlHTrj12MRlBrUCgYBbBWiHuNbQ3aR1e5nBi1zQdQPduv8y+uJiBdMeyQO1UahracwTZCIXvYL7ZIxvqhi81fMcwu3/65Aw+bOTHo1BanDSzICdOH1SSNzlzskfHmmXthIjSB2gsjryA2Smt8wBmZNDrkv0V0QsbqKXC5eGL5X2KRp5MK/c1RVdScvlOg==";
//    }
    public static String data = "hello world";
    public static String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhGq+VmOPBeSJ1BUHbnS1iiPVdrXsmqXg17qIiV/7dcff/OwbsXbbvz77yN50iZNNeiCG4Rj/TK5oTQgTjbLikAfEtPLROGHh5mH3eyefHYtuIETUXGstXm6+xMFHFvuH91yjOoVH0C55f6JpLhcy37/p3WzWGbvWyEVoYWs6VBt8bWYd/rqHadHpk+9II+/qe1S6I0a3qGdtOMpSucjSSo5EZd6ByVb5V5Vv3YEcF2NISc3gue0O2InhidEA5LfMNaMm+ZVmo6d+RaApnL8B8bcvZpn51l7d6lEZ/GbSaPeZGMQq5xZvdo3SFa92GmdX0OmnVS7CVGoHdLOnpkIerwIDAQAB";
    public static String privateKeyString = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCEar5WY48F5InUFQdudLWKI9V2teyapeDXuoiJX/t1x9/87Buxdtu/PvvI3nSJk016IIbhGP9MrmhNCBONsuKQB8S08tE4YeHmYfd7J58di24gRNRcay1ebr7EwUcW+4f3XKM6hUfQLnl/omkuFzLfv+ndbNYZu9bIRWhhazpUG3xtZh3+uodp0emT70gj7+p7VLojRreoZ204ylK5yNJKjkRl3oHJVvlXlW/dgRwXY0hJzeC57Q7YieGJ0QDkt8w1oyb5lWajp35FoCmcvwHxty9mmfnWXt3qURn8ZtJo95kYxCrnFm92jdIVr3YaZ1fQ6adVLsJUagd0s6emQh6vAgMBAAECggEAb0hZ/7YZy4T+RAsMPMq+ioKE8gf/+ROwuvwbpP/SD3DTj/ZJa8IM+VOQPIaff8MYmKtfTys32xSzuRExhaMxfoPYz41FQVIZjAkG+CwbL7Qu79WIdsbn0PXXQvl/qhPnd34V+6do688y8o3mQQLkEWByxVCjOes/nP2ftEduNKHIWCRASCtQJ5pGcgEOp3BipEXRzBPklVAbUUYTNTDXKzJxrubH90HTXjOuKXORDBk4UTiTI8cWdAg0KDxnpagAKBC9dmAloXajDzysfbgX1bhq9LlFqR10+A4YkzpTAbL0V+Xh7ZCTl25hhy+GaKpd/RSBF+ERb6o0XgVR9Elc0QKBgQDwReY1zT9ClDkYhRmGj/O95pWo/7Opkl5+p0y/NUL/pChHJ4J1GaiatXQTmFkUGa7J6CO9NTZ3zdJ7ZcAm95GyI7LXaaIfm35ZdN6fYiPSu7sys1amW7f3anF6GHKzRQRYUZgbhwPaGvS1HFUfnYDjl21CqtSbgW9BWmBwikosGQKBgQCNFZHDRtAxEkB4nSCwU68U1yfHEqz0mZSZDn0HugfeX0aiVe13/G0FHdsxI/Anou141/i8Qrb19Xn5aqTHwJNmvTlbXictbEmacHWOGoNvXSznkdG9fWE7BY4yguTLxLndD0fdjQasp1tKMTG04k64bFLonxgG9oxMkv1VZc96BwKBgHFoPKmGT+aH+Y8GO68UwPIQJPGYh19xU6KqKoJRjGcHP2+eSWgmDTvAi6I4FUt0d9ia9kt3E1dm0YMm2pRJ4/3V9bLRDBGpHfDxRaaq9sefjlL27N4mimWAW0FKytCssclR8d6EUqAeewQE9HSwrcY+kfaWlTU02aNaGgzkaO/5AoGAV7CWXsd+02FCzTTsgmwhIFTylltXQNjMca19rPXFukOBxZie9rrgkBOUj6CEvj4YV8n1Ah59Vbbzz0CnlrhtZagrJE0LEMKDpQhNKLv2AZvqMyyBLsPlUSgMz/xndPebhnje9CeZhGqo5R5ahNE8mIhLp+ZqqrlHTrj12MRlBrUCgYBbBWiHuNbQ3aR1e5nBi1zQdQPduv8y+uJiBdMeyQO1UahracwTZCIXvYL7ZIxvqhi81fMcwu3/65Aw+bOTHo1BanDSzICdOH1SSNzlzskfHmmXthIjSB2gsjryA2Smt8wBmZNDrkv0V0QsbqKXC5eGL5X2KRp5MK/c1RVdScvlOg==";

    public static void main(String[] args) throws Exception {


        //获取公钥
        PublicKey publicKey = getPublicKey(publicKeyString);

        //获取私钥
        PrivateKey privateKey = getPrivateKey(privateKeyString);

        //公钥加密
        byte[] encryptedBytes = encrypt(data.getBytes(), publicKey);


        System.out.println("加密后：" + new String(encryptedBytes));


        //私钥解密
        byte[] decryptedBytes = decrypt(encryptedBytes, privateKey);
        System.out.println("解密后：" + new String(decryptedBytes));
    }

    //将base64编码后的公钥字符串转成PublicKey实例
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //将base64编码后的私钥字符串转成PrivateKey实例
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    //公钥加密
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

}
