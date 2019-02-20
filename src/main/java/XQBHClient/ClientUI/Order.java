package XQBHClient.ClientUI;

public class Order {
    public static String HTLS_U;
    public static String HTRQ_U;
    public static String SPMC_U;
    public static double JYJE_U;
    public static String QRCODE;
    public static boolean finalOut=true;
    public static String callStatus;
    public static boolean outFail;

    /*ÆúÓÃ
    public static String controllerIP;
    public static int controllerPort;
    public static int controllerAdress;
    */
    public static String controllerCOMName;
    public static int positionX;
    public static int positionY;
    public static int positionZ;


    public static String position;
    public static String ZFFS_U;




    public static void Init(){
        SPMC_U="";
        position="";
        JYJE_U=0;
        QRCODE="";
        finalOut=true;
        callStatus ="FAIL";
        outFail=false;
        /*ÆúÓÃ
        controllerIP="";
        controllerPort=9999;
        controllerAdress=9999;
        */
        ZFFS_U="";

    }
}
