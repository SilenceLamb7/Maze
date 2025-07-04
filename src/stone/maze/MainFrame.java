package stone.maze;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener, ActionListener {
    int[][] datas=new int[4][4];
    int[][] data= {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
    int x0=0,y0=0;
    int n=0;

    int score=1000;

    int winFlag=0;
    JMenuItem item1=new JMenuItem("鲨兔");
    JMenuItem item2=new JMenuItem("暗黑");

    String theme="B-";
    String form=".png";
    public MainFrame(){
        initFrame();
        initData();
        initMenu();
        printView();
        setVisible(true);//可视化
    }

    public void initMenu() {
        JMenuBar menuBar =new JMenuBar();
        //栏目对象
        JMenu menu=new JMenu("换肤");
        Font font = new Font("微软雅黑", Font.PLAIN, 24);

        //

        menuBar.add(menu);
        menu.setFont(font);

        menu.add(item1);
        menu.add(item2);

        item1.setFont(font);
        item2.setFont(font);

        item1.addActionListener(this);
        item2.addActionListener(this);
        setJMenuBar(menuBar);

    }

    public void initData() {
        int[] nums={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r=new Random();
        for (int i = 0; i < nums.length; i++) {
            int index=r.nextInt(nums.length);
            int temp=nums[i];
            nums[i]=nums[index];
            nums[index]=temp;
        }
        for (int i = 0; i < nums.length; i++) {
            datas[i/4][i%4]=nums[i];
            if (nums[i]==0){
                x0 = i / 4;
                y0 = i % 4;
            }
        }
    }

    public void initFrame(){//初始化窗体
        //setSize(514,538);//调用成员方法，设置窗体可见
        setSize(540,600);
        setLocationRelativeTo(null);  // 窗口居中显示
        setAlwaysOnTop(true);//置顶
        setDefaultCloseOperation(3);//窗体关闭终止java程序
        setTitle("Maze");
        setLayout(null);//取消默认布局、

        this.addKeyListener(this);//为窗体添加键盘监听
    }
    public void printView(){//绘制游戏界面
        //移除界面内容
        getContentPane().removeAll();
        if (winFlag==1){
            JLabel winLabel =new JLabel(new ImageIcon("./image/win.png"));
            winLabel.setBounds(40,40,420,420);
            getContentPane().add(winLabel);//把JLabel对象添加到面板
        }
        score=100-n;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel image =new JLabel(new ImageIcon("./image/"+theme+datas[i][j]+form));
                image.setBounds(50+100*j,50+100*i,100,100);
                super.getContentPane().add(image);//把JLabel对象添加到面板
            }
        }
        //添加背景
        JLabel image =new JLabel(new ImageIcon("./image/background"+form));
        image.setBounds(40,40,420,420);
        getContentPane().add(image);//把JLabel对象添加到面板
        //调用父类的方法，并且若子类未改写，不用写super就可

        JLabel back =new JLabel("得分"+score);
        back.setBounds(50,10,500,30);
        back.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        getContentPane().add(back);//把JLabel对象添加到面板
        System.out.println();
//        刷新界面
        getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code =e.getKeyCode();
//        System.out.println(code);//左上右下37 38 39 40
        if (winFlag==1){
            return;
        }
        else if (code==37){
            if (y0==0){
                return;
            }
            else{
            leftMove();
            n++;
            //System.out.println("左");
            }
        }
        else if (code==38){
            if (x0==0){
                return;
            }
            else{
            topMove();
                n++;
            //System.out.println("上");
            }
        }
        else if(code==39){
            if (y0==3){
                return;
            }
            else{
            rightMove();
                n++;
            //System.out.println("右");
            }
        }
        else if(code==40){
            if (x0==3){
                return;
            }
            else{
            downMove();
                n++;
            //System.out.println("下");
            }
        }
        else if(code==55){
            setToVictoryState();
        }
        printView();//重新绘制
    }

    public void downMove() {
        datas[x0][y0]=datas[x0+1][y0];
        datas[x0+1][y0]=0;
        x0++;
    }

    public void leftMove() {
        datas[x0][y0]=datas[x0][y0-1];
        datas[x0][y0-1]=0;
        y0--;
    }

    public void topMove() {
        datas[x0][y0]=datas[x0-1][y0];
        datas[x0-1][y0]=0;
        x0--;
    }

    public void rightMove() {
        datas[x0][y0]=datas[x0][y0+1];
        datas[x0][y0+1]=0;
        y0++;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==item1){
            System.out.println("鲨兔");
            theme="A-";
            form=".jpg";
        }
        else if (e.getSource()==item2){
            System.out.println("暗黑");
            theme="B-";
            form=".png";
        }
        printView(); // 重新绘制界面以应用新皮肤
    }
    public void victory(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (datas[i][j]!=data[i][j]){
                    winFlag=0;
                }
            }
        }
        winFlag=1;
    }
    private void setToVictoryState() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                datas[i][j] = data[i][j];
            }
        }
        winFlag=1;
    }
}
