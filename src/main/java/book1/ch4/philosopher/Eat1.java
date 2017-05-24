package book1.ch4.philosopher;

/**
 * Author by darcy
 * Date on 17-5-24 上午10:32.
 * Description:
 */
class kuai{

    String name;
    boolean Enable = true;
    public kuai(String name)
    {
        this.name = name;
    }
    public synchronized void pickup()
    {

        this.Enable =false;
    }
    public synchronized void putdown()
    {
        this.Enable =true;
        this.notifyAll();
    }
}
class Philosopher2 extends Thread
{
    String name;
    kuai left;
    kuai right;
    public Philosopher2(String name, kuai l, kuai r)
    {
        this.name = name;
        left = l;
        right = r;
    }
    public void run()
    {
        if(left.Enable)
        {
            left.pickup();
        }
        else
        {
            while(!left.Enable)
            {
                try
                {
                    System.out.println(name + " 在等待 "+left.name);
                    Thread.sleep(500);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(name + " 眼明手快，以迅雷不及掩耳之势一把抓起 "+left.name);

        try
        {
            Thread.sleep(500);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        if(right.Enable)
        {
            right.pickup();
        }
        else
        {
            while (!left.Enable)
            {
                try
                {
                    System.out.println(name + "在等待 "+right.name);
                    Thread.sleep(500);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

        }

        System.out.println(name + " 眼明手快，以迅雷不及掩耳之势一把抓起 "+right.name);
        System.out.println(name + " 左右开弓，狼吞虎咽起来");

        try
        {
            Thread.sleep(2000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(name + " 酒足饭饱，打了个饱嗝，心满意足的放下了 "+left.name+" 和 " +right.name);
        left.putdown();
        right.putdown();
    }
}
public class Eat1
{
    public static void main(String args[])
    {
        kuai k1 = new kuai("Fork1");
        kuai k2 = new kuai("Fork2");
        kuai k3 = new kuai("Fork3");
        kuai k4 = new kuai("Fork4");
        kuai k5 = new kuai("Fork5");
        Philosopher2 p1 = new Philosopher2("A", k1, k2);
        Philosopher2 p2 = new Philosopher2("B", k2, k3);
        Philosopher2 p3 = new Philosopher2("C", k3, k4);
        Philosopher2 p4 = new Philosopher2("D", k4, k5);
        Philosopher2 p5 = new Philosopher2("E", k5, k1);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}
