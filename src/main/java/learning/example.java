package learning;

public class example {
    static animal dog = new dog();
    public static void main(String[] args){
        doAnimalStuff(dog);
    }
    public static void doAnimalStuff(animal animal){
        animal.makeNoise();
        if (animal instanceof dog){
            dog dog1 = (learning.dog) animal;
            dog1.attack();
        }
    }
    public static int convertToHex(int input){
        int power = 0;
        String hexEquiv = "";
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 15; i++) {
                int val = 16;
                try{
                    val = (int) ((input % Math.pow(16,power))/15);
                } catch (Exception e){
                }
                if (val <= 15){
                    hexEquiv = hexEquiv + val;
                }
            }
            power++;
        }
        return Integer.parseInt(hexEquiv);
    }

}
