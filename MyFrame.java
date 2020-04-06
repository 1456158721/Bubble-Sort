import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class MyFrame extends Application {
    private int auto = 0;
    Button button_1;

    public void start (Stage primaryStage) {
        int[] num = new int[20];
        Random r = new Random( );
        for(int i = 0; i < num.length; i++){
            num[i] = r.nextInt(20) + 1;
        }
        MyPanel myPanel = new MyPanel( );
        BorderPane borderPane = new BorderPane();
        //盒子
        HBox hBox = new HBox();
        Object obj = new Object();
        AtomicReference<Animation> a = new AtomicReference<>( new Animation( myPanel,num,this ) );
        button_1 = new Button( "自动" );
        button_1.setOnAction( event -> {
            //System.out.println("尝试自动执行线程");
            if (auto==0){
                button_1.setText( "暂停" );
                auto = 1;
                try {
                    Animation.caseAorB = 1;
                    a.get().start();
                } catch (Exception e) {
                    synchronized (obj) {
                        obj.notify();
                    }
                }
            } else {
                button_1.setText( "继续" );
                auto = 0;
                try {
                    synchronized (obj){
                        obj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } );
        Button button_2 = new Button( "下一步" );
        button_2.setOnAction( event -> {
            Animation.caseAorB = 0;
            //System.out.println("尝试启动线程");
            a.set( new Animation( myPanel,num,this ) );
            a.get().start();
        } );
        Button button_3 = new Button( "重置" );
        button_3.setOnAction( event -> {
            //System.out.println("尝试重置数组并杀死线程");
            for(int i = 0; i < num.length; i++){
                num[i] = r.nextInt(20) + 1;
            }
            Animation.j = 0;
            auto = 0;
            button_1.setText( "自动" );
            a.get().stop();
            myPanel.reDrew(num,-2);
        } );
        //对齐
        hBox.setAlignment( Pos.CENTER );
        hBox.getChildren().addAll( button_1,button_2,button_3 );
        borderPane.setBottom( hBox );
        borderPane.setCenter( myPanel );
        Scene s = new Scene( borderPane,400,200 );
        primaryStage.setTitle( "冒泡排序" );
        primaryStage.setScene( s );
        primaryStage.show();
        myPanel.reDrew(num,-2);
    }
}
