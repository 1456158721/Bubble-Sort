import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

class MyPanel extends Pane {
    private int[] num;

    void reDrew (int[] num,int j){
        this.num = num;
        this.getChildren().clear();
        this.drew(j);
    }
    void over () {
        this.getChildren().clear();
    }
    private void drew (int j){
        for (int i = 0;i<num.length;i++){
            double w = this.getWidth()/20;
            double h = num[i] * 5;
            Rectangle rectangle = new Rectangle(w, h);
            rectangle.setX(i * w);
            rectangle.setY(this.getHeight() - h);
            Random random = new Random();
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);
            rectangle.setFill(Color.rgb( r, g, b ));
            rectangle.setStroke( Color.WHITE );
            if (j==i-1 || j==i){
                //System.out.println(j);
                rectangle.setFill( Color.BLUE );
            }
            Label label = new Label( num[i] +"" );
            label.setLayoutX( i * w + 4);
            label.setLayoutY( this.getHeight() - h - 20 );
            this.getChildren().addAll( rectangle , label);
        }
    }
}
