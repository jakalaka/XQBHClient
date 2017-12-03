package XQBHClient.Utils.RSA;

public class test {
    public static void main(String[] args) {
        String privateKey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCbVK4RG3Y2S1CYyy8C4SdlQtNUfvtmSrj/duDoBtXHG6K8x3unQuy7tdkqfohjMNHB6FTYZgZL+uOb0SPbz1aOemW8Kba+FnMCB+/pIK5s6gmAAOnGrgLHsFJcRw/qXqQTzuS6zr9+cds03ScC0nupFAOJD/x4TTbj9Me1EaBDzbdlapDgaCS4zNPjufptksknjcVKhMSaFrxwVfztb64Uia0Kz4YlWfT56FiPOMprM3BLWnGL4cxrkLAx0Vfc/QejaAwaFynF3h4+cbw8pCglRLDtwZSuR6IuuVpOhJ0OeTDjFbsT+uggXTu4L2L5GL57l9JJel4sS507LbPjirkFAgMBAAECggEAciGETLma4EXykHIqtrjtE3BIiztKSVz6FWsp0hLDnSrnJkKEzt9TxpDVYm3alNdYKFwni2FbmxRbjS/cYzQzazjv65PS3FTuaD2gv/DteMV1+YGyQ/OzwzoBKiBkx1aGxK8uTTyAY84QvQUKFv7FMfSsrFb8sQA7Jr/CxX6P/YDS4p312WB3LnYCZeGKTgaY8+TK2vh43IWPg0ZYdnwfNfzKhP5XjUu+AdlILW52DlKT0B9HX9kpO6f3poO5jsmTL1wzTpnNhcgI6j/6QNldppfTilrmvBhc+Mw9e2XH0wfLWzyv7+WkwgTd1W5K8eeMyZ9z4lprA3wLA8JGDTkWeQKBgQDNvs1lTHDW75x1Ec595lck8mFpROAY97Nj+00ffVc0CdXdYpG7QuCv7A6H714S47wLb+FAjy2d4vkRZ18KgMZjV9u1mcBfHTNoZ8AiBgpe/37zhsjn47xjZ84rmlceCYJ6zcGBvSsdHYWdLigYuX8qU8Stgpk6Hd2vlieS0tOA9wKBgQDBRXhuqYxmtCQK7gLrat8EHFj9rj9KOWZlmu4HolIYnXlh8OZQywAzV6pyX6mIj55BV0MuP6IGuI9aEe62m/BfEnyOPlj1w/PLdrg2kjvt9SnFLHVFpm4QVqdbG3ddWjeyBCWFAgTxERo+YHRbVXqlG7aIuVJDkl+wS7xMNwIS4wKBgF3QYaGOAGnRKhsremdn71Z2erpgYHqh/5dYUCtKoMemsHDY8DBd50vwLBQJrnCYjPb+Fcy1PIygoQcxhmGopE1KE3Gz4Ma97uHtZOteq73zbDSowdUSv6ToAVdacdzNY0SRq2l4Ez+cZX9tO3VCp9xqA+VGBo48bggRRIwrO6uzAoGASI4m7wxRfADmgv/lMrG1NVrbAakXc2rnCP/HmjASt9DuT5lbLTt4QM4JW9ST1YNs+sSioMscrX89uad/wSf7bgWHovK+/MxqSpiwATSWsPdXjgDpqZMYQKP3QdmmzvHedMLfvvYKGuG7w2z8YYlOeItV1DAWNO3PZGSaNNZ1vdcCgYBOB6EGsyiWyCvH3rnM3ye317ZG5ngKRHYsXqZKXQ1M4Bmh3KRNIgLQamTHSycgF0I70W4bHwjdshlIVwDcK7CbS0WCXH+pncXp1d1c36rm6pXLOM3Cz1b4t/3qrQPKU3/METkgEftiiDXLA3Sai9MkE8xHcurCYKwD5/Wm3yi3Og==";
        String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm1SuERt2NktQmMsvAuEnZULTVH77Zkq4/3bg6AbVxxuivMd7p0Lsu7XZKn6IYzDRwehU2GYGS/rjm9Ej289WjnplvCm2vhZzAgfv6SCubOoJgADpxq4Cx7BSXEcP6l6kE87kus6/fnHbNN0nAtJ7qRQDiQ/8eE024/THtRGgQ823ZWqQ4GgkuMzT47n6bZLJJ43FSoTEmha8cFX87W+uFImtCs+GJVn0+ehYjzjKazNwS1pxi+HMa5CwMdFX3P0Ho2gMGhcpxd4ePnG8PKQoJUSw7cGUrkeiLrlaToSdDnkw4xW7E/roIF07uC9i+Ri+e5fSSXpeLEudOy2z44q5BQIDAQAB";
        System.out.println("---------------私钥签名过程------------------");
        String content="xxxb";
        String signstr=RSASignature.sign(content,privateKey);
        System.out.println("签名原串："+content);
        System.out.println("签名串："+signstr);
        System.out.println();

        System.out.println("---------------公钥校验签名------------------");
        System.out.println("签名原串："+content);
        System.out.println("签名串："+signstr);

        System.out.println("验签结果："+RSASignature.doCheck(content, signstr,publicKey));
        System.out.println();
    }
}
