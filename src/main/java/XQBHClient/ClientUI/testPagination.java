package XQBHClient.ClientUI;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;


public class testPagination extends Application{
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("testPagination.fxml"));
//        primaryStage.setTitle("新奇百货");
//        Scene scene=new Scene(root);
//        primaryStage.setMaximized(true);
//        primaryStage.setScene(scene);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.show();
//
//    }
@Override
public void start(Stage primaryStage) {
    Group group = new Group();

    Scene scene = new Scene(group, 640, 480);
    primaryStage.setScene(scene);
    primaryStage.setTitle("JavaFX之Pagination的使用");
    primaryStage.show();


    Pagination mPagination = new Pagination(5, 0);
    mPagination.setPageFactory(new Callback<Integer, Node>() {

        @Override
        public Node call(Integer param) {

            ImageView imageView=new ImageView();

            Image image=new Image("file:resources/Model_/购买/生活用品/纸巾/img0.png");
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(400);
            return imageView;
        }
    });
//    Pagination mPagination = new Pagination(5, 0);
//    mPagination.setPageFactory(new Callback<Integer, Node>() {
//
//        @Override
//        public Node call(Integer param) {
//            Label mLabel = new Label();
//            mLabel.setText("这是第" + param + "页");
//            return mLabel;
//        }
//    });


//    Pagination mPagination2 = PaginationBuilder.create().pageCount(5).currentPageIndex(0).pageFactory(new Callback<Integer, Node>() {
//        @Override
//        public Node call(Integer param) {
//            Button mbtn = new Button();
//            mbtn.setText("这是第" + param + "页");
//            return mbtn;
//        }
//    }).build();

    mPagination.setLayoutX(50);
    mPagination.setLayoutY(50);

//    mPagination2.setLayoutX(50);
//    mPagination2.setLayoutY(250);

    group.getChildren().add(mPagination);
//    group.getChildren().add(mPagination2);


}

    public static void main(String[] args) {
        launch(args);
    }
}
