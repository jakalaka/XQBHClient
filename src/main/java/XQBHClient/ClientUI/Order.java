package XQBHClient.ClientUI;

public class Order {
    public static String QTLS_U;
    public static String QTRQ_U;
    public static String SPMC_U;
    public static double JYJE_U;
    public static String QRCODE;
    public static boolean finalOut=true;
    public static boolean callFail;
    public static boolean outFail;

    public static String controllerIP;
    public static int controllerPort;
    public static int controllerAdress;
    public static void Init(){
        SPMC_U="";
        JYJE_U=0;
        QRCODE="";
        finalOut=true;
        callFail=false;
        outFail=false;
        controllerIP="";
        controllerPort=9999;
        controllerAdress=9999;
    }
}
