package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.ClientUI.Unit.CommunicateActionUIController;
import XQBHClient.ClientUI.Unit.OrderDialogController;
import XQBHClient.ClientUI.Unit.ScanActionUIController;
import XQBHClient.Utils.QRReader.QRReader;
import XQBHClient.Utils.log.Logger;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ScanAction {

    private QRReader qrReader;
    public ScanAction() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CommunicateActionUIController.class.getResource("ScanActionUI.fxml"));

        try {
            loader.load(); //加载二维码扫描界面
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);

        ScanActionUIController.stage = new Stage(StageStyle.UNDECORATED);
        ScanActionUIController.stage.initModality(Modality.WINDOW_MODAL);
        ScanActionUIController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        ScanActionUIController.stage.initStyle(StageStyle.TRANSPARENT);
        ScanActionUIController.stage.initOwner(OrderDialogController.orderDialogstage);
        ScanActionUIController.stage.setAlwaysOnTop(true);

    }

    public void show() {
        /*扫描设备的初始化检查*/
        qrReader = new QRReader(Com.QRReaderComName, 9600, 8, 1, 0);
        boolean initOK = false;
        try {
            initOK = qrReader.Init();
        } catch (PortInUseException e) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader异常,请联系管理员!!!");
            e.printStackTrace();
        } catch (IOException e) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader读写错误!!!请联系管理员");
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader操作指令错误!!!请联系管理员");
            e.printStackTrace();
        } catch (NoSuchPortException e) {
            WarmingAction.show(WarmingAction.Dialog_ERR, "QRReader端口miss!!!请联系管理员");
            e.printStackTrace();
        }
        ScanActionUIController.stage.show();
        Logger.log("LOG_DEBUG","Scanning show");
    }
    public String read(){
        return qrReader.getQRCode();
    }

    public void close() {
        ScanActionUIController.stage.close();
        Logger.log("LOG_DEBUG","Scanning close");
    }
}
