package Algorithm;

public class ChildThread extends Thread{

    @Override
    public void run(){

        System.out.println(this.getName());
    }
}
