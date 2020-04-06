import javafx.application.Platform;

public class Animation extends Thread{
    static int caseAorB = 0;
    static int j = 0;
    private static int jj = 1;
    private int[] num ;
    private MyPanel myPanel;
    private MyFrame myFrame;

    Animation (MyPanel myPanel,int[] num,MyFrame myFrame){
        this.myPanel = myPanel;
        this.num = num;
        this.myFrame = myFrame;
    }
    @Override
    public void run(){
        if (caseAorB == 1){
            auto();
        } else {
            getFlag();
            if (j == num.length - jj) {
                j = 0;
                jj++;
            }
            if (num[j] > num[j + 1]) {
                int t = num[j];
                num[j] = num[j + 1];
                num[j + 1] = t;
            }
            //System.out.println("j=" + j);
            Platform.runLater( () -> myPanel.reDrew( num,j++ ) );
            //System.out.println("end");
        }
    }
    private void auto(){
        for(int i = 0; i < num.length - 1; i++) {
            for (int j = 0;j < num.length - i - 1;j++) {
                if (num[j] > num[j + 1]) {
                    int t = num[j];
                    num[j] = num[j + 1];
                    num[j + 1] = t;
                }
            }
            getFlag();
            try {
                Thread.sleep( 500 );
            } catch (Exception e) {
                e.printStackTrace();
            }
            Platform.runLater( ()-> myPanel.reDrew( num,j++ ) );
        }
    }
    private void getFlag(){
        boolean flag = false;
        for (int i = 0;i < num.length - 1;i++) {
            if (num[i] > num[i + 1]) {
                flag = false;
                break;
            }
            flag = true;
        }
        //flag为true时表示数组已全部按升序排序
        if (flag) {
            for (int i = 0;i < 3;i++) {
                Platform.runLater( () -> myPanel.over() );
                over();
                Platform.runLater( () -> myPanel.reDrew( num,- 2 ) );
                over();
                Platform.runLater( () -> myPanel.over() );
                over();
                Platform.runLater( () -> myPanel.reDrew( num,- 2 ) );
            }

            Platform.runLater( () -> myFrame.button_1.setText( "自动" ) );
            //结束进程
            this.stop();
        }
    }
    private void over(){
        try {
            Thread.sleep( 450 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
