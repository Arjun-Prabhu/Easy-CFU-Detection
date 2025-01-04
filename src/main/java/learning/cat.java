package learning;

public class cat extends animal{
    @Override
    public void makeNoise(){
        System.out.println("meow");
    }
    public void attack(){
        System.out.println("scratch");
    }
}
